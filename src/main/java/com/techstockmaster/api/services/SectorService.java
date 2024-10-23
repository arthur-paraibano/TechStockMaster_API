package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.SectorDto;
import com.techstockmaster.api.controllers.dtos.SectorLocacaoDto;
import com.techstockmaster.api.controllers.dtos.SectorSupervisorDto;
import com.techstockmaster.api.domain.models.SectorModel;

public interface SectorService extends CrudService<SectorModel, Integer, SectorDto> {
    SectorModel updateSupervisor(Integer id, SectorSupervisorDto dto);

    SectorModel updateLocacao(Integer id, SectorLocacaoDto dto);
}
