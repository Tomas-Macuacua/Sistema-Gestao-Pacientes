/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;


import com.pacientes.dao.ConsultaDAO;
import com.pacientes.dao.IConsultaDAO;
import com.pacientes.dao.IPacienteDAO;
import com.pacientes.dao.PacienteDAO;
import com.pacientes.enums.Permissao;
import com.pacientes.exception.RegraNegocioException;
import com.pacientes.exception.ValidacaoException;
import com.pacientes.model.Paciente;
import com.pacientes.util.Sessao;
import com.pacientes.util.Validador;

import java.util.List;
import java.util.Optional;

public class PacienteController {

    private final IPacienteDAO pacienteDAO = new PacienteDAO();
    private final IConsultaDAO consultaDAO = new ConsultaDAO();

    public void cadastrar(Paciente paciente) {
        Sessao.exigir(Permissao.GERIR_PACIENTES);
        Validador.validarPaciente(paciente);
        verificarProcessoUnico(paciente);
        pacienteDAO.salvar(paciente);
    }

    public void atualizar(Paciente paciente) {
        Sessao.exigir(Permissao.GERIR_PACIENTES);
        if (paciente == null || paciente.getId() == null) {
            throw new ValidacaoException("Seleccione um paciente existente para actualizar.");
        }
        Validador.validarPaciente(paciente);
        verificarProcessoUnico(paciente);
        pacienteDAO.atualizar(paciente);
    }

    public void eliminar(Long id) {
        Sessao.exigir(Permissao.GERIR_PACIENTES);
        if (!consultaDAO.listarPorPaciente(id).isEmpty()) {
            throw new RegraNegocioException(
                    "Não é possível eliminar: o paciente tem consultas registadas.");
        }
        pacienteDAO.eliminar(id);
    }

    public Paciente buscar(Long id) {
        Sessao.exigir(Permissao.VER_PACIENTES);
        return pacienteDAO.buscarPorId(id);
    }

    public Optional<Paciente> buscarPorNumeroProcesso(String numeroProcesso) {
        Sessao.exigir(Permissao.VER_PACIENTES);
        return pacienteDAO.buscarPorNumeroProcesso(numeroProcesso);
    }

    public List<Paciente> pesquisarPorNome(String nome) {
        Sessao.exigir(Permissao.VER_PACIENTES);
        return pacienteDAO.pesquisarPorNome(nome);
    }

    public List<Paciente> listar() {
        Sessao.exigir(Permissao.VER_PACIENTES);
        return pacienteDAO.listarTodos();
    }

    /** Regra: não podem existir dois pacientes com o mesmo número de processo. */
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