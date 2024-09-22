package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/*
 * Classe responsável pro mostrar possíveis erros da entidade UserModal.
 */
public record UserPasswordDto(
        @NotBlank(message = "A senha não deve estar em branco")
        @Pattern(regexp = ".{8,}", message = "A senha deve ter no mínimo 8 dígitos") String password) {
}
