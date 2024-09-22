package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.SupervisorDto;
import com.techstockmaster.api.domain.models.SupervisorModel;

import java.util.List;

public interface CrudService <T, ID, DTO> {
    List<T> findAll();
    T findById(ID id);
    T create(DTO entity);
    T delete(ID id);
    T update(ID id, DTO entity);
}
