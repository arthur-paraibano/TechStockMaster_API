package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;

/*
 * Classe responsável pro mostrar possíveis erros da entidade UserModal.
 */
public record TagUpdateDto(
        @NotBlank(message = "A descrição não deve estar em branco") String descircao
) {
}