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
import entidade.Conta;
import entidade.LancamentoRecebimento;
import entidade.Obra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class LancamentoRecebimentoJpaController implements Serializable {

    public LancamentoRecebimentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LancamentoRecebimento lancamentoRecebimento) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Conta contaDestino = lancamentoRecebimento.getContaDestino();
            if (contaDestino != null) {
                contaDestino = em.getReference(contaDestino.getClass(), contaDestino.getConta());
                lancamentoRecebimento.setContaDestino(contaDestino);
            }
            Obra cdObra = lancamentoRecebimento.getCdObra();
            if (cdObra != null) {
                cdObra = em.getReference(cdObra.getClass(), cdObra.getCdObra());
                lancamentoRecebimento.setCdObra(cdObra);
            }
            em.persist(lancamentoRecebimento);
            if (contaDestino != null) {
                contaDestino.getLancamentoRecebimentoCollection().add(lancamentoRecebimento);
                contaDestino = em.merge(contaDestino);
            }
            if (cdObra != null) {
                cdObra.getLancamentoRecebimentoCollection().add(lancamentoRecebimento);
                cdObra = em.merge(cdObra);
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

    public void edit(LancamentoRecebimento lancamentoRecebimento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            LancamentoRecebimento persistentLancamentoRecebimento = em.find(LancamentoRecebimento.class, lancamentoRecebimento.getIdLancamentoRecebimento());
            Conta contaDestinoOld = persistentLancamentoRecebimento.getContaDestino();
            Conta contaDestinoNew = lancamentoRecebimento.getContaDestino();
            Obra cdObraOld = persistentLancamentoRecebimento.getCdObra();
            Obra cdObraNew = lancamentoRecebimento.getCdObra();
            if (contaDestinoNew != null) {
                contaDestinoNew = em.getReference(contaDestinoNew.getClass(), contaDestinoNew.getConta());
                lancamentoRecebimento.setContaDestino(contaDestinoNew);
            }
            if (cdObraNew != null) {
                cdObraNew = em.getReference(cdObraNew.getClass(), cdObraNew.getCdObra());
                lancamentoRecebimento.setCdObra(cdObraNew);
            }
            lancamentoRecebimento = em.merge(lancamentoRecebimento);
            if (contaDestinoOld != null && !contaDestinoOld.equals(contaDestinoNew)) {
                contaDestinoOld.getLancamentoRecebimentoCollection().remove(lancamentoRecebimento);
                contaDestinoOld = em.merge(contaDestinoOld);
            }
            if (contaDestinoNew != null && !contaDestinoNew.equals(contaDestinoOld)) {
                contaDestinoNew.getLancamentoRecebimentoCollection().add(lancamentoRecebimento);
                contaDestinoNew = em.merge(contaDestinoNew);
            }
            if (cdObraOld != null && !cdObraOld.equals(cdObraNew)) {
                cdObraOld.getLancamentoRecebimentoCollection().remove(lancamentoRecebimento);
                cdObraOld = em.merge(cdObraOld);
            }
            if (cdObraNew != null && !cdObraNew.equals(cdObraOld)) {
                cdObraNew.getLancamentoRecebimentoCollection().add(lancamentoRecebimento);
                cdObraNew = em.merge(cdObraNew);
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
                Integer id = lancamentoRecebimento.getIdLancamentoRecebimento();
                if (findLancamentoRecebimento(id) == null) {
                    throw new NonexistentEntityException("The lancamentoRecebimento with id " + id + " no longer exists.");
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
            LancamentoRecebimento lancamentoRecebimento;
            try {
                lancamentoRecebimento = em.getReference(LancamentoRecebimento.class, id);
                lancamentoRecebimento.getIdLancamentoRecebimento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lancamentoRecebimento with id " + id + " no longer exists.", enfe);
            }
            Conta contaDestino = lancamentoRecebimento.getContaDestino();
            if (contaDestino != null) {
                contaDestino.getLancamentoRecebimentoCollection().remove(lancamentoRecebimento);
                contaDestino = em.merge(contaDestino);
            }
            Obra cdObra = lancamentoRecebimento.getCdObra();
            if (cdObra != null) {
                cdObra.getLancamentoRecebimentoCollection().remove(lancamentoRecebimento);
                cdObra = em.merge(cdObra);
            }
            em.remove(lancamentoRecebimento);
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

    public List<LancamentoRecebimento> findLancamentoRecebimentoEntities() {
        return findLancamentoRecebimentoEntities(true, -1, -1);
    }

    public List<LancamentoRecebimento> findLancamentoRecebimentoEntities(int maxResults, int firstResult) {
        return findLancamentoRecebimentoEntities(false, maxResults, firstResult);
    }

    private List<LancamentoRecebimento> findLancamentoRecebimentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LancamentoRecebimento.class));
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

    public LancamentoRecebimento findLancamentoRecebimento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LancamentoRecebimento.class, id);
        } finally {
            em.close();
        }
    }

    public int getLancamentoRecebimentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LancamentoRecebimento> rt = cq.from(LancamentoRecebimento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
