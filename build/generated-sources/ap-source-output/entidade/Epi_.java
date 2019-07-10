package entidade;

import entidade.Funcionario;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(Epi.class)
public class Epi_ { 

    public static volatile SingularAttribute<Epi, Integer> idEpi;
    public static volatile SingularAttribute<Epi, BigDecimal> valor;
    public static volatile SingularAttribute<Epi, Date> dataRetirada;
    public static volatile SingularAttribute<Epi, String> dsEpi;
    public static volatile SingularAttribute<Epi, Funcionario> funcionario;

}