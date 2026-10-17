/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.controller;

import com.pacientes.dao.ConsultaDAO;
import com.pacientes.dao.IConsultaDAO;
import com.pacientes.enums.EstadoConsulta;
import com.pacientes.enums.Perfil;
import com.pacientes.enums.Permissao;
import com.pacientes.exception.AcessoNegadoException;
import com.pacientes.exception.RegraNegocioException;
import com.pacientes.exception.ValidacaoException;
import com.pacientes.model.Consulta;
import com.pacientes.model.Utilizador;
import com.pacientes.util.Sessao;
import com.pacientes.util.Validador;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author administrator
 */
public class ConsultaController {
    
    /** Cada consulta ocupa o médico durante este número de minutos. */
    public static final int DURACAO_MINUTOS = 30;

    private final IConsultaDAO consultaDAO = new ConsultaDAO();

    // ---------- Funcionalidades de negócio ----------

    public void agendar(Consulta consulta) {
        Sessao.exigir(Permissao.AGENDAR_CONSULTAS);
        Validador.validarConsulta(consulta, true);
        verificarConflito(consulta);
        consultaDAO.salvar(consulta);
    }

    public void cancelar(Long id) {
        Sessao.exigir(Permissao.AGENDAR_CONSULTAS);
        Consulta consulta = obterExistente(id);
        consulta.cancelar();
        consultaDAO.atualizar(consulta);
    }

    /** Só o médico da consulta pode registar o diagnóstico. */
    public void realizar(Long id, String diagnostico) {
        Sessao.exigir(Permissao.REGISTAR_DIAGNOSTICO);
        Consulta consulta = obterExistente(id);

        if (!pertenceAoUtilizador(consulta)) {
            throw new AcessoNegadoException(
                    "Só pode registar o diagnóstico das suas próprias consultas.");
        }
        consulta.realizar(diagnostico);
        consultaDAO.atualizar(consulta);
    }

    // ---------- CRUD ----------

    public void atualizar(Consulta consulta) {
        Sessao.exigir(Permissao.AGENDAR_CONSULTAS);
        if (consulta == null || consulta.getId() == null) {
            throw new ValidacaoException("Seleccione uma consulta existente para actualizar.");
        }
        Validador.validarConsulta(consulta, false);
        if (consulta.getEstado() != EstadoConsulta.CANCELADA) {
            verificarConflito(consulta);
        }
        consultaDAO.atualizar(consulta);
    }

    public void eliminar(Long id) {
        Sessao.exigir(Permissao.AGENDAR_CONSULTAS);
        consultaDAO.eliminar(id);
    }

    public Consulta buscar(Long id) {
        Sessao.exigir(Permissao.VER_CONSULTAS);
        Consulta consulta = consultaDAO.buscarPorId(id);
        if (consulta != null && !visivel(consulta)) {
            throw new AcessoNegadoException("Não tem acesso a esta consulta.");
        }
        return consulta;
    }

    // As listagens passam por "filtrarVisiveis": o MÉDICO só vê as suas consultas.

    public List<Consulta> listarPorPaciente(Long pacienteId) {
        Sessao.exigir(Permissao.VER_CONSULTAS);
        return filtrarVisiveis(consultaDAO.listarPorPaciente(pacienteId));
    }

    public List<Consulta> listarPorMedico(Long medicoId) {
        Sessao.exigir(Permissao.VER_CONSULTAS);
        return filtrarVisiveis(consultaDAO.listarPorMedico(medicoId));
    }

    public List<Consulta> listarPorEstado(EstadoConsulta estado) {
        Sessao.exigir(Permissao.VER_CONSULTAS);
        return filtrarVisiveis(consultaDAO.listarPorEstado(estado));
    }

    public List<Consulta> listar() {
        Sessao.exigir(Permissao.VER_CONSULTAS);
        return filtrarVisiveis(consultaDAO.listarTodos());
    }

    // ---------- Auxiliares ----------

    private Consulta obterExistente(Long id) {
        Consulta consulta = (id == null) ? null : consultaDAO.buscarPorId(id);
        if (consulta == null) {
            throw new RegraNegocioException("A consulta não foi encontrada.");
        }
        return consulta;
    }

    /** A consulta é do médico que está autenticado? */
    private boolean pertenceAoUtilizador(Consulta consulta) {
        Utilizador utilizador = Sessao.getUtilizador();
        return utilizador.getMedico() != null
                && utilizador.getMedico().getId().equals(consulta.getMedico().getId());
    }

    /** Admin e recepcionista vêem tudo. O médico só vê as suas. */
    private boolean visivel(Consulta consulta) {
        if (Sessao.getUtilizador().getPerfil() == Perfil.MEDICO) {
            return pertenceAoUtilizador(consulta);
        }
        return true;
    }

    private List<Consulta> filtrarVisiveis(List<Consulta> consultas) {
        return consultas.stream().filter(this::visivel).toList();
    }

    /** Regra: o mesmo médico não pode ter duas consultas sobrepostas. */
    private void verificarConflito(Consulta consulta) {
        LocalDateTime inicio = consulta.getDataHora().minusMinutes(DURACAO_MINUTOS);
        LocalDateTime fim = consulta.getDataHora().plusMinutes(DURACAO_MINUTOS);
        Long ignorarId = (consulta.getId() != null) ? consulta.getId() : -1L;

        if (consultaDAO.existeConflito(consulta.getMedico().getId(), inicio, fim, ignorarId)) {
            throw new RegraNegocioException("O médico já tem uma consulta marcada nesse horário "
                    + "(cada consulta dura " + DURACAO_MINUTOS + " minutos).");
        }
    }
}
