package com.fullcycle.admin.catalogo.domain;

import com.fullcycle.admin.catalogo.domain.validation.IValidationHandler;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode
public abstract class Entity<ID extends Identifier> {
    protected final ID id;

    protected Entity(final ID id) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
    }

    public abstract void validate(IValidationHandler handler);

    public ID getId(){
        return id;
    }
}
