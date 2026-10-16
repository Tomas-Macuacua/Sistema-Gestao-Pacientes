package com.pacientes.app;

import com.pacientes.controller.PacienteController;
import com.pacientes.enums.Sexo;
import com.pacientes.model.Paciente;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        PacienteController controller =
                new PacienteController();

        Paciente paciente = new Paciente(
                "João Manuel",
                LocalDate.of(2000, 5, 10),
                Sexo.MASCULINO,
                "841234567",
                "Maputo",
                "P-" + System.currentTimeMillis()
        );

        controller.cadastrar(paciente);

        for (Paciente p : controller.listar()){
            System.out.println(p + " | " + p.getIdade() + " anos | " + p.getNumeroProcesso());
        }

    }
}