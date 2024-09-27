package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.GeneralEquipmentDto;
import com.techstockmaster.api.domain.models.GeneralEquipmentModal;
import com.techstockmaster.api.domain.repositories.GeneralEquipmentRepository;
import com.techstockmaster.api.services.GeneralEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class GeneralEquipmentServiceImpl implements GeneralEquipmentService {

    @Autowired
    @Qualifier("jdbcTemplateLocal")
    private JdbcTemplate jdbcTemplateLocal;

    @Autowired
    @Qualifier("jdbcTemplateLykos")
    private JdbcTemplate jdbcTemplateLykos;

    @Autowired
    private final GeneralEquipmentRepository repository;

    public GeneralEquipmentServiceImpl(GeneralEquipmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GeneralEquipmentModal> findAll() {
        return repository.findAll();
    }

    @Override
    public GeneralEquipmentModal findById(Integer id) {
        Optional<GeneralEquipmentModal> entity = repository.findById(id);
        return entity.orElse(null);
    }

    @Override
    public GeneralEquipmentModal create(GeneralEquipmentDto entity) {
        return null;
    }

    @Override
    public GeneralEquipmentModal delete(Integer integer) {
        return null;
    }

    @Override
    public GeneralEquipmentModal update(Integer integer, GeneralEquipmentDto entity) {
        return null;
    }

    @Transactional
    public int executeIntegration() throws SQLException {
        // 1. Pega a lista de equipamentos do banco Lykos
        List<GeneralEquipmentModal> equipments = lista();

        // 2. Insere os equipamentos no banco local
        int rowsProcessed = 0;
        for (GeneralEquipmentModal equipment : equipments) {
            rowsProcessed += equipmentRegistration(equipment);
        }
        return rowsProcessed;
    }

    @Transactional(readOnly = true, transactionManager = "transactionManagerLykos")
    public List<GeneralEquipmentModal> lista() throws SQLException {
        String sql = "SELECT * FROM integracao.view_material WHERE ID > ? ORDER BY ID ASC";
        return jdbcTemplateLykos.query(sql, new Object[]{ultimoSequencia()}, new GeneralEquipmentMapper());
    }

    @Transactional(readOnly = true, transactionManager = "transactionManagerLocal")
    public String ultimoSequencia() {
        String sql = "SELECT ID_KERY FROM bd_estoque.equipamento_geral ORDER BY ID_KERY DESC LIMIT 1";
        return jdbcTemplateLocal.queryForObject(sql, String.class);
    }

    @Transactional
    public int equipmentRegistration(GeneralEquipmentModal equipment) {
        String sql = "INSERT INTO bd_estoque.equipamento_geral (ID_KERY, CODIGO, DESCRICAO, ABREVIACAO_UM, DESCRICAO_UM) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplateLocal.update(sql,
                equipment.getIdKey(),
                equipment.getCodigo(),
                equipment.getDescricao(),
                equipment.getAbreviacaoUm(),
                equipment.getDescricaoUm());
    }

    private static class GeneralEquipmentMapper implements RowMapper<GeneralEquipmentModal> {
        @Override
        public GeneralEquipmentModal mapRow(ResultSet rs, int rowNum) throws SQLException {
            GeneralEquipmentModal equipment = new GeneralEquipmentModal();
            equipment.setIdKey(rs.getInt("id"));
            equipment.setCodigo(rs.getString("codigo"));
            equipment.setDescricao(rs.getString("descricao"));
            equipment.setAbreviacaoUm(rs.getString("abreviacao_um"));
            equipment.setDescricaoUm(rs.getString("descricao_um"));
            return equipment;
        }
    }
}
