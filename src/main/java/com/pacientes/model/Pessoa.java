/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.model;

import com.pacientes.enums.Sexo;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author administrator
 */
@Entity
@Table(name = "pessoas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Sexo sexo;
    
    @Column(length = 15)
    private String telefone;
    
    @Column(length = 200)
    private String endereco;
    
    
    protected Pessoa(){}

    protected Pessoa(String nome, LocalDate dataNascimento, Sexo sexo, String telefone, String endereco) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.telefone = telefone;
        this.endereco = endereco;
    }
    
    // Método abstracto: cada subclasse responde a sua maneira (Polimorfismo)
    public abstract String getTipo();
    
    public int getIdade(){
        if (dataNascimento == null){
            return 0;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
    
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    @Override
    public String toString(){
        return getTipo() + " #" + id + " - " + nome;
    }
    
    
}
