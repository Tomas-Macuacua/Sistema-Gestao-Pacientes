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
import com.pacientes.exception.RegraNegocioException;
import com.pacientes.exception.ValidacaoException;
import com.pacientes.model.Medico;
import com.pacientes.util.Validador;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author administrator
 */
public class MedicoController {
    
    private final IMedicoDAO medicoDAO;
    private final IConsultaDAO consultaDAO;
    private final IUtilizadorDAO utilizadorDAO;
    
    public MedicoController(){
        medicoDAO = new MedicoDAO();
        consultaDAO = new ConsultaDAO();
        utilizadorDAO = new UtilizadorDAO();
    }
    
    public void cadastrar(Medico medico){
        Validador.validarMedico(medico);
        verificarOrdemUnica(medico);
        medicoDAO.salvar(medico);
    }
    
    public void atualizar(Medico medico){
        if (medico == null || medico.getId() == null) {
            throw new ValidacaoException("Seleccione um médico existente para actualizar.");
        }   
        Validador.validarMedico(medico);
        verificarOrdemUnica(medico);
        medicoDAO.atualizar(medico);
    }
    
    public void eliminar(Long id){
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
    
    public Medico buscar(Long id){
        return medicoDAO.buscarPorId(id);
    }
    
    public Optional<Medico> buscarPorNumeroOrdem(String numeroOrdem){
        return medicoDAO.buscarPorNumeroOrdem(numeroOrdem);
    }
    
    public List<Medico> pesquisarPorNome(String nome){
        return medicoDAO.pesquisarPorNome(nome);
    }
    
    public List<Medico> listarPorEspeialidade(String especialidade){
        return medicoDAO.listarPorEspecialidade(especialidade);
    }
    
    public List<Medico> listar(){
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
