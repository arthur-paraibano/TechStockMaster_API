package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.EquipmentDto;
import com.techstockmaster.api.domain.models.EquipmentModal;

public interface EquipmentService extends CrudService<EquipmentModal, Integer, EquipmentDto> {
}
