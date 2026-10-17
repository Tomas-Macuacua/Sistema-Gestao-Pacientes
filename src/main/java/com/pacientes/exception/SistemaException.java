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
 * Raiz de todas as excepções do sistema. As telas só precisam de apanhar
 * esta classe para tratar qualquer erro "esperado" da aplicação.
 */
public class SistemaException extends RuntimeException{
    
    public SistemaException(String mensagem){
        super(mensagem);
    }
    
    public SistemaException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}
