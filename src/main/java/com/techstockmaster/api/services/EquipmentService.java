package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.EquipmentDto;
import com.techstockmaster.api.domain.models.EquipmentModel;

public interface EquipmentService extends CrudService<EquipmentModel, Integer, EquipmentDto> {
}
