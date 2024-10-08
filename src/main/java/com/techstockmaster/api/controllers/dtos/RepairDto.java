package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RepairDto(
        @NotNull(message = "Informe o Id do Equipamento") Integer idEquipamento,
        @NotNull(message = "Informe o Id do Usuário") Integer idUsuario,
        String descricao,
        @NotBlank(message = "Informe o Status")
        @Pattern(regexp = "(?i)AGUARDANDO ENVIO|EM CONSERTO", message = "O tipo deve ser AGUARDANDO ENVIO ou EM CONSERTO") String status
) {
}
