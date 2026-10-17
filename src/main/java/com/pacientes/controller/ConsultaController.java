/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;

import com.pacientes.dao.ConsultaDAO;
import com.pacientes.dao.IConsultaDAO;
import com.pacientes.enums.EstadoConsulta;
import com.pacientes.model.Consulta;
import java.util.List;

/**
 *
 * @author administrator
 */
public class ConsultaController {
    
    private final IConsultaDAO consultaDAO;
    
    public ConsultaController(){
        consultaDAO = new ConsultaDAO();
    }
    
    public void cadastrar(Consulta consulta){
        consultaDAO.salvar(consulta);
    }
    
    public void atualizar(Consulta consulta){
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
}
