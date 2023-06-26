package com.fullcycle.admin.catalogo.application.category.delete;

import com.fullcycle.admin.catalogo.domain.category.ICategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final ICategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(final ICategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public void execute(final String anIn) {
        this.categoryGateway.deleteById(CategoryID.from(anIn));
    }
}
