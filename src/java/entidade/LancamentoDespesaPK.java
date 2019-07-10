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
public class LancamentoDespesaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "cd_despesa")
    private int cdDespesa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_despesa")
    @Temporal(TemporalType.DATE)
    private Date dataDespesa;

    public LancamentoDespesaPK() {
    }

    public LancamentoDespesaPK(int cdDespesa, Date dataDespesa) {
        this.cdDespesa = cdDespesa;
        this.dataDespesa = dataDespesa;
    }

    public int getCdDespesa() {
        return cdDespesa;
    }

    public void setCdDespesa(int cdDespesa) {
        this.cdDespesa = cdDespesa;
    }

    public Date getDataDespesa() {
        return dataDespesa;
    }

    public void setDataDespesa(Date dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cdDespesa;
        hash += (dataDespesa != null ? dataDespesa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LancamentoDespesaPK)) {
            return false;
        }
        LancamentoDespesaPK other = (LancamentoDespesaPK) object;
        if (this.cdDespesa != other.cdDespesa) {
            return false;
        }
        if ((this.dataDespesa == null && other.dataDespesa != null) || (this.dataDespesa != null && !this.dataDespesa.equals(other.dataDespesa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.LancamentoDespesaPK[ cdDespesa=" + cdDespesa + ", dataDespesa=" + dataDespesa + " ]";
    }
    
}
