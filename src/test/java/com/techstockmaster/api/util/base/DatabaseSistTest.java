package com.techstockmaster.api.util.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DatabaseSistTest {

    @Autowired
    private DatabaseSist db;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        assertNotNull(db);
        assertNotNull(dataSource);
    }

    @Test
    void testConexao() {
        Connection connection = db.getConnection();
        assertNotNull(connection, "Conexão deveria ser estabelecida");
    }

    @AfterEach
    void tearDown() {
        db.closeConnection();
    }
}