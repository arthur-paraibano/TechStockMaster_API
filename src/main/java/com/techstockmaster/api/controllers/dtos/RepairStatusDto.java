package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RepairStatusDto(
        @NotBlank(message = "Informe o Status")
        @Pattern(regexp = "(?i)AGUARDANDO ENVIO|EM CONSERTO|RECEBIDO", message = "O tipo deve ser AGUARDANDO ENVIO, EM CONSERTO ou RECEBIDO") String status
) {
}
