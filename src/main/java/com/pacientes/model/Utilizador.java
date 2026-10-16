/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.model;

import com.pacientes.enums.Perfil;
import jakarta.persistence.*;

/**
 *
 * @author administrator
 */
@Entity
@Table(name = "utilizadores")
public class Utilizador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    
    @Column (name = "senha_hash", nullable = false, length = 100)
    private String senhaHash;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Perfil perfil;
    
    @Column(nullable = false)
    private boolean ativo = true;
    
    //Só preenchido quando o perfil é MEDICO
    @OneToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
    
    public Utilizador(){}

    public Utilizador(String username, String senhaHash, Perfil perfil) {
        this.username = username;
        this.senhaHash = senhaHash;
        this.perfil = perfil;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    
    @Override
    public String toString() {
        return username + " (" + perfil + ")";
    }
}
