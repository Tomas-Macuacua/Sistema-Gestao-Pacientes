/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;

import com.pacientes.model.Paciente;

import java.util.List;

public class PacienteDAO extends GenericDAO<Paciente> implements IPacienteDAO{

    public PacienteDAO(){
        super (Paciente.class);
    }
}
