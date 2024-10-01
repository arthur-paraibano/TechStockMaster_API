package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MovementDto(
        @NotBlank(message = "Informe o Id do Equipamento") Integer idEquipamento,
        @NotBlank(message = "Informe a quantidade") Double quantidade,
        String nLykos,
        @NotBlank(message = "Informe o Id do Usuário") Integer idUsuario,
        @NotBlank(message = "Informe o movimento")
        @Pattern(regexp = "(?i)IN|OUT", message = "O tipo deve ser IN (Entrada) ou OUT (Saída)") String type,
        @NotBlank(message = "Informe o Id do Setor") Integer idSetor
) {
}
