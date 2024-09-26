package com.techstockmaster.api.services.impl;

import com.techstockmaster.api.controllers.dtos.GeneralEquipmentDto;
import com.techstockmaster.api.domain.models.GeneralEquipmentModal;
import com.techstockmaster.api.domain.repositories.GeneralEquipmentRepository;
import com.techstockmaster.api.services.GeneralEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JdbcTemplate jdbcTemplate;

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
    public GeneralEquipmentModal findById(Integer integer) {
        Optional<GeneralEquipmentModal> entity = repository.findById(integer);
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

    /***
     * Registra os equipamentos no banco de dados local
     ***/
    @Transactional
    public int equipmentRegistration(GeneralEquipmentModal equipment) {
        String sql = "INSERT INTO bd_estoque.equipamento_geral (ID_KERY, CODIGO, DESCRICAO, ABREVIACAO_UM, DESCRICAO_UM) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                equipment.getIdKey(),
                equipment.getCodigo(),
                equipment.getDescricao(),
                equipment.getAbreviacaoUm(),
                equipment.getDescricaoUm());
    }

    /***
     * Lista os equipamentos do banco de dados da Lykos
     ***/
    @Transactional(readOnly = true)
    public List<GeneralEquipmentModal> lista() throws SQLException {
        String sql = "SELECT CAST(ID AS SIGNED) AS ID_KERY, CODIGO as codigo, DESCRICAO as descricao, " +
                "ABREVIACAO_UM as abreviacao_um, DESCRICAO_UM as descricao_um " +
                "FROM integracao.view_material WHERE ID > ? ORDER BY ID ASC";
        return jdbcTemplate.query(sql, new Object[]{ultimoSequencia()}, new GeneralEquipmentMapper());
    }

    /***
     * Retorna o último ID_KERY do banco de dados local
     ***/
    @Transactional(readOnly = true)
    public String ultimoSequencia() {
        String sql = "SELECT ID_KERY FROM bd_estoque.equipamento_geral ORDER BY ID_KERY DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    // Mapeador de ResultSet para GeneralEquipmentModal
    private static class GeneralEquipmentMapper implements RowMapper<GeneralEquipmentModal> {
        @Override
        public GeneralEquipmentModal mapRow(ResultSet rs, int rowNum) throws SQLException {
            GeneralEquipmentModal equipment = new GeneralEquipmentModal();
            equipment.setIdKey(rs.getInt("ID_KERY"));
            equipment.setCodigo(rs.getString("codigo"));
            equipment.setDescricao(rs.getString("descricao"));
            equipment.setAbreviacaoUm(rs.getString("abreviacao_um"));
            equipment.setDescricaoUm(rs.getString("descricao_um"));
            return equipment;
        }
    }
}
