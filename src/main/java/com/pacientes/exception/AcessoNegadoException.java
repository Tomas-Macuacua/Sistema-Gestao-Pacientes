/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.exception;

/**
 *
 * @author administrator
 */
/**
 * O utilizador não tem permissão para a operação pedida.
 */
public class AcessoNegadoException extends SistemaException {

    public AcessoNegadoException(String mensagem) {
        super(mensagem);
    }
}
