package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.SectorDto;
import com.techstockmaster.api.domain.models.SectorModal;
import com.techstockmaster.api.domain.repositories.SectorRepository;
import com.techstockmaster.api.services.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private final SectorRepository sectorRepository;

    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<SectorModal> findAll() {
        return sectorRepository.findAll();
    }

    @Override
    public SectorModal findById(Integer id) {
        Optional<SectorModal> user = sectorRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public SectorModal create(SectorDto entity) {
        return null;
    }

    @Override
    public SectorModal delete(Integer integer) {
        return null;
    }

    @Override
    public SectorModal update(Integer integer, SectorDto entity) {
        return null;
    }
}