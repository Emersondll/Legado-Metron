package entidade;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(RelGeral.class)
public class RelGeral_ { 

    public static volatile SingularAttribute<RelGeral, BigInteger> totalServicos;
    public static volatile SingularAttribute<RelGeral, BigInteger> totalGeral;
    public static volatile SingularAttribute<RelGeral, String> cliente;
    public static volatile SingularAttribute<RelGeral, String> obra;
    public static volatile SingularAttribute<RelGeral, String> statusObra;
    public static volatile SingularAttribute<RelGeral, BigInteger> totalDespesas;
    public static volatile SingularAttribute<RelGeral, BigInteger> totalPago;

}