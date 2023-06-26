package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.application.UseCaseTest;
import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.ICategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

public class UpdateCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private ICategoryGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    //1. Teste caminho feliz
    //2. Teste passando prop inválida (name)
    //3. Teste atualizando uma categoria para inativa
    //4. Teste simulando erro generico vindo do gateway
    //5. Teste atualizar categoria passando ID inválido.

    @Test
    public void givenValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var category = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria 1";
        final var expectedIsActive = true;

        final var id = category.getId();

        final var command = UpdateCategoryCommand.with(id.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(gateway.findById(eq(id)))
                .thenReturn(Optional.of(category.clone()));

        Mockito.when(gateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(gateway, times(1)).findById(eq(id));
        Mockito.verify(gateway, times(1)).update(argThat(
               updatedCategory ->
                    Objects.equals(expectedName, updatedCategory.getName())
                            && Objects.equals(expectedDescription, updatedCategory.getDescription())
                            && Objects.equals(expectedIsActive, updatedCategory.isActive())
                            && Objects.equals(id, updatedCategory.getId())
                            && Objects.equals(category.getUpdatedAt(), updatedCategory.getCreatedAt())
                            && category.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt())
                            && Objects.isNull(updatedCategory.getDeletedAt())
                    ));
    }

    @Test
    public void givenInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException(){
        final var category = Category.newCategory("Film", null, true);

        final String expectedName = null;
        final var expectedDescription = "Categoria 1";
        final var expectedIsActive = true;
        final String expectedErrorMessage = "'name' should not be null";

        final var id = category.getId();

        final var command = UpdateCategoryCommand.with(id.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(gateway.findById(eq(id)))
                .thenReturn(Optional.of(category.clone()));

        final var notification = useCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(gateway, times(0)).create(any());
    }


}
