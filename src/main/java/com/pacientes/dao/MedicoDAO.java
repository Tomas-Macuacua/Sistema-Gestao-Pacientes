/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;

import com.pacientes.model.Medico;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author administrator
 */
public class MedicoDAO extends GenericDAO<Medico> implements IMedicoDAO{
    public MedicoDAO(){
        super(Medico.class);
    }
    
    @Override
    public Optional<Medico> buscarPorNumeroOrdem(String numeroOrdem){
        return consultarUnico(
                "FROM Medico m WHERE m.numeroOrdem = ?1",
                numeroOrdem
        );
    }
    
    @Override
    public List<Medico> pesquisarPorNome(String nome){
        return consultar(
                "FROM Medico m WHERE LOWER(m.nome) LIKE ?1 ORDER BY m.nome",
                Medico.class,
                contem(nome));
    }
    
    @Override
    public List<Medico> listarPorEspecialidade(String especialidade){
        return consultar(
                "FROM Medico m WHERE LOWER(m.especialidade) LIKE ?1 ORDER BY m.nome",
                Medico.class,
                contem(especialidade));
    }
}
