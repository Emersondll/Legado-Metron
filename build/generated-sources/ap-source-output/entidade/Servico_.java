package entidade;

import entidade.LancServPrestado;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(Servico.class)
public class Servico_ { 

    public static volatile SingularAttribute<Servico, String> descServico;
    public static volatile SingularAttribute<Servico, Character> tpServico;
    public static volatile SingularAttribute<Servico, Integer> cdServico;
    public static volatile SingularAttribute<Servico, BigDecimal> vlPadrao;
    public static volatile SingularAttribute<Servico, Character> idFalta;
    public static volatile SingularAttribute<Servico, Character> tpCobranca;
    public static volatile CollectionAttribute<Servico, LancServPrestado> lancServPrestadoCollection;
    public static volatile SingularAttribute<Servico, Character> tpObra;

}