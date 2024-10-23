package com.techstockmaster.api.services;

import com.techstockmaster.api.controllers.dtos.PurchaseRequestDto;
import com.techstockmaster.api.domain.models.PurchaseRequestModel;

public interface PurchaseRequestService extends CrudService<PurchaseRequestModel, Integer, PurchaseRequestDto> {
}
