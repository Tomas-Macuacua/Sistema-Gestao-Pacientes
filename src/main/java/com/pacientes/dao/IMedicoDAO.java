/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;

import com.pacientes.model.Medico;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author administrator
 */
public interface IMedicoDAO extends IGenericDAO<Medico, Long> {
    
    Optional<Medico> buscarPorNumeroOrdem(String numeroOrdem);
    
    List<Medico> pesquisarPorNome(String nome);
    
    List<Medico> listarPorEspecialidade(String especialidade);
}
