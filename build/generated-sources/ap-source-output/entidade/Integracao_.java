package entidade;

import entidade.Funcionario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(Integracao.class)
public class Integracao_ { 

    public static volatile SingularAttribute<Integracao, Funcionario> cpfFuncionario;
    public static volatile SingularAttribute<Integracao, Integer> idIntegracao;
    public static volatile SingularAttribute<Integracao, Date> dtInicio;
    public static volatile SingularAttribute<Integracao, Date> dtFim;
    public static volatile SingularAttribute<Integracao, String> empresaIntegracao;

}