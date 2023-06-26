package com.fullcycle.admin.catalogo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {
    protected AggregateRoot(final ID id) {
        super(id);
    }
}
