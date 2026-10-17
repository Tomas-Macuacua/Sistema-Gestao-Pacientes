/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;

import com.pacientes.dao.IUtilizadorDAO;
import com.pacientes.dao.UtilizadorDAO;
import com.pacientes.enums.Perfil;
import com.pacientes.exception.AutenticacaoException;
import com.pacientes.model.Utilizador;
import com.pacientes.util.Seguranca;
import com.pacientes.util.Sessao;
import com.pacientes.util.Validador;

/**
 *
 * @author administrator
 */
public class AuthController {
    
    public static final String ADMIN_INICIAL = "admin";
    public static final String SENHA_INICIAL = "Admin123";
    
    private final IUtilizadorDAO utilizadorDAO = new UtilizadorDAO();

    /**
     * Se a base de dados ainda não tem nenhum utilizador, cria o administrador
     * inicial (sem ele ninguém conseguiria entrar para criar os restantes).
     * Chamar no arranque da aplicação.
     */
    public void garantirAdminInicial() {
        if (utilizadorDAO.contarTodos() == 0) {
            Utilizador admin = new Utilizador(
                    ADMIN_INICIAL, Seguranca.gerarHash(SENHA_INICIAL), Perfil.ADMIN);
            utilizadorDAO.salvar(admin);
        }
    }
    
    public Utilizador login(String username, String senha) {
        if (Validador.vazio(username) || Validador.vazio(senha)) {
            throw new AutenticacaoException("Informe o username e a palavra-passe.");
        }

        Utilizador utilizador = utilizadorDAO.buscarPorUsername(username.trim()).orElse(null);

        // Mesma mensagem para "não existe" e "senha errada": não revela quais username existem
        if (utilizador == null || !Seguranca.verificar(senha, utilizador.getSenhaHash())) {
            throw new AutenticacaoException("Username ou palavra-passe incorrectos.");
        }
        if (!utilizador.isAtivo()) {
            throw new AutenticacaoException("Esta conta está desactivada. Contacte o administrador.");
        }

        Sessao.iniciar(utilizador);
        return utilizador;
    }
    
    public void logout() {
        Sessao.terminar();
    }

    /** O próprio utilizador autenticado muda a sua palavra-passe. */
    public void alterarSenha(String senhaActual, String senhaNova) {
        Sessao.exigirAutenticado();
        Utilizador utilizador = Sessao.getUtilizador();

        if (!Seguranca.verificar(senhaActual, utilizador.getSenhaHash())) {
            throw new AutenticacaoException("A palavra-passe actual está incorrecta.");
        }
        Seguranca.validarForca(senhaNova);

        utilizador.setSenhaHash(Seguranca.gerarHash(senhaNova));
        utilizadorDAO.atualizar(utilizador);
    }
}
