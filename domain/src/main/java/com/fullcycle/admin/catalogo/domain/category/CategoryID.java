package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.Identifier;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode
public class CategoryID extends Identifier {
    private final String value;

    public CategoryID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CategoryID unique(){
        return new CategoryID(UUID.randomUUID().toString().toLowerCase());
    }

    public static CategoryID from(final String id){
        return new CategoryID(id);
    }

    public static CategoryID from(final UUID id){
        return new CategoryID(id.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }
}
