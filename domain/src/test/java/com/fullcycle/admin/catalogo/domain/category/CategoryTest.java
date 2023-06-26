package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenValidParameters_whenNewCategory_thenInstantiate(){
        final var name = "Ação";
        final var description = "Categoria mais assistida";
        final var active = true;

        final var validCategory = Category.newCategory(name, description, active);

        Assertions.assertNotNull(validCategory);
        Assertions.assertNotNull(validCategory.getId());
        Assertions.assertEquals(name, validCategory.getName());
        Assertions.assertEquals(description, validCategory.getDescription());
        Assertions.assertEquals(active, validCategory.isActive());
        Assertions.assertNotNull(validCategory.getCreatedAt());
        Assertions.assertNotNull(validCategory.getUpdatedAt());
        Assertions.assertNull(validCategory.getDeletedAt());
    }

    @Test
    public void givenIvalidNullName_whenNewCategory_thenShouldException(){
        final String name = null;
        final var description = "Categoria mais assistida";
        final var active = true;

        final var validCategory = Category.newCategory(name, description, active);

        final var exception = Assertions.assertThrows(DomainException.class, () -> validCategory.validate(new ThrowsValidationHandler()));

        final var expectedErrorMessageCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        Assertions.assertEquals(expectedErrorMessageCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenIvalidNameLengthLessThen3_whenNewCategory_thenShouldException(){
        final String name = "Fi ";
        final var description = "Categoria mais assistida";
        final var active = true;

        final var validCategory = Category.newCategory(name, description, active);

        final var exception = Assertions.assertThrows(DomainException.class, () -> validCategory.validate(new ThrowsValidationHandler()));

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characteres";

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenIvalidNameLengthGraterThen255_whenNewCategory_thenShouldException(){
        final String name = """
                 No entanto, não podemos esquecer que a execução dos pontos do programa cumpre um papel essencial na formulação das condições financeiras 
                 e administrativas exigidas. A certificação de metodologias que nos auxiliam a lidar com a percepção das dificuldades não pode mais 
                 se dissociar dos métodos utilizados na avaliação de resultados. Por outro lado, a constante divulgação das informações pode nos levar a 
                 considerar a reestruturação das posturas dos órgãos dirigentes com relação às suas atribuições.""";
        final var description = "Categoria mais assistida";
        final var active = true;

        final var validCategory = Category.newCategory(name, description, active);

        final var exception = Assertions.assertThrows(DomainException.class, () -> validCategory.validate(new ThrowsValidationHandler()));

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characteres";

        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    public void givenValidEmptyDescription_whenNewCategory_thenInstantiate(){
        final var name = "Ação";
        final var description = " ";
        final var active = true;

        final var validCategory = Category.newCategory(name, description, active);

        Assertions.assertDoesNotThrow(() -> validCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(validCategory);
        Assertions.assertNotNull(validCategory.getId());
        Assertions.assertEquals(name, validCategory.getName());
        Assertions.assertEquals(description, validCategory.getDescription());
        Assertions.assertEquals(active, validCategory.isActive());
        Assertions.assertNotNull(validCategory.getCreatedAt());
        Assertions.assertNotNull(validCategory.getUpdatedAt());
        Assertions.assertNull(validCategory.getDeletedAt());
    }

    @Test
    public void givenValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated(){
        final var name = "Ação";
        final var description = "A categoria mais assistida";
        final var active = true;

        final var validCategory = Category.newCategory(name, description, active);

        Assertions.assertDoesNotThrow(() -> validCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = validCategory.getCreatedAt();
        final var updatedAt = validCategory.getUpdatedAt();

        Assertions.assertNull(validCategory.getDeletedAt());
        Assertions.assertTrue(validCategory.isActive());

        final var actualCategory = validCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(validCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(name, actualCategory.getName());
        Assertions.assertEquals(description, actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenValidInactiveCategory_whenCallActivate_thenReturnCategoryActivated(){
        final var name = "Ação";
        final var description = "A categoria mais assistida";
        final var active = false;

        final var validCategory = Category.newCategory(name, description, active);

        Assertions.assertDoesNotThrow(() -> validCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = validCategory.getCreatedAt();
        final var updatedAt = validCategory.getUpdatedAt();

        Assertions.assertNotNull(validCategory.getDeletedAt());
        Assertions.assertFalse(validCategory.isActive());

        final var actualCategory = validCategory.activate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(validCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(name, actualCategory.getName());
        Assertions.assertEquals(description, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenValidCategory_whenCallUpdate_thenReturnCategoryUpdated(){
        final var name = "Ação";
        final var description = "A categoria mais assistida";
        final var active = true;

        final var validCategory = Category.newCategory("Policial", "A Categoria policial", active);

        Assertions.assertDoesNotThrow(() -> validCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = validCategory.getCreatedAt();
        final var updatedAt = validCategory.getUpdatedAt();

        final var actualCategory = validCategory.update(name, description, active);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(validCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(name, actualCategory.getName());
        Assertions.assertEquals(description, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenValidCategory_whenCallUpdateInactive_thenReturnCategoryUpdated(){
        final var name = "Ação";
        final var description = "A categoria mais assistida";
        final var active = false;

        final var validCategory = Category.newCategory("Policial", "A Categoria policial", true);

        Assertions.assertDoesNotThrow(() -> validCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = validCategory.getCreatedAt();
        final var updatedAt = validCategory.getUpdatedAt();

        final var actualCategory = validCategory.update(name, description, active);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(validCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(name, actualCategory.getName());
        Assertions.assertEquals(description, actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }
}
