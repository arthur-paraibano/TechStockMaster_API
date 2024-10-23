package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotNull;

public record SectorSupervisorDto(
        @NotNull(message = "Informe o Id do Supervisor") Integer idSupervisor
) {
}
