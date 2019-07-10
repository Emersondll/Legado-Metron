/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.Funcionario;
import entidade.Integracao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class IntegracaoJpaController implements Serializable {

    public IntegracaoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Integracao integracao) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Funcionario cpfFuncionario = integracao.getCpfFuncionario();
            if (cpfFuncionario != null) {
                cpfFuncionario = em.getReference(cpfFuncionario.getClass(), cpfFuncionario.getCpf());
                integracao.setCpfFuncionario(cpfFuncionario);
            }
            em.persist(integracao);
            if (cpfFuncionario != null) {
                cpfFuncionario.getIntegracaoCollection().add(integracao);
                cpfFuncionario = em.merge(cpfFuncionario);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Integracao integracao) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Integracao persistentIntegracao = em.find(Integracao.class, integracao.getIdIntegracao());
            Funcionario cpfFuncionarioOld = persistentIntegracao.getCpfFuncionario();
            Funcionario cpfFuncionarioNew = integracao.getCpfFuncionario();
            if (cpfFuncionarioNew != null) {
                cpfFuncionarioNew = em.getReference(cpfFuncionarioNew.getClass(), cpfFuncionarioNew.getCpf());
                integracao.setCpfFuncionario(cpfFuncionarioNew);
            }
            integracao = em.merge(integracao);
            if (cpfFuncionarioOld != null && !cpfFuncionarioOld.equals(cpfFuncionarioNew)) {
                cpfFuncionarioOld.getIntegracaoCollection().remove(integracao);
                cpfFuncionarioOld = em.merge(cpfFuncionarioOld);
            }
            if (cpfFuncionarioNew != null && !cpfFuncionarioNew.equals(cpfFuncionarioOld)) {
                cpfFuncionarioNew.getIntegracaoCollection().add(integracao);
                cpfFuncionarioNew = em.merge(cpfFuncionarioNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = integracao.getIdIntegracao();
                if (findIntegracao(id) == null) {
                    throw new NonexistentEntityException("The integracao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Integracao integracao;
            try {
                integracao = em.getReference(Integracao.class, id);
                integracao.getIdIntegracao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The integracao with id " + id + " no longer exists.", enfe);
            }
            Funcionario cpfFuncionario = integracao.getCpfFuncionario();
            if (cpfFuncionario != null) {
                cpfFuncionario.getIntegracaoCollection().remove(integracao);
                cpfFuncionario = em.merge(cpfFuncionario);
            }
            em.remove(integracao);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Integracao> findIntegracaoEntities() {
        return findIntegracaoEntities(true, -1, -1);
    }

    public List<Integracao> findIntegracaoEntities(int maxResults, int firstResult) {
        return findIntegracaoEntities(false, maxResults, firstResult);
    }

    private List<Integracao> findIntegracaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Integracao.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Integracao findIntegracao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Integracao.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntegracaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Integracao> rt = cq.from(Integracao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
