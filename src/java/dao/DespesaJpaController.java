/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Despesa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.LancamentoDespesa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class DespesaJpaController implements Serializable {

    public DespesaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Despesa despesa) throws RollbackFailureException, Exception {
        if (despesa.getLancamentoDespesaCollection() == null) {
            despesa.setLancamentoDespesaCollection(new ArrayList<LancamentoDespesa>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<LancamentoDespesa> attachedLancamentoDespesaCollection = new ArrayList<LancamentoDespesa>();
            for (LancamentoDespesa lancamentoDespesaCollectionLancamentoDespesaToAttach : despesa.getLancamentoDespesaCollection()) {
                lancamentoDespesaCollectionLancamentoDespesaToAttach = em.getReference(lancamentoDespesaCollectionLancamentoDespesaToAttach.getClass(), lancamentoDespesaCollectionLancamentoDespesaToAttach.getLancamentoDespesaPK());
                attachedLancamentoDespesaCollection.add(lancamentoDespesaCollectionLancamentoDespesaToAttach);
            }
            despesa.setLancamentoDespesaCollection(attachedLancamentoDespesaCollection);
            em.persist(despesa);
            for (LancamentoDespesa lancamentoDespesaCollectionLancamentoDespesa : despesa.getLancamentoDespesaCollection()) {
                Despesa oldDespesaOfLancamentoDespesaCollectionLancamentoDespesa = lancamentoDespesaCollectionLancamentoDespesa.getDespesa();
                lancamentoDespesaCollectionLancamentoDespesa.setDespesa(despesa);
                lancamentoDespesaCollectionLancamentoDespesa = em.merge(lancamentoDespesaCollectionLancamentoDespesa);
                if (oldDespesaOfLancamentoDespesaCollectionLancamentoDespesa != null) {
                    oldDespesaOfLancamentoDespesaCollectionLancamentoDespesa.getLancamentoDespesaCollection().remove(lancamentoDespesaCollectionLancamentoDespesa);
                    oldDespesaOfLancamentoDespesaCollectionLancamentoDespesa = em.merge(oldDespesaOfLancamentoDespesaCollectionLancamentoDespesa);
                }
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

    public void edit(Despesa despesa) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Despesa persistentDespesa = em.find(Despesa.class, despesa.getCdDespesa());
            Collection<LancamentoDespesa> lancamentoDespesaCollectionOld = persistentDespesa.getLancamentoDespesaCollection();
            Collection<LancamentoDespesa> lancamentoDespesaCollectionNew = despesa.getLancamentoDespesaCollection();
            List<String> illegalOrphanMessages = null;
            for (LancamentoDespesa lancamentoDespesaCollectionOldLancamentoDespesa : lancamentoDespesaCollectionOld) {
                if (!lancamentoDespesaCollectionNew.contains(lancamentoDespesaCollectionOldLancamentoDespesa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LancamentoDespesa " + lancamentoDespesaCollectionOldLancamentoDespesa + " since its despesa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<LancamentoDespesa> attachedLancamentoDespesaCollectionNew = new ArrayList<LancamentoDespesa>();
            for (LancamentoDespesa lancamentoDespesaCollectionNewLancamentoDespesaToAttach : lancamentoDespesaCollectionNew) {
                lancamentoDespesaCollectionNewLancamentoDespesaToAttach = em.getReference(lancamentoDespesaCollectionNewLancamentoDespesaToAttach.getClass(), lancamentoDespesaCollectionNewLancamentoDespesaToAttach.getLancamentoDespesaPK());
                attachedLancamentoDespesaCollectionNew.add(lancamentoDespesaCollectionNewLancamentoDespesaToAttach);
            }
            lancamentoDespesaCollectionNew = attachedLancamentoDespesaCollectionNew;
            despesa.setLancamentoDespesaCollection(lancamentoDespesaCollectionNew);
            despesa = em.merge(despesa);
            for (LancamentoDespesa lancamentoDespesaCollectionNewLancamentoDespesa : lancamentoDespesaCollectionNew) {
                if (!lancamentoDespesaCollectionOld.contains(lancamentoDespesaCollectionNewLancamentoDespesa)) {
                    Despesa oldDespesaOfLancamentoDespesaCollectionNewLancamentoDespesa = lancamentoDespesaCollectionNewLancamentoDespesa.getDespesa();
                    lancamentoDespesaCollectionNewLancamentoDespesa.setDespesa(despesa);
                    lancamentoDespesaCollectionNewLancamentoDespesa = em.merge(lancamentoDespesaCollectionNewLancamentoDespesa);
                    if (oldDespesaOfLancamentoDespesaCollectionNewLancamentoDespesa != null && !oldDespesaOfLancamentoDespesaCollectionNewLancamentoDespesa.equals(despesa)) {
                        oldDespesaOfLancamentoDespesaCollectionNewLancamentoDespesa.getLancamentoDespesaCollection().remove(lancamentoDespesaCollectionNewLancamentoDespesa);
                        oldDespesaOfLancamentoDespesaCollectionNewLancamentoDespesa = em.merge(oldDespesaOfLancamentoDespesaCollectionNewLancamentoDespesa);
                    }
                }
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
                Integer id = despesa.getCdDespesa();
                if (findDespesa(id) == null) {
                    throw new NonexistentEntityException("The despesa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Despesa despesa;
            try {
                despesa = em.getReference(Despesa.class, id);
                despesa.getCdDespesa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The despesa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<LancamentoDespesa> lancamentoDespesaCollectionOrphanCheck = despesa.getLancamentoDespesaCollection();
            for (LancamentoDespesa lancamentoDespesaCollectionOrphanCheckLancamentoDespesa : lancamentoDespesaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Despesa (" + despesa + ") cannot be destroyed since the LancamentoDespesa " + lancamentoDespesaCollectionOrphanCheckLancamentoDespesa + " in its lancamentoDespesaCollection field has a non-nullable despesa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(despesa);
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

    public List<Despesa> findDespesaEntities() {
        return findDespesaEntities(true, -1, -1);
    }

    public List<Despesa> findDespesaEntities(int maxResults, int firstResult) {
        return findDespesaEntities(false, maxResults, firstResult);
    }

    private List<Despesa> findDespesaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Despesa.class));
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

    public Despesa findDespesa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Despesa.class, id);
        } finally {
            em.close();
        }
    }

    public int getDespesaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Despesa> rt = cq.from(Despesa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
