package entidade;

import entidade.Conta;
import entidade.Obra;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(LancamentoRecebimento.class)
public class LancamentoRecebimento_ { 

    public static volatile SingularAttribute<LancamentoRecebimento, String> observacao;
    public static volatile SingularAttribute<LancamentoRecebimento, Conta> contaDestino;
    public static volatile SingularAttribute<LancamentoRecebimento, Date> dataPagamento;
    public static volatile SingularAttribute<LancamentoRecebimento, Integer> idLancamentoRecebimento;
    public static volatile SingularAttribute<LancamentoRecebimento, Obra> cdObra;
    public static volatile SingularAttribute<LancamentoRecebimento, BigDecimal> valorPago;
    public static volatile SingularAttribute<LancamentoRecebimento, String> notaFiscal;
    public static volatile SingularAttribute<LancamentoRecebimento, Character> tipoPagamento;

}