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
public class ConsultaDAO extends GenericDAO<Consulta> implements IConsultaDAO{
    
    public ConsultaDAO(){
        super(Consulta.class);
    }
    
    @Override
    public List<Consulta> listarPorPaciente(Long pacienteId){
        return consultar(
                "FROM Consulta c WHERE c.paciente.id = ?1 ORDER BY c.dataHora DESC",
                Consulta.class,
                pacienteId
        );
    }
    
    @Override
    public List<Consulta> listarPorMedico(Long medicoId){
        return consultar(
                "FROM Consulta c WHERE c.medico.id = ?1 ORDER BY c.dataHora DESC",
                Consulta.class,
                medicoId
        );
    }
    
    @Override
    public List<Consulta> listarPorEstado(EstadoConsulta estado){
        return consultar(
                "FROM Consulta c WHERE c.estado = ?1 ORDER BY c.dataHora",
                Consulta.class,
                estado
        );
    }
}
