package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.OnMessage;

public record EquipmentDto(
        @NotBlank(message = "Informe o Id do equipamento deseja cadastrar") Integer id,
        String descricao,
        @NotBlank(message = "Deve ser informado se é um equipamento: 0- Não | 1- Sim") Integer equipCheck,
        String status,
        Integer idSector,
        Integer tag
) {
}
