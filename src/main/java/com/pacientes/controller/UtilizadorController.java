/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;

import com.pacientes.dao.IUtilizadorDAO;
import com.pacientes.dao.UtilizadorDAO;
import com.pacientes.enums.Perfil;
import com.pacientes.enums.Permissao;
import com.pacientes.exception.RegraNegocioException;
import com.pacientes.exception.ValidacaoException;
import com.pacientes.model.Utilizador;
import com.pacientes.util.Seguranca;
import com.pacientes.util.Sessao;
import com.pacientes.util.Validador;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author administrator
 */
public class UtilizadorController {
    
    private final IUtilizadorDAO utilizadorDAO = new UtilizadorDAO();

    /** A palavra-passe chega em texto e é guardada apenas como hash. */
    public void cadastrar(Utilizador utilizador, String senhaPlana) {
        Sessao.exigir(Permissao.GERIR_UTILIZADORES);
        Seguranca.validarForca(senhaPlana);
        utilizador.setSenhaHash(Seguranca.gerarHash(senhaPlana));

        Validador.validarUtilizador(utilizador);
        verificarUsernameUnico(utilizador);
        utilizadorDAO.salvar(utilizador);
    }

    /** Actualiza username, perfil, estado... A palavra-passe NÃO muda aqui. */
    public void atualizar(Utilizador utilizador) {
        Sessao.exigir(Permissao.GERIR_UTILIZADORES);
        if (utilizador == null || utilizador.getId() == null) {
            throw new ValidacaoException("Seleccione um utilizador existente para actualizar.");
        }
        Validador.validarUtilizador(utilizador);
        verificarUsernameUnico(utilizador);
        protegerUltimoAdmin(utilizador);
        utilizadorDAO.atualizar(utilizador);
    }

    /** O administrador define uma palavra-passe nova a outro utilizador. */
    public void redefinirSenha(Long id, String senhaNova) {
        Sessao.exigir(Permissao.GERIR_UTILIZADORES);
        Seguranca.validarForca(senhaNova);

        Utilizador utilizador = obterExistente(id);
        utilizador.setSenhaHash(Seguranca.gerarHash(senhaNova));
        utilizadorDAO.atualizar(utilizador);
    }

    public void eliminar(Long id) {
        Sessao.exigir(Permissao.GERIR_UTILIZADORES);

        if (id != null && id.equals(Sessao.getUtilizador().getId())) {
            throw new RegraNegocioException("Não pode eliminar o seu próprio utilizador.");
        }
        Utilizador alvo = obterExistente(id);
        if (alvo.getPerfil() == Perfil.ADMIN && alvo.isAtivo()
                && utilizadorDAO.contarAtivosPorPerfil(Perfil.ADMIN) <= 1) {
            throw new RegraNegocioException("Tem de existir pelo menos um administrador activo.");
        }
        utilizadorDAO.eliminar(id);
    }

    public Utilizador buscar(Long id) {
        Sessao.exigir(Permissao.GERIR_UTILIZADORES);
        return utilizadorDAO.buscarPorId(id);
    }

    public Optional<Utilizador> buscarPorUsername(String username) {
        Sessao.exigir(Permissao.GERIR_UTILIZADORES);
        return utilizadorDAO.buscarPorUsername(username);
    }

    public List<Utilizador> listar() {
        Sessao.exigir(Permissao.GERIR_UTILIZADORES);
        return utilizadorDAO.listarTodos();
    }

    // ---------- Auxiliares ----------

    private Utilizador obterExistente(Long id) {
        Utilizador utilizador = (id == null) ? null : utilizadorDAO.buscarPorId(id);
        if (utilizador == null) {
            throw new RegraNegocioException("O utilizador não foi encontrado.");
        }
        return utilizador;
    }

    private void verificarUsernameUnico(Utilizador utilizador) {
        String username = utilizador.getUsername().trim();
        utilizadorDAO.buscarPorUsername(username).ifPresent(existente -> {
            if (!existente.getId().equals(utilizador.getId())) {
                throw new RegraNegocioException("O username '" + username + "' já está em uso.");
            }
        });
    }

    /** Regra: nunca ficar sem nenhum administrador activo. */
    private void protegerUltimoAdmin(Utilizador alterado) {
        Utilizador antes = obterExistente(alterado.getId());
        boolean eraAdminActivo = antes.getPerfil() == Perfil.ADMIN && antes.isAtivo();
        boolean continuaAdminActivo = alterado.getPerfil() == Perfil.ADMIN && alterado.isAtivo();

        if (eraAdminActivo && !continuaAdminActivo
                && utilizadorDAO.contarAtivosPorPerfil(Perfil.ADMIN) <= 1) {
            throw new RegraNegocioException("Tem de existir pelo menos um administrador activo.");
        }
    }
}
