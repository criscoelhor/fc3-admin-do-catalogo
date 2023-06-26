package com.fullcycle.admin.catalogo.application.category.retrieve.get;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.ICategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.NotFoundException;

import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final ICategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(ICategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public CategoryOutput execute(final String id) {
        final var categoryID = CategoryID.from(id);

        return this.categoryGateway.findById(categoryID)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(categoryID));
    }

    private Supplier<NotFoundException> notFound(final CategoryID id) {
        return () -> NotFoundException.with(Category.class, id);
    }
}
