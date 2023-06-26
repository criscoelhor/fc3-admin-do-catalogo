package com.fullcycle.admin.catalogo.domain.validation;

public abstract class Validator {
    private final IValidationHandler handler;

    protected Validator(final IValidationHandler handler){
        this.handler = handler;
    }

    protected IValidationHandler validationHandler(){
        return this.handler;
    }

    public abstract void validate();
}
