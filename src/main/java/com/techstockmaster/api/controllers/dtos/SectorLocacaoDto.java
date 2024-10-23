package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotNull;

public record SectorLocacaoDto(
        @NotNull(message = "Informe o nome do Setor") String locacao
) {
}
