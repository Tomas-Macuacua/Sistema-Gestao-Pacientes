/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;

import java.util.List;

/**
 *
 * @author administrator
 */
// Interface DAO Generica
public interface IGenericDAO<T, ID> {
    
    void salvar(T entidade);
    
    void atualizar(T entidade);
    
    void eliminar (ID id);
    
    T buscarPorId(ID id);
    
    List<T> listarTodos();
    
}
