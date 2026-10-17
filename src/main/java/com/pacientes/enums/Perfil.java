/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.pacientes.enums;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

/**
 *
 * @author administrator
 */
public enum Perfil {
    ADMIN(
            Permissao.GERIR_UTILIZADORES,
            Permissao.GERIR_MEDICOS,
            Permissao.GERIR_PACIENTES,
            Permissao.VER_PACIENTES,
            Permissao.AGENDAR_CONSULTAS,
            Permissao.VER_CONSULTAS,
            Permissao.VER_RELATORIOS
    ),
    
    MEDICO(
            Permissao.VER_PACIENTES,
            Permissao.VER_CONSULTAS,
            Permissao.REGISTAR_DIAGNOSTICO,
            Permissao.VER_RELATORIOS
    ),
    
    RECEPCIONISTA(
            Permissao.GERIR_PACIENTES,
            Permissao.VER_PACIENTES,
            Permissao.AGENDAR_CONSULTAS,
            Permissao.VER_CONSULTAS
    );
    
    private final Set<Permissao> permissoes;
    
    Perfil(Permissao... permissoes){
        this.permissoes = EnumSet.copyOf(Arrays.asList(permissoes));
    }
    
    public boolean temPermissao(Permissao permissao){
        return permissoes.contains(permissao);
    }
}
