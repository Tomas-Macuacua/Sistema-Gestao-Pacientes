/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;


import com.pacientes.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface IPacienteDAO extends IGenericDAO<Paciente, Long> {

    Optional<Paciente> buscarPorNumeroProcesso(String numeroProcesso);
    
    List<Paciente> pesquisarPorNome(String nome);
}