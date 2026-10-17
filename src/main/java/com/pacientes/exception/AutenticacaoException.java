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
 * Falha no login (credenciais erradas, conta desactivada...).
 */
public class AutenticacaoException extends SistemaException {

    public AutenticacaoException(String mensagem) {
        super(mensagem);
    }
}