package entidade;

import entidade.Conta;
import entidade.Despesa;
import entidade.LancamentoDespesaPK;
import entidade.Obra;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(LancamentoDespesa.class)
public class LancamentoDespesa_ { 

    public static volatile SingularAttribute<LancamentoDespesa, LancamentoDespesaPK> lancamentoDespesaPK;
    public static volatile SingularAttribute<LancamentoDespesa, BigDecimal> valorDespesa;
    public static volatile SingularAttribute<LancamentoDespesa, Integer> quatidade;
    public static volatile SingularAttribute<LancamentoDespesa, Despesa> despesa;
    public static volatile SingularAttribute<LancamentoDespesa, Obra> cdObra;
    public static volatile SingularAttribute<LancamentoDespesa, Conta> conta;

}