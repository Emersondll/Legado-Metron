/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Bem;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.LancServPrestado;
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
public class BemJpaController implements Serializable {

    public BemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bem bem) throws RollbackFailureException, Exception {
        if (bem.getLancServPrestadoCollection() == null) {
            bem.setLancServPrestadoCollection(new ArrayList<LancServPrestado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<LancServPrestado> attachedLancServPrestadoCollection = new ArrayList<LancServPrestado>();
            for (LancServPrestado lancServPrestadoCollectionLancServPrestadoToAttach : bem.getLancServPrestadoCollection()) {
                lancServPrestadoCollectionLancServPrestadoToAttach = em.getReference(lancServPrestadoCollectionLancServPrestadoToAttach.getClass(), lancServPrestadoCollectionLancServPrestadoToAttach.getLancServPrestadoPK());
                attachedLancServPrestadoCollection.add(lancServPrestadoCollectionLancServPrestadoToAttach);
            }
            bem.setLancServPrestadoCollection(attachedLancServPrestadoCollection);
            em.persist(bem);
            for (LancServPrestado lancServPrestadoCollectionLancServPrestado : bem.getLancServPrestadoCollection()) {
                Bem oldCdBemOfLancServPrestadoCollectionLancServPrestado = lancServPrestadoCollectionLancServPrestado.getCdBem();
                lancServPrestadoCollectionLancServPrestado.setCdBem(bem);
                lancServPrestadoCollectionLancServPrestado = em.merge(lancServPrestadoCollectionLancServPrestado);
                if (oldCdBemOfLancServPrestadoCollectionLancServPrestado != null) {
                    oldCdBemOfLancServPrestadoCollectionLancServPrestado.getLancServPrestadoCollection().remove(lancServPrestadoCollectionLancServPrestado);
                    oldCdBemOfLancServPrestadoCollectionLancServPrestado = em.merge(oldCdBemOfLancServPrestadoCollectionLancServPrestado);
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

    public void edit(Bem bem) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Bem persistentBem = em.find(Bem.class, bem.getCdBem());
            Collection<LancServPrestado> lancServPrestadoCollectionOld = persistentBem.getLancServPrestadoCollection();
            Collection<LancServPrestado> lancServPrestadoCollectionNew = bem.getLancServPrestadoCollection();
            Collection<LancServPrestado> attachedLancServPrestadoCollectionNew = new ArrayList<LancServPrestado>();
            for (LancServPrestado lancServPrestadoCollectionNewLancServPrestadoToAttach : lancServPrestadoCollectionNew) {
                lancServPrestadoCollectionNewLancServPrestadoToAttach = em.getReference(lancServPrestadoCollectionNewLancServPrestadoToAttach.getClass(), lancServPrestadoCollectionNewLancServPrestadoToAttach.getLancServPrestadoPK());
                attachedLancServPrestadoCollectionNew.add(lancServPrestadoCollectionNewLancServPrestadoToAttach);
            }
            lancServPrestadoCollectionNew = attachedLancServPrestadoCollectionNew;
            bem.setLancServPrestadoCollection(lancServPrestadoCollectionNew);
            bem = em.merge(bem);
            for (LancServPrestado lancServPrestadoCollectionOldLancServPrestado : lancServPrestadoCollectionOld) {
                if (!lancServPrestadoCollectionNew.contains(lancServPrestadoCollectionOldLancServPrestado)) {
                    lancServPrestadoCollectionOldLancServPrestado.setCdBem(null);
                    lancServPrestadoCollectionOldLancServPrestado = em.merge(lancServPrestadoCollectionOldLancServPrestado);
                }
            }
            for (LancServPrestado lancServPrestadoCollectionNewLancServPrestado : lancServPrestadoCollectionNew) {
                if (!lancServPrestadoCollectionOld.contains(lancServPrestadoCollectionNewLancServPrestado)) {
                    Bem oldCdBemOfLancServPrestadoCollectionNewLancServPrestado = lancServPrestadoCollectionNewLancServPrestado.getCdBem();
                    lancServPrestadoCollectionNewLancServPrestado.setCdBem(bem);
                    lancServPrestadoCollectionNewLancServPrestado = em.merge(lancServPrestadoCollectionNewLancServPrestado);
                    if (oldCdBemOfLancServPrestadoCollectionNewLancServPrestado != null && !oldCdBemOfLancServPrestadoCollectionNewLancServPrestado.equals(bem)) {
                        oldCdBemOfLancServPrestadoCollectionNewLancServPrestado.getLancServPrestadoCollection().remove(lancServPrestadoCollectionNewLancServPrestado);
                        oldCdBemOfLancServPrestadoCollectionNewLancServPrestado = em.merge(oldCdBemOfLancServPrestadoCollectionNewLancServPrestado);
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
                Integer id = bem.getCdBem();
                if (findBem(id) == null) {
                    throw new NonexistentEntityException("The bem with id " + id + " no longer exists.");
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
            Bem bem;
            try {
                bem = em.getReference(Bem.class, id);
                bem.getCdBem();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bem with id " + id + " no longer exists.", enfe);
            }
            Collection<LancServPrestado> lancServPrestadoCollection = bem.getLancServPrestadoCollection();
            for (LancServPrestado lancServPrestadoCollectionLancServPrestado : lancServPrestadoCollection) {
                lancServPrestadoCollectionLancServPrestado.setCdBem(null);
                lancServPrestadoCollectionLancServPrestado = em.merge(lancServPrestadoCollectionLancServPrestado);
            }
            em.remove(bem);
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

    public List<Bem> findBemEntities() {
        return findBemEntities(true, -1, -1);
    }

    public List<Bem> findBemEntities(int maxResults, int firstResult) {
        return findBemEntities(false, maxResults, firstResult);
    }

    private List<Bem> findBemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bem.class));
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

    public Bem findBem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bem.class, id);
        } finally {
            em.close();
        }
    }

    public int getBemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bem> rt = cq.from(Bem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
