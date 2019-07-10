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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Administrador
 */
@Embeddable
public class PagtoFuncionarioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "cpf_funcionario")
    private long cpfFuncionario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_inicio")
    @Temporal(TemporalType.DATE)
    private Date dtInicio;

    public PagtoFuncionarioPK() {
    }

    public PagtoFuncionarioPK(long cpfFuncionario, Date dtInicio) {
        this.cpfFuncionario = cpfFuncionario;
        this.dtInicio = dtInicio;
    }

    public long getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(long cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cpfFuncionario;
        hash += (dtInicio != null ? dtInicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagtoFuncionarioPK)) {
            return false;
        }
        PagtoFuncionarioPK other = (PagtoFuncionarioPK) object;
        if (this.cpfFuncionario != other.cpfFuncionario) {
            return false;
        }
        if ((this.dtInicio == null && other.dtInicio != null) || (this.dtInicio != null && !this.dtInicio.equals(other.dtInicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.PagtoFuncionarioPK[ cpfFuncionario=" + cpfFuncionario + ", dtInicio=" + dtInicio + " ]";
    }
    
}
