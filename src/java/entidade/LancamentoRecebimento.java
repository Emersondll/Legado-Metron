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
@Table(name = "lancamento_recebimento")
@NamedQueries({
    @NamedQuery(name = "LancamentoRecebimento.findAll", query = "SELECT l FROM LancamentoRecebimento l"),
    @NamedQuery(name = "LancamentoRecebimento.findByDataPagamento", query = "SELECT l FROM LancamentoRecebimento l WHERE l.dataPagamento = :dataPagamento"),
    @NamedQuery(name = "LancamentoRecebimento.findByValorPago", query = "SELECT l FROM LancamentoRecebimento l WHERE l.valorPago = :valorPago"),
    @NamedQuery(name = "LancamentoRecebimento.findByNotaFiscal", query = "SELECT l FROM LancamentoRecebimento l WHERE l.notaFiscal = :notaFiscal"),
    @NamedQuery(name = "LancamentoRecebimento.findByTipoPagamento", query = "SELECT l FROM LancamentoRecebimento l WHERE l.tipoPagamento = :tipoPagamento"),
    @NamedQuery(name = "LancamentoRecebimento.findByObservacao", query = "SELECT l FROM LancamentoRecebimento l WHERE l.observacao = :observacao"),
    @NamedQuery(name = "LancamentoRecebimento.findByIdLancamentoRecebimento", query = "SELECT l FROM LancamentoRecebimento l WHERE l.idLancamentoRecebimento = :idLancamentoRecebimento")})
public class LancamentoRecebimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "data_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_pago")
    private BigDecimal valorPago;
    @Size(max = 20)
    @Column(name = "nota_fiscal")
    private String notaFiscal;
    @Column(name = "tipo_pagamento")
    private Character tipoPagamento;
    @Size(max = 2147483647)
    @Column(name = "observacao")
    private String observacao;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lancamento_recebimento")
    private Integer idLancamentoRecebimento;
    @JoinColumn(name = "conta_destino", referencedColumnName = "conta")
    @ManyToOne
    private Conta contaDestino;
    @JoinColumn(name = "cd_obra", referencedColumnName = "cd_obra")
    @ManyToOne
    private Obra cdObra;

    public LancamentoRecebimento() {
    }

    public LancamentoRecebimento(Integer idLancamentoRecebimento) {
        this.idLancamentoRecebimento = idLancamentoRecebimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
       // notaFiscal = notaFiscal.toUpperCase();
        this.notaFiscal = notaFiscal;
    }

    public Character getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(Character tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getIdLancamentoRecebimento() {
        return idLancamentoRecebimento;
    }

    public void setIdLancamentoRecebimento(Integer idLancamentoRecebimento) {
        this.idLancamentoRecebimento = idLancamentoRecebimento;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
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
        hash += (idLancamentoRecebimento != null ? idLancamentoRecebimento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LancamentoRecebimento)) {
            return false;
        }
        LancamentoRecebimento other = (LancamentoRecebimento) object;
        if ((this.idLancamentoRecebimento == null && other.idLancamentoRecebimento != null) || (this.idLancamentoRecebimento != null && !this.idLancamentoRecebimento.equals(other.idLancamentoRecebimento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidade.LancamentoRecebimento[ idLancamentoRecebimento=" + idLancamentoRecebimento + " ]";
    }
    
}
