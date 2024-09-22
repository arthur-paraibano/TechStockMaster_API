package com.techstockmaster.api.util.base;

import com.techstockmaster.api.util.base.exception.DbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DatabaseSist {

    @Autowired
    @Qualifier("dataSourceLocal")
    private DataSource dataSource;

    private Connection connection;

    // check database connection
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        closeConnection(connection);
    }

    public void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
                System.out.println("Conexão finalizada");
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    public void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
                System.out.println("Conexão finalizada");
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    public void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Conexão finalizada");
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    public void testeConnection() {
        try {
            getConnection();
            System.out.println("Conexão estabelecida!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}