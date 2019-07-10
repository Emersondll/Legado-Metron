/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "funcionario")
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f"),
    @NamedQuery(name = "Funcionario.findByCpf", query = "SELECT f FROM Funcionario f WHERE f.cpf = :cpf"),
    @NamedQuery(name = "Funcionario.findByNmFuncionario", query = "SELECT f FROM Funcionario f WHERE f.nmFuncionario = :nmFuncionario"),
    @NamedQuery(name = "Funcionario.findByApelido", query = "SELECT f FROM Funcionario f WHERE f.apelido = :apelido"),
    @NamedQuery(name = "Funcionario.findByRg", query = "SELECT f FROM Funcionario f WHERE f.rg = :rg"),
    @NamedQuery(name = "Funcionario.findByCtps", query = "SELECT f FROM Funcionario f WHERE f.ctps = :ctps"),
    @NamedQuery(name = "Funcionario.findByPisPasep", query = "SELECT f FROM Funcionario f WHERE f.pisPasep = :pisPasep"),
    @NamedQuery(name = "Funcionario.findByCnh", query = "SELECT f FROM Funcionario f WHERE f.cnh = :cnh"),
    @NamedQuery(name = "Funcionario.findByEndereco", query = "SELECT f FROM Funcionario f WHERE f.endereco = :endereco"),
    @NamedQuery(name = "Funcionario.findByBairro", query = "SELECT f FROM Funcionario f WHERE f.bairro = :bairro"),
    @NamedQuery(name = "Funcionario.findByCep", query = "SELECT f FROM Funcionario f WHERE f.cep = :cep"),
    @NamedQuery(name = "Funcionario.findByCidade", query = "SELECT f FROM Funcionario f WHERE f.cidade = :cidade"),
    @NamedQuery(name = "Funcionario.findByUf", query = "SELECT f FROM Funcionario f WHERE f.uf = :uf"),
    @NamedQuery(name = "Funcionario.findByBanco", query = "SELECT f FROM Funcionario f WHERE f.banco = :banco"),
    @NamedQuery(name = "Funcionario.findByAgencia", query = "SELECT f FROM Funcionario f WHERE f.agencia = :agencia"),
    @NamedQuery(name = "Funcionario.findByConta", query = "SELECT f FROM Funcionario f WHERE f.conta = :conta"),
    @NamedQuery(name = "Funcionario.findByFoneResid", query = "SELECT f FROM Funcionario f WHERE f.foneResid = :foneResid"),
    @NamedQuery(name = "Funcionario.findByCelular1", query = "SELECT f FROM Funcionario f WHERE f.celular1 = :celular1"),
    @NamedQuery(name = "Funcionario.findByDtAdmissao", query = "SELECT f FROM Funcionario f WHERE f.dtAdmissao = :dtAdmissao"),
    @NamedQuery(name = "Funcionario.findByDtDemissao", query = "SELECT f FROM Funcionario f WHERE f.dtDemissao = :dtDemissao"),
    @NamedQuery(name = "Funcionario.findByAsoDtInicio", query = "SELECT f FROM Funcionario f WHERE f.asoDtInicio = :asoDtInicio"),
    @NamedQuery(name = "Funcionario.findByAsoDtFim", query = "SELECT f FROM Funcionario f WHERE f.asoDtFim = :asoDtFim"),
    @NamedQuery(name = "Funcionario.findByObservacao", query = "SELECT f FROM Funcionario f WHERE f.observacao = :observacao")})
