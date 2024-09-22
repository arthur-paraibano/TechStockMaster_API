package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;

/*
 * Classe responsável pro mostrar possíveis erros da entidade UserModal.
 */
public record SupervisorDto(
        @NotBlank(message = "O nome não deve estar em branco") String name) {
}
