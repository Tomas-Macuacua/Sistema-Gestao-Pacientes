/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;


import com.pacientes.model.Paciente;

import java.util.List;

public interface IPacienteDAO {

    void salvar(Paciente paciente);

    void atualizar(Paciente paciente);

    void eliminar(Long id);

    Paciente buscarPorId(Long id);

    List<Paciente> listarTodos();
}