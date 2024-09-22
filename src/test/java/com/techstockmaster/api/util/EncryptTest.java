package com.techstockmaster.api.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EncryptTest {

    @Test
    void testEncriptografar_NotNull() {
        String senha = "123456";
        String senhaCriptografada = Encrypt.encriptografar(senha);
        assertNotNull(senhaCriptografada, "O método encriptografar não deve retornar null");
    }

    @Test
    void testEncriptografar_Length() {
        String senha = "123456";
        String senhaCriptografada = Encrypt.encriptografar(senha);
        assertEquals(32, senhaCriptografada.length(), "O hash MD5 deve ter 32 caracteres");
    }

    @Test
    void testEncriptografar_Consistencia() {
        String senha = "123456";
        String hash1 = Encrypt.encriptografar(senha);
        String hash2 = Encrypt.encriptografar(senha);
        assertEquals(hash1, hash2, "O método encriptografar deve ser consistente para a mesma entrada");
    }

    @Test
    void testEncriptografar_ValorCorreto() {
        String senha = "123456";
        String senhaCriptografada = Encrypt.encriptografar(senha);
        String hashEsperado = "e10adc3949ba59abbe56e057f20f883e"; // MD5 esperado para "123456"
        assertEquals(hashEsperado, senhaCriptografada, "O método encriptografar deve retornar o hash correto");
    }
}