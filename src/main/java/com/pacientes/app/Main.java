package com.pacientes.app;

import com.pacientes.controller.PacienteController;
import com.pacientes.dao.MedicoDAO;
import com.pacientes.dao.PessoaDAO;
import com.pacientes.enums.Perfil;
import com.pacientes.enums.Sexo;
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
        PacienteController pacienteController = new PacienteController();
        MedicoDAO medicoDAO = new MedicoDAO();
        
        Paciente paciente = new Paciente(
                "Jão Manuel",
                LocalDate.of(2000, 5, 10),
                Sexo.MASCULINO,
                "841234567",
                "Maputo",
                "P-" + agora
        );
        paciente.setGrupoSanguineo("O+");
        pacienteController.cadastrar(paciente);
        
        Medico medico = new Medico(
                "Ana Machava",
                LocalDate.of(1985, 3, 22),
                Sexo.FEMENINO,
                "84241798",
                "Matola",
                "Clínica Geral",
                "OM-" + agora
        );
        medicoDAO.salvar(medico);
        
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
    }
}