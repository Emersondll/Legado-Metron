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
import entidade.Cliente;
import entidade.LancamentoDespesa;
import java.util.ArrayList;
import java.util.Collection;
import entidade.LancamentoRecebimento;
import entidade.LancServPrestado;
import entidade.Obra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class ObraJpaController implements Serializable {

    public ObraJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Obra obra) throws RollbackFailureException, Exception {
        if (obra.getLancamentoDespesaCollection() == null) {
            obra.setLancamentoDespesaCollection(new ArrayList<LancamentoDespesa>());
        }
        if (obra.getLancamentoRecebimentoCollection() == null) {
            obra.setLancamentoRecebimentoCollection(new ArrayList<LancamentoRecebimento>());
        }
        if (obra.getLancServPrestadoCollection() == null) {
            obra.setLancServPrestadoCollection(new ArrayList<LancServPrestado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente cdCliente = obra.getCdCliente();
            if (cdCliente != null) {
                cdCliente = em.getReference(cdCliente.getClass(), cdCliente.getCdCliente());
                obra.setCdCliente(cdCliente);
            }
            Collection<LancamentoDespesa> attachedLancamentoDespesaCollection = new ArrayList<LancamentoDespesa>();
            for (LancamentoDespesa lancamentoDespesaCollectionLancamentoDespesaToAttach : obra.getLancamentoDespesaCollection()) {
                lancamentoDespesaCollectionLancamentoDespesaToAttach = em.getReference(lancamentoDespesaCollectionLancamentoDespesaToAttach.getClass(), lancamentoDespesaCollectionLancamentoDespesaToAttach.getLancamentoDespesaPK());
                attachedLancamentoDespesaCollection.add(lancamentoDespesaCollectionLancamentoDespesaToAttach);
            }
            obra.setLancamentoDespesaCollection(attachedLancamentoDespesaCollection);
            Collection<LancamentoRecebimento> attachedLancamentoRecebimentoCollection = new ArrayList<LancamentoRecebimento>();
            for (LancamentoRecebimento lancamentoRecebimentoCollectionLancamentoRecebimentoToAttach : obra.getLancamentoRecebimentoCollection()) {
                lancamentoRecebimentoCollectionLancamentoRecebimentoToAttach = em.getReference(lancamentoRecebimentoCollectionLancamentoRecebimentoToAttach.getClass(), lancamentoRecebimentoCollectionLancamentoRecebimentoToAttach.getIdLancamentoRecebimento());
                attachedLancamentoRecebimentoCollection.add(lancamentoRecebimentoCollectionLancamentoRecebimentoToAttach);
            }
            obra.setLancamentoRecebimentoCollection(attachedLancamentoRecebimentoCollection);
            Collection<LancServPrestado> attachedLancServPrestadoCollection = new ArrayList<LancServPrestado>();
            for (LancServPrestado lancServPrestadoCollectionLancServPrestadoToAttach : obra.getLancServPrestadoCollection()) {
                lancServPrestadoCollectionLancServPrestadoToAttach = em.getReference(lancServPrestadoCollectionLancServPrestadoToAttach.getClass(), lancServPrestadoCollectionLancServPrestadoToAttach.getLancServPrestadoPK());
                attachedLancServPrestadoCollection.add(lancServPrestadoCollectionLancServPrestadoToAttach);
            }
            obra.setLancServPrestadoCollection(attachedLancServPrestadoCollection);
            em.persist(obra);
            if (cdCliente != null) {
                cdCliente.getObraCollection().add(obra);
                cdCliente = em.merge(cdCliente);
            }
            for (LancamentoDespesa lancamentoDespesaCollectionLancamentoDespesa : obra.getLancamentoDespesaCollection()) {
                Obra oldCdObraOfLancamentoDespesaCollectionLancamentoDespesa = lancamentoDespesaCollectionLancamentoDespesa.getCdObra();
                lancamentoDespesaCollectionLancamentoDespesa.setCdObra(obra);
                lancamentoDespesaCollectionLancamentoDespesa = em.merge(lancamentoDespesaCollectionLancamentoDespesa);
                if (oldCdObraOfLancamentoDespesaCollectionLancamentoDespesa != null) {
                    oldCdObraOfLancamentoDespesaCollectionLancamentoDespesa.getLancamentoDespesaCollection().remove(lancamentoDespesaCollectionLancamentoDespesa);
                    oldCdObraOfLancamentoDespesaCollectionLancamentoDespesa = em.merge(oldCdObraOfLancamentoDespesaCollectionLancamentoDespesa);
                }
            }
            for (LancamentoRecebimento lancamentoRecebimentoCollectionLancamentoRecebimento : obra.getLancamentoRecebimentoCollection()) {
                Obra oldCdObraOfLancamentoRecebimentoCollectionLancamentoRecebimento = lancamentoRecebimentoCollectionLancamentoRecebimento.getCdObra();
                lancamentoRecebimentoCollectionLancamentoRecebimento.setCdObra(obra);
                lancamentoRecebimentoCollectionLancamentoRecebimento = em.merge(lancamentoRecebimentoCollectionLancamentoRecebimento);
                if (oldCdObraOfLancamentoRecebimentoCollectionLancamentoRecebimento != null) {
                    oldCdObraOfLancamentoRecebimentoCollectionLancamentoRecebimento.getLancamentoRecebimentoCollection().remove(lancamentoRecebimentoCollectionLancamentoRecebimento);
                    oldCdObraOfLancamentoRecebimentoCollectionLancamentoRecebimento = em.merge(oldCdObraOfLancamentoRecebimentoCollectionLancamentoRecebimento);
                }
            }
            for (LancServPrestado lancServPrestadoCollectionLancServPrestado : obra.getLancServPrestadoCollection()) {
                Obra oldObraOfLancServPrestadoCollectionLancServPrestado = lancServPrestadoCollectionLancServPrestado.getObra();
                lancServPrestadoCollectionLancServPrestado.setObra(obra);
                lancServPrestadoCollectionLancServPrestado = em.merge(lancServPrestadoCollectionLancServPrestado);
                if (oldObraOfLancServPrestadoCollectionLancServPrestado != null) {
                    oldObraOfLancServPrestadoCollectionLancServPrestado.getLancServPrestadoCollection().remove(lancServPrestadoCollectionLancServPrestado);
                    oldObraOfLancServPrestadoCollectionLancServPrestado = em.merge(oldObraOfLancServPrestadoCollectionLancServPrestado);
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

    public void edit(Obra obra) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Obra persistentObra = em.find(Obra.class, obra.getCdObra());
            Cliente cdClienteOld = persistentObra.getCdCliente();
            Cliente cdClienteNew = obra.getCdCliente();
            Collection<LancamentoDespesa> lancamentoDespesaCollectionOld = persistentObra.getLancamentoDespesaCollection();
            Collection<LancamentoDespesa> lancamentoDespesaCollectionNew = obra.getLancamentoDespesaCollection();
            Collection<LancamentoRecebimento> lancamentoRecebimentoCollectionOld = persistentObra.getLancamentoRecebimentoCollection();
            Collection<LancamentoRecebimento> lancamentoRecebimentoCollectionNew = obra.getLancamentoRecebimentoCollection();
            Collection<LancServPrestado> lancServPrestadoCollectionOld = persistentObra.getLancServPrestadoCollection();
            Collection<LancServPrestado> lancServPrestadoCollectionNew = obra.getLancServPrestadoCollection();
            List<String> illegalOrphanMessages = null;
            for (LancServPrestado lancServPrestadoCollectionOldLancServPrestado : lancServPrestadoCollectionOld) {
                if (!lancServPrestadoCollectionNew.contains(lancServPrestadoCollectionOldLancServPrestado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LancServPrestado " + lancServPrestadoCollectionOldLancServPrestado + " since its obra field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cdClienteNew != null) {
                cdClienteNew = em.getReference(cdClienteNew.getClass(), cdClienteNew.getCdCliente());
                obra.setCdCliente(cdClienteNew);
            }
            Collection<LancamentoDespesa> attachedLancamentoDespesaCollectionNew = new ArrayList<LancamentoDespesa>();
            for (LancamentoDespesa lancamentoDespesaCollectionNewLancamentoDespesaToAttach : lancamentoDespesaCollectionNew) {
                lancamentoDespesaCollectionNewLancamentoDespesaToAttach = em.getReference(lancamentoDespesaCollectionNewLancamentoDespesaToAttach.getClass(), lancamentoDespesaCollectionNewLancamentoDespesaToAttach.getLancamentoDespesaPK());
                attachedLancamentoDespesaCollectionNew.add(lancamentoDespesaCollectionNewLancamentoDespesaToAttach);
            }
            lancamentoDespesaCollectionNew = attachedLancamentoDespesaCollectionNew;
            obra.setLancamentoDespesaCollection(lancamentoDespesaCollectionNew);
            Collection<LancamentoRecebimento> attachedLancamentoRecebimentoCollectionNew = new ArrayList<LancamentoRecebimento>();
            for (LancamentoRecebimento lancamentoRecebimentoCollectionNewLancamentoRecebimentoToAttach : lancamentoRecebimentoCollectionNew) {
                lancamentoRecebimentoCollectionNewLancamentoRecebimentoToAttach = em.getReference(lancamentoRecebimentoCollectionNewLancamentoRecebimentoToAttach.getClass(), lancamentoRecebimentoCollectionNewLancamentoRecebimentoToAttach.getIdLancamentoRecebimento());
                attachedLancamentoRecebimentoCollectionNew.add(lancamentoRecebimentoCollectionNewLancamentoRecebimentoToAttach);
            }
            lancamentoRecebimentoCollectionNew = attachedLancamentoRecebimentoCollectionNew;
            obra.setLancamentoRecebimentoCollection(lancamentoRecebimentoCollectionNew);
            Collection<LancServPrestado> attachedLancServPrestadoCollectionNew = new ArrayList<LancServPrestado>();
            for (LancServPrestado lancServPrestadoCollectionNewLancServPrestadoToAttach : lancServPrestadoCollectionNew) {
                lancServPrestadoCollectionNewLancServPrestadoToAttach = em.getReference(lancServPrestadoCollectionNewLancServPrestadoToAttach.getClass(), lancServPrestadoCollectionNewLancServPrestadoToAttach.getLancServPrestadoPK());
                attachedLancServPrestadoCollectionNew.add(lancServPrestadoCollectionNewLancServPrestadoToAttach);
            }
            lancServPrestadoCollectionNew = attachedLancServPrestadoCollectionNew;
            obra.setLancServPrestadoCollection(lancServPrestadoCollectionNew);
            obra = em.merge(obra);
            if (cdClienteOld != null && !cdClienteOld.equals(cdClienteNew)) {
                cdClienteOld.getObraCollection().remove(obra);
                cdClienteOld = em.merge(cdClienteOld);
            }
            if (cdClienteNew != null && !cdClienteNew.equals(cdClienteOld)) {
                cdClienteNew.getObraCollection().add(obra);
                cdClienteNew = em.merge(cdClienteNew);
            }
            for (LancamentoDespesa lancamentoDespesaCollectionOldLancamentoDespesa : lancamentoDespesaCollectionOld) {
                if (!lancamentoDespesaCollectionNew.contains(lancamentoDespesaCollectionOldLancamentoDespesa)) {
                    lancamentoDespesaCollectionOldLancamentoDespesa.setCdObra(null);
                    lancamentoDespesaCollectionOldLancamentoDespesa = em.merge(lancamentoDespesaCollectionOldLancamentoDespesa);
                }
            }
            for (LancamentoDespesa lancamentoDespesaCollectionNewLancamentoDespesa : lancamentoDespesaCollectionNew) {
                if (!lancamentoDespesaCollectionOld.contains(lancamentoDespesaCollectionNewLancamentoDespesa)) {
                    Obra oldCdObraOfLancamentoDespesaCollectionNewLancamentoDespesa = lancamentoDespesaCollectionNewLancamentoDespesa.getCdObra();
                    lancamentoDespesaCollectionNewLancamentoDespesa.setCdObra(obra);
                    lancamentoDespesaCollectionNewLancamentoDespesa = em.merge(lancamentoDespesaCollectionNewLancamentoDespesa);
                    if (oldCdObraOfLancamentoDespesaCollectionNewLancamentoDespesa != null && !oldCdObraOfLancamentoDespesaCollectionNewLancamentoDespesa.equals(obra)) {
                        oldCdObraOfLancamentoDespesaCollectionNewLancamentoDespesa.getLancamentoDespesaCollection().remove(lancamentoDespesaCollectionNewLancamentoDespesa);
                        oldCdObraOfLancamentoDespesaCollectionNewLancamentoDespesa = em.merge(oldCdObraOfLancamentoDespesaCollectionNewLancamentoDespesa);
                    }
                }
            }
            for (LancamentoRecebimento lancamentoRecebimentoCollectionOldLancamentoRecebimento : lancamentoRecebimentoCollectionOld) {
                if (!lancamentoRecebimentoCollectionNew.contains(lancamentoRecebimentoCollectionOldLancamentoRecebimento)) {
                    lancamentoRecebimentoCollectionOldLancamentoRecebimento.setCdObra(null);
                    lancamentoRecebimentoCollectionOldLancamentoRecebimento = em.merge(lancamentoRecebimentoCollectionOldLancamentoRecebimento);
                }
            }
            for (LancamentoRecebimento lancamentoRecebimentoCollectionNewLancamentoRecebimento : lancamentoRecebimentoCollectionNew) {
                if (!lancamentoRecebimentoCollectionOld.contains(lancamentoRecebimentoCollectionNewLancamentoRecebimento)) {
                    Obra oldCdObraOfLancamentoRecebimentoCollectionNewLancamentoRecebimento = lancamentoRecebimentoCollectionNewLancamentoRecebimento.getCdObra();
                    lancamentoRecebimentoCollectionNewLancamentoRecebimento.setCdObra(obra);
                    lancamentoRecebimentoCollectionNewLancamentoRecebimento = em.merge(lancamentoRecebimentoCollectionNewLancamentoRecebimento);
                    if (oldCdObraOfLancamentoRecebimentoCollectionNewLancamentoRecebimento != null && !oldCdObraOfLancamentoRecebimentoCollectionNewLancamentoRecebimento.equals(obra)) {
                        oldCdObraOfLancamentoRecebimentoCollectionNewLancamentoRecebimento.getLancamentoRecebimentoCollection().remove(lancamentoRecebimentoCollectionNewLancamentoRecebimento);
                        oldCdObraOfLancamentoRecebimentoCollectionNewLancamentoRecebimento = em.merge(oldCdObraOfLancamentoRecebimentoCollectionNewLancamentoRecebimento);
                    }
                }
            }
            for (LancServPrestado lancServPrestadoCollectionNewLancServPrestado : lancServPrestadoCollectionNew) {
                if (!lancServPrestadoCollectionOld.contains(lancServPrestadoCollectionNewLancServPrestado)) {
                    Obra oldObraOfLancServPrestadoCollectionNewLancServPrestado = lancServPrestadoCollectionNewLancServPrestado.getObra();
                    lancServPrestadoCollectionNewLancServPrestado.setObra(obra);
                    lancServPrestadoCollectionNewLancServPrestado = em.merge(lancServPrestadoCollectionNewLancServPrestado);
                    if (oldObraOfLancServPrestadoCollectionNewLancServPrestado != null && !oldObraOfLancServPrestadoCollectionNewLancServPrestado.equals(obra)) {
                        oldObraOfLancServPrestadoCollectionNewLancServPrestado.getLancServPrestadoCollection().remove(lancServPrestadoCollectionNewLancServPrestado);
                        oldObraOfLancServPrestadoCollectionNewLancServPrestado = em.merge(oldObraOfLancServPrestadoCollectionNewLancServPrestado);
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
                Integer id = obra.getCdObra();
                if (findObra(id) == null) {
                    throw new NonexistentEntityException("The obra with id " + id + " no longer exists.");
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
            Obra obra;
            try {
                obra = em.getReference(Obra.class, id);
                obra.getCdObra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The obra with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<LancServPrestado> lancServPrestadoCollectionOrphanCheck = obra.getLancServPrestadoCollection();
            for (LancServPrestado lancServPrestadoCollectionOrphanCheckLancServPrestado : lancServPrestadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Obra (" + obra + ") cannot be destroyed since the LancServPrestado " + lancServPrestadoCollectionOrphanCheckLancServPrestado + " in its lancServPrestadoCollection field has a non-nullable obra field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente cdCliente = obra.getCdCliente();
            if (cdCliente != null) {
                cdCliente.getObraCollection().remove(obra);
                cdCliente = em.merge(cdCliente);
            }
            Collection<LancamentoDespesa> lancamentoDespesaCollection = obra.getLancamentoDespesaCollection();
            for (LancamentoDespesa lancamentoDespesaCollectionLancamentoDespesa : lancamentoDespesaCollection) {
                lancamentoDespesaCollectionLancamentoDespesa.setCdObra(null);
                lancamentoDespesaCollectionLancamentoDespesa = em.merge(lancamentoDespesaCollectionLancamentoDespesa);
            }
            Collection<LancamentoRecebimento> lancamentoRecebimentoCollection = obra.getLancamentoRecebimentoCollection();
            for (LancamentoRecebimento lancamentoRecebimentoCollectionLancamentoRecebimento : lancamentoRecebimentoCollection) {
                lancamentoRecebimentoCollectionLancamentoRecebimento.setCdObra(null);
                lancamentoRecebimentoCollectionLancamentoRecebimento = em.merge(lancamentoRecebimentoCollectionLancamentoRecebimento);
            }
            em.remove(obra);
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

    public List<Obra> findObraEntities() {
        return findObraEntities(true, -1, -1);
    }

    public List<Obra> findObraEntities(int maxResults, int firstResult) {
        return findObraEntities(false, maxResults, firstResult);
    }

    private List<Obra> findObraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Obra.class));
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

    public Obra findObra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Obra.class, id);
        } finally {
            em.close();
        }
    }

    public int getObraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Obra> rt = cq.from(Obra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
