package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.ICategoryGateway;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase{

    private final ICategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(ICategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(CreateCategoryCommand command) {

        final var notification = Notification.create();

        var category = Category.newCategory(
                command.name(),
                command.description(),
                command.isActive());

        category.validate(notification);

        return notification.hasErrors() ? Left(notification) : create(category);
    }

    private Either<Notification, CreateCategoryOutput> create(Category category) {
        return Try(() -> this.categoryGateway.create(category))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