public class Funcionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cpf")
    private Long cpf;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nm_funcionario")
    private String nmFuncionario;
    @Size(max = 30)
    @Column(name = "apelido")
    private String apelido;
    @Size(max = 30)
    @Column(name = "rg")
    private String rg;
    @Size(max = 30)
    @Column(name = "ctps")
    private String ctps;
    @Size(max = 30)
    @Column(name = "pis_pasep")
    private String pisPasep;
    @Size(max = 20)
    @Column(name = "cnh")
    private String cnh;
    @Size(max = 50)
    @Column(name = "endereco")
    private String endereco;
    @Size(max = 30)
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cep")
    private Integer cep;
    @Size(max = 30)
    @Column(name = "cidade")
    private String cidade;
    @Size(max = 2)
    @Column(name = "uf")
    private String uf;
    @Size(max = 20)
    @Column(name = "banco")
    private String banco;
    @Size(max = 15)
    @Column(name = "agencia")
    private String agencia;
    @Size(max = 15)
    @Column(name = "conta")
    private String conta;
    @Size(max = 30)
    @Column(name = "fone_resid")
    private String foneResid;
    @Size(max = 30)
    @Column(name = "celular1")
    private String celular1;
    @Column(name = "dt_admissao")
    @Temporal(TemporalType.DATE)
    private Date dtAdmissao;
    @Column(name = "dt_demissao")
    @Temporal(TemporalType.DATE)
    private Date dtDemissao;
    @Column(name = "aso_dt_inicio")
    @Temporal(TemporalType.DATE)
    private Date asoDtInicio;
    @Column(name = "aso_dt_fim")
    @Temporal(TemporalType.DATE)
    private Date asoDtFim;
    @Size(max = 1000)
    @Column(name = "observacao")
    private String observacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private Collection<LancServPrestado> lancServPrestadoCollection;
    @OneToMany(mappedBy = "cpfFuncionario")
    private Collection<Integracao> integracaoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private Collection<PagtoFuncionario> pagtoFuncionarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionario")
    private Collection<Epi> epiCollection;

    public Funcionario() {
    }

    public Funcionario(Long cpf) {
        this.cpf = cpf;
    }

    public Funcionario(Long cpf, String nmFuncionario) {
        this.cpf = cpf;
        this.nmFuncionario = nmFuncionario;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNmFuncionario() {
        return nmFuncionario;
    }

    public void setNmFuncionario(String nmFuncionario) {
     //   nmFuncionario =nmFuncionario.toUpperCase();
        this.nmFuncionario = nmFuncionario;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
     //   apelido =apelido.toUpperCase();
        this.apelido = apelido;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
    //    rg = rg.toUpperCase();
        this.rg = rg;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
    //    ctps = ctps.toUpperCase();
        this.ctps = ctps;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
    //    pisPasep = pisPasep.toUpperCase();
        this.pisPasep = pisPasep;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
    //    cnh = cnh.toUpperCase();
        this.cnh = cnh;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
    //    endereco =endereco.toUpperCase();
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
    //    bairro =bairro.toUpperCase();
        this.bairro = bairro;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
     //   cidade =cidade.toUpperCase();
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
     //   banco = banco.toUpperCase();
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
     //   agencia =agencia.toUpperCase();
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
    //    conta =conta.toUpperCase();
        this.conta = conta;
    }

    public String getFoneResid() {
        return foneResid;
    }

    public void setFoneResid(String foneResid) {
        this.foneResid = foneResid;
    }

    public String getCelular1() {
        return celular1;
    }

    public void setCelular1(String celular1) {
        this.celular1 = celular1;
    }

    public Date getDtAdmissao() {
        return dtAdmissao;
    }

    public void setDtAdmissao(Date dtAdmissao) {
        this.dtAdmissao = dtAdmissao;
    }

    public Date getDtDemissao() {
        return dtDemissao;
    }

    public void setDtDemissao(Date dtDemissao) {
        this.dtDemissao = dtDemissao;
    }

    public Date getAsoDtInicio() {
        return asoDtInicio;
    }

    public void setAsoDtInicio(Date asoDtInicio) {
        this.asoDtInicio = asoDtInicio;
    }

    public Date getAsoDtFim() {
        return asoDtFim;
    }

    public void setAsoDtFim(Date asoDtFim) {
        this.asoDtFim = asoDtFim;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Collection<LancServPrestado> getLancServPrestadoCollection() {
        return lancServPrestadoCollection;
    }

    public void setLancServPrestadoCollection(Collection<LancServPrestado> lancServPrestadoCollection) {
        this.lancServPrestadoCollection = lancServPrestadoCollection;
    }

    public Collection<Integracao> getIntegracaoCollection() {
        return integracaoCollection;
    }

    public void setIntegracaoCollection(Collection<Integracao> integracaoCollection) {
        this.integracaoCollection = integracaoCollection;
    }

    public Collection<PagtoFuncionario> getPagtoFuncionarioCollection() {
        return pagtoFuncionarioCollection;
    }

    public void setPagtoFuncionarioCollection(Collection<PagtoFuncionario> pagtoFuncionarioCollection) {
        this.pagtoFuncionarioCollection = pagtoFuncionarioCollection;
    }

    public Collection<Epi> getEpiCollection() {
        return epiCollection;
    }

    public void setEpiCollection(Collection<Epi> epiCollection) {
        this.epiCollection = epiCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpf != null ? cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.cpf == null && other.cpf != null) || (this.cpf != null && !this.cpf.equals(other.cpf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nmFuncionario.toString().toUpperCase() + " - " + cpf.toString() ;
    }
    
}
