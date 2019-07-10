/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.faces.bean.SessionScoped;
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
@SessionScoped
@Entity
@Table(name = "cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCdCliente", query = "SELECT c FROM Cliente c WHERE c.cdCliente = :cdCliente"),
    @NamedQuery(name = "Cliente.findByNmCliente", query = "SELECT c FROM Cliente c WHERE c.nmCliente = :nmCliente"),
    @NamedQuery(name = "Cliente.findByEndereco", query = "SELECT c FROM Cliente c WHERE c.endereco = :endereco"),
    @NamedQuery(name = "Cliente.findByBairro", query = "SELECT c FROM Cliente c WHERE c.bairro = :bairro"),
    @NamedQuery(name = "Cliente.findByCidade", query = "SELECT c FROM Cliente c WHERE c.cidade = :cidade"),
    @NamedQuery(name = "Cliente.findByUf", query = "SELECT c FROM Cliente c WHERE c.uf = :uf"),
    @NamedQuery(name = "Cliente.findByFoneComercial", query = "SELECT c FROM Cliente c WHERE c.foneComercial = :foneComercial"),
    @NamedQuery(name = "Cliente.findByFoneResidencial", query = "SELECT c FROM Cliente c WHERE c.foneResidencial = :foneResidencial"),
    @NamedQuery(name = "Cliente.findByCelular", query = "SELECT c FROM Cliente c WHERE c.celular = :celular"),
    @NamedQuery(name = "Cliente.findByTpPessoa", query = "SELECT c FROM Cliente c WHERE c.tpPessoa = :tpPessoa"),
    @NamedQuery(name = "Cliente.findByCpfCnpj", query = "SELECT c FROM Cliente c WHERE c.cpfCnpj = :cpfCnpj"),
    @NamedQuery(name = "Cliente.findByRgInsc", query = "SELECT c FROM Cliente c WHERE c.rgInsc = :rgInsc"),
    @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email"),
    @NamedQuery(name = "Cliente.findByObservacao", query = "SELECT c FROM Cliente c WHERE c.observacao = :observacao"),
    @NamedQuery(name = "Cliente.findByCep", query = "SELECT c FROM Cliente c WHERE c.cep = :cep")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cd_cliente")
    private Integer cdCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nm_cliente")
    private String nmCliente;
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
    @Size(max = 30)
    @Column(name = "fone_comercial")
    private String foneComercial;
    @Size(max = 30)
    @Column(name = "fone_residencial")
    private String foneResidencial;
    @Size(max = 50)
    @Column(name = "celular")
    private String celular;
    @Column(name = "tp_pessoa")
    private Character tpPessoa;
    @Column(name = "cpf_cnpj")
    private Long cpfCnpj;
    @Column(name = "rg_insc")
    private BigInteger rgInsc;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    @Size(max = 1000)
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "cep")
    private Integer cep;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cdCliente")
    private Collection<Obra> obraCollection;

    public Cliente() {
    }

    public Cliente(Integer cdCliente) {
        this.cdCliente = cdCliente;
    }

    public Cliente(Integer cdCliente, String nmCliente) {
        this.cdCliente = cdCliente;
        this.nmCliente = nmCliente;
    }

    public Integer getCdCliente() {
        return cdCliente;
    }

    public void setCdCliente(Integer cdCliente) {
        this.cdCliente = cdCliente;
    }

    public String getNmCliente() {
        
        return nmCliente;
    }

    public void setNmCliente(String nmCliente) {
        nmCliente= nmCliente.toUpperCase();
        this.nmCliente = nmCliente;
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
        cidade = cidade.toUpperCase();
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        uf= uf.toUpperCase();
        this.uf = uf;
    }

    public String getFoneComercial() {
        return foneComercial;
    }

    public void setFoneComercial(String foneComercial) {
        this.foneComercial = foneComercial;
    }

    public String getFoneResidencial() {
        return foneResidencial;
    }

    public void setFoneResidencial(String foneResidencial) {
        this.foneResidencial = foneResidencial;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Character getTpPessoa() {
        return tpPessoa;
    }

    public void setTpPessoa(Character tpPessoa) {
        this.tpPessoa = tpPessoa;
    }

    public Long getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(Long cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public BigInteger getRgInsc() {
        return rgInsc;
    }

    public void setRgInsc(BigInteger rgInsc) {
        this.rgInsc = rgInsc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email.toLowerCase();
        this.email = email;
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

    public Collection<Obra> getObraCollection() {
        return obraCollection;
    }

    public void setObraCollection(Collection<Obra> obraCollection) {
        this.obraCollection = obraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdCliente != null ? cdCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.cdCliente == null && other.cdCliente != null) || (this.cdCliente != null && !this.cdCliente.equals(other.cdCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nmCliente.toString().toUpperCase() + " - " + cdCliente.toString();// Aqui foi editado para aparecer o nome do cliente caso o metodo seja chamado
    }
    
}
