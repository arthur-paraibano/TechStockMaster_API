package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.RepairDto;
import com.techstockmaster.api.domain.models.*;
import com.techstockmaster.api.domain.repositories.EquipmentRepository;
import com.techstockmaster.api.domain.repositories.RepairRepository;
import com.techstockmaster.api.domain.repositories.SectorRepository;
import com.techstockmaster.api.domain.repositories.UserRepository;
import com.techstockmaster.api.services.RepairService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private final RepairRepository repository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final EquipmentRepository equipmentRepository;

    @Autowired
    private final SectorRepository sectorRepository;

    public RepairServiceImpl(RepairRepository repository, UserRepository userRepository, EquipmentRepository equipmentRepository, SectorRepository sectorRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
        this.sectorRepository = sectorRepository;
    }

    public List<RepairModel> findAll() {
        return repository.findAll();
    }

    public RepairModel findById(Integer id) {
        Optional<RepairModel> repair = repository.findById(id);
        return repair.orElse(null);
    }

    @Override
    public RepairModel create(RepairDto dto) {
//        Integer id = dto.idEquipamento();
//        if (id != null) {
//            Optional<RepairModel> existingRepair = repository.findByIdRepair(id);
//            if (existingRepair.isPresent()) {
//                throw new IllegalArgumentException("Equipamento já cadastrado para conserto!");
//            }
//        }
//
//        // Status já é validado no DTO, então podemos remover a validação aqui
//        String status = dto.status().toUpperCase();
//
//        // Buscar o equipamento pelo ID
//        EquipmentModel equip = equipmentRepository.findByIdEquipm(dto.idEquipamento())
//                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
//
//        // Buscar o usuário pelo ID
//        UserModel user = userRepository.findById(dto.idUsuario())
//                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
//
//        // Criar nova instância de RepairModel e setar os campos
//        RepairModel rep = new RepairModel();
//        // Associar a entidade EquipmentModel diretamente
//        rep.setIdEquipment(equip);
//        // Associar a entidade SectorModel diretamente
//        rep.setIdSector(equip.getIdSector());
//        // Definir a tag, utilizando a abreviação, por exemplo
//        rep.setTag(equip.getTag().getAbrevTag() + "-" + String.format("%03d", equip.getTag()));
//        // Associar o usuário
//        rep.setIdUser(user);
//        // Definir a data atual
//        rep.setDate(LocalDate.now());
//        // Definir a descrição e status
//        rep.setDescricao(dto.descricao());
//        rep.setStatus(status);
//
//        // Salvar e retornar o modelo de conserto criado
//        return repository.save(rep);
        return null;
    }

    @Override
    public RepairModel delete(Integer integer) {
        return null;
    }

    @Override
    public RepairModel update(Integer integer, RepairDto entity) {
        return null;
    }
}
