package com.techstockmaster.api.util;

import com.techstockmaster.api.domain.models.GeneralEquipmentModal;
import com.techstockmaster.api.util.base.DatabaseLykos;
import com.techstockmaster.api.util.base.DatabaseSist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Integracao {
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    /***
     * Dados apara atualizar os equipamentos do banco de dados da Lykos para base de
     * dados local.
     ***/
    public Integer equipmentRegistration(GeneralEquipmentModal equipment) throws SQLException, Exception {
        this.con = DatabaseSist.getConnection();
        String sql = "INSERT INTO bd_estoque.equipamento_geral (ID_KERY, CODIGO, DESCRICAO, ABREVIACAO_UM, DESCRICAO_UM) VALUES (?,?,?,?,?)";
        this.stmt = this.con.prepareStatement(sql);
        this.stmt.setInt(1, equipment.getIdKey());
        this.stmt.setString(2, equipment.getCodigo());
        this.stmt.setString(3, equipment.getDescricao());
        this.stmt.setString(4, equipment.getAbreviacaoUm());
        this.stmt.setString(5, equipment.getDescricaoUm());
        int rowsAffected = this.stmt.executeUpdate();
        DatabaseSist.closeConnection(con, stmt, null);
        return rowsAffected;
    }

    public List<GeneralEquipmentModal> lista() throws SQLException, Exception {
        List<GeneralEquipmentModal> list = new ArrayList<>();
        this.con = DatabaseLykos.getConnection();
        this.stmt = con.prepareStatement(
                "SELECT CAST(ID AS SIGNED) AS ID_KERY, CODIGO as codigo, DESCRICAO as descricao, ID_UM as id_um, ABREVIACAO_UM as abreviacao_um, DESCRICAO_UM as descricao_um FROM integracao.view_material WHERE ID > ? ORDER BY ID ASC");
        this.stmt.setString(1, ultimoSequencia());
        this.rs = stmt.executeQuery();

        while (rs.next()) {
            GeneralEquipmentModal e = new GeneralEquipmentModal();
            e.setIdKey(rs.getInt("ID_KERY"));
            e.setCodigo(rs.getString("codigo"));
            e.setDescricao(rs.getString("descricao"));
            e.setAbreviacaoUm(rs.getString("abreviacao_um"));
            e.setDescricao(rs.getString("descricao_um"));
            list.add(e);
        }
        DatabaseLykos.closeConnection(con, stmt, rs);
        return list;
    }

    public String ultimoSequencia() throws SQLException, Exception {
        String id_kery = null;
        this.con = DatabaseSist.getConnection();
        String sql = "SELECT ID_KERY FROM bd_estoque.equipamento_geral order by  ID_KERY desc limit 1";
        this.stmt = con.prepareStatement(sql);
        this.rs = this.stmt.executeQuery();
        if (rs.next()) {
            id_kery = String.valueOf(rs.getInt("ID_KERY"));
        }
        return id_kery;
    }
}
