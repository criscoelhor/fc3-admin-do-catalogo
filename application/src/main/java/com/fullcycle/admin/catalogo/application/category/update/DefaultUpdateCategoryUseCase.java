package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.category.ICategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Try;
import static io.vavr.control.Either.left;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final ICategoryGateway gateway;

    public DefaultUpdateCategoryUseCase(ICategoryGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(UpdateCategoryCommand updateCategoryCommand) {
        final var id = CategoryID.from(updateCategoryCommand.id());
        final var name = updateCategoryCommand.name();
        final var description = updateCategoryCommand.description();
        final var isActive = updateCategoryCommand.isActive();

        var category = gateway.findById(id)
                .orElseThrow(notFound(id));

        final var notification = Notification.create();

        category.update(name, description, isActive)
                .validate(notification);

        return notification.hasErrors() ? left(notification) : update(category);
    }

    private Either<Notification, UpdateCategoryOutput> update(Category category) {
        return Try(() -> gateway.update(category))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(CategoryID id){
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(id.getValue())));
    }
}
