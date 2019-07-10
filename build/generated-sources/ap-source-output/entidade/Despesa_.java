package entidade;

import entidade.LancamentoDespesa;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(Despesa.class)
public class Despesa_ { 

    public static volatile SingularAttribute<Despesa, Integer> cdDespesa;
    public static volatile CollectionAttribute<Despesa, LancamentoDespesa> lancamentoDespesaCollection;
    public static volatile SingularAttribute<Despesa, BigDecimal> vlPadrao;
    public static volatile SingularAttribute<Despesa, String> dsDespesa;

}