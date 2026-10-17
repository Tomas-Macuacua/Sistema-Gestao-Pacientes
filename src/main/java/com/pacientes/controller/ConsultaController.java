/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;

import com.pacientes.dao.ConsultaDAO;
import com.pacientes.dao.IConsultaDAO;
import com.pacientes.enums.EstadoConsulta;
import com.pacientes.exception.RegraNegocioException;
import com.pacientes.exception.ValidacaoException;
import com.pacientes.model.Consulta;
import com.pacientes.util.Validador;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author administrator
 */
public class ConsultaController {
    
    /**Cada consulta ocupa o médico durante este número de minutos. */
    private static final int DURACAO_MINUTOS = 30;
    
    private final IConsultaDAO consultaDAO;
    
    public ConsultaController(){
        consultaDAO = new ConsultaDAO();
    }
    
    // ============= FUNCIONALIDADES DE NEGÓCIO =============
   
    public void agendar(Consulta consulta){
        Validador.validarConsulta(consulta, true);
        verificarConflito(consulta);
        consultaDAO.salvar(consulta);
    }
    
    public void cancelar(Long id){
        Consulta consulta = obterExistente(id);
        consulta.cancelar();
        consultaDAO.atualizar(consulta);
    }
    
    public void realizar(Long id, String diagnostico){
        Consulta consulta = obterExistente(id);
        consulta.realizar(diagnostico);
        consultaDAO.atualizar(consulta);
    }
    
    // ============ CRUD ============
    
    public void atualizar(Consulta consulta){
        if (consulta == null || consulta.getId() == null) {
            throw new ValidacaoException("Seleccione uma consulta existente para actualizar.");
        }
        Validador.validarConsulta(consulta, false);
        if (consulta.getEstado() != EstadoConsulta.CANCELADA) {
            verificarConflito(consulta);
        }
        consultaDAO.atualizar(consulta);
    }
    
    public void eliminar(Long id){
        consultaDAO.eliminar(id);
    }
    
    public Consulta buscar(Long id){
        return consultaDAO.buscarPorId(id);
    }
    
    public List<Consulta> listarPorPaciente(Long pacienteId){
        return consultaDAO.listarPorPaciente(pacienteId);
    }
    
    public List<Consulta> listarPorMedico(Long medicoId){
        return consultaDAO.listarPorMedico(medicoId);
    }
    
    public List<Consulta> listarPorEstado(EstadoConsulta estado){
        return consultaDAO.listarPorEstado(estado);
    }
    
    public List<Consulta> listar(){
        return consultaDAO.listarTodos();
    }
    
    // ========= Auxiliares =========

    private Consulta obterExistente(Long id) {
        Consulta consulta = consultaDAO.buscarPorId(id);
        if (consulta == null) {
            throw new RegraNegocioException("A consulta não foi encontrada.");
        }
        return consulta;
    }
    
    /** Regra: o mesmo médico não pode ter duas consultas sobrepostas. */
    private void verificarConflito(Consulta consulta) {
        LocalDateTime inicio = consulta.getDataHora().minusMinutes(DURACAO_MINUTOS);
        LocalDateTime fim = consulta.getDataHora().plusMinutes(DURACAO_MINUTOS);
        Long ignorarId = (consulta.getId() != null) ? consulta.getId() : -1L;

        if (consultaDAO.existeConflito(consulta.getMedico().getId(), inicio, fim, ignorarId)) {
            throw new RegraNegocioException("O médico já tem uma consulta marcada nesse horário "
                    + "(cada consulta dura " + DURACAO_MINUTOS + " minutos).");
        }
    }
}
