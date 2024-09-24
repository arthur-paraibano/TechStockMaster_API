package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.SectorDto;
import com.techstockmaster.api.domain.models.SectorModel;
import com.techstockmaster.api.domain.models.SupervisorModel;
import com.techstockmaster.api.domain.repositories.SectorRepository;
import com.techstockmaster.api.domain.repositories.SupervisorRepository;
import com.techstockmaster.api.services.SectorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private final SectorRepository sectorRepository;

    @Autowired
    private final SupervisorRepository supervisorRepository;

    public SectorServiceImpl(SectorRepository sectorRepository, SupervisorRepository supervisorRepository) {
        this.sectorRepository = sectorRepository;
        this.supervisorRepository = supervisorRepository;
    }

    @Override
    public List<SectorModel> findAll() {
        return sectorRepository.findAll();
    }

    @Override
    public SectorModel findById(Integer id) {
        Optional<SectorModel> user = sectorRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public SectorModel create(SectorDto dto) {
        String locacao = dto.locacao();
        if (!locacao.matches("^(D'Padua|Pro-fé)$")) {
            throw new IllegalArgumentException("Locação inválida");
        }

        SectorModel modal = new SectorModel();
        modal.setName(dto.setor().toUpperCase());
        modal.setName(dto.locacao().toUpperCase());

        // Buscar usuário pelo ID
        SupervisorModel user = supervisorRepository.findById(dto.idSupervisor())
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado"));
        modal.setSupervisor(user);

        return sectorRepository.save(modal);
    }

    @Override
    public SectorModel delete(Integer integer) {
        return null;
    }

    @Override
    public SectorModel update(Integer integer, SectorDto entity) {
        return null;
    }
}