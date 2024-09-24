package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.SectorDto;
import com.techstockmaster.api.domain.models.SectorModal;

public interface SectorService extends CrudService<SectorModal, Integer, SectorDto> {
}
