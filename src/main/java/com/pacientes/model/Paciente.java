/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.model;

import com.pacientes.enums.Sexo;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
public class Paciente extends Pessoa {

    @Column (name = "numero_processo", nullable = false, unique = true, length = 30)
    private String numeroProcesso;
    
    @Column(name = "grupo_sanguineo", length = 3)
    private String grupoSanguineo;
    
    @Column(length = 255)
    private String alergias;
    
    
    public Paciente(){}
    
    public Paciente(String nome, LocalDate dataNascimento, Sexo sexo, String telefone, String endereco, String numeroProcesso){
        super(nome, dataNascimento, sexo, telefone, endereco);
        this.numeroProcesso = numeroProcesso;
    }
    
    @Override
    public String getTipo(){
        return "Paciente";
    }
    
    @Override
    public String getResumo(){
        return getNome() + " | " + 
                getIdade() + " anos | Processo " + numeroProcesso +
                (grupoSanguineo != null? " | Grupo " + grupoSanguineo : "");
    }
    
    public String getNumeroProcesso() { return numeroProcesso; }
    public void setNumeroProcesso(String numeroProcesso) { this.numeroProcesso = numeroProcesso; }

    public String getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(String grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }

    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
}