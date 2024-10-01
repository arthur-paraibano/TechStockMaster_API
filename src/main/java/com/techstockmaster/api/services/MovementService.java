package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.MovementDto;
import com.techstockmaster.api.domain.models.MovementModel;

public interface MovementService extends CrudService<MovementModel, Integer, MovementDto> {
}
