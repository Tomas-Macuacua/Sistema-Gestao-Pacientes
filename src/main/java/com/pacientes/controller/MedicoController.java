/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;

import com.pacientes.dao.IMedicoDAO;
import com.pacientes.dao.MedicoDAO;
import com.pacientes.model.Medico;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author administrator
 */
public class MedicoController {
    
    private final IMedicoDAO medicoDAO;
    
    public MedicoController(){
        medicoDAO = new MedicoDAO();
    }
    
    public void cadastrar(Medico medico){
        medicoDAO.salvar(medico);
    }
    
    public void atualizar(Medico medico){
        medicoDAO.atualizar(medico);
    }
    
    public void eliminar(Long id){
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
}
