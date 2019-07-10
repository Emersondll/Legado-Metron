/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.LancServPrestado;
import entidade.Servico;
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
public class ServicoJpaController implements Serializable {

    public ServicoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servico servico) throws RollbackFailureException, Exception {
        if (servico.getLancServPrestadoCollection() == null) {
            servico.setLancServPrestadoCollection(new ArrayList<LancServPrestado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<LancServPrestado> attachedLancServPrestadoCollection = new ArrayList<LancServPrestado>();
            for (LancServPrestado lancServPrestadoCollectionLancServPrestadoToAttach : servico.getLancServPrestadoCollection()) {
                lancServPrestadoCollectionLancServPrestadoToAttach = em.getReference(lancServPrestadoCollectionLancServPrestadoToAttach.getClass(), lancServPrestadoCollectionLancServPrestadoToAttach.getLancServPrestadoPK());
                attachedLancServPrestadoCollection.add(lancServPrestadoCollectionLancServPrestadoToAttach);
            }
            servico.setLancServPrestadoCollection(attachedLancServPrestadoCollection);
            em.persist(servico);
            for (LancServPrestado lancServPrestadoCollectionLancServPrestado : servico.getLancServPrestadoCollection()) {
                Servico oldServicoOfLancServPrestadoCollectionLancServPrestado = lancServPrestadoCollectionLancServPrestado.getServico();
                lancServPrestadoCollectionLancServPrestado.setServico(servico);
                lancServPrestadoCollectionLancServPrestado = em.merge(lancServPrestadoCollectionLancServPrestado);
                if (oldServicoOfLancServPrestadoCollectionLancServPrestado != null) {
                    oldServicoOfLancServPrestadoCollectionLancServPrestado.getLancServPrestadoCollection().remove(lancServPrestadoCollectionLancServPrestado);
                    oldServicoOfLancServPrestadoCollectionLancServPrestado = em.merge(oldServicoOfLancServPrestadoCollectionLancServPrestado);
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

    public void edit(Servico servico) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Servico persistentServico = em.find(Servico.class, servico.getCdServico());
            Collection<LancServPrestado> lancServPrestadoCollectionOld = persistentServico.getLancServPrestadoCollection();
            Collection<LancServPrestado> lancServPrestadoCollectionNew = servico.getLancServPrestadoCollection();
            List<String> illegalOrphanMessages = null;
            for (LancServPrestado lancServPrestadoCollectionOldLancServPrestado : lancServPrestadoCollectionOld) {
                if (!lancServPrestadoCollectionNew.contains(lancServPrestadoCollectionOldLancServPrestado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LancServPrestado " + lancServPrestadoCollectionOldLancServPrestado + " since its servico field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<LancServPrestado> attachedLancServPrestadoCollectionNew = new ArrayList<LancServPrestado>();
            for (LancServPrestado lancServPrestadoCollectionNewLancServPrestadoToAttach : lancServPrestadoCollectionNew) {
                lancServPrestadoCollectionNewLancServPrestadoToAttach = em.getReference(lancServPrestadoCollectionNewLancServPrestadoToAttach.getClass(), lancServPrestadoCollectionNewLancServPrestadoToAttach.getLancServPrestadoPK());
                attachedLancServPrestadoCollectionNew.add(lancServPrestadoCollectionNewLancServPrestadoToAttach);
            }
            lancServPrestadoCollectionNew = attachedLancServPrestadoCollectionNew;
            servico.setLancServPrestadoCollection(lancServPrestadoCollectionNew);
            servico = em.merge(servico);
            for (LancServPrestado lancServPrestadoCollectionNewLancServPrestado : lancServPrestadoCollectionNew) {
                if (!lancServPrestadoCollectionOld.contains(lancServPrestadoCollectionNewLancServPrestado)) {
                    Servico oldServicoOfLancServPrestadoCollectionNewLancServPrestado = lancServPrestadoCollectionNewLancServPrestado.getServico();
                    lancServPrestadoCollectionNewLancServPrestado.setServico(servico);
                    lancServPrestadoCollectionNewLancServPrestado = em.merge(lancServPrestadoCollectionNewLancServPrestado);
                    if (oldServicoOfLancServPrestadoCollectionNewLancServPrestado != null && !oldServicoOfLancServPrestadoCollectionNewLancServPrestado.equals(servico)) {
                        oldServicoOfLancServPrestadoCollectionNewLancServPrestado.getLancServPrestadoCollection().remove(lancServPrestadoCollectionNewLancServPrestado);
                        oldServicoOfLancServPrestadoCollectionNewLancServPrestado = em.merge(oldServicoOfLancServPrestadoCollectionNewLancServPrestado);
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
                Integer id = servico.getCdServico();
                if (findServico(id) == null) {
                    throw new NonexistentEntityException("The servico with id " + id + " no longer exists.");
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
            Servico servico;
            try {
                servico = em.getReference(Servico.class, id);
                servico.getCdServico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<LancServPrestado> lancServPrestadoCollectionOrphanCheck = servico.getLancServPrestadoCollection();
            for (LancServPrestado lancServPrestadoCollectionOrphanCheckLancServPrestado : lancServPrestadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Servico (" + servico + ") cannot be destroyed since the LancServPrestado " + lancServPrestadoCollectionOrphanCheckLancServPrestado + " in its lancServPrestadoCollection field has a non-nullable servico field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(servico);
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

    public List<Servico> findServicoEntities() {
        return findServicoEntities(true, -1, -1);
    }

    public List<Servico> findServicoEntities(int maxResults, int firstResult) {
        return findServicoEntities(false, maxResults, firstResult);
    }

    private List<Servico> findServicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servico.class));
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

    public Servico findServico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servico.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servico> rt = cq.from(Servico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
