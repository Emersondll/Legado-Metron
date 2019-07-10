/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "lancamento_despesa")
@NamedQueries({
    @NamedQuery(name = "LancamentoDespesa.findAll", query = "SELECT l FROM LancamentoDespesa l"),
    @NamedQuery(name = "LancamentoDespesa.findByCdDespesa", query = "SELECT l FROM LancamentoDespesa l WHERE l.lancamentoDespesaPK.cdDespesa = :cdDespesa"),
    @NamedQuery(name = "LancamentoDespesa.findByDataDespesa", query = "SELECT l FROM LancamentoDespesa l WHERE l.lancamentoDespesaPK.dataDespesa = :dataDespesa"),
    @NamedQuery(name = "LancamentoDespesa.findByValorDespesa", query = "SELECT l FROM LancamentoDespesa l WHERE l.valorDespesa = :valorDespesa"),
    @NamedQuery(name = "LancamentoDespesa.findByQuatidade", query = "SELECT l FROM LancamentoDespesa l WHERE l.quatidade = :quatidade")})
public class LancamentoDespesa implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LancamentoDespesaPK lancamentoDespesaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_despesa")
    private BigDecimal valorDespesa;
    @Column(name = "quatidade")
    private Integer quatidade;
    @JoinColumn(name = "conta", referencedColumnName = "conta")
    @ManyToOne
    private Conta conta;
    @JoinColumn(name = "cd_despesa", referencedColumnName = "cd_despesa", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Despesa despesa;
    @JoinColumn(name = "cd_obra", referencedColumnName = "cd_obra")
    @ManyToOne
    private Obra cdObra;

    public LancamentoDespesa() {
    }

    public LancamentoDespesa(LancamentoDespesaPK lancamentoDespesaPK) {
        this.lancamentoDespesaPK = lancamentoDespesaPK;
    }

    public LancamentoDespesa(int cdDespesa, Date dataDespesa) {
        this.lancamentoDespesaPK = new LancamentoDespesaPK(cdDespesa, dataDespesa);
    }

    public LancamentoDespesaPK getLancamentoDespesaPK() {
        return lancamentoDespesaPK;
    }

    public void setLancamentoDespesaPK(LancamentoDespesaPK lancamentoDespesaPK) {
        this.lancamentoDespesaPK = lancamentoDespesaPK;
    }

    public BigDecimal getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(BigDecimal valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public Integer getQuatidade() {
        return quatidade;
    }

    public void setQuatidade(Integer quatidade) {
        this.quatidade = quatidade;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public Obra getCdObra() {
        return cdObra;
    }

    public void setCdObra(Obra cdObra) {
        this.cdObra = cdObra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lancamentoDespesaPK != null ? lancamentoDespesaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LancamentoDespesa)) {
            return false;
        }
        LancamentoDespesa other = (LancamentoDespesa) object;
        if ((this.lancamentoDespesaPK == null && other.lancamentoDespesaPK != null) || (this.lancamentoDespesaPK != null && !this.lancamentoDespesaPK.equals(other.lancamentoDespesaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.LancamentoDespesa[ lancamentoDespesaPK=" + lancamentoDespesaPK + " ]";
    }
    
}
