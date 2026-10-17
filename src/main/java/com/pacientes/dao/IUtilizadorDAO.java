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
public interface IUtilizadorDAO extends IGenericDAO<Utilizador, Long>{
    
    Optional<Utilizador> buscarPorUsername(String username);
}
