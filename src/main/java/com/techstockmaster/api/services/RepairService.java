package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.RepairDto;
import com.techstockmaster.api.domain.models.RepairModel;

public interface RepairService extends CrudService<RepairModel, Integer, RepairDto> {
}
