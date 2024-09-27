package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.GeneralEquipmentDto;
import com.techstockmaster.api.domain.models.GeneralEquipmentModal;

import java.sql.SQLException;

public interface GeneralEquipmentService extends CrudService<GeneralEquipmentModal, Integer, GeneralEquipmentDto> {
    int executeIntegration() throws SQLException;
}
