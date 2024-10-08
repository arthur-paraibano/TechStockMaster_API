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
        String status = dto.status().toUpperCase();
        if (!status.matches("AGUARDANDO ENVIO|EM CONSERTO")) {
            throw new IllegalArgumentException("O status deve ser AGUARDANDO ENVIO ou EM CONSERTO");
        }

        Integer idEquipamento = dto.idEquipamento();

        EquipmentModel equip = equipmentRepository.findById(dto.idEquipamento()).orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
        UserModel user = userRepository.findById(dto.idUsuario()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Integer idquip = equip.getId();


        // ID, FK_EQUIPE, TAG, FK_SET, FK_TECNIC, DATA, DESCRICAO, STATUS
        RepairModel rep = new RepairModel();
        rep.setIdEquipment(equip);
//        rep.setTag();
//        rep.setIdSector();
        rep.setIdUser(user);
        rep.setDate(LocalDate.now());
        rep.setDescricao(dto.descricao());
        rep.setStatus(dto.status());

        return repository.save(rep);
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
