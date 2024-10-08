package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/*
 * Classe responsável pro mostrar possíveis erros da entidade UserModal.
 */
public record TagDto(
        @NotBlank(message = "O nome não deve estar em branco")
        @Pattern(regexp = ".{3,3}", message = "O nome deve ter no mínimo e no máximo 3 caracteres")
        String abreviacao,

        @NotBlank(message = "A descrição não deve estar em branco") String descircao
) {
}