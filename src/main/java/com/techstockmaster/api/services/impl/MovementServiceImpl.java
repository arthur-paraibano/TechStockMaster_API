package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.MovementDto;
import com.techstockmaster.api.domain.models.MovementModel;
import com.techstockmaster.api.domain.repositories.MovementRepository;
import com.techstockmaster.api.services.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private final MovementRepository repository;

    public MovementServiceImpl(MovementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MovementModel> findAll() {
        return repository.findAll();
    }

    @Override
    public MovementModel findById(Integer id) {
        Optional<MovementModel> movment = repository.findById(id);
        return movment.orElse(null);
    }

    @Override
    public MovementModel create(MovementDto entity) {
        return null;
    }

    @Override
    public MovementModel delete(Integer integer) {
        return null;
    }

    @Override
    public MovementModel update(Integer integer, MovementDto entity) {
        return null;
    }
}
