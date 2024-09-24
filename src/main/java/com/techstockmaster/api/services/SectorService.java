package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.SectorDto;
import com.techstockmaster.api.domain.models.SectorModel;

public interface SectorService extends CrudService<SectorModel, Integer, SectorDto> {
}
