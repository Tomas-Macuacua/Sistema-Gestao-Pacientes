package com.pacientes.app;

import com.pacientes.controller.PacienteController;
import com.pacientes.model.Paciente;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        PacienteController controller =
                new PacienteController();

        Paciente paciente = new Paciente(
                "João Manuel",
                LocalDate.of(2000, 5, 10),
                "Masculino",
                "841234567",
                "Maputo"
        );

        controller.cadastrar(paciente);

        System.out.println("Paciente cadastrado!");

    }
}