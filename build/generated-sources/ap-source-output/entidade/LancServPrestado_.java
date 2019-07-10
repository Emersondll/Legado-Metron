package entidade;

import entidade.Bem;
import entidade.Funcionario;
import entidade.LancServPrestadoPK;
import entidade.Obra;
import entidade.Servico;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(LancServPrestado.class)
public class LancServPrestado_ { 

    public static volatile SingularAttribute<LancServPrestado, BigDecimal> qtdCobrar;
    public static volatile SingularAttribute<LancServPrestado, Bem> cdBem;
    public static volatile SingularAttribute<LancServPrestado, Date> hrSaidaEscritorio;
    public static volatile SingularAttribute<LancServPrestado, BigDecimal> qtdRealizada;
    public static volatile SingularAttribute<LancServPrestado, Date> hrInicioServico;
    public static volatile SingularAttribute<LancServPrestado, Obra> obra;
    public static volatile SingularAttribute<LancServPrestado, Date> hrChegadaEscritorio;
    public static volatile SingularAttribute<LancServPrestado, BigDecimal> valorCobrar;
    public static volatile SingularAttribute<LancServPrestado, Date> hrFimServico;
    public static volatile SingularAttribute<LancServPrestado, LancServPrestadoPK> lancServPrestadoPK;
    public static volatile SingularAttribute<LancServPrestado, Funcionario> funcionario;
    public static volatile SingularAttribute<LancServPrestado, String> notaFiscal;
    public static volatile SingularAttribute<LancServPrestado, Servico> servico;

}