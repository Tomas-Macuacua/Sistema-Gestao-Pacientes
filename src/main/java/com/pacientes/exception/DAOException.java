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
 * Erro ocorrido na camada de acesso aos dados (Hibernate/MySQL).
 */
public class DAOException extends SistemaException{
    
    public DAOException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}
