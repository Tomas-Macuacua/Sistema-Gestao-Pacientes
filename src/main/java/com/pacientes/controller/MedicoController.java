/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;

import com.pacientes.dao.ConsultaDAO;
import com.pacientes.dao.IConsultaDAO;
import com.pacientes.dao.IMedicoDAO;
import com.pacientes.dao.IUtilizadorDAO;
import com.pacientes.dao.MedicoDAO;
import com.pacientes.dao.UtilizadorDAO;
import com.pacientes.enums.Permissao;
import com.pacientes.exception.RegraNegocioException;
import com.pacientes.exception.ValidacaoException;
import com.pacientes.model.Medico;
import com.pacientes.util.Sessao;
import com.pacientes.util.Validador;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author administrator
 */
public class MedicoController {
    
    private final IMedicoDAO medicoDAO = new MedicoDAO();
    private final IConsultaDAO consultaDAO = new ConsultaDAO();
    private final IUtilizadorDAO utilizadorDAO = new UtilizadorDAO();

    public void cadastrar(Medico medico) {
        Sessao.exigir(Permissao.GERIR_MEDICOS);
        Validador.validarMedico(medico);
        verificarOrdemUnica(medico);
        medicoDAO.salvar(medico);
    }

    public void atualizar(Medico medico) {
        Sessao.exigir(Permissao.GERIR_MEDICOS);
        if (medico == null || medico.getId() == null) {
            throw new ValidacaoException("Seleccione um médico existente para actualizar.");
        }
        Validador.validarMedico(medico);
        verificarOrdemUnica(medico);
        medicoDAO.atualizar(medico);
    }

    public void eliminar(Long id) {
        Sessao.exigir(Permissao.GERIR_MEDICOS);
        if (!consultaDAO.listarPorMedico(id).isEmpty()) {
            throw new RegraNegocioException(
                    "Não é possível eliminar: o médico tem consultas registadas.");
        }
        if (utilizadorDAO.buscarPorMedico(id).isPresent()) {
            throw new RegraNegocioException(
                    "Não é possível eliminar: existe um utilizador associado a este médico.");
        }
        medicoDAO.eliminar(id);
    }

    // A leitura de médicos é permitida a qualquer utilizador autenticado
    // (a recepção precisa deles para agendar consultas).

    public Medico buscar(Long id) {
        Sessao.exigirAutenticado();
        return medicoDAO.buscarPorId(id);
    }

    public Optional<Medico> buscarPorNumeroOrdem(String numeroOrdem) {
        Sessao.exigirAutenticado();
        return medicoDAO.buscarPorNumeroOrdem(numeroOrdem);
    }

    public List<Medico> pesquisarPorNome(String nome) {
        Sessao.exigirAutenticado();
        return medicoDAO.pesquisarPorNome(nome);
    }

    public List<Medico> listarPorEspecialidade(String especialidade) {
        Sessao.exigirAutenticado();
        return medicoDAO.listarPorEspecialidade(especialidade);
    }

    public List<Medico> listar() {
        Sessao.exigirAutenticado();
        return medicoDAO.listarTodos();
    }

    private void verificarOrdemUnica(Medico medico) {
        String ordem = medico.getNumeroOrdem().trim();
        medicoDAO.buscarPorNumeroOrdem(ordem).ifPresent(existente -> {
            if (!existente.getId().equals(medico.getId())) {
                throw new RegraNegocioException(
                        "Já existe um médico com o número de ordem " + ordem + ".");
            }
        });
    }
}
