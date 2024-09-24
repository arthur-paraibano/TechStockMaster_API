package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SectorDto(
        @NotBlank(message = "O setor não pode ser branco")
        @NotBlank(message = "Informe o nome do Setor") String setor,
        @NotBlank(message = "A descrição da mensagem não deve estar em branco")
        @NotBlank(message = "Informe o nome do Setor") String locacao,
        @NotNull(message = "O ID do Supervisor não deve estar em branco") Integer idSupervisor
) {
}
