/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.exception;

import java.util.List;

/**
 *
 * @author administrator
 */
public class ValidacaoException extends SistemaException{

    private final List<String> erros;

    public ValidacaoException(String erro) {
        this(List.of(erro));
    }

    public ValidacaoException(List<String> erros) {
        super(String.join("\n", erros));
        this.erros = List.copyOf(erros);
    }

    public List<String> getErros() {
        return erros;
    }
}
