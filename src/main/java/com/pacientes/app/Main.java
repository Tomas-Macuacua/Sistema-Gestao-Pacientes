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
        
        // Gravar um paciente e um médico
        PacienteController pacientes = new PacienteController();
        MedicoController medicos = new MedicoController();
        UtilizadorController utilizadores = new UtilizadorController();
        ConsultaController consultas = new ConsultaController();
        //MedicoDAO medicoDAO = new MedicoDAO();
        
        // =========== CREATE ============
        System.out.println("=== CREATE ===");
        Paciente paciente = new Paciente(
                "Jão Manuel",
                LocalDate.of(2000, 5, 10),
                Sexo.MASCULINO,
                "841234567",
                "Maputo",
                "P-" + agora
        );
        paciente.setGrupoSanguineo("O+");
        pacientes.cadastrar(paciente);
        
        Medico medico = new Medico(
                "Ana Machava",
                LocalDate.of(1985, 3, 22),
                Sexo.FEMENINO,
                "84241798",
                "Matola",
                "Clínica Geral",
                "OM-" + agora
        );
        medicos.cadastrar(medico);
        
        Utilizador utilizador = new Utilizador("Ana" + agora, "hash-provisorio", Perfil.MEDICO);
        utilizador.setMedico(medico);
        utilizadores.cadastrar(utilizador);
        
        Consulta consulta = new Consulta(
                paciente,
                medico,
                LocalDateTime.now().plusDays(1),
                "Dor de cabeça"
        );
        consultas.cadastrar(consulta);
        
        System.out.println("Criaddos: paciente id=" + paciente.getId()
                + ", médico id=" + medico.getId()
                + ", utilizador id=" + utilizador.getId()
                + ", consulta id=" + consulta.getId()
        );
        
        // =========== READ ============
        System.out.println("\n=== READ ===");
        Paciente lido = pacientes.buscar(paciente.getId());
        System.out.println(lido.getResumo());
        
        // =========== UPADTE ============
        System.out.println("\n=== UPDATE ===");
        lido.setTelefone("839999999");
        pacientes.atualizar(lido);
        System.out.println("Novo telefone: " + pacientes.buscar(paciente.getId()));
        
        // =========== PESQUISAS ============
        System.out.println("\n=== PESQUISAS ===");
        System.out.println("Pacientes com 'joão': " + pacientes.pesquisarPorNome("joão").size());
        System.out.println("Médicos de 'clínica:' " + medicos.listarPorEspeialidade("clínica"));
        utilizadores.buscarPorUsername(utilizador.getUsername())
                .ifPresent(u -> System.out.println("Login encontrdo: " + u.getResumo()));
        consultas.listarPorPaciente(paciente.getId())
                .forEach(c -> System.out.println(c.getResumo()));
        
        //=========== DELETE ============
        System.out.println("\n=== DELETE ===");
        try{
            pacientes.eliminar(paciente.getId());
            System.out.println("(não deveria chegar aqui)");
        }catch(DAOException e){
            System.out.println("Bloqueado, como esperado: o paciente tem consultas. "
                    + e.getMessage()
            );
        }
        
        // Ordem correcta: Preimiro quem depnde, depois quem é referencido
        consultas.eliminar(consulta.getId());
        utilizadores.eliminar(utilizador.getId());
        pacientes.eliminar(paciente.getId());
        medicos.eliminar(medico.getId());
        
        System.out.println("Paciente depois de eliminado: " + pacientes.buscar((paciente.getId())));
        
        /*
        // Polimorfismo por heranca: uma lista de Pessoa
        System.out.println("=== Pessoas ===");
        List<Pessoa> pessoas = new PessoaDAO().listarTodos();
        for (Pessoa p : pessoas){
            System.out.println(p.getTipo() + " -> " + p.getResumo());
        }
        
        // Polimorfismo por interface: classes sem relacao entre si
        System.out.println("\n=== Descritivel ===");
        List<Descritivel> itens = new ArrayList<>(pessoas);
        itens.add(new Consulta(paciente, medico, LocalDateTime.now().plusDays(1), "Dor de cabeça"));
        itens.add(new Utilizador("admin", "hash-provisorio", Perfil.ADMIN));
        for (Descritivel d : itens) {
            System.out.println(d.getResumo());
        }
        */
    }
}