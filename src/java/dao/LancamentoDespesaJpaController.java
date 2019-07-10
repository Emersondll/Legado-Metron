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
import entidade.Conta;
import entidade.Despesa;
import entidade.LancamentoDespesa;
import entidade.LancamentoDespesaPK;
import entidade.Obra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class LancamentoDespesaJpaController implements Serializable {

    public LancamentoDespesaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LancamentoDespesa lancamentoDespesa) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (lancamentoDespesa.getLancamentoDespesaPK() == null) {
            lancamentoDespesa.setLancamentoDespesaPK(new LancamentoDespesaPK());
        }
        lancamentoDespesa.getLancamentoDespesaPK().setCdDespesa(lancamentoDespesa.getDespesa().getCdDespesa());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Conta conta = lancamentoDespesa.getConta();
            if (conta != null) {
                conta = em.getReference(conta.getClass(), conta.getConta());
                lancamentoDespesa.setConta(conta);
            }
            Despesa despesa = lancamentoDespesa.getDespesa();
            if (despesa != null) {
                despesa = em.getReference(despesa.getClass(), despesa.getCdDespesa());
                lancamentoDespesa.setDespesa(despesa);
            }
            Obra cdObra = lancamentoDespesa.getCdObra();
            if (cdObra != null) {
                cdObra = em.getReference(cdObra.getClass(), cdObra.getCdObra());
                lancamentoDespesa.setCdObra(cdObra);
            }
            em.persist(lancamentoDespesa);
            if (conta != null) {
                conta.getLancamentoDespesaCollection().add(lancamentoDespesa);
                conta = em.merge(conta);
            }
            if (despesa != null) {
                despesa.getLancamentoDespesaCollection().add(lancamentoDespesa);
                despesa = em.merge(despesa);
            }
            if (cdObra != null) {
                cdObra.getLancamentoDespesaCollection().add(lancamentoDespesa);
                cdObra = em.merge(cdObra);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLancamentoDespesa(lancamentoDespesa.getLancamentoDespesaPK()) != null) {
                throw new PreexistingEntityException("LancamentoDespesa " + lancamentoDespesa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LancamentoDespesa lancamentoDespesa) throws NonexistentEntityException, RollbackFailureException, Exception {
        lancamentoDespesa.getLancamentoDespesaPK().setCdDespesa(lancamentoDespesa.getDespesa().getCdDespesa());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            LancamentoDespesa persistentLancamentoDespesa = em.find(LancamentoDespesa.class, lancamentoDespesa.getLancamentoDespesaPK());
            Conta contaOld = persistentLancamentoDespesa.getConta();
            Conta contaNew = lancamentoDespesa.getConta();
            Despesa despesaOld = persistentLancamentoDespesa.getDespesa();
            Despesa despesaNew = lancamentoDespesa.getDespesa();
            Obra cdObraOld = persistentLancamentoDespesa.getCdObra();
            Obra cdObraNew = lancamentoDespesa.getCdObra();
            if (contaNew != null) {
                contaNew = em.getReference(contaNew.getClass(), contaNew.getConta());
                lancamentoDespesa.setConta(contaNew);
            }
            if (despesaNew != null) {
                despesaNew = em.getReference(despesaNew.getClass(), despesaNew.getCdDespesa());
                lancamentoDespesa.setDespesa(despesaNew);
            }
            if (cdObraNew != null) {
                cdObraNew = em.getReference(cdObraNew.getClass(), cdObraNew.getCdObra());
                lancamentoDespesa.setCdObra(cdObraNew);
            }
            lancamentoDespesa = em.merge(lancamentoDespesa);
            if (contaOld != null && !contaOld.equals(contaNew)) {
                contaOld.getLancamentoDespesaCollection().remove(lancamentoDespesa);
                contaOld = em.merge(contaOld);
            }
            if (contaNew != null && !contaNew.equals(contaOld)) {
                contaNew.getLancamentoDespesaCollection().add(lancamentoDespesa);
                contaNew = em.merge(contaNew);
            }
            if (despesaOld != null && !despesaOld.equals(despesaNew)) {
                despesaOld.getLancamentoDespesaCollection().remove(lancamentoDespesa);
                despesaOld = em.merge(despesaOld);
            }
            if (despesaNew != null && !despesaNew.equals(despesaOld)) {
                despesaNew.getLancamentoDespesaCollection().add(lancamentoDespesa);
                despesaNew = em.merge(despesaNew);
            }
            if (cdObraOld != null && !cdObraOld.equals(cdObraNew)) {
                cdObraOld.getLancamentoDespesaCollection().remove(lancamentoDespesa);
                cdObraOld = em.merge(cdObraOld);
            }
            if (cdObraNew != null && !cdObraNew.equals(cdObraOld)) {
                cdObraNew.getLancamentoDespesaCollection().add(lancamentoDespesa);
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
                LancamentoDespesaPK id = lancamentoDespesa.getLancamentoDespesaPK();
                if (findLancamentoDespesa(id) == null) {
                    throw new NonexistentEntityException("The lancamentoDespesa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LancamentoDespesaPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            LancamentoDespesa lancamentoDespesa;
            try {
                lancamentoDespesa = em.getReference(LancamentoDespesa.class, id);
                lancamentoDespesa.getLancamentoDespesaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lancamentoDespesa with id " + id + " no longer exists.", enfe);
            }
            Conta conta = lancamentoDespesa.getConta();
            if (conta != null) {
                conta.getLancamentoDespesaCollection().remove(lancamentoDespesa);
                conta = em.merge(conta);
            }
            Despesa despesa = lancamentoDespesa.getDespesa();
            if (despesa != null) {
                despesa.getLancamentoDespesaCollection().remove(lancamentoDespesa);
                despesa = em.merge(despesa);
            }
            Obra cdObra = lancamentoDespesa.getCdObra();
            if (cdObra != null) {
                cdObra.getLancamentoDespesaCollection().remove(lancamentoDespesa);
                cdObra = em.merge(cdObra);
            }
            em.remove(lancamentoDespesa);
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

    public List<LancamentoDespesa> findLancamentoDespesaEntities() {
        return findLancamentoDespesaEntities(true, -1, -1);
    }

    public List<LancamentoDespesa> findLancamentoDespesaEntities(int maxResults, int firstResult) {
        return findLancamentoDespesaEntities(false, maxResults, firstResult);
    }

    private List<LancamentoDespesa> findLancamentoDespesaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LancamentoDespesa.class));
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

    public LancamentoDespesa findLancamentoDespesa(LancamentoDespesaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LancamentoDespesa.class, id);
        } finally {
            em.close();
        }
    }

    public int getLancamentoDespesaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LancamentoDespesa> rt = cq.from(LancamentoDespesa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
