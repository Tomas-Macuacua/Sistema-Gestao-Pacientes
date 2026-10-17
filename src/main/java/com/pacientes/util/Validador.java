/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.util;

import com.pacientes.exception.ValidacaoException;
import com.pacientes.model.Consulta;
import com.pacientes.model.Medico;
import com.pacientes.model.Paciente;
import com.pacientes.model.Pessoa;
import com.pacientes.model.Utilizador;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author administrator
 */
public class Validador {
    
    private static final int IDADE_MAXIMA = 130;
    private static final int IDADE_MINIMA_MEDICO = 22;
    
    //Telemovel de Moçambique: 82..87 + 7 digitos, com ou sem +258
    private static final Pattern TELEFONE = Pattern.compile("^(\\+?258)?8[2-7]\\d{7}$");
    private static final Pattern NOME = Pattern.compile("^\\p{L}[\\p{L} .'-]{2,99}$");
    private static final Pattern GRUPO_SANGUINEO = Pattern.compile("^(A|B|AB|O)[+-]$");
    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9._]{3,50}$");
    
    private Validador(){
        // Classe utilitária: não se instancia
    }
    
    /*
    Validações por entidade. Cada uma junta TODOS os erros e, se
    houver algum, lança uma unica ValidacaoException.
    */
    
    public static void validarPaciente(Paciente p){
        List<String> erros = new ArrayList<>();
        if(p == null){
            throw new ValidacaoException("Paciente não informado.");
        }
        validarPessoa(p, erros);
        
        if(vazio(p.getNumeroProcesso())){
            erros.add("O numero de processo é obrigatório.");
        }
        if(!vazio(p.getGrupoSanguineo())
            && !GRUPO_SANGUINEO.matcher(p.getGrupoSanguineo().trim()).matches()){
            erros.add("Grupo sanguineo inválido (use A+, A-, B+, B-, AB+, AB-, O+ ou O-).");
        }
        lancarSeHouverErros(erros);
    }
    
    public static void validarMedico(Medico m){
        List<String> erros = new ArrayList<>();
        if(m == null){
            throw new ValidacaoException("Medico não informado");
        }
        
        validarPessoa(m, erros);
        
        if(m.getDataNascimento() != null && m.getIdade() < IDADE_MINIMA_MEDICO
                && !m.getDataNascimento().isAfter(LocalDate.now())){
            erros.add("O médico deve ter pelo menos " + IDADE_MINIMA_MEDICO + " anos");
        }
        if(vazio(m.getEspecialidade())){
            erros.add("A specialidade é obrigatória.");
        }
        if (vazio(m.getNumeroOrdem())) {
            erros.add("O número da Ordem dos Médicos é obrigatório.");
        }
        lancarSeHouverErros(erros);
    }
    
    public static void validarUtilizador(Utilizador u){
        List<String> erros = new ArrayList<>();
        if(u == null){
            throw new ValidacaoException("Utilizador não informado.");
        }
        if (vazio(u.getUsername()) || !USERNAME.matcher(u.getUsername().trim()).matches()) {
            erros.add("O username deve ter 3 a 50 caracteres (letras, números, ponto ou _).");
        }
        if (vazio(u.getSenhaHash())) {
            erros.add("A palavra-passe é obrigatória.");
        }
        if (u.getPerfil() == null) {
            erros.add("O perfil é obrigatório.");
        } else if (u.getPerfil() == com.pacientes.enums.Perfil.MEDICO && u.getMedico() == null) {
            erros.add("Um utilizador com perfil MEDICO tem de estar associado a um médico.");
        }
        lancarSeHouverErros(erros);
    }
    
    public static void validarConsulta(Consulta c, boolean nova){
        List<String> erros = new ArrayList<>();
        if(c == null){
            throw new ValidacaoException("Consulta não informada.");
        }
        if (c.getPaciente() == null) {
            erros.add("O paciente é obrigatório.");
        }
        if (c.getMedico() == null) {
            erros.add("O médico é obrigatório.");
        }
        if (c.getDataHora() == null) {
            erros.add("A data e hora são obrigatórias.");
        } else if (nova && !c.getDataHora().isAfter(LocalDateTime.now())) {
            erros.add("A consulta tem de ser marcada para uma data e hora futuras.");
        }
        if (vazio(c.getMotivo())) {
            erros.add("O motivo da consulta é obrigatório.");
        }
        lancarSeHouverErros(erros);
    }
    
    /*
        =============================
        |        AUXILIARES         |
        =============================
    */
    
    /**
     * Regras comuns a qualquer Pessoa (Paciente ou Medico).
     */
    private static void validarPessoa(Pessoa p, List<String> erros){
        if(vazio(p.getNome()) || !NOME.matcher(p.getNome().trim()).matches()){
            erros.add("O nome é obrigatorio (minimo 3 letras, sem números).");
        }
        
        LocalDate nasc = p.getDataNascimento();
        if(nasc == null){
            erros.add("A data de nascimento é obrigatoria");
        }else if(nasc.isAfter(LocalDate.now())){
            erros.add("A data de nascimento não pode ser futura");
        }else if(p.getIdade() > IDADE_MAXIMA){
            erros.add("A data de nascimento é invalida (idade superior a " + IDADE_MAXIMA + " anos)");
        }
        
        if(p.getSexo() == null){
            erros.add("O sexo é obrigatório");
        }
        
        if (!vazio(p.getTelefone()) && !telefoneValido(p.getTelefone())) {
            erros.add("Telefone inválido (ex.: 84 123 4567 ou +258841234567).");
        }
        
    }
    
    public static boolean telefoneValido(String telefone) {
        return telefone != null && TELEFONE.matcher(telefone.replaceAll("\\s", "")).matches();
    }
    
    public static boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }
    
    private static void lancarSeHouverErros(List<String> erros){
        if(!erros.isEmpty()){
            throw new ValidacaoException(erros);
        }
    }
}

