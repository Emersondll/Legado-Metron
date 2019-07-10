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
public class LancServPrestadoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "dt_servico")
    @Temporal(TemporalType.DATE)
    private Date dtServico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cpf_fucionario")
    private long cpfFucionario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cd_obra")
    private int cdObra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cd_servico")
    private int cdServico;

    public LancServPrestadoPK() {
    }

    public LancServPrestadoPK(Date dtServico, long cpfFucionario, int cdObra, int cdServico) {
        this.dtServico = dtServico;
        this.cpfFucionario = cpfFucionario;
        this.cdObra = cdObra;
        this.cdServico = cdServico;
    }

    public Date getDtServico() {
        return dtServico;
    }

    public void setDtServico(Date dtServico) {
        this.dtServico = dtServico;
    }

    public long getCpfFucionario() {
        return cpfFucionario;
    }

    public void setCpfFucionario(long cpfFucionario) {
        this.cpfFucionario = cpfFucionario;
    }

    public int getCdObra() {
        return cdObra;
    }

    public void setCdObra(int cdObra) {
        this.cdObra = cdObra;
    }

    public int getCdServico() {
        return cdServico;
    }

    public void setCdServico(int cdServico) {
        this.cdServico = cdServico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dtServico != null ? dtServico.hashCode() : 0);
        hash += (int) cpfFucionario;
        hash += (int) cdObra;
        hash += (int) cdServico;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LancServPrestadoPK)) {
            return false;
        }
        LancServPrestadoPK other = (LancServPrestadoPK) object;
        if ((this.dtServico == null && other.dtServico != null) || (this.dtServico != null && !this.dtServico.equals(other.dtServico))) {
            return false;
        }
        if (this.cpfFucionario != other.cpfFucionario) {
            return false;
        }
        if (this.cdObra != other.cdObra) {
            return false;
        }
        if (this.cdServico != other.cdServico) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.LancServPrestadoPK[ dtServico=" + dtServico + ", cpfFucionario=" + cpfFucionario + ", cdObra=" + cdObra + ", cdServico=" + cdServico + " ]";
    }
    
}
