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
import entidade.Bem;
import entidade.Funcionario;
import entidade.LancServPrestado;
import entidade.LancServPrestadoPK;
import entidade.Obra;
import entidade.Servico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class LancServPrestadoJpaController implements Serializable {

    public LancServPrestadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LancServPrestado lancServPrestado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (lancServPrestado.getLancServPrestadoPK() == null) {
            lancServPrestado.setLancServPrestadoPK(new LancServPrestadoPK());
        }
        lancServPrestado.getLancServPrestadoPK().setCpfFucionario(lancServPrestado.getFuncionario().getCpf());
        lancServPrestado.getLancServPrestadoPK().setCdObra(lancServPrestado.getObra().getCdObra());
        lancServPrestado.getLancServPrestadoPK().setCdServico(lancServPrestado.getServico().getCdServico());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Bem cdBem = lancServPrestado.getCdBem();
            if (cdBem != null) {
                cdBem = em.getReference(cdBem.getClass(), cdBem.getCdBem());
                lancServPrestado.setCdBem(cdBem);
            }
            Funcionario funcionario = lancServPrestado.getFuncionario();
            if (funcionario != null) {
                funcionario = em.getReference(funcionario.getClass(), funcionario.getCpf());
                lancServPrestado.setFuncionario(funcionario);
            }
            Obra obra = lancServPrestado.getObra();
            if (obra != null) {
                obra = em.getReference(obra.getClass(), obra.getCdObra());
                lancServPrestado.setObra(obra);
            }
            Servico servico = lancServPrestado.getServico();
            if (servico != null) {
                servico = em.getReference(servico.getClass(), servico.getCdServico());
                lancServPrestado.setServico(servico);
            }
            em.persist(lancServPrestado);
            if (cdBem != null) {
                cdBem.getLancServPrestadoCollection().add(lancServPrestado);
                cdBem = em.merge(cdBem);
            }
            if (funcionario != null) {
                funcionario.getLancServPrestadoCollection().add(lancServPrestado);
                funcionario = em.merge(funcionario);
            }
            if (obra != null) {
                obra.getLancServPrestadoCollection().add(lancServPrestado);
                obra = em.merge(obra);
            }
            if (servico != null) {
                servico.getLancServPrestadoCollection().add(lancServPrestado);
                servico = em.merge(servico);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLancServPrestado(lancServPrestado.getLancServPrestadoPK()) != null) {
                throw new PreexistingEntityException("LancServPrestado " + lancServPrestado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LancServPrestado lancServPrestado) throws NonexistentEntityException, RollbackFailureException, Exception {
        lancServPrestado.getLancServPrestadoPK().setCpfFucionario(lancServPrestado.getFuncionario().getCpf());
        lancServPrestado.getLancServPrestadoPK().setCdObra(lancServPrestado.getObra().getCdObra());
        lancServPrestado.getLancServPrestadoPK().setCdServico(lancServPrestado.getServico().getCdServico());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            LancServPrestado persistentLancServPrestado = em.find(LancServPrestado.class, lancServPrestado.getLancServPrestadoPK());
            Bem cdBemOld = persistentLancServPrestado.getCdBem();
            Bem cdBemNew = lancServPrestado.getCdBem();
            Funcionario funcionarioOld = persistentLancServPrestado.getFuncionario();
            Funcionario funcionarioNew = lancServPrestado.getFuncionario();
            Obra obraOld = persistentLancServPrestado.getObra();
            Obra obraNew = lancServPrestado.getObra();
            Servico servicoOld = persistentLancServPrestado.getServico();
            Servico servicoNew = lancServPrestado.getServico();
            if (cdBemNew != null) {
                cdBemNew = em.getReference(cdBemNew.getClass(), cdBemNew.getCdBem());
                lancServPrestado.setCdBem(cdBemNew);
            }
            if (funcionarioNew != null) {
                funcionarioNew = em.getReference(funcionarioNew.getClass(), funcionarioNew.getCpf());
                lancServPrestado.setFuncionario(funcionarioNew);
            }
            if (obraNew != null) {
                obraNew = em.getReference(obraNew.getClass(), obraNew.getCdObra());
                lancServPrestado.setObra(obraNew);
            }
            if (servicoNew != null) {
                servicoNew = em.getReference(servicoNew.getClass(), servicoNew.getCdServico());
                lancServPrestado.setServico(servicoNew);
            }
            lancServPrestado = em.merge(lancServPrestado);
            if (cdBemOld != null && !cdBemOld.equals(cdBemNew)) {
                cdBemOld.getLancServPrestadoCollection().remove(lancServPrestado);
                cdBemOld = em.merge(cdBemOld);
            }
            if (cdBemNew != null && !cdBemNew.equals(cdBemOld)) {
                cdBemNew.getLancServPrestadoCollection().add(lancServPrestado);
                cdBemNew = em.merge(cdBemNew);
            }
            if (funcionarioOld != null && !funcionarioOld.equals(funcionarioNew)) {
                funcionarioOld.getLancServPrestadoCollection().remove(lancServPrestado);
                funcionarioOld = em.merge(funcionarioOld);
            }
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                funcionarioNew.getLancServPrestadoCollection().add(lancServPrestado);
                funcionarioNew = em.merge(funcionarioNew);
            }
            if (obraOld != null && !obraOld.equals(obraNew)) {
                obraOld.getLancServPrestadoCollection().remove(lancServPrestado);
                obraOld = em.merge(obraOld);
            }
            if (obraNew != null && !obraNew.equals(obraOld)) {
                obraNew.getLancServPrestadoCollection().add(lancServPrestado);
                obraNew = em.merge(obraNew);
            }
            if (servicoOld != null && !servicoOld.equals(servicoNew)) {
                servicoOld.getLancServPrestadoCollection().remove(lancServPrestado);
                servicoOld = em.merge(servicoOld);
            }
            if (servicoNew != null && !servicoNew.equals(servicoOld)) {
                servicoNew.getLancServPrestadoCollection().add(lancServPrestado);
                servicoNew = em.merge(servicoNew);
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
                LancServPrestadoPK id = lancServPrestado.getLancServPrestadoPK();
                if (findLancServPrestado(id) == null) {
                    throw new NonexistentEntityException("The lancServPrestado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LancServPrestadoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            LancServPrestado lancServPrestado;
            try {
                lancServPrestado = em.getReference(LancServPrestado.class, id);
                lancServPrestado.getLancServPrestadoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lancServPrestado with id " + id + " no longer exists.", enfe);
            }
            Bem cdBem = lancServPrestado.getCdBem();
            if (cdBem != null) {
                cdBem.getLancServPrestadoCollection().remove(lancServPrestado);
                cdBem = em.merge(cdBem);
            }
            Funcionario funcionario = lancServPrestado.getFuncionario();
            if (funcionario != null) {
                funcionario.getLancServPrestadoCollection().remove(lancServPrestado);
                funcionario = em.merge(funcionario);
            }
            Obra obra = lancServPrestado.getObra();
            if (obra != null) {
                obra.getLancServPrestadoCollection().remove(lancServPrestado);
                obra = em.merge(obra);
            }
            Servico servico = lancServPrestado.getServico();
            if (servico != null) {
                servico.getLancServPrestadoCollection().remove(lancServPrestado);
                servico = em.merge(servico);
            }
            em.remove(lancServPrestado);
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

    public List<LancServPrestado> findLancServPrestadoEntities() {
        return findLancServPrestadoEntities(true, -1, -1);
    }

    public List<LancServPrestado> findLancServPrestadoEntities(int maxResults, int firstResult) {
        return findLancServPrestadoEntities(false, maxResults, firstResult);
    }

    private List<LancServPrestado> findLancServPrestadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LancServPrestado.class));
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

    public LancServPrestado findLancServPrestado(LancServPrestadoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LancServPrestado.class, id);
        } finally {
            em.close();
        }
    }

    public int getLancServPrestadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LancServPrestado> rt = cq.from(LancServPrestado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
