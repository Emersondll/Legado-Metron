package entidade;

import entidade.LancamentoDespesa;
import entidade.LancamentoRecebimento;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(Conta.class)
public class Conta_ { 

    public static volatile CollectionAttribute<Conta, LancamentoRecebimento> lancamentoRecebimentoCollection;
    public static volatile CollectionAttribute<Conta, LancamentoDespesa> lancamentoDespesaCollection;
    public static volatile SingularAttribute<Conta, String> conta;
    public static volatile SingularAttribute<Conta, String> banco;
    public static volatile SingularAttribute<Conta, String> agencia;
    public static volatile SingularAttribute<Conta, Character> status;

}