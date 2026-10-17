/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.util;

import com.pacientes.enums.Permissao;
import com.pacientes.exception.AcessoNegadoException;
import com.pacientes.model.Utilizador;

/**
 *
 * @author administrator
 */
/**
 * Guarda quem está autenticado neste momento. Como é uma aplicação de
 * secretária (um utilizador de cada vez), basta um estado estático.
 */
public class Sessao {
    
    private static Utilizador utilizadorAtual;

    private Sessao() {
    }

    public static void iniciar(Utilizador utilizador) {
        utilizadorAtual = utilizador;
    }

    public static void terminar() {
        utilizadorAtual = null;
    }

    public static Utilizador getUtilizador() {
        return utilizadorAtual;
    }

    public static boolean estaAutenticado() {
        return utilizadorAtual != null;
    }
    
    /** Para as telas esconderem ou desactivarem botões (não lança excepção). */
    public static boolean pode(Permissao permissao) {
        return utilizadorAtual != null && utilizadorAtual.getPerfil().temPermissao(permissao);
    }

    public static void exigirAutenticado() {
        if (utilizadorAtual == null) {
            throw new AcessoNegadoException("Inicie sessão para continuar.");
        }
    }
    
    /** Para os controllers bloquearem operações sem permissão. */
    public static void exigir(Permissao permissao) {
        exigirAutenticado();
        if (!utilizadorAtual.getPerfil().temPermissao(permissao)) {
            throw new AcessoNegadoException("O perfil " + utilizadorAtual.getPerfil()
                    + " não tem permissão para esta operação.");
        }
    }
}
