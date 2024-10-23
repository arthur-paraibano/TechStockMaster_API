package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.PurchaseRequestDto;
import com.techstockmaster.api.domain.models.PurchaseRequestModel;
import com.techstockmaster.api.domain.repositories.PurchaseRequestRepository;
import com.techstockmaster.api.services.PurchaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseRequestServiceImpl implements PurchaseRequestService {

    @Autowired
    private PurchaseRequestRepository repository;

    @Override
    public List<PurchaseRequestModel> findAll() {
        return repository.findAll();
    }

    @Override
    public PurchaseRequestModel findById(Integer id) {
        Optional<PurchaseRequestModel> repair = repository.findById(id);
        return repair.orElse(null);
    }

    @Override
    public PurchaseRequestModel create(PurchaseRequestDto entity) {
        return null;
    }

    @Override
    public PurchaseRequestModel delete(Integer integer) {
        return null;
    }

    @Override
    public PurchaseRequestModel update(Integer integer, PurchaseRequestDto entity) {
        return null;
    }
}
