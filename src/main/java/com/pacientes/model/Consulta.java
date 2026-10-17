/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.model;

import com.pacientes.enums.EstadoConsulta;
import com.pacientes.exception.RegraNegocioException;
import com.pacientes.exception.ValidacaoException;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author administrator
 */
@Entity
@Table(name = "consultas")
public class Consulta implements Descritivel{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
    
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;
    
    @Column(length = 255)
    private String motivo;
    
    @Column(length = 1000)
    private String diagnostico;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoConsulta estado = EstadoConsulta.AGENDADA;
    
    public Consulta(){}

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String motivo) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.motivo = motivo;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public EstadoConsulta getEstado() { return estado; }
    public void setEstado(EstadoConsulta estado) { this.estado = estado; }
    
    /**
     * Regra: só se canela uma consulta que ainda está agendada.
     */
    public void cancelar() {
        if (estado != EstadoConsulta.AGENDADA) {
            throw new RegraNegocioException(
                    "Só é possível cancelar consultas agendadas (estado actual: " + estado + ").");
        }
        estado = EstadoConsulta.CANCELADA;
    }
    
    /**
     * Regra: só se realiza uma consulta agendada e com diagnóstico.
     */
    public void realizar(String diagnostico) {
        if (estado != EstadoConsulta.AGENDADA) {
            throw new RegraNegocioException(
                    "Só é possível realizar consultas agendadas (estado actual: " + estado + ").");
        }
        if (diagnostico == null || diagnostico.isBlank()) {
            throw new ValidacaoException("O diagnóstico é obrigatório para concluir a consulta.");
        }
        this.diagnostico = diagnostico.trim();
        this.estado = EstadoConsulta.REALIZADA;
    }
    
    @Override
    public String getResumo(){
        return "Consulta " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                + " | " + paciente.getNome()
                + " com Dr(a). " + medico.getNome()
                + " | " + estado;
    }
}
