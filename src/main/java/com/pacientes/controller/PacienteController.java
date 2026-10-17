/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;


import com.pacientes.dao.IPacienteDAO;
import com.pacientes.dao.PacienteDAO;
import com.pacientes.model.Paciente;

import java.util.List;
import java.util.Optional;

public class PacienteController {

    private final IPacienteDAO pacienteDAO;

    public PacienteController() {
        pacienteDAO = new PacienteDAO();
    }

    public void cadastrar(Paciente paciente) {
        pacienteDAO.salvar(paciente);
    }

    public void atualizar(Paciente paciente) {
        pacienteDAO.atualizar(paciente);
    }

    public void eliminar(Long id) {
        pacienteDAO.eliminar(id);
    }

    public Paciente buscar(Long id) {
        return pacienteDAO.buscarPorId(id);
    }
    
    public Optional<Paciente> buscarPorNomeProcesso(String numeroProcesso){
        return pacienteDAO.buscarPorNumeroProcesso(numeroProcesso);
    }
    
    public List<Paciente> pesquisarPorNome(String nome){
        return pacienteDAO.pesquisarPorNome(nome);
    }

    public List<Paciente> listar() {
        return pacienteDAO.listarTodos();
    }
}