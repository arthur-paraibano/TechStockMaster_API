package com.techstockmaster.api.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/*
 * Esta classe é responsável para criptografar e tirar criptografia com o metodo MD5 com padrão UTF-8
 */
@Component
public class Encrypt {
    public static String encriptografar(String senha) {
        String retorno = "";
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(senha.getBytes(StandardCharsets.UTF_8)));
            retorno = hash.toString(16);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }
}