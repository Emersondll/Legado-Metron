/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "bem")
@NamedQueries({
    @NamedQuery(name = "Bem.findAll", query = "SELECT b FROM Bem b"),
    @NamedQuery(name = "Bem.findByCdBem", query = "SELECT b FROM Bem b WHERE b.cdBem = :cdBem"),
    @NamedQuery(name = "Bem.findByDsBem", query = "SELECT b FROM Bem b WHERE b.dsBem = :dsBem"),
    @NamedQuery(name = "Bem.findByTpBem", query = "SELECT b FROM Bem b WHERE b.tpBem = :tpBem")})
public class Bem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_bem")
    private Integer cdBem;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ds_bem")
    private String dsBem;
    @Column(name = "tp_bem")
    private Character tpBem;
    @OneToMany(mappedBy = "cdBem")
    private Collection<LancServPrestado> lancServPrestadoCollection;

    public Bem() {
    }

    public Bem(Integer cdBem) {
        this.cdBem = cdBem;
    }

    public Bem(Integer cdBem, String dsBem) {
        this.cdBem = cdBem;
        this.dsBem = dsBem;
    }

    public Integer getCdBem() {
        return cdBem;
    }

    public void setCdBem(Integer cdBem) {
        this.cdBem = cdBem;
    }

    public String getDsBem() {
        return dsBem;
    }

    public void setDsBem(String dsBem) {
        dsBem= dsBem.toUpperCase();
        this.dsBem = dsBem;
    }

    public Character getTpBem() {
        return tpBem;
    }

    public void setTpBem(Character tpBem) {
        this.tpBem = tpBem;
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
        hash += (cdBem != null ? cdBem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bem)) {
            return false;
        }
        Bem other = (Bem) object;
        if ((this.cdBem == null && other.cdBem != null) || (this.cdBem != null && !this.cdBem.equals(other.cdBem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  dsBem.toString().toUpperCase() + " - " + cdBem.toString() ;
    }
    
}
