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
import javax.validation.constraints.Size;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "lanc_serv_prestado")
@NamedQueries({
    @NamedQuery(name = "LancServPrestado.findAll", query = "SELECT l FROM LancServPrestado l"),
    @NamedQuery(name = "LancServPrestado.findByDtServico", query = "SELECT l FROM LancServPrestado l WHERE l.lancServPrestadoPK.dtServico = :dtServico"),
    @NamedQuery(name = "LancServPrestado.findByCpfFucionario", query = "SELECT l FROM LancServPrestado l WHERE l.lancServPrestadoPK.cpfFucionario = :cpfFucionario"),
    @NamedQuery(name = "LancServPrestado.findByCdObra", query = "SELECT l FROM LancServPrestado l WHERE l.lancServPrestadoPK.cdObra = :cdObra"),
    @NamedQuery(name = "LancServPrestado.findByCdServico", query = "SELECT l FROM LancServPrestado l WHERE l.lancServPrestadoPK.cdServico = :cdServico"),
    @NamedQuery(name = "LancServPrestado.findByNotaFiscal", query = "SELECT l FROM LancServPrestado l WHERE l.notaFiscal = :notaFiscal"),
    @NamedQuery(name = "LancServPrestado.findByHrSaidaEscritorio", query = "SELECT l FROM LancServPrestado l WHERE l.hrSaidaEscritorio = :hrSaidaEscritorio"),
    @NamedQuery(name = "LancServPrestado.findByHrChegadaEscritorio", query = "SELECT l FROM LancServPrestado l WHERE l.hrChegadaEscritorio = :hrChegadaEscritorio"),
    @NamedQuery(name = "LancServPrestado.findByHrInicioServico", query = "SELECT l FROM LancServPrestado l WHERE l.hrInicioServico = :hrInicioServico"),
    @NamedQuery(name = "LancServPrestado.findByHrFimServico", query = "SELECT l FROM LancServPrestado l WHERE l.hrFimServico = :hrFimServico"),
    @NamedQuery(name = "LancServPrestado.findByQtdCobrar", query = "SELECT l FROM LancServPrestado l WHERE l.qtdCobrar = :qtdCobrar"),
    @NamedQuery(name = "LancServPrestado.findByQtdRealizada", query = "SELECT l FROM LancServPrestado l WHERE l.qtdRealizada = :qtdRealizada"),
    @NamedQuery(name = "LancServPrestado.findByValorCobrar", query = "SELECT l FROM LancServPrestado l WHERE l.valorCobrar = :valorCobrar")})
public class LancServPrestado implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LancServPrestadoPK lancServPrestadoPK;
    @Size(max = 20)
    @Column(name = "nota_fiscal")
    private String notaFiscal;
    @Column(name = "hr_saida_escritorio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hrSaidaEscritorio;
    @Column(name = "hr_chegada_escritorio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hrChegadaEscritorio;
    @Column(name = "hr_inicio_servico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hrInicioServico;
    @Column(name = "hr_fim_servico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hrFimServico;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "qtd_cobrar")
    private BigDecimal qtdCobrar;
    @Column(name = "qtd_realizada")
    private BigDecimal qtdRealizada;
    @Column(name = "valor_cobrar")
    private BigDecimal valorCobrar;
    @JoinColumn(name = "cd_bem", referencedColumnName = "cd_bem")
    @ManyToOne
    private Bem cdBem;
    @JoinColumn(name = "cpf_fucionario", referencedColumnName = "cpf", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Funcionario funcionario;
    @JoinColumn(name = "cd_obra", referencedColumnName = "cd_obra", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Obra obra;
    @JoinColumn(name = "cd_servico", referencedColumnName = "cd_servico", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Servico servico;

    public LancServPrestado() {
    }

    public LancServPrestado(LancServPrestadoPK lancServPrestadoPK) {
        this.lancServPrestadoPK = lancServPrestadoPK;
    }

    public LancServPrestado(Date dtServico, long cpfFucionario, int cdObra, int cdServico) {
        this.lancServPrestadoPK = new LancServPrestadoPK(dtServico, cpfFucionario, cdObra, cdServico);
    }

    public LancServPrestadoPK getLancServPrestadoPK() {
        return lancServPrestadoPK;
    }

    public void setLancServPrestadoPK(LancServPrestadoPK lancServPrestadoPK) {
        this.lancServPrestadoPK = lancServPrestadoPK;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
      //  notaFiscal= notaFiscal.toUpperCase();
        this.notaFiscal = notaFiscal;
    }

    public Date getHrSaidaEscritorio() {
        return hrSaidaEscritorio;
    }

    public void setHrSaidaEscritorio(Date hrSaidaEscritorio) {
        this.hrSaidaEscritorio = hrSaidaEscritorio;
    }

    public Date getHrChegadaEscritorio() {
        return hrChegadaEscritorio;
    }

    public void setHrChegadaEscritorio(Date hrChegadaEscritorio) {
        this.hrChegadaEscritorio = hrChegadaEscritorio;
    }

    public Date getHrInicioServico() {
        return hrInicioServico;
    }

    public void setHrInicioServico(Date hrInicioServico) {
        this.hrInicioServico = hrInicioServico;
    }

    public Date getHrFimServico() {
        return hrFimServico;
    }

    public void setHrFimServico(Date hrFimServico) {
        this.hrFimServico = hrFimServico;
    }

    public BigDecimal getQtdCobrar() {
        return qtdCobrar;
    }

    public void setQtdCobrar(BigDecimal qtdCobrar) {
        this.qtdCobrar = qtdCobrar;
    }

    public BigDecimal getQtdRealizada() {
        return qtdRealizada;
    }

    public void setQtdRealizada(BigDecimal qtdRealizada) {
        this.qtdRealizada = qtdRealizada;
    }

    public BigDecimal getValorCobrar() {
        return valorCobrar;
    }

    public void setValorCobrar(BigDecimal valorCobrar) {
        this.valorCobrar = valorCobrar;
    }

    public Bem getCdBem() {
        return cdBem;
    }

    public void setCdBem(Bem cdBem) {
        this.cdBem = cdBem;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lancServPrestadoPK != null ? lancServPrestadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LancServPrestado)) {
            return false;
        }
        LancServPrestado other = (LancServPrestado) object;
        if ((this.lancServPrestadoPK == null && other.lancServPrestadoPK != null) || (this.lancServPrestadoPK != null && !this.lancServPrestadoPK.equals(other.lancServPrestadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.LancServPrestado[ lancServPrestadoPK=" + lancServPrestadoPK + " ]";
    }
    
}
