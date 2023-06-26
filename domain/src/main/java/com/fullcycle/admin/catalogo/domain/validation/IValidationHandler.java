package com.fullcycle.admin.catalogo.domain.validation;

import java.util.List;

public interface IValidationHandler {
    IValidationHandler append(Error error);

    IValidationHandler append(IValidationHandler handler);

    <T> T validate(IValidation<T> validation);

    List<Error> getErrors();

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError(){
        if(getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().get(0);
        } else {
            return null;
        }
    }

    interface IValidation<T> {
        T validate();
    }
}
