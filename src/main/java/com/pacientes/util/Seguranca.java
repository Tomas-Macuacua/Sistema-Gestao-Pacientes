/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.util;

import com.pacientes.exception.ValidacaoException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author administrator
 */
public class Seguranca {
    
    private static final int CUSTO_BCRYPT = 10;
    private static final int TAMANHO_MINIMO = 6;
    private static final int TAMANHO_MAXIMO = 50;

    private Seguranca() {
    }

    /** Transforma a palavra-passe num hash BCrypt (com "sal" aleatório incluído). */
    public static String gerarHash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt(CUSTO_BCRYPT));
    }

    /** Compara a palavra-passe escrita com o hash guardado. */
    public static boolean verificar(String senha, String hash) {
        if (senha == null || hash == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(senha, hash);
        } catch (IllegalArgumentException e) {
            return false; // hash guardado em formato inválido
        }
    }
    
    /** Regras mínimas para uma palavra-passe nova. */
    public static void validarForca(String senha) {
        List<String> erros = new ArrayList<>();

        if (senha == null || senha.length() < TAMANHO_MINIMO) {
            erros.add("A palavra-passe deve ter pelo menos " + TAMANHO_MINIMO + " caracteres.");
        } else {
            if (senha.length() > TAMANHO_MAXIMO) {
                erros.add("A palavra-passe não pode ter mais de " + TAMANHO_MAXIMO + " caracteres.");
            }
            if (senha.chars().noneMatch(Character::isLetter)) {
                erros.add("A palavra-passe deve conter pelo menos uma letra.");
            }
            if (senha.chars().noneMatch(Character::isDigit)) {
                erros.add("A palavra-passe deve conter pelo menos um número.");
            }
        }

        if (!erros.isEmpty()) {
            throw new ValidacaoException(erros);
        }
    }
}
