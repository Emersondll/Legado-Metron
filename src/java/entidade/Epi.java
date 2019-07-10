/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "epi")
@NamedQueries({
    @NamedQuery(name = "Epi.findAll", query = "SELECT e FROM Epi e"),
    @NamedQuery(name = "Epi.findByIdEpi", query = "SELECT e FROM Epi e WHERE e.idEpi = :idEpi"),
    @NamedQuery(name = "Epi.findByDsEpi", query = "SELECT e FROM Epi e WHERE e.dsEpi = :dsEpi"),
    @NamedQuery(name = "Epi.findByValor", query = "SELECT e FROM Epi e WHERE e.valor = :valor"),
    @NamedQuery(name = "Epi.findByDataRetirada", query = "SELECT e FROM Epi e WHERE e.dataRetirada = :dataRetirada")})
public class Epi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_epi")
    private Integer idEpi;
    @Size(max = 50)
    @Column(name = "ds_epi")
    private String dsEpi;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "data_retirada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRetirada;
    @JoinColumn(name = "funcionario", referencedColumnName = "cpf")
    @ManyToOne(optional = false)
    private Funcionario funcionario;

    public Epi() {
    }

    public Epi(Integer idEpi) {
        this.idEpi = idEpi;
    }

    public Integer getIdEpi() {
        return idEpi;
    }

    public void setIdEpi(Integer idEpi) {
        this.idEpi = idEpi;
    }

    public String getDsEpi() {
        return dsEpi;
    }

    public void setDsEpi(String dsEpi) {
        dsEpi = dsEpi.toUpperCase();
        this.dsEpi = dsEpi;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(Date dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEpi != null ? idEpi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Epi)) {
            return false;
        }
        Epi other = (Epi) object;
        if ((this.idEpi == null && other.idEpi != null) || (this.idEpi != null && !this.idEpi.equals(other.idEpi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return dsEpi.toUpperCase() +" - " + idEpi;
    }
    
}
