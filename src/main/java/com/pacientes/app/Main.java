package com.pacientes.app;

import com.pacientes.controller.ConsultaController;
import com.pacientes.controller.MedicoController;
import com.pacientes.controller.PacienteController;
import com.pacientes.controller.UtilizadorController;
import com.pacientes.dao.MedicoDAO;
import com.pacientes.dao.PessoaDAO;
import com.pacientes.enums.Perfil;
import com.pacientes.enums.Sexo;
import com.pacientes.exception.DAOException;
import com.pacientes.exception.SistemaException;
import com.pacientes.model.Consulta;
import com.pacientes.model.Descritivel;
import com.pacientes.model.Medico;
import com.pacientes.model.Paciente;
import com.pacientes.model.Pessoa;
import com.pacientes.model.Utilizador;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        
        long agora = System.currentTimeMillis();
        PacienteController pacientes = new PacienteController();
        MedicoController medicos = new MedicoController();
        ConsultaController consultas = new ConsultaController();
        
        LocalDateTime amanha9h = LocalDate.now().plusDays(1).atTime(9, 0);
        
        // ============== Validações ==============
        
        System.out.println("=== VALIDAÇÕES ===");
        Paciente invalido = new Paciente("A1", null, null, "123", "Maputo", " ");
        tentar("Paciente com dados invalidos", () -> pacientes.cadastrar(invalido));
        
        Paciente paciente = new Paciente("João Manuel", LocalDate.of(2000, 5, 10),
                Sexo.MASCULINO, "84 123 4567", "Maputo", "P-"+agora);
        tentar("Paciente, válido", () -> pacientes.cadastrar(paciente));
        
        Paciente duplicado = new Paciente("Maria José", LocalDate.of(1995, 1, 2),
                Sexo.FEMENINO, "861234567", "Matola", paciente.getNumeroProcesso());
        tentar("Mesmo número de processo", () -> pacientes.cadastrar(duplicado));
        
        Medico medico = new Medico("Ana Machava", LocalDate.of(1985, 3, 22),
                Sexo.FEMENINO, "872345679", "Matola", "Clínica Geral", "OM-" + agora);
        tentar("Médico válido", () -> medicos.cadastrar(medico));
        
        
        // ---------- Regras de negócio ----------
        System.out.println("\n=== REGRAS DE NEGÓCIO ===");
        Consulta c1 = new Consulta(paciente, medico, amanha9h, "Dor de cabeça");
        tentar("Agendar 09:00", () -> consultas.agendar(c1));
        
        Consulta c2 = new Consulta(paciente, medico, amanha9h.plusMinutes(15), "Febre");
        tentar("Agendar 09:15 (sobrepõe)", () -> consultas.agendar(c2));
        
        Consulta c3 = new Consulta(paciente, medico, amanha9h.plusMinutes(30), "Febre");
        tentar("Agendar 09:30 (livre)", () -> consultas.agendar(c3));
                
         Consulta passada = new Consulta(paciente, medico, LocalDateTime.now().minusDays(1), "Tosse");
        tentar("Agendar no passado", () -> consultas.agendar(passada));
        
        tentar("Realizar sem diagnóstico", () -> consultas.realizar(c1.getId(), "  "));
        tentar("Realizar com diagnóstico", () -> consultas.realizar(c1.getId(), "Enxaqueca ligeira"));
        tentar("Cancelar consulta já realizada", () -> consultas.cancelar(c1.getId()));
        tentar("Cancelar consulta agendada (09:30)", () -> consultas.cancelar(c3.getId()));
        tentar("Eliminar paciente com consultas", () -> pacientes.eliminar(paciente.getId()));
        tentar("Eliminar médico com consultas", () -> medicos.eliminar(medico.getId()));
        
        // ---------- Limpeza ----------
        System.out.println("\n=== LIMPEZA ===");
        consultas.listarPorPaciente(paciente.getId())
                .forEach(c -> consultas.eliminar(c.getId()));
        tentar("Eliminar paciente (já sem consultas)", () -> pacientes.eliminar(paciente.getId()));
        tentar("Eliminar médico (já sem consultas)", () -> medicos.eliminar(medico.getId()));
    
    }
    
    /** Executa a acção e mostra se passou ou foi bloqueada pelo sistema. */
    private static void tentar(String titulo, Runnable acao) {
        try {
            acao.run();
            System.out.println("[OK]        " + titulo);
        } catch (SistemaException e) {
            System.out.println("[BLOQUEADO] " + titulo + " -> "
                    + e.getMessage().replace("\n", " | "));
        }
    }
}