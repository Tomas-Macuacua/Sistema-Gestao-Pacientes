package com.pacientes.app;

import com.pacientes.controller.AuthController;
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
import com.pacientes.util.Sessao;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        long agora = System.currentTimeMillis();

        AuthController auth = new AuthController();
        PacienteController pacientes = new PacienteController();
        MedicoController medicos = new MedicoController();
        UtilizadorController utilizadores = new UtilizadorController();
        ConsultaController consultas = new ConsultaController();

        // Objectos usados na demonstração
        Medico medico = new Medico("Ana Machava", LocalDate.of(1985, 3, 22),
                Sexo.FEMENINO, "842345678", "Matola", "Clínica Geral", "OM-" + agora);
        Utilizador uMedico = new Utilizador("ana" + agora, null, Perfil.MEDICO);
        uMedico.setMedico(medico);
        Utilizador uRecepcao = new Utilizador("rec" + agora, null, Perfil.RECEPCIONISTA);
        Utilizador uFraco = new Utilizador("fraco" + agora, null, Perfil.RECEPCIONISTA);
        Paciente paciente = new Paciente("João Manuel", LocalDate.of(2000, 5, 10),
                Sexo.MASCULINO, "84 123 4567", "Maputo", "P-" + agora);
        Consulta consulta = new Consulta(paciente, medico,
                LocalDate.now().plusDays(1).atTime(9, 0), "Dor de cabeça");

        // Arranque: garante que existe pelo menos o administrador inicial
        auth.garantirAdminInicial();

        System.out.println("=== LOGIN ===");
        tentar("Sem sessão: listar pacientes", () -> pacientes.listar());
        tentar("Login com palavra-passe errada", () -> auth.login("admin", "errada1"));
        tentar("Login como admin", () -> auth.login("admin", "Admin123"));

        System.out.println("\n=== ADMIN prepara os dados ===");
        tentar("Admin: cadastrar médico", () -> medicos.cadastrar(medico));
        tentar("Admin: criar utilizador MEDICO", () -> utilizadores.cadastrar(uMedico, "Medico123"));
        tentar("Admin: criar utilizador RECEPCIONISTA", () -> utilizadores.cadastrar(uRecepcao, "Recep123"));
        tentar("Admin: palavra-passe fraca (123)", () -> utilizadores.cadastrar(uFraco, "123"));
        tentar("Admin: registar diagnóstico (não é do perfil)", () -> consultas.realizar(1L, "x"));

        System.out.println("\n=== RECEPCIONISTA ===");
        tentar("Login como recepcionista", () -> auth.login(uRecepcao.getUsername(), "Recep123"));
        tentar("Recep: cadastrar paciente", () -> pacientes.cadastrar(paciente));
        tentar("Recep: agendar consulta", () -> consultas.agendar(consulta));
        tentar("Recep: criar utilizador", () -> utilizadores.cadastrar(uFraco, "Fraco123"));
        tentar("Recep: eliminar médico", () -> medicos.eliminar(medico.getId()));
        tentar("Recep: registar diagnóstico", () -> consultas.realizar(consulta.getId(), "Enxaqueca"));

        System.out.println("\n=== MÉDICO ===");
        tentar("Login como médico", () -> auth.login(uMedico.getUsername(), "Medico123"));
        tentar("Médico: ver pacientes", () -> pacientes.listar());
        tentar("Médico: cadastrar paciente", () -> pacientes.cadastrar(paciente));
        tentar("Médico: agendar consulta", () -> consultas.agendar(consulta));
        tentar("Médico: registar diagnóstico", () -> consultas.realizar(consulta.getId(), "Enxaqueca ligeira"));
        tentar("Médico: listar as suas consultas", () ->
                System.out.println("            -> " + consultas.listar().size() + " consulta(s) visível(is)"));

        System.out.println("\n=== LIMPEZA (como admin) ===");
        auth.logout();
        tentar("Login como admin", () -> auth.login("admin", "Admin123"));
        tentar("Admin: eliminar as consultas do paciente", () ->
                consultas.listarPorPaciente(paciente.getId()).forEach(c -> consultas.eliminar(c.getId())));
        tentar("Admin: eliminar paciente", () -> pacientes.eliminar(paciente.getId()));
        tentar("Admin: eliminar utilizador médico", () -> utilizadores.eliminar(uMedico.getId()));
        tentar("Admin: eliminar utilizador recepção", () -> utilizadores.eliminar(uRecepcao.getId()));
        tentar("Admin: eliminar médico", () -> medicos.eliminar(medico.getId()));
        tentar("Admin: eliminar o próprio utilizador", () ->
                utilizadores.eliminar(Sessao.getUtilizador().getId()));
        auth.logout();
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