/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "rel_geral")
@NamedQueries({
    @NamedQuery(name = "RelGeral.findAll", query = "SELECT r FROM RelGeral r"),
    @NamedQuery(name = "RelGeral.findByCliente", query = "SELECT r FROM RelGeral r WHERE r.cliente = :cliente"),
    @NamedQuery(name = "RelGeral.findByObra", query = "SELECT r FROM RelGeral r WHERE r.obra = :obra"),
    @NamedQuery(name = "RelGeral.findByStatusObra", query = "SELECT r FROM RelGeral r WHERE r.statusObra = :statusObra"),
    @NamedQuery(name = "RelGeral.findByTotalServicos", query = "SELECT r FROM RelGeral r WHERE r.totalServicos = :totalServicos"),
    @NamedQuery(name = "RelGeral.findByTotalDespesas", query = "SELECT r FROM RelGeral r WHERE r.totalDespesas = :totalDespesas"),
    @NamedQuery(name = "RelGeral.findByTotalPago", query = "SELECT r FROM RelGeral r WHERE r.totalPago = :totalPago"),
    @NamedQuery(name = "RelGeral.findByTotalGeral", query = "SELECT r FROM RelGeral r WHERE r.totalGeral = :totalGeral")})
public class RelGeral implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Size(max = 2147483647)
    @Column(name = "cliente")
    private String cliente;
    @Size(max = 2147483647)
    @Column(name = "obra")
    private String obra;
    @Size(max = 2147483647)
    @Column(name = "status_obra")
    private String statusObra;
    @Column(name = "total_servicos")
    private BigInteger totalServicos;
    @Column(name = "total_despesas")
    private BigInteger totalDespesas;
    @Column(name = "total_pago")
    private BigInteger totalPago;
    @Column(name = "total_geral")
    private BigInteger totalGeral;

    public RelGeral() {
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
       // obra = obra.toUpperCase();
        this.obra = obra;
    }

    public String getStatusObra() {
        return statusObra;
    }

    public void setStatusObra(String statusObra) {
      //  statusObra = statusObra.toUpperCase();
        this.statusObra = statusObra;
    }

    public BigInteger getTotalServicos() {
        return totalServicos;
    }

    public void setTotalServicos(BigInteger totalServicos) {
        this.totalServicos = totalServicos;
    }

    public BigInteger getTotalDespesas() {
        return totalDespesas;
    }

    public void setTotalDespesas(BigInteger totalDespesas) {
        this.totalDespesas = totalDespesas;
    }

    public BigInteger getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(BigInteger totalPago) {
        this.totalPago = totalPago;
    }

    public BigInteger getTotalGeral() {
        return totalGeral;
    }

    public void setTotalGeral(BigInteger totalGeral) {
        this.totalGeral = totalGeral;
    }
    
}
