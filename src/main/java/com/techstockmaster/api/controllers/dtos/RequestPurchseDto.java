package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotNull;

public record RequestPurchseDto(
        @NotNull(message = "Informe o Id do Equipamento") Integer idEquipamento,
        @NotNull(message = "Informe o a quantidade") Double quantidade,
        @NotNull(message = "Informe o setor") Integer idSector,
        @NotNull(message = "Informe o Id do Usuário") Integer idUsuario,
        String descricao
) {
}
