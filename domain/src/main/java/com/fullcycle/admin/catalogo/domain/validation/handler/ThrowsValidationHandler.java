package com.fullcycle.admin.catalogo.domain.validation.handler;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.IValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements IValidationHandler {
    @Override
    public IValidationHandler append(final Error error) {
        throw DomainException.with(error);
    }

    @Override
    public IValidationHandler append(IValidationHandler handler) {
        throw DomainException.with(handler.getErrors());
    }

    @Override
    public <T> T validate(Validation<T> validation) {
        try{
            return validation.validate();
        } catch (final Exception ex) {
            throw DomainException.with(new Error(ex.getMessage()));
        }
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
