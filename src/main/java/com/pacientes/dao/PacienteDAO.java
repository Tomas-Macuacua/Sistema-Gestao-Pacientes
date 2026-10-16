/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pacientes.dao;

import com.pacientes.model.Paciente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.pacientes.util.HibernateUtil;

import java.util.List;

public class PacienteDAO implements IPacienteDAO {

    @Override
    public void eliminar(Long id) {

        Transaction transaction = null;

        try (Session session
                = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Paciente paciente
                    = session.get(Paciente.class, id);

            if (paciente != null) {
                session.remove(paciente);
            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public Paciente buscarPorId(Long id) {

        try (Session session
                = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Paciente.class, id);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public List<Paciente> listarTodos() {

        try (Session session
                = HibernateUtil.getSessionFactory().openSession()) {

            return session
                    .createQuery(
                            "FROM Paciente",
                            Paciente.class
                    )
                    .getResultList();

        } catch (Exception e) {

            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public void salvar(Paciente paciente) {
        Transaction transaction = null;

        try (Session session
                = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(paciente);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Paciente paciente) {
        Transaction transaction = null;

        try (Session session
                = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.merge(paciente);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
}
