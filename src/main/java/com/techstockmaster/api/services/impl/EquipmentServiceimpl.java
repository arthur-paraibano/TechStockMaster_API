package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.EquipmentDto;
import com.techstockmaster.api.controllers.dtos.FeedbackDto;
import com.techstockmaster.api.domain.models.EquipmentModal;
import com.techstockmaster.api.domain.models.FeedbackModel;
import com.techstockmaster.api.domain.models.UserModel;
import com.techstockmaster.api.domain.repositories.EquipmentRepository;
import com.techstockmaster.api.services.EquipmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<EquipmentModal> findAll() {
        return repository.findAll();
    }

    @Override
    public EquipmentModal findById(Integer id) {
        Optional<EquipmentModal> chamado = repository.findById(id);
        return chamado.orElse(null);
    }

    @Override
    public EquipmentModal create(EquipmentDto dto) {
        Integer id = dto.id();
        if (id != null) {
            Optional<EquipmentModal> chamado = repository.findById(id);
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
                if (dto.idSector() == null || dto.tag() == null){
                    throw new IllegalArgumentException("Preencha todos os campos Id do Setor, Id da Tag referente respectivamente");
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de código invalido!");
        }

        return null;
    }

    @Override
    public EquipmentModal delete(Integer integer) {
        return null;
    }

    @Override
    public EquipmentModal update(Integer integer, EquipmentDto entity) {
        return null;
    }
}
