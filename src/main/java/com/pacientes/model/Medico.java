/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.model;

import com.pacientes.enums.Sexo;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 *
 * @author administrator
 */
@Entity
@Table(name = "medicos")
public class Medico extends Pessoa {
    
    @Column(name = "numero_ordem", nullable = false, unique = true, length = 30)
    private String numeroOrdem;
    
    @Column(nullable = false, length = 80)
    private String especialidade;
    
    public Medico(){}

    public Medico(String nome, LocalDate dataNascimento, Sexo sexo, String telefone, String endereco, String especialidade, String numeroOrdem) {
        super(nome, dataNascimento, sexo, telefone, endereco);
        this.numeroOrdem = numeroOrdem;
        this.especialidade = especialidade;
    }
    
    @Override
    public String getTipo(){
        return "Medico";
    }
    
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getNumeroOrdem() { return numeroOrdem; }
    public void setNumeroOrdem(String numeroOrdem) { this.numeroOrdem = numeroOrdem; } 
}
