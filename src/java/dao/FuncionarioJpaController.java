/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidade.LancServPrestado;
import java.util.ArrayList;
import java.util.Collection;
import entidade.Integracao;
import entidade.PagtoFuncionario;
import entidade.Epi;
import entidade.Funcionario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class FuncionarioJpaController implements Serializable {

    public FuncionarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (funcionario.getLancServPrestadoCollection() == null) {
            funcionario.setLancServPrestadoCollection(new ArrayList<LancServPrestado>());
        }
        if (funcionario.getIntegracaoCollection() == null) {
            funcionario.setIntegracaoCollection(new ArrayList<Integracao>());
        }
        if (funcionario.getPagtoFuncionarioCollection() == null) {
            funcionario.setPagtoFuncionarioCollection(new ArrayList<PagtoFuncionario>());
        }
        if (funcionario.getEpiCollection() == null) {
            funcionario.setEpiCollection(new ArrayList<Epi>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<LancServPrestado> attachedLancServPrestadoCollection = new ArrayList<LancServPrestado>();
            for (LancServPrestado lancServPrestadoCollectionLancServPrestadoToAttach : funcionario.getLancServPrestadoCollection()) {
                lancServPrestadoCollectionLancServPrestadoToAttach = em.getReference(lancServPrestadoCollectionLancServPrestadoToAttach.getClass(), lancServPrestadoCollectionLancServPrestadoToAttach.getLancServPrestadoPK());
                attachedLancServPrestadoCollection.add(lancServPrestadoCollectionLancServPrestadoToAttach);
            }
            funcionario.setLancServPrestadoCollection(attachedLancServPrestadoCollection);
            Collection<Integracao> attachedIntegracaoCollection = new ArrayList<Integracao>();
            for (Integracao integracaoCollectionIntegracaoToAttach : funcionario.getIntegracaoCollection()) {
                integracaoCollectionIntegracaoToAttach = em.getReference(integracaoCollectionIntegracaoToAttach.getClass(), integracaoCollectionIntegracaoToAttach.getIdIntegracao());
                attachedIntegracaoCollection.add(integracaoCollectionIntegracaoToAttach);
            }
            funcionario.setIntegracaoCollection(attachedIntegracaoCollection);
            Collection<PagtoFuncionario> attachedPagtoFuncionarioCollection = new ArrayList<PagtoFuncionario>();
            for (PagtoFuncionario pagtoFuncionarioCollectionPagtoFuncionarioToAttach : funcionario.getPagtoFuncionarioCollection()) {
                pagtoFuncionarioCollectionPagtoFuncionarioToAttach = em.getReference(pagtoFuncionarioCollectionPagtoFuncionarioToAttach.getClass(), pagtoFuncionarioCollectionPagtoFuncionarioToAttach.getPagtoFuncionarioPK());
                attachedPagtoFuncionarioCollection.add(pagtoFuncionarioCollectionPagtoFuncionarioToAttach);
            }
            funcionario.setPagtoFuncionarioCollection(attachedPagtoFuncionarioCollection);
            Collection<Epi> attachedEpiCollection = new ArrayList<Epi>();
            for (Epi epiCollectionEpiToAttach : funcionario.getEpiCollection()) {
                epiCollectionEpiToAttach = em.getReference(epiCollectionEpiToAttach.getClass(), epiCollectionEpiToAttach.getIdEpi());
                attachedEpiCollection.add(epiCollectionEpiToAttach);
            }
            funcionario.setEpiCollection(attachedEpiCollection);
            em.persist(funcionario);
            for (LancServPrestado lancServPrestadoCollectionLancServPrestado : funcionario.getLancServPrestadoCollection()) {
                Funcionario oldFuncionarioOfLancServPrestadoCollectionLancServPrestado = lancServPrestadoCollectionLancServPrestado.getFuncionario();
                lancServPrestadoCollectionLancServPrestado.setFuncionario(funcionario);
                lancServPrestadoCollectionLancServPrestado = em.merge(lancServPrestadoCollectionLancServPrestado);
                if (oldFuncionarioOfLancServPrestadoCollectionLancServPrestado != null) {
                    oldFuncionarioOfLancServPrestadoCollectionLancServPrestado.getLancServPrestadoCollection().remove(lancServPrestadoCollectionLancServPrestado);
                    oldFuncionarioOfLancServPrestadoCollectionLancServPrestado = em.merge(oldFuncionarioOfLancServPrestadoCollectionLancServPrestado);
                }
            }
            for (Integracao integracaoCollectionIntegracao : funcionario.getIntegracaoCollection()) {
                Funcionario oldCpfFuncionarioOfIntegracaoCollectionIntegracao = integracaoCollectionIntegracao.getCpfFuncionario();
                integracaoCollectionIntegracao.setCpfFuncionario(funcionario);
                integracaoCollectionIntegracao = em.merge(integracaoCollectionIntegracao);
                if (oldCpfFuncionarioOfIntegracaoCollectionIntegracao != null) {
                    oldCpfFuncionarioOfIntegracaoCollectionIntegracao.getIntegracaoCollection().remove(integracaoCollectionIntegracao);
                    oldCpfFuncionarioOfIntegracaoCollectionIntegracao = em.merge(oldCpfFuncionarioOfIntegracaoCollectionIntegracao);
                }
            }
            for (PagtoFuncionario pagtoFuncionarioCollectionPagtoFuncionario : funcionario.getPagtoFuncionarioCollection()) {
                Funcionario oldFuncionarioOfPagtoFuncionarioCollectionPagtoFuncionario = pagtoFuncionarioCollectionPagtoFuncionario.getFuncionario();
                pagtoFuncionarioCollectionPagtoFuncionario.setFuncionario(funcionario);
                pagtoFuncionarioCollectionPagtoFuncionario = em.merge(pagtoFuncionarioCollectionPagtoFuncionario);
                if (oldFuncionarioOfPagtoFuncionarioCollectionPagtoFuncionario != null) {
                    oldFuncionarioOfPagtoFuncionarioCollectionPagtoFuncionario.getPagtoFuncionarioCollection().remove(pagtoFuncionarioCollectionPagtoFuncionario);
                    oldFuncionarioOfPagtoFuncionarioCollectionPagtoFuncionario = em.merge(oldFuncionarioOfPagtoFuncionarioCollectionPagtoFuncionario);
                }
            }
            for (Epi epiCollectionEpi : funcionario.getEpiCollection()) {
                Funcionario oldFuncionarioOfEpiCollectionEpi = epiCollectionEpi.getFuncionario();
                epiCollectionEpi.setFuncionario(funcionario);
                epiCollectionEpi = em.merge(epiCollectionEpi);
                if (oldFuncionarioOfEpiCollectionEpi != null) {
                    oldFuncionarioOfEpiCollectionEpi.getEpiCollection().remove(epiCollectionEpi);
                    oldFuncionarioOfEpiCollectionEpi = em.merge(oldFuncionarioOfEpiCollectionEpi);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFuncionario(funcionario.getCpf()) != null) {
                throw new PreexistingEntityException("Funcionario " + funcionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getCpf());
            Collection<LancServPrestado> lancServPrestadoCollectionOld = persistentFuncionario.getLancServPrestadoCollection();
            Collection<LancServPrestado> lancServPrestadoCollectionNew = funcionario.getLancServPrestadoCollection();
            Collection<Integracao> integracaoCollectionOld = persistentFuncionario.getIntegracaoCollection();
            Collection<Integracao> integracaoCollectionNew = funcionario.getIntegracaoCollection();
            Collection<PagtoFuncionario> pagtoFuncionarioCollectionOld = persistentFuncionario.getPagtoFuncionarioCollection();
            Collection<PagtoFuncionario> pagtoFuncionarioCollectionNew = funcionario.getPagtoFuncionarioCollection();
            Collection<Epi> epiCollectionOld = persistentFuncionario.getEpiCollection();
            Collection<Epi> epiCollectionNew = funcionario.getEpiCollection();
            List<String> illegalOrphanMessages = null;
            for (LancServPrestado lancServPrestadoCollectionOldLancServPrestado : lancServPrestadoCollectionOld) {
                if (!lancServPrestadoCollectionNew.contains(lancServPrestadoCollectionOldLancServPrestado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LancServPrestado " + lancServPrestadoCollectionOldLancServPrestado + " since its funcionario field is not nullable.");
                }
            }
            for (PagtoFuncionario pagtoFuncionarioCollectionOldPagtoFuncionario : pagtoFuncionarioCollectionOld) {
                if (!pagtoFuncionarioCollectionNew.contains(pagtoFuncionarioCollectionOldPagtoFuncionario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagtoFuncionario " + pagtoFuncionarioCollectionOldPagtoFuncionario + " since its funcionario field is not nullable.");
                }
            }
            for (Epi epiCollectionOldEpi : epiCollectionOld) {
                if (!epiCollectionNew.contains(epiCollectionOldEpi)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Epi " + epiCollectionOldEpi + " since its funcionario field is not nullable.");
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
            funcionario.setLancServPrestadoCollection(lancServPrestadoCollectionNew);
            Collection<Integracao> attachedIntegracaoCollectionNew = new ArrayList<Integracao>();
            for (Integracao integracaoCollectionNewIntegracaoToAttach : integracaoCollectionNew) {
                integracaoCollectionNewIntegracaoToAttach = em.getReference(integracaoCollectionNewIntegracaoToAttach.getClass(), integracaoCollectionNewIntegracaoToAttach.getIdIntegracao());
                attachedIntegracaoCollectionNew.add(integracaoCollectionNewIntegracaoToAttach);
            }
            integracaoCollectionNew = attachedIntegracaoCollectionNew;
            funcionario.setIntegracaoCollection(integracaoCollectionNew);
            Collection<PagtoFuncionario> attachedPagtoFuncionarioCollectionNew = new ArrayList<PagtoFuncionario>();
            for (PagtoFuncionario pagtoFuncionarioCollectionNewPagtoFuncionarioToAttach : pagtoFuncionarioCollectionNew) {
                pagtoFuncionarioCollectionNewPagtoFuncionarioToAttach = em.getReference(pagtoFuncionarioCollectionNewPagtoFuncionarioToAttach.getClass(), pagtoFuncionarioCollectionNewPagtoFuncionarioToAttach.getPagtoFuncionarioPK());
                attachedPagtoFuncionarioCollectionNew.add(pagtoFuncionarioCollectionNewPagtoFuncionarioToAttach);
            }
            pagtoFuncionarioCollectionNew = attachedPagtoFuncionarioCollectionNew;
            funcionario.setPagtoFuncionarioCollection(pagtoFuncionarioCollectionNew);
            Collection<Epi> attachedEpiCollectionNew = new ArrayList<Epi>();
            for (Epi epiCollectionNewEpiToAttach : epiCollectionNew) {
                epiCollectionNewEpiToAttach = em.getReference(epiCollectionNewEpiToAttach.getClass(), epiCollectionNewEpiToAttach.getIdEpi());
                attachedEpiCollectionNew.add(epiCollectionNewEpiToAttach);
            }
            epiCollectionNew = attachedEpiCollectionNew;
            funcionario.setEpiCollection(epiCollectionNew);
            funcionario = em.merge(funcionario);
            for (LancServPrestado lancServPrestadoCollectionNewLancServPrestado : lancServPrestadoCollectionNew) {
                if (!lancServPrestadoCollectionOld.contains(lancServPrestadoCollectionNewLancServPrestado)) {
                    Funcionario oldFuncionarioOfLancServPrestadoCollectionNewLancServPrestado = lancServPrestadoCollectionNewLancServPrestado.getFuncionario();
                    lancServPrestadoCollectionNewLancServPrestado.setFuncionario(funcionario);
                    lancServPrestadoCollectionNewLancServPrestado = em.merge(lancServPrestadoCollectionNewLancServPrestado);
                    if (oldFuncionarioOfLancServPrestadoCollectionNewLancServPrestado != null && !oldFuncionarioOfLancServPrestadoCollectionNewLancServPrestado.equals(funcionario)) {
                        oldFuncionarioOfLancServPrestadoCollectionNewLancServPrestado.getLancServPrestadoCollection().remove(lancServPrestadoCollectionNewLancServPrestado);
                        oldFuncionarioOfLancServPrestadoCollectionNewLancServPrestado = em.merge(oldFuncionarioOfLancServPrestadoCollectionNewLancServPrestado);
                    }
                }
            }
            for (Integracao integracaoCollectionOldIntegracao : integracaoCollectionOld) {
                if (!integracaoCollectionNew.contains(integracaoCollectionOldIntegracao)) {
                    integracaoCollectionOldIntegracao.setCpfFuncionario(null);
                    integracaoCollectionOldIntegracao = em.merge(integracaoCollectionOldIntegracao);
                }
            }
            for (Integracao integracaoCollectionNewIntegracao : integracaoCollectionNew) {
                if (!integracaoCollectionOld.contains(integracaoCollectionNewIntegracao)) {
                    Funcionario oldCpfFuncionarioOfIntegracaoCollectionNewIntegracao = integracaoCollectionNewIntegracao.getCpfFuncionario();
                    integracaoCollectionNewIntegracao.setCpfFuncionario(funcionario);
                    integracaoCollectionNewIntegracao = em.merge(integracaoCollectionNewIntegracao);
                    if (oldCpfFuncionarioOfIntegracaoCollectionNewIntegracao != null && !oldCpfFuncionarioOfIntegracaoCollectionNewIntegracao.equals(funcionario)) {
                        oldCpfFuncionarioOfIntegracaoCollectionNewIntegracao.getIntegracaoCollection().remove(integracaoCollectionNewIntegracao);
                        oldCpfFuncionarioOfIntegracaoCollectionNewIntegracao = em.merge(oldCpfFuncionarioOfIntegracaoCollectionNewIntegracao);
                    }
                }
            }
            for (PagtoFuncionario pagtoFuncionarioCollectionNewPagtoFuncionario : pagtoFuncionarioCollectionNew) {
                if (!pagtoFuncionarioCollectionOld.contains(pagtoFuncionarioCollectionNewPagtoFuncionario)) {
                    Funcionario oldFuncionarioOfPagtoFuncionarioCollectionNewPagtoFuncionario = pagtoFuncionarioCollectionNewPagtoFuncionario.getFuncionario();
                    pagtoFuncionarioCollectionNewPagtoFuncionario.setFuncionario(funcionario);
                    pagtoFuncionarioCollectionNewPagtoFuncionario = em.merge(pagtoFuncionarioCollectionNewPagtoFuncionario);
                    if (oldFuncionarioOfPagtoFuncionarioCollectionNewPagtoFuncionario != null && !oldFuncionarioOfPagtoFuncionarioCollectionNewPagtoFuncionario.equals(funcionario)) {
                        oldFuncionarioOfPagtoFuncionarioCollectionNewPagtoFuncionario.getPagtoFuncionarioCollection().remove(pagtoFuncionarioCollectionNewPagtoFuncionario);
                        oldFuncionarioOfPagtoFuncionarioCollectionNewPagtoFuncionario = em.merge(oldFuncionarioOfPagtoFuncionarioCollectionNewPagtoFuncionario);
                    }
                }
            }
            for (Epi epiCollectionNewEpi : epiCollectionNew) {
                if (!epiCollectionOld.contains(epiCollectionNewEpi)) {
                    Funcionario oldFuncionarioOfEpiCollectionNewEpi = epiCollectionNewEpi.getFuncionario();
                    epiCollectionNewEpi.setFuncionario(funcionario);
                    epiCollectionNewEpi = em.merge(epiCollectionNewEpi);
                    if (oldFuncionarioOfEpiCollectionNewEpi != null && !oldFuncionarioOfEpiCollectionNewEpi.equals(funcionario)) {
                        oldFuncionarioOfEpiCollectionNewEpi.getEpiCollection().remove(epiCollectionNewEpi);
                        oldFuncionarioOfEpiCollectionNewEpi = em.merge(oldFuncionarioOfEpiCollectionNewEpi);
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
                Long id = funcionario.getCpf();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getCpf();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<LancServPrestado> lancServPrestadoCollectionOrphanCheck = funcionario.getLancServPrestadoCollection();
            for (LancServPrestado lancServPrestadoCollectionOrphanCheckLancServPrestado : lancServPrestadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the LancServPrestado " + lancServPrestadoCollectionOrphanCheckLancServPrestado + " in its lancServPrestadoCollection field has a non-nullable funcionario field.");
            }
            Collection<PagtoFuncionario> pagtoFuncionarioCollectionOrphanCheck = funcionario.getPagtoFuncionarioCollection();
            for (PagtoFuncionario pagtoFuncionarioCollectionOrphanCheckPagtoFuncionario : pagtoFuncionarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the PagtoFuncionario " + pagtoFuncionarioCollectionOrphanCheckPagtoFuncionario + " in its pagtoFuncionarioCollection field has a non-nullable funcionario field.");
            }
            Collection<Epi> epiCollectionOrphanCheck = funcionario.getEpiCollection();
            for (Epi epiCollectionOrphanCheckEpi : epiCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Epi " + epiCollectionOrphanCheckEpi + " in its epiCollection field has a non-nullable funcionario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Integracao> integracaoCollection = funcionario.getIntegracaoCollection();
            for (Integracao integracaoCollectionIntegracao : integracaoCollection) {
                integracaoCollectionIntegracao.setCpfFuncionario(null);
                integracaoCollectionIntegracao = em.merge(integracaoCollectionIntegracao);
            }
            em.remove(funcionario);
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

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
