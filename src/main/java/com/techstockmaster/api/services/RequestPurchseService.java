package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.RequestPurchseDto;
import com.techstockmaster.api.controllers.dtos.RequestPurchseUpdateDto;
import com.techstockmaster.api.domain.models.RequestPurchseModel;

public interface RequestPurchseService extends CrudService<RequestPurchseModel, Integer, RequestPurchseDto>{
    RequestPurchseModel updateStatus(Integer id, RequestPurchseUpdateDto entity);
}
