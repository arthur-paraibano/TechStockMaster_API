package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserBlockedDto(
        @NotBlank(message = "O ID do Solicitante não deve estar em branco")
        @NotNull(message = "ID do Solicitante não pode ser Nulo") Integer idUserRequester
) {
}
