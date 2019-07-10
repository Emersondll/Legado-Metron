/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "obra")
@NamedQueries({
    @NamedQuery(name = "Obra.findAll", query = "SELECT o FROM Obra o"),
    @NamedQuery(name = "Obra.findByCdObra", query = "SELECT o FROM Obra o WHERE o.cdObra = :cdObra"),
    @NamedQuery(name = "Obra.findByNmObra", query = "SELECT o FROM Obra o WHERE o.nmObra = :nmObra"),
    @NamedQuery(name = "Obra.findByEndereco", query = "SELECT o FROM Obra o WHERE o.endereco = :endereco"),
    @NamedQuery(name = "Obra.findByBairro", query = "SELECT o FROM Obra o WHERE o.bairro = :bairro"),
    @NamedQuery(name = "Obra.findByCidade", query = "SELECT o FROM Obra o WHERE o.cidade = :cidade"),
    @NamedQuery(name = "Obra.findByUf", query = "SELECT o FROM Obra o WHERE o.uf = :uf"),
    @NamedQuery(name = "Obra.findByStatus", query = "SELECT o FROM Obra o WHERE o.status = :status"),
    @NamedQuery(name = "Obra.findByTpObra", query = "SELECT o FROM Obra o WHERE o.tpObra = :tpObra"),
    @NamedQuery(name = "Obra.findByObservacao", query = "SELECT o FROM Obra o WHERE o.observacao = :observacao"),
    @NamedQuery(name = "Obra.findByCep", query = "SELECT o FROM Obra o WHERE o.cep = :cep")})
public class Obra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_obra")
    private Integer cdObra;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nm_obra")
    private String nmObra;
    @Size(max = 50)
    @Column(name = "endereco")
    private String endereco;
    @Size(max = 30)
    @Column(name = "bairro")
    private String bairro;
    @Size(max = 30)
    @Column(name = "cidade")
    private String cidade;
    @Size(max = 2)
    @Column(name = "uf")
    private String uf;
    @Column(name = "status")
    private Character status;
    @Column(name = "tp_obra")
    private Character tpObra;
    @Size(max = 1000)
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "cep")
    private Integer cep;
    @OneToMany(mappedBy = "cdObra")
    private Collection<LancamentoDespesa> lancamentoDespesaCollection;
    @OneToMany(mappedBy = "cdObra")
    private Collection<LancamentoRecebimento> lancamentoRecebimentoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "obra")
    private Collection<LancServPrestado> lancServPrestadoCollection;
    @JoinColumn(name = "cd_cliente", referencedColumnName = "cd_cliente")
    @ManyToOne(optional = false)
    private Cliente cdCliente;

    public Obra() {
    }

    public Obra(Integer cdObra) {
        this.cdObra = cdObra;
    }

    public Obra(Integer cdObra, String nmObra) {
        this.cdObra = cdObra;
        this.nmObra = nmObra;
    }

    public Integer getCdObra() {
        return cdObra;
    }

    public void setCdObra(Integer cdObra) {
        this.cdObra = cdObra;
    }

    public String getNmObra() {
        return nmObra;
    }

    public void setNmObra(String nmObra) {
        nmObra = nmObra.toUpperCase();
        this.nmObra = nmObra;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        endereco = endereco.toUpperCase();
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        bairro = bairro.toUpperCase();
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        cidade =cidade.toUpperCase();
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        uf = uf.toUpperCase();
        this.uf = uf;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Character getTpObra() {
        return tpObra;
    }

    public void setTpObra(Character tpObra) {
        this.tpObra = tpObra;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public Collection<LancamentoDespesa> getLancamentoDespesaCollection() {
        return lancamentoDespesaCollection;
    }

    public void setLancamentoDespesaCollection(Collection<LancamentoDespesa> lancamentoDespesaCollection) {
        this.lancamentoDespesaCollection = lancamentoDespesaCollection;
    }

    public Collection<LancamentoRecebimento> getLancamentoRecebimentoCollection() {
        return lancamentoRecebimentoCollection;
    }

    public void setLancamentoRecebimentoCollection(Collection<LancamentoRecebimento> lancamentoRecebimentoCollection) {
        this.lancamentoRecebimentoCollection = lancamentoRecebimentoCollection;
    }

    public Collection<LancServPrestado> getLancServPrestadoCollection() {
        return lancServPrestadoCollection;
    }

    public void setLancServPrestadoCollection(Collection<LancServPrestado> lancServPrestadoCollection) {
        this.lancServPrestadoCollection = lancServPrestadoCollection;
    }

    public Cliente getCdCliente() {
        return cdCliente;
    }

    public void setCdCliente(Cliente cdCliente) {
        this.cdCliente = cdCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdObra != null ? cdObra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obra)) {
            return false;
        }
        Obra other = (Obra) object;
        if ((this.cdObra == null && other.cdObra != null) || (this.cdObra != null && !this.cdObra.equals(other.cdObra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nmObra.toString().toUpperCase() + " - " + cdObra.toString()  ; // Aqui foi editado para aparecer o nome da obra caso o metodo seja chamado
    }
    
}
