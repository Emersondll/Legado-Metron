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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "pagto_funcionario")
@NamedQueries({
    @NamedQuery(name = "PagtoFuncionario.findAll", query = "SELECT p FROM PagtoFuncionario p"),
    @NamedQuery(name = "PagtoFuncionario.findByCpfFuncionario", query = "SELECT p FROM PagtoFuncionario p WHERE p.pagtoFuncionarioPK.cpfFuncionario = :cpfFuncionario"),
    @NamedQuery(name = "PagtoFuncionario.findByDtInicio", query = "SELECT p FROM PagtoFuncionario p WHERE p.pagtoFuncionarioPK.dtInicio = :dtInicio"),
    @NamedQuery(name = "PagtoFuncionario.findByDtFim", query = "SELECT p FROM PagtoFuncionario p WHERE p.dtFim = :dtFim"),
    @NamedQuery(name = "PagtoFuncionario.findByValor", query = "SELECT p FROM PagtoFuncionario p WHERE p.valor = :valor"),
    @NamedQuery(name = "PagtoFuncionario.findByValorRefeicaoDia", query = "SELECT p FROM PagtoFuncionario p WHERE p.valorRefeicaoDia = :valorRefeicaoDia"),
    @NamedQuery(name = "PagtoFuncionario.findByQtdValeRefeicao", query = "SELECT p FROM PagtoFuncionario p WHERE p.qtdValeRefeicao = :qtdValeRefeicao"),
    @NamedQuery(name = "PagtoFuncionario.findByValorValeTransporte", query = "SELECT p FROM PagtoFuncionario p WHERE p.valorValeTransporte = :valorValeTransporte"),
    @NamedQuery(name = "PagtoFuncionario.findByQtdValeTransporte", query = "SELECT p FROM PagtoFuncionario p WHERE p.qtdValeTransporte = :qtdValeTransporte")})
public class PagtoFuncionario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PagtoFuncionarioPK pagtoFuncionarioPK;
    @Column(name = "dt_fim")
    @Temporal(TemporalType.DATE)
    private Date dtFim;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "valor_refeicao_dia")
    private BigDecimal valorRefeicaoDia;
    @Column(name = "qtd_vale_refeicao")
    private Long qtdValeRefeicao;
    @Column(name = "valor_vale_transporte")
    private BigDecimal valorValeTransporte;
    @Column(name = "qtd_vale_transporte")
    private Long qtdValeTransporte;
    @JoinColumn(name = "cpf_funcionario", referencedColumnName = "cpf", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Funcionario funcionario;

    public PagtoFuncionario() {
    }

    public PagtoFuncionario(PagtoFuncionarioPK pagtoFuncionarioPK) {
        this.pagtoFuncionarioPK = pagtoFuncionarioPK;
    }

    public PagtoFuncionario(long cpfFuncionario, Date dtInicio) {
        this.pagtoFuncionarioPK = new PagtoFuncionarioPK(cpfFuncionario, dtInicio);
    }

    public PagtoFuncionarioPK getPagtoFuncionarioPK() {
        return pagtoFuncionarioPK;
    }

    public void setPagtoFuncionarioPK(PagtoFuncionarioPK pagtoFuncionarioPK) {
        this.pagtoFuncionarioPK = pagtoFuncionarioPK;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorRefeicaoDia() {
        return valorRefeicaoDia;
    }

    public void setValorRefeicaoDia(BigDecimal valorRefeicaoDia) {
        this.valorRefeicaoDia = valorRefeicaoDia;
    }

    public Long getQtdValeRefeicao() {
        return qtdValeRefeicao;
    }

    public void setQtdValeRefeicao(Long qtdValeRefeicao) {
        this.qtdValeRefeicao = qtdValeRefeicao;
    }

    public BigDecimal getValorValeTransporte() {
        return valorValeTransporte;
    }

    public void setValorValeTransporte(BigDecimal valorValeTransporte) {
        this.valorValeTransporte = valorValeTransporte;
    }

    public Long getQtdValeTransporte() {
        return qtdValeTransporte;
    }

    public void setQtdValeTransporte(Long qtdValeTransporte) {
        this.qtdValeTransporte = qtdValeTransporte;
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
        hash += (pagtoFuncionarioPK != null ? pagtoFuncionarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagtoFuncionario)) {
            return false;
        }
        PagtoFuncionario other = (PagtoFuncionario) object;
        if ((this.pagtoFuncionarioPK == null && other.pagtoFuncionarioPK != null) || (this.pagtoFuncionarioPK != null && !this.pagtoFuncionarioPK.equals(other.pagtoFuncionarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.PagtoFuncionario[ pagtoFuncionarioPK=" + pagtoFuncionarioPK + " ]";
    }
    
}
