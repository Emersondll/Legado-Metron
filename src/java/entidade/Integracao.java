/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "integracao")
@NamedQueries({
    @NamedQuery(name = "Integracao.findAll", query = "SELECT i FROM Integracao i"),
    @NamedQuery(name = "Integracao.findByIdIntegracao", query = "SELECT i FROM Integracao i WHERE i.idIntegracao = :idIntegracao"),
    @NamedQuery(name = "Integracao.findByDtInicio", query = "SELECT i FROM Integracao i WHERE i.dtInicio = :dtInicio"),
    @NamedQuery(name = "Integracao.findByDtFim", query = "SELECT i FROM Integracao i WHERE i.dtFim = :dtFim"),
    @NamedQuery(name = "Integracao.findByEmpresaIntegracao", query = "SELECT i FROM Integracao i WHERE i.empresaIntegracao = :empresaIntegracao")})
public class Integracao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_integracao")
    private Integer idIntegracao;
    @Column(name = "dt_inicio")
    @Temporal(TemporalType.DATE)
    private Date dtInicio;
    @Column(name = "dt_fim")
    @Temporal(TemporalType.DATE)
    private Date dtFim;
    @Size(max = 2147483647)
    @Column(name = "empresa_integracao")
    private String empresaIntegracao;
    @JoinColumn(name = "cpf_funcionario", referencedColumnName = "cpf")
    @ManyToOne
    private Funcionario cpfFuncionario;

    public Integracao() {
    }

    public Integracao(Integer idIntegracao) {
        this.idIntegracao = idIntegracao;
    }

    public Integer getIdIntegracao() {
        return idIntegracao;
    }

    public void setIdIntegracao(Integer idIntegracao) {
        this.idIntegracao = idIntegracao;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public String getEmpresaIntegracao() {
        return empresaIntegracao;
    }

    public void setEmpresaIntegracao(String empresaIntegracao) {
        empresaIntegracao= empresaIntegracao.toUpperCase();
        this.empresaIntegracao = empresaIntegracao;
    }

    public Funcionario getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(Funcionario cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIntegracao != null ? idIntegracao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Integracao)) {
            return false;
        }
        Integracao other = (Integracao) object;
        if ((this.idIntegracao == null && other.idIntegracao != null) || (this.idIntegracao != null && !this.idIntegracao.equals(other.idIntegracao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.Integracao[ idIntegracao=" + idIntegracao + " ]";
    }
    
}
