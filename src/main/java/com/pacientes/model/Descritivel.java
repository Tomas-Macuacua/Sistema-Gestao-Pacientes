/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pacientes.model;

/**
 *
 * @author administrator
 */

/**
 * 
 * Contrato para qualquer objecto que saiba produzir um resumo de si proprio numa linha
 * Usado em listas, relatórios e no dashboard.
 */
public interface Descritivel {
    String getResumo();
}
