/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;

import com.pacientes.model.Paciente;

import java.util.List;
import java.util.Optional;

public class PacienteDAO extends GenericDAO<Paciente> implements IPacienteDAO{

    public PacienteDAO(){
        super (Paciente.class);
    }
    
    @Override
    public Optional<Paciente> buscarPorNumeroProcesso(String numeroProcesso){
        return consultarUnico(
                "FROM Paciente p WHERE p.numeroProcesso = ?1", numeroProcesso);
    }
    
    @Override
    public List<Paciente> pesquisarPorNome(String nome){
        return consultar(
                "FROM Paciente p WHERE LOWER(p.nome) LIKE ?1 ORDER BY p.nome",
                Paciente.class, contem(nome));
    }
}
