/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "despesa")
@NamedQueries({
    @NamedQuery(name = "Despesa.findAll", query = "SELECT d FROM Despesa d"),
    @NamedQuery(name = "Despesa.findByCdDespesa", query = "SELECT d FROM Despesa d WHERE d.cdDespesa = :cdDespesa"),
    @NamedQuery(name = "Despesa.findByDsDespesa", query = "SELECT d FROM Despesa d WHERE d.dsDespesa = :dsDespesa"),
    @NamedQuery(name = "Despesa.findByVlPadrao", query = "SELECT d FROM Despesa d WHERE d.vlPadrao = :vlPadrao")})
public class Despesa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_despesa")
    private Integer cdDespesa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ds_despesa")
    private String dsDespesa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vl_padrao")
    private BigDecimal vlPadrao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "despesa")
    private Collection<LancamentoDespesa> lancamentoDespesaCollection;

    public Despesa() {
    }

    public Despesa(Integer cdDespesa) {
        this.cdDespesa = cdDespesa;
    }

    public Despesa(Integer cdDespesa, String dsDespesa) {
        this.cdDespesa = cdDespesa;
        this.dsDespesa = dsDespesa;
    }

    public Integer getCdDespesa() {
        return cdDespesa;
    }

    public void setCdDespesa(Integer cdDespesa) {
        this.cdDespesa = cdDespesa;
    }

    public String getDsDespesa() {
        return dsDespesa;
    }

    public void setDsDespesa(String dsDespesa) {
        dsDespesa =dsDespesa.toUpperCase();
        this.dsDespesa = dsDespesa;
    }

    public BigDecimal getVlPadrao() {
        return vlPadrao;
    }

    public void setVlPadrao(BigDecimal vlPadrao) {
        this.vlPadrao = vlPadrao;
    }

    public Collection<LancamentoDespesa> getLancamentoDespesaCollection() {
        return lancamentoDespesaCollection;
    }

    public void setLancamentoDespesaCollection(Collection<LancamentoDespesa> lancamentoDespesaCollection) {
        this.lancamentoDespesaCollection = lancamentoDespesaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdDespesa != null ? cdDespesa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Despesa)) {
            return false;
        }
        Despesa other = (Despesa) object;
        if ((this.cdDespesa == null && other.cdDespesa != null) || (this.cdDespesa != null && !this.cdDespesa.equals(other.cdDespesa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
       return dsDespesa.toString().toUpperCase()  +" - "+   cdDespesa.toString();
    }
    
}
