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
 * Uma regra do negócio foi violada (ex: conflito de horário,
 * eleminar paciente com consultas, numero de processo repetido);
 */
public class RegraNegocioException extends SistemaException{
    
    public RegraNegocioException(String mensagem){
        super(mensagem);
    }
}
