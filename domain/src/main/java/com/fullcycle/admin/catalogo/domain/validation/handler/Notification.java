package com.fullcycle.admin.catalogo.domain.validation.handler;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.IValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements IValidationHandler {

    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create(){
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Throwable t){
        return create(new Error(t.getMessage()));
    }

    public static Notification create(final Error error){
        return new Notification(new ArrayList<>()).append(error);
    }

    @Override
    public Notification append(Error error) {
        this.errors.add(error);
        return this;
    }

    @Override
    public Notification append(IValidationHandler handler) {
        this.errors.addAll(handler.getErrors());
        return this;
    }

    @Override
    public <T> T validate(Validation<T> validation) {
        try{
            validation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }

        return null;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
