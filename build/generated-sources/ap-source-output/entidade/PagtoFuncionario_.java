package entidade;

import entidade.Funcionario;
import entidade.PagtoFuncionarioPK;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(PagtoFuncionario.class)
public class PagtoFuncionario_ { 

    public static volatile SingularAttribute<PagtoFuncionario, Long> qtdValeRefeicao;
    public static volatile SingularAttribute<PagtoFuncionario, PagtoFuncionarioPK> pagtoFuncionarioPK;
    public static volatile SingularAttribute<PagtoFuncionario, BigDecimal> valorRefeicaoDia;
    public static volatile SingularAttribute<PagtoFuncionario, BigDecimal> valor;
    public static volatile SingularAttribute<PagtoFuncionario, BigDecimal> valorValeTransporte;
    public static volatile SingularAttribute<PagtoFuncionario, Long> qtdValeTransporte;
    public static volatile SingularAttribute<PagtoFuncionario, Funcionario> funcionario;
    public static volatile SingularAttribute<PagtoFuncionario, Date> dtFim;

}