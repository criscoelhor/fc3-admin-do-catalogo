package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.application.UseCaseTest;
import com.fullcycle.admin.catalogo.domain.category.ICategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


public class CreateCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private ICategoryGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    //1. Teste caminho feliz
    //2. Teste passando prop inválida (name)
    //3. Teste criando uma categoria inativa
    //4. Teste simulando erro generico vindo do gateway

    @Test
    public void givenValidCommand_whenCallsCreateCategory_shouldReturnCategoryId(){
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria 1";
        final var expectedIsActive = true;

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(gateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(gateway, times(1)).create(argThat(
                category ->
                    Objects.equals(expectedName, category.getName())
                            && Objects.equals(expectedDescription, category.getDescription())
                            && Objects.equals(expectedIsActive, category.isActive())
                            && Objects.nonNull(category.getId())
                            && Objects.nonNull(category.getCreatedAt())
                            && Objects.nonNull(category.getUpdatedAt())
                            && Objects.isNull(category.getDeletedAt())


                ));
    }

    @Test
    public void givenInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException(){
        final String expectedName = null;
        final var expectedDescription = "Categoria 1";
        final var expectedIsActive = true;
        final String expectedErrorMessage = "'name' should not be null";

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var notification = useCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(gateway, times(0)).create(any());
    }

    @Test
    public void givenValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId(){
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria 1";
        final var expectedIsActive = false;

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(gateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(gateway, times(1))
                .create(argThat(category -> {
                            return Objects.equals(expectedName, category.getName())
                                    && Objects.equals(expectedDescription, category.getDescription())
                                    && Objects.equals(expectedIsActive, category.isActive())
                                    && Objects.nonNull(category.getId())
                                    && Objects.nonNull(category.getCreatedAt())
                                    && Objects.nonNull(category.getUpdatedAt())
                                    && Objects.nonNull(category.getDeletedAt());
                        }

                ));
    }
    @Test
    public void givenValidCommand_whengatewayThrowsRandomException_shouldReturnException(){
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria 1";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";

        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(gateway.create(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(gateway, times(1)).create(argThat(category ->
                            Objects.equals(expectedName, category.getName())
                                    && Objects.equals(expectedDescription, category.getDescription())
                                    && Objects.equals(expectedIsActive, category.isActive())
                                    && Objects.nonNull(category.getId())
                                    && Objects.nonNull(category.getCreatedAt())
                                    && Objects.nonNull(category.getUpdatedAt())
                                    && Objects.isNull(category.getDeletedAt())


                ));
    }
}
