/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;

import com.pacientes.dao.IUtilizadorDAO;
import com.pacientes.dao.UtilizadorDAO;
import com.pacientes.exception.RegraNegocioException;
import com.pacientes.exception.ValidacaoException;
import com.pacientes.model.Utilizador;
import com.pacientes.util.Validador;
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
        Validador.validarUtilizador(utilizador);
        verificarUsernameUnico(utilizador);
        utilizadorDAO.salvar(utilizador);
    }
    
    public void atualizar(Utilizador utilizador){
        if (utilizador == null || utilizador.getId() == null) {
            throw new ValidacaoException("Seleccione um utilizador existente para actualizar.");
        }
        Validador.validarUtilizador(utilizador);
        verificarUsernameUnico(utilizador);
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
    
    private void verificarUsernameUnico(Utilizador utilizador) {
        String username = utilizador.getUsername().trim();
        utilizadorDAO.buscarPorUsername(username).ifPresent(existente -> {
            if (!existente.getId().equals(utilizador.getId())) {
                throw new RegraNegocioException("O username '" + username + "' já está em uso.");
            }
        });
    }
}
