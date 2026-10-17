/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;

import com.pacientes.enums.EstadoConsulta;
import com.pacientes.model.Consulta;

import java.util.List;

/**
 *
 * @author administrator
 */
public interface IConsultaDAO extends IGenericDAO<Consulta, Long>{
    
    List<Consulta> listarPorPaciente(Long paciente);
    
    List<Consulta> listarPorMedico(Long medicoId);
    
    List<Consulta> listarPorEstado(EstadoConsulta estado);
}
