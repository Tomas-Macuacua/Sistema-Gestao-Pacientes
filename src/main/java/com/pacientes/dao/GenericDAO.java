/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;

import com.pacientes.exception.DAOException;
import com.pacientes.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author administrator
 */

/**
 * 
 * Classe abstracta com implementação
 */
public abstract class GenericDAO<T> implements IGenericDAO<T, Long> {
    
    private final Class<T> classe;
    
    protected GenericDAO(Class<T> classe){
        this.classe = classe;
    }
    
    @Override
    public void salvar(T entidade){
        executarEmTransacao(session -> session.persist(entidade));
    }
    
    @Override
    public void atualizar(T entidade){
        executarEmTransacao(session -> session.merge(entidade));
    }
    
    @Override
    public void eliminar(Long id){
        executarEmTransacao(session -> {
            T entidade = session.get(classe, id);
            if (entidade != null){
                session.remove(entidade);
            }
        });
    }
    
    @Override
    public T buscarPorId(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(classe, id);
        }catch (Exception e){
            throw new DAOException ("Erro ao buscar " + classe.getSimpleName()
                    + " com id " + id, e);
        }
    }
    
    @Override
    public List<T> listarTodos(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session
                    .createQuery("FROM " + classe.getSimpleName(), classe)
                    .getResultList();
        }catch (Exception e){
            throw new DAOException("Erro ao listar " + classe.getSimpleName(), e);
        }
    }

    /**
     * Executa uma consulta HQL com parâmetros posicionais (?1, ?2, ...)
     * @param <R>
     * @param hql
     * @param tipo
     * @param parametros
     * @return 
     */
    protected <R> List<R> consultar(String hql, Class<R> tipo, Object... parametros){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<R> query = session.createQuery(hql, tipo);
            for (int i = 0; i < parametros.length; i++){
                query.setParameter(i + 1, parametros[i]);
            }
            return query.getResultList();
        }catch (Exception e){
            throw new DAOException("Erro na consulta: " + hql, e);
        }
    }
    
    /**
     * Identico ao consultar(), mas devolve só o primeiro resultado (ou vazio)
     * @param hql
     * @param parametros
     * @return 
     */
    protected Optional<T> consultarUnico(String hql, Object... parametros){
        return consultar(hql, classe, parametros).stream().findFirst();
    }
    
    /**
     * Prepara um texto para usar com LIKE: "joão" -> "%joão%"
     * @param texto
     * @return 
     */
    protected static String contem(String texto){
        String limpo = (texto == null) ? "" : texto.trim().toLowerCase();
        return "%" + limpo + "%";
    }
    
    /**
     * Abre sessão, inicializa transacção, executa a operação, faz commit.
     * Se algo falhar faz rollback e lança DAOException.
     */
    protected void executarEmTransacao(Consumer<Session> operacao){
        Transaction transaction = null;
        
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            
            try{
                operacao.accept(session);
                transaction.commit();                
            }catch(RuntimeException e){
                if(transaction.isActive()){
                    transaction.rollback();
                }
                throw e;
            }

        }catch(Exception e){
            throw new DAOException("Erro ao gravar " + classe.getSimpleName(), e);
        }
            
    }
    
}
