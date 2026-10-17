/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;

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
}
