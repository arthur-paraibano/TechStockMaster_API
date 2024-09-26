package com.techstockmaster.api.controllers.dtos;

public record GeneralEquipmentDto(
        Integer id,
        String idKey,
        String codigo,
        String descricao,
        String abreviacaoUm,
        String descricaoUm
) {
}
