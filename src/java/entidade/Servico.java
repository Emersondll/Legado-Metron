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
@Table(name = "servico")
@NamedQueries({
    @NamedQuery(name = "Servico.findAll", query = "SELECT s FROM Servico s"),
    @NamedQuery(name = "Servico.findByCdServico", query = "SELECT s FROM Servico s WHERE s.cdServico = :cdServico"),
    @NamedQuery(name = "Servico.findByDescServico", query = "SELECT s FROM Servico s WHERE s.descServico = :descServico"),
    @NamedQuery(name = "Servico.findByTpServico", query = "SELECT s FROM Servico s WHERE s.tpServico = :tpServico"),
    @NamedQuery(name = "Servico.findByTpObra", query = "SELECT s FROM Servico s WHERE s.tpObra = :tpObra"),
    @NamedQuery(name = "Servico.findByVlPadrao", query = "SELECT s FROM Servico s WHERE s.vlPadrao = :vlPadrao"),
    @NamedQuery(name = "Servico.findByTpCobranca", query = "SELECT s FROM Servico s WHERE s.tpCobranca = :tpCobranca"),
    @NamedQuery(name = "Servico.findByIdFalta", query = "SELECT s FROM Servico s WHERE s.idFalta = :idFalta")})
public class Servico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_servico")
    private Integer cdServico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "desc_servico")
    private String descServico;
    @Column(name = "tp_servico")
    private Character tpServico;
    @Column(name = "tp_obra")
    private Character tpObra;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vl_padrao")
    private BigDecimal vlPadrao;
    @Column(name = "tp_cobranca")
    private Character tpCobranca;
    @Column(name = "id_falta")
    private Character idFalta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servico")
    private Collection<LancServPrestado> lancServPrestadoCollection;

    public Servico() {
    }

    public Servico(Integer cdServico) {
        this.cdServico = cdServico;
    }

    public Servico(Integer cdServico, String descServico) {
        this.cdServico = cdServico;
        this.descServico = descServico;
    }

    public Integer getCdServico() {
        return cdServico;
    }

    public void setCdServico(Integer cdServico) {
        this.cdServico = cdServico;
    }

    public String getDescServico() {
        return descServico;
    }

    public void setDescServico(String descServico) {
        descServico = descServico.toUpperCase();
        this.descServico = descServico;
    }

    public Character getTpServico() {
        return tpServico;
    }

    public void setTpServico(Character tpServico) {
        this.tpServico = tpServico;
    }

    public Character getTpObra() {
        return tpObra;
    }

    public void setTpObra(Character tpObra) {
        this.tpObra = tpObra;
    }

    public BigDecimal getVlPadrao() {
        return vlPadrao;
    }

    public void setVlPadrao(BigDecimal vlPadrao) {
        this.vlPadrao = vlPadrao;
    }

    public Character getTpCobranca() {
        return tpCobranca;
    }

    public void setTpCobranca(Character tpCobranca) {
        this.tpCobranca = tpCobranca;
    }

    public Character getIdFalta() {
        return idFalta;
    }

    public void setIdFalta(Character idFalta) {
        this.idFalta = idFalta;
    }

    public Collection<LancServPrestado> getLancServPrestadoCollection() {
        return lancServPrestadoCollection;
    }

    public void setLancServPrestadoCollection(Collection<LancServPrestado> lancServPrestadoCollection) {
        this.lancServPrestadoCollection = lancServPrestadoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdServico != null ? cdServico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servico)) {
            return false;
        }
        Servico other = (Servico) object;
        if ((this.cdServico == null && other.cdServico != null) || (this.cdServico != null && !this.cdServico.equals(other.cdServico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descServico.toString().toUpperCase() + " - " +  cdServico.toString() ;
    }
    
}
