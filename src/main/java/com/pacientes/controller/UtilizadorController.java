/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;

import com.pacientes.dao.IUtilizadorDAO;
import com.pacientes.dao.UtilizadorDAO;
import com.pacientes.model.Utilizador;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author administrator
 */
public class UtilizadorController {
    
    private final IUtilizadorDAO utilizadorDAO;
    
    public UtilizadorController(){
        utilizadorDAO = new UtilizadorDAO();
    }
    
    public void cadastrar(Utilizador utilizador){
        utilizadorDAO.salvar(utilizador);
    }
    
    public void atualizar(Utilizador utilizador){
        utilizadorDAO.atualizar(utilizador);
    }
    
    public void eliminar(Long id){
        utilizadorDAO.eliminar(id);
    }
    
    public Utilizador buscar(Long id){
        return utilizadorDAO.buscarPorId(id);
    }
    
    public Optional<Utilizador> buscarPorUsername(String username){
        return utilizadorDAO.buscarPorUsername(username);
    }
    
    public List<Utilizador> listar(){
        return utilizadorDAO.listarTodos();
    }
}
