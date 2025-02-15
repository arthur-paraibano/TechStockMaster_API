package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.RepairDto;
import com.techstockmaster.api.controllers.dtos.RepairStatusDto;
import com.techstockmaster.api.domain.models.*;
import com.techstockmaster.api.domain.repositories.EquipmentRepository;
import com.techstockmaster.api.domain.repositories.RepairRepository;
import com.techstockmaster.api.domain.repositories.SectorRepository;
import com.techstockmaster.api.domain.repositories.UserRepository;
import com.techstockmaster.api.services.RepairService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    @Qualifier("jdbcTemplateLocal")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private final RepairRepository repository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final EquipmentRepository equipmentRepository;

    @Autowired
    private final SectorRepository sectorRepository;

    public List<RepairModel> findAll() {
        return repository.findAll();
    }

    public RepairModel findById(Integer id) {
        Optional<RepairModel> repair = repository.findById(id);
        return repair.orElse(null);
    }

    @Override
    public RepairModel create(RepairDto dto) {
        Integer id = dto.idEquipamento();
        if (id != null) {
            Optional<RepairModel> existingRepair = repository.findById(id);
            if (existingRepair.isPresent()) {
                throw new IllegalArgumentException("Equipamento já cadastrado para conserto!");
            }
        }

        // Status já é validado no DTO, então podemos remover a validação aqui
        String status = dto.status().toUpperCase();

        // Buscar o equipamento pelo ID
        EquipmentModel equip = equipmentRepository.findByIdEquipm(dto.idEquipamento())
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));

        // Buscar o usuário pelo ID
        UserModel user = userRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        String tag = findTag(dto.idEquipamento());
        if (tag.trim().equals("") || tag == null) {
            throw new IllegalArgumentException("Não é um equipamento com TAG!");
        }
        String tagExit = findExist(tag);
        if (!tagExit.trim().equals("") || tagExit == null){
            throw new IllegalArgumentException("Equipamento já cadastrado para Conserto!");
        }

        RepairModel rep = new RepairModel();

        rep.setIdEquipment(equip);
        rep.setIdSector(equip.getIdSector());
        rep.setTag(tag);
        rep.setIdUser(user);
        rep.setDate(LocalDate.now());
        rep.setDescricao(dto.descricao());
        rep.setStatus(status);

        return repository.save(rep);
    }

    @Transactional(readOnly = true, transactionManager = "transactionManagerLocal")
    private String findTag(Integer id) {
        String sql = "SELECT concat(abrevTag,'-',LPAD(TAG_SEQ, 3,0)) AS TAG FROM bd_estoque.equipamento ED LEFT JOIN  bd_estoque.tag T ON FK_TAG = T.ID  LEFT JOIN bd_estoque.setor S ON ID_CODSETOR = S.ID WHERE ED.ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
    }

    @Transactional(readOnly = true, transactionManager = "transactionManagerLocal")
    private String findExist(String tag) {
        String sql = "SELECT TAG FROM bd_estoque.conserto WHERE TAG = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{tag}, String.class);
    }

    @Override
    public RepairModel delete(Integer integer) {
        return null;
    }

    @Override
    public RepairModel update(Integer integer, RepairDto entity) {
        return null;
    }

    @Override
    public RepairModel updateStatus(Integer id, RepairStatusDto dto) {
        RepairModel equip = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));

        String status = dto.status().toUpperCase().trim();
        if (!status.equals("AGUARDANDO ENVIO") || status.equals("EM CONSERTO") || status.equals("RECEBIDO")) {
            throw new IllegalArgumentException("O tipo deve ser AGUARDANDO ENVIO, EM CONSERTO ou RECEBIDO!");
        }

        equip.setStatus(status);

        return repository.save(equip);
    }
}
