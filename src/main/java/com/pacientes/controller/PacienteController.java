/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;


import com.pacientes.dao.ConsultaDAO;
import com.pacientes.dao.IConsultaDAO;
import com.pacientes.dao.IPacienteDAO;
import com.pacientes.dao.PacienteDAO;
import com.pacientes.exception.RegraNegocioException;
import com.pacientes.exception.ValidacaoException;
import com.pacientes.model.Paciente;
import com.pacientes.util.Validador;

import java.util.List;
import java.util.Optional;

public class PacienteController {

    private final IPacienteDAO pacienteDAO;
    private final IConsultaDAO consultaDAO;

    public PacienteController() {
        pacienteDAO = new PacienteDAO();
        consultaDAO = new ConsultaDAO();
    }

    public void cadastrar(Paciente paciente) {
        Validador.validarPaciente(paciente);
        verificarProcessoUnico(paciente);
        pacienteDAO.salvar(paciente);
    }

    public void atualizar(Paciente paciente) {
        if (paciente == null || paciente.getId() == null) {
            throw new ValidacaoException("Seleccione um paciente existente para actualizar.");
        }
        Validador.validarPaciente(paciente);
        verificarProcessoUnico(paciente);
        pacienteDAO.atualizar(paciente);
    }

    public void eliminar(Long id) {
        if (!consultaDAO.listarPorPaciente(id).isEmpty()) {
            throw new RegraNegocioException(
                    "Não é possível eliminar: o paciente tem consultas registadas.");
        }
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
    
    /**
     * Regra: não podem existir dois pacientes com o mesmo número de processo.
     */
    private void verificarProcessoUnico(Paciente paciente) {
        String processo = paciente.getNumeroProcesso().trim();
        pacienteDAO.buscarPorNumeroProcesso(processo).ifPresent(existente -> {
            if (!existente.getId().equals(paciente.getId())) {
                throw new RegraNegocioException(
                        "Já existe um paciente com o número de processo " + processo + ".");
            }
        });
    }
}