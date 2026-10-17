/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;

import com.pacientes.enums.Perfil;
import com.pacientes.model.Utilizador;

import java.util.Optional;

/**
 *
 * @author administrator
 */
public class UtilizadorDAO extends GenericDAO<Utilizador> implements IUtilizadorDAO{
    
    public UtilizadorDAO(){
        super(Utilizador.class);
    }
    
    @Override
    public Optional<Utilizador> buscarPorUsername(String username){
        return consultarUnico(
                "FROM Utilizador u WHERE u.username = ?1",
                username
        );
    }
    
    @Override
    public Optional<Utilizador> buscarPorMedico(Long medicoId){
        return consultarUnico(
            "FROM Utilizador u WHERE u.medico.id = ?1", medicoId);
    }
    
    @Override
    public long contarTodos() {
        return consultar("SELECT COUNT(u) FROM Utilizador u", Long.class).get(0);
    }

    @Override
    public long contarAtivosPorPerfil(Perfil perfil) {
        return consultar(
                "SELECT COUNT(u) FROM Utilizador u WHERE u.perfil = ?1 AND u.ativo = true",
                Long.class, perfil).get(0);
    }    
}
