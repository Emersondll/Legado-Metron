/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.Obra;
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

public class ClienteJpaController implements Serializable {

    public ClienteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws RollbackFailureException, Exception {
        if (cliente.getObraCollection() == null) {
            cliente.setObraCollection(new ArrayList<Obra>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Obra> attachedObraCollection = new ArrayList<Obra>();
            for (Obra obraCollectionObraToAttach : cliente.getObraCollection()) {
                obraCollectionObraToAttach = em.getReference(obraCollectionObraToAttach.getClass(), obraCollectionObraToAttach.getCdObra());
                attachedObraCollection.add(obraCollectionObraToAttach);
            }
            cliente.setObraCollection(attachedObraCollection);
            em.persist(cliente);
            for (Obra obraCollectionObra : cliente.getObraCollection()) {
                Cliente oldCdClienteOfObraCollectionObra = obraCollectionObra.getCdCliente();
                obraCollectionObra.setCdCliente(cliente);
                obraCollectionObra = em.merge(obraCollectionObra);
                if (oldCdClienteOfObraCollectionObra != null) {
                    oldCdClienteOfObraCollectionObra.getObraCollection().remove(obraCollectionObra);
                    oldCdClienteOfObraCollectionObra = em.merge(oldCdClienteOfObraCollectionObra);
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

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getCdCliente());
            Collection<Obra> obraCollectionOld = persistentCliente.getObraCollection();
            Collection<Obra> obraCollectionNew = cliente.getObraCollection();
            List<String> illegalOrphanMessages = null;
            for (Obra obraCollectionOldObra : obraCollectionOld) {
                if (!obraCollectionNew.contains(obraCollectionOldObra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Obra " + obraCollectionOldObra + " since its cdCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Obra> attachedObraCollectionNew = new ArrayList<Obra>();
            for (Obra obraCollectionNewObraToAttach : obraCollectionNew) {
                obraCollectionNewObraToAttach = em.getReference(obraCollectionNewObraToAttach.getClass(), obraCollectionNewObraToAttach.getCdObra());
                attachedObraCollectionNew.add(obraCollectionNewObraToAttach);
            }
            obraCollectionNew = attachedObraCollectionNew;
            cliente.setObraCollection(obraCollectionNew);
            cliente = em.merge(cliente);
            for (Obra obraCollectionNewObra : obraCollectionNew) {
                if (!obraCollectionOld.contains(obraCollectionNewObra)) {
                    Cliente oldCdClienteOfObraCollectionNewObra = obraCollectionNewObra.getCdCliente();
                    obraCollectionNewObra.setCdCliente(cliente);
                    obraCollectionNewObra = em.merge(obraCollectionNewObra);
                    if (oldCdClienteOfObraCollectionNewObra != null && !oldCdClienteOfObraCollectionNewObra.equals(cliente)) {
                        oldCdClienteOfObraCollectionNewObra.getObraCollection().remove(obraCollectionNewObra);
                        oldCdClienteOfObraCollectionNewObra = em.merge(oldCdClienteOfObraCollectionNewObra);
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
                Integer id = cliente.getCdCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getCdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Obra> obraCollectionOrphanCheck = cliente.getObraCollection();
            for (Obra obraCollectionOrphanCheckObra : obraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Obra " + obraCollectionOrphanCheckObra + " in its obraCollection field has a non-nullable cdCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
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

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
