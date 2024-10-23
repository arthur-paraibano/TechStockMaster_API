package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.EquipmentDto;
import com.techstockmaster.api.controllers.dtos.EquipmentUpdateDto;
import com.techstockmaster.api.domain.models.EquipmentModel;
import com.techstockmaster.api.domain.repositories.EquipmentRepository;
import com.techstockmaster.api.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceimpl implements EquipmentService {

    @Autowired
    private final EquipmentRepository repository;

    public EquipmentServiceimpl(EquipmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EquipmentModel> findAll() {
        return repository.findAll();
    }

    @Override
    public EquipmentModel findById(Integer id) {
        Optional<EquipmentModel> chamado = repository.findById(id);
        return chamado.orElse(null);
    }

    @Override
    public EquipmentModel create(EquipmentDto dto) {
        Integer id = dto.id();
        if (id != null) {
            Optional<EquipmentModel> chamado = repository.findById(id);
            if (chamado.isPresent()) {
                throw new IllegalArgumentException("Equipamento já existe");
            }
        }
        Integer equipCheck = dto.equipCheck();
        switch (equipCheck) {
            case 1:
                String status = dto.status().toLowerCase().trim();
                if (!status.matches("emuso|estoque|inativo")) {
                    throw new IllegalArgumentException("Status deve ser EM USO, ESTOQUE ou INATIVO");
                }
                Integer idSector, tag;
                if (dto.idSector() == null || dto.tag() == null) {
                    throw new IllegalArgumentException("Preencha todos os campos Id do Setor, Id da Tag referente respectivamente");
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de código invalido!");
        }

        return null;
    }

    @Override
    public EquipmentModel delete(Integer integer) {
        return null;
    }

    @Override
    public EquipmentModel update(Integer integer, EquipmentDto entity) {
        return null;
    }

    @Override
    public EquipmentModel updateDescription(Integer id, EquipmentUpdateDto dto) {
        if (!repository.existsById(id)) {
            throw new DataIntegrityViolationException("O Equipamento não existe!");
        }
        EquipmentModel user = repository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException("Equipamento não encontrado!"));
        user.setDescription(dto.description());
        repository.save(user);
        return user;
    }
}
