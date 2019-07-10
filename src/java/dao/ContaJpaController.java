/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dao.exceptions.RollbackFailureException;
import entidade.Conta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.LancamentoDespesa;
import java.util.ArrayList;
import java.util.Collection;
import entidade.LancamentoRecebimento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class ContaJpaController implements Serializable {

    public ContaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Conta conta) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (conta.getLancamentoDespesaCollection() == null) {
            conta.setLancamentoDespesaCollection(new ArrayList<LancamentoDespesa>());
        }
        if (conta.getLancamentoRecebimentoCollection() == null) {
            conta.setLancamentoRecebimentoCollection(new ArrayList<LancamentoRecebimento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<LancamentoDespesa> attachedLancamentoDespesaCollection = new ArrayList<LancamentoDespesa>();
            for (LancamentoDespesa lancamentoDespesaCollectionLancamentoDespesaToAttach : conta.getLancamentoDespesaCollection()) {
                lancamentoDespesaCollectionLancamentoDespesaToAttach = em.getReference(lancamentoDespesaCollectionLancamentoDespesaToAttach.getClass(), lancamentoDespesaCollectionLancamentoDespesaToAttach.getLancamentoDespesaPK());
                attachedLancamentoDespesaCollection.add(lancamentoDespesaCollectionLancamentoDespesaToAttach);
            }
            conta.setLancamentoDespesaCollection(attachedLancamentoDespesaCollection);
            Collection<LancamentoRecebimento> attachedLancamentoRecebimentoCollection = new ArrayList<LancamentoRecebimento>();
            for (LancamentoRecebimento lancamentoRecebimentoCollectionLancamentoRecebimentoToAttach : conta.getLancamentoRecebimentoCollection()) {
                lancamentoRecebimentoCollectionLancamentoRecebimentoToAttach = em.getReference(lancamentoRecebimentoCollectionLancamentoRecebimentoToAttach.getClass(), lancamentoRecebimentoCollectionLancamentoRecebimentoToAttach.getIdLancamentoRecebimento());
                attachedLancamentoRecebimentoCollection.add(lancamentoRecebimentoCollectionLancamentoRecebimentoToAttach);
            }
            conta.setLancamentoRecebimentoCollection(attachedLancamentoRecebimentoCollection);
            em.persist(conta);
            for (LancamentoDespesa lancamentoDespesaCollectionLancamentoDespesa : conta.getLancamentoDespesaCollection()) {
                Conta oldContaOfLancamentoDespesaCollectionLancamentoDespesa = lancamentoDespesaCollectionLancamentoDespesa.getConta();
                lancamentoDespesaCollectionLancamentoDespesa.setConta(conta);
                lancamentoDespesaCollectionLancamentoDespesa = em.merge(lancamentoDespesaCollectionLancamentoDespesa);
                if (oldContaOfLancamentoDespesaCollectionLancamentoDespesa != null) {
                    oldContaOfLancamentoDespesaCollectionLancamentoDespesa.getLancamentoDespesaCollection().remove(lancamentoDespesaCollectionLancamentoDespesa);
                    oldContaOfLancamentoDespesaCollectionLancamentoDespesa = em.merge(oldContaOfLancamentoDespesaCollectionLancamentoDespesa);
                }
            }
            for (LancamentoRecebimento lancamentoRecebimentoCollectionLancamentoRecebimento : conta.getLancamentoRecebimentoCollection()) {
                Conta oldContaDestinoOfLancamentoRecebimentoCollectionLancamentoRecebimento = lancamentoRecebimentoCollectionLancamentoRecebimento.getContaDestino();
                lancamentoRecebimentoCollectionLancamentoRecebimento.setContaDestino(conta);
                lancamentoRecebimentoCollectionLancamentoRecebimento = em.merge(lancamentoRecebimentoCollectionLancamentoRecebimento);
                if (oldContaDestinoOfLancamentoRecebimentoCollectionLancamentoRecebimento != null) {
                    oldContaDestinoOfLancamentoRecebimentoCollectionLancamentoRecebimento.getLancamentoRecebimentoCollection().remove(lancamentoRecebimentoCollectionLancamentoRecebimento);
                    oldContaDestinoOfLancamentoRecebimentoCollectionLancamentoRecebimento = em.merge(oldContaDestinoOfLancamentoRecebimentoCollectionLancamentoRecebimento);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConta(conta.getConta()) != null) {
                throw new PreexistingEntityException("Conta " + conta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Conta conta) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Conta persistentConta = em.find(Conta.class, conta.getConta());
            Collection<LancamentoDespesa> lancamentoDespesaCollectionOld = persistentConta.getLancamentoDespesaCollection();
            Collection<LancamentoDespesa> lancamentoDespesaCollectionNew = conta.getLancamentoDespesaCollection();
            Collection<LancamentoRecebimento> lancamentoRecebimentoCollectionOld = persistentConta.getLancamentoRecebimentoCollection();
            Collection<LancamentoRecebimento> lancamentoRecebimentoCollectionNew = conta.getLancamentoRecebimentoCollection();
            Collection<LancamentoDespesa> attachedLancamentoDespesaCollectionNew = new ArrayList<LancamentoDespesa>();
            for (LancamentoDespesa lancamentoDespesaCollectionNewLancamentoDespesaToAttach : lancamentoDespesaCollectionNew) {
                lancamentoDespesaCollectionNewLancamentoDespesaToAttach = em.getReference(lancamentoDespesaCollectionNewLancamentoDespesaToAttach.getClass(), lancamentoDespesaCollectionNewLancamentoDespesaToAttach.getLancamentoDespesaPK());
                attachedLancamentoDespesaCollectionNew.add(lancamentoDespesaCollectionNewLancamentoDespesaToAttach);
            }
            lancamentoDespesaCollectionNew = attachedLancamentoDespesaCollectionNew;
            conta.setLancamentoDespesaCollection(lancamentoDespesaCollectionNew);
            Collection<LancamentoRecebimento> attachedLancamentoRecebimentoCollectionNew = new ArrayList<LancamentoRecebimento>();
            for (LancamentoRecebimento lancamentoRecebimentoCollectionNewLancamentoRecebimentoToAttach : lancamentoRecebimentoCollectionNew) {
                lancamentoRecebimentoCollectionNewLancamentoRecebimentoToAttach = em.getReference(lancamentoRecebimentoCollectionNewLancamentoRecebimentoToAttach.getClass(), lancamentoRecebimentoCollectionNewLancamentoRecebimentoToAttach.getIdLancamentoRecebimento());
                attachedLancamentoRecebimentoCollectionNew.add(lancamentoRecebimentoCollectionNewLancamentoRecebimentoToAttach);
            }
            lancamentoRecebimentoCollectionNew = attachedLancamentoRecebimentoCollectionNew;
            conta.setLancamentoRecebimentoCollection(lancamentoRecebimentoCollectionNew);
            conta = em.merge(conta);
            for (LancamentoDespesa lancamentoDespesaCollectionOldLancamentoDespesa : lancamentoDespesaCollectionOld) {
                if (!lancamentoDespesaCollectionNew.contains(lancamentoDespesaCollectionOldLancamentoDespesa)) {
                    lancamentoDespesaCollectionOldLancamentoDespesa.setConta(null);
                    lancamentoDespesaCollectionOldLancamentoDespesa = em.merge(lancamentoDespesaCollectionOldLancamentoDespesa);
                }
            }
            for (LancamentoDespesa lancamentoDespesaCollectionNewLancamentoDespesa : lancamentoDespesaCollectionNew) {
                if (!lancamentoDespesaCollectionOld.contains(lancamentoDespesaCollectionNewLancamentoDespesa)) {
                    Conta oldContaOfLancamentoDespesaCollectionNewLancamentoDespesa = lancamentoDespesaCollectionNewLancamentoDespesa.getConta();
                    lancamentoDespesaCollectionNewLancamentoDespesa.setConta(conta);
                    lancamentoDespesaCollectionNewLancamentoDespesa = em.merge(lancamentoDespesaCollectionNewLancamentoDespesa);
                    if (oldContaOfLancamentoDespesaCollectionNewLancamentoDespesa != null && !oldContaOfLancamentoDespesaCollectionNewLancamentoDespesa.equals(conta)) {
                        oldContaOfLancamentoDespesaCollectionNewLancamentoDespesa.getLancamentoDespesaCollection().remove(lancamentoDespesaCollectionNewLancamentoDespesa);
                        oldContaOfLancamentoDespesaCollectionNewLancamentoDespesa = em.merge(oldContaOfLancamentoDespesaCollectionNewLancamentoDespesa);
                    }
                }
            }
            for (LancamentoRecebimento lancamentoRecebimentoCollectionOldLancamentoRecebimento : lancamentoRecebimentoCollectionOld) {
                if (!lancamentoRecebimentoCollectionNew.contains(lancamentoRecebimentoCollectionOldLancamentoRecebimento)) {
                    lancamentoRecebimentoCollectionOldLancamentoRecebimento.setContaDestino(null);
                    lancamentoRecebimentoCollectionOldLancamentoRecebimento = em.merge(lancamentoRecebimentoCollectionOldLancamentoRecebimento);
                }
            }
            for (LancamentoRecebimento lancamentoRecebimentoCollectionNewLancamentoRecebimento : lancamentoRecebimentoCollectionNew) {
                if (!lancamentoRecebimentoCollectionOld.contains(lancamentoRecebimentoCollectionNewLancamentoRecebimento)) {
                    Conta oldContaDestinoOfLancamentoRecebimentoCollectionNewLancamentoRecebimento = lancamentoRecebimentoCollectionNewLancamentoRecebimento.getContaDestino();
                    lancamentoRecebimentoCollectionNewLancamentoRecebimento.setContaDestino(conta);
                    lancamentoRecebimentoCollectionNewLancamentoRecebimento = em.merge(lancamentoRecebimentoCollectionNewLancamentoRecebimento);
                    if (oldContaDestinoOfLancamentoRecebimentoCollectionNewLancamentoRecebimento != null && !oldContaDestinoOfLancamentoRecebimentoCollectionNewLancamentoRecebimento.equals(conta)) {
                        oldContaDestinoOfLancamentoRecebimentoCollectionNewLancamentoRecebimento.getLancamentoRecebimentoCollection().remove(lancamentoRecebimentoCollectionNewLancamentoRecebimento);
                        oldContaDestinoOfLancamentoRecebimentoCollectionNewLancamentoRecebimento = em.merge(oldContaDestinoOfLancamentoRecebimentoCollectionNewLancamentoRecebimento);
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
                String id = conta.getConta();
                if (findConta(id) == null) {
                    throw new NonexistentEntityException("The conta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Conta conta;
            try {
                conta = em.getReference(Conta.class, id);
                conta.getConta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conta with id " + id + " no longer exists.", enfe);
            }
            Collection<LancamentoDespesa> lancamentoDespesaCollection = conta.getLancamentoDespesaCollection();
            for (LancamentoDespesa lancamentoDespesaCollectionLancamentoDespesa : lancamentoDespesaCollection) {
                lancamentoDespesaCollectionLancamentoDespesa.setConta(null);
                lancamentoDespesaCollectionLancamentoDespesa = em.merge(lancamentoDespesaCollectionLancamentoDespesa);
            }
            Collection<LancamentoRecebimento> lancamentoRecebimentoCollection = conta.getLancamentoRecebimentoCollection();
            for (LancamentoRecebimento lancamentoRecebimentoCollectionLancamentoRecebimento : lancamentoRecebimentoCollection) {
                lancamentoRecebimentoCollectionLancamentoRecebimento.setContaDestino(null);
                lancamentoRecebimentoCollectionLancamentoRecebimento = em.merge(lancamentoRecebimentoCollectionLancamentoRecebimento);
            }
            em.remove(conta);
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

    public List<Conta> findContaEntities() {
        return findContaEntities(true, -1, -1);
    }

    public List<Conta> findContaEntities(int maxResults, int firstResult) {
        return findContaEntities(false, maxResults, firstResult);
    }

    private List<Conta> findContaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Conta.class));
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

    public Conta findConta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Conta.class, id);
        } finally {
            em.close();
        }
    }

    public int getContaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Conta> rt = cq.from(Conta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
