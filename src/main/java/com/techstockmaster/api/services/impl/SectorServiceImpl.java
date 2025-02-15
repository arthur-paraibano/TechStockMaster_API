package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.SectorDto;
import com.techstockmaster.api.controllers.dtos.SectorLocacaoDto;
import com.techstockmaster.api.controllers.dtos.SectorSupervisorDto;
import com.techstockmaster.api.domain.models.SectorModel;
import com.techstockmaster.api.domain.models.SupervisorModel;
import com.techstockmaster.api.domain.repositories.SectorRepository;
import com.techstockmaster.api.domain.repositories.SupervisorRepository;
import com.techstockmaster.api.services.SectorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private final SectorRepository sectorRepository;

    @Autowired
    private final SupervisorRepository supervisorRepository;

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
        if (!locacao.matches("^(D'Padua|Pro-fé|D'Padua / Pro-fé)$")) {
            throw new IllegalArgumentException("Locação inválida! Deve ser D'Padua, Pro-fé ou D'Padua / Pro-fé.");
        }

        SectorModel modal = new SectorModel();
        modal.setName(dto.setor().toUpperCase());
        modal.setName(dto.locacao());

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

    @Override
    public SectorModel updateSupervisor(Integer id, SectorSupervisorDto dto) {
        if (!sectorRepository.existsById(id)) {
            throw new DataIntegrityViolationException("O Setor não existe!");
        }
        if (!supervisorRepository.existsById(dto.idSupervisor())) {
            throw new DataIntegrityViolationException("O Supervisor não existe!");
        }

        SectorModel modal = sectorRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException("Sector não encontrado!"));
        if (modal.getSupervisor().getId().equals(dto.idSupervisor())) {
            throw new DataIntegrityViolationException("O Supervisor já é responsável por este setor!");
        }

        SupervisorModel user = supervisorRepository.findById(dto.idSupervisor())
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado"));
        modal.setSupervisor(user);
        sectorRepository.save(modal);
        return modal;
    }

    @Override
    public SectorModel updateLocacao(Integer id, SectorLocacaoDto dto) {
        if (!sectorRepository.existsById(id)) {
            throw new DataIntegrityViolationException("O Setor não existe!");
        }
        String locacao = dto.locacao();
        if (!locacao.matches("^(D'Padua|Pro-fé|D'Padua / Pro-fé)$")) {
            throw new IllegalArgumentException("Locação inválida! Deve ser D'Padua, Pro-fé ou D'Padua / Pro-fé.");
        }

        SectorModel modal = sectorRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException("Sector não encontrado!"));
        if (modal.getRetal().equals(locacao)) {
            throw new IllegalArgumentException("Setor já faz parte desta locação!");
        }

        modal.setRetal(dto.locacao());
        sectorRepository.save(modal);
        return modal;
    }

}