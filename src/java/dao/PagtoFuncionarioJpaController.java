/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.Funcionario;
import entidade.PagtoFuncionario;
import entidade.PagtoFuncionarioPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class PagtoFuncionarioJpaController implements Serializable {

    public PagtoFuncionarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagtoFuncionario pagtoFuncionario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (pagtoFuncionario.getPagtoFuncionarioPK() == null) {
            pagtoFuncionario.setPagtoFuncionarioPK(new PagtoFuncionarioPK());
        }
        pagtoFuncionario.getPagtoFuncionarioPK().setCpfFuncionario(pagtoFuncionario.getFuncionario().getCpf());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Funcionario funcionario = pagtoFuncionario.getFuncionario();
            if (funcionario != null) {
                funcionario = em.getReference(funcionario.getClass(), funcionario.getCpf());
                pagtoFuncionario.setFuncionario(funcionario);
            }
            em.persist(pagtoFuncionario);
            if (funcionario != null) {
                funcionario.getPagtoFuncionarioCollection().add(pagtoFuncionario);
                funcionario = em.merge(funcionario);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPagtoFuncionario(pagtoFuncionario.getPagtoFuncionarioPK()) != null) {
                throw new PreexistingEntityException("PagtoFuncionario " + pagtoFuncionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagtoFuncionario pagtoFuncionario) throws NonexistentEntityException, RollbackFailureException, Exception {
        pagtoFuncionario.getPagtoFuncionarioPK().setCpfFuncionario(pagtoFuncionario.getFuncionario().getCpf());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PagtoFuncionario persistentPagtoFuncionario = em.find(PagtoFuncionario.class, pagtoFuncionario.getPagtoFuncionarioPK());
            Funcionario funcionarioOld = persistentPagtoFuncionario.getFuncionario();
            Funcionario funcionarioNew = pagtoFuncionario.getFuncionario();
            if (funcionarioNew != null) {
                funcionarioNew = em.getReference(funcionarioNew.getClass(), funcionarioNew.getCpf());
                pagtoFuncionario.setFuncionario(funcionarioNew);
            }
            pagtoFuncionario = em.merge(pagtoFuncionario);
            if (funcionarioOld != null && !funcionarioOld.equals(funcionarioNew)) {
                funcionarioOld.getPagtoFuncionarioCollection().remove(pagtoFuncionario);
                funcionarioOld = em.merge(funcionarioOld);
            }
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                funcionarioNew.getPagtoFuncionarioCollection().add(pagtoFuncionario);
                funcionarioNew = em.merge(funcionarioNew);
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
                PagtoFuncionarioPK id = pagtoFuncionario.getPagtoFuncionarioPK();
                if (findPagtoFuncionario(id) == null) {
                    throw new NonexistentEntityException("The pagtoFuncionario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PagtoFuncionarioPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PagtoFuncionario pagtoFuncionario;
            try {
                pagtoFuncionario = em.getReference(PagtoFuncionario.class, id);
                pagtoFuncionario.getPagtoFuncionarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagtoFuncionario with id " + id + " no longer exists.", enfe);
            }
            Funcionario funcionario = pagtoFuncionario.getFuncionario();
            if (funcionario != null) {
                funcionario.getPagtoFuncionarioCollection().remove(pagtoFuncionario);
                funcionario = em.merge(funcionario);
            }
            em.remove(pagtoFuncionario);
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

    public List<PagtoFuncionario> findPagtoFuncionarioEntities() {
        return findPagtoFuncionarioEntities(true, -1, -1);
    }

    public List<PagtoFuncionario> findPagtoFuncionarioEntities(int maxResults, int firstResult) {
        return findPagtoFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<PagtoFuncionario> findPagtoFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagtoFuncionario.class));
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

    public PagtoFuncionario findPagtoFuncionario(PagtoFuncionarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagtoFuncionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagtoFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagtoFuncionario> rt = cq.from(PagtoFuncionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
