package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotNull;

public record RequestPurchseUpdateDto(
        @NotNull(message = "Informe o status") String status
) {
}
