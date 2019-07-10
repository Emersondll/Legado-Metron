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
@Table(name = "conta")
@NamedQueries({
    @NamedQuery(name = "Conta.findAll", query = "SELECT c FROM Conta c"),
    @NamedQuery(name = "Conta.findByConta", query = "SELECT c FROM Conta c WHERE c.conta = :conta"),
    @NamedQuery(name = "Conta.findByBanco", query = "SELECT c FROM Conta c WHERE c.banco = :banco"),
    @NamedQuery(name = "Conta.findByAgencia", query = "SELECT c FROM Conta c WHERE c.agencia = :agencia"),
    @NamedQuery(name = "Conta.findByStatus", query = "SELECT c FROM Conta c WHERE c.status = :status")})
public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "conta")
    private String conta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "banco")
    private String banco;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "agencia")
    private String agencia;
    @Column(name = "status")
    private Character status;
    @OneToMany(mappedBy = "conta")
    private Collection<LancamentoDespesa> lancamentoDespesaCollection;
    @OneToMany(mappedBy = "contaDestino")
    private Collection<LancamentoRecebimento> lancamentoRecebimentoCollection;

    public Conta() {
    }

    public Conta(String conta) {
        this.conta = conta;
    }

    public Conta(String conta, String banco, String agencia) {
        this.conta = conta;
        this.banco = banco;
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        conta = conta.toUpperCase();
        this.conta = conta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        banco =banco.toUpperCase();
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        agencia = agencia.toUpperCase();
        this.agencia = agencia;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conta != null ? conta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conta)) {
            return false;
        }
        Conta other = (Conta) object;
        if ((this.conta == null && other.conta != null) || (this.conta != null && !this.conta.equals(other.conta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return banco.toString().toUpperCase()  + " - "+  conta.toString();// Aqui foi editado para aparecer o nome do cliente caso o metodo seja chamado;
    }
    
}
