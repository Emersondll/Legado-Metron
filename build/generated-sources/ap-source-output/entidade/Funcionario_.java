package entidade;

import entidade.Epi;
import entidade.Integracao;
import entidade.LancServPrestado;
import entidade.PagtoFuncionario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(Funcionario.class)
public class Funcionario_ { 

    public static volatile SingularAttribute<Funcionario, Date> asoDtFim;
    public static volatile SingularAttribute<Funcionario, String> cidade;
    public static volatile SingularAttribute<Funcionario, String> observacao;
    public static volatile SingularAttribute<Funcionario, String> apelido;
    public static volatile SingularAttribute<Funcionario, String> nmFuncionario;
    public static volatile SingularAttribute<Funcionario, String> celular1;
    public static volatile SingularAttribute<Funcionario, String> conta;
    public static volatile CollectionAttribute<Funcionario, PagtoFuncionario> pagtoFuncionarioCollection;
    public static volatile SingularAttribute<Funcionario, String> agencia;
    public static volatile SingularAttribute<Funcionario, Date> dtDemissao;
    public static volatile SingularAttribute<Funcionario, Integer> cep;
    public static volatile SingularAttribute<Funcionario, String> uf;
    public static volatile SingularAttribute<Funcionario, String> pisPasep;
    public static volatile SingularAttribute<Funcionario, Long> cpf;
    public static volatile SingularAttribute<Funcionario, String> ctps;
    public static volatile SingularAttribute<Funcionario, String> cnh;
    public static volatile CollectionAttribute<Funcionario, LancServPrestado> lancServPrestadoCollection;
    public static volatile CollectionAttribute<Funcionario, Integracao> integracaoCollection;
    public static volatile CollectionAttribute<Funcionario, Epi> epiCollection;
    public static volatile SingularAttribute<Funcionario, String> endereco;
    public static volatile SingularAttribute<Funcionario, String> bairro;
    public static volatile SingularAttribute<Funcionario, String> banco;
    public static volatile SingularAttribute<Funcionario, String> rg;
    public static volatile SingularAttribute<Funcionario, Date> dtAdmissao;
    public static volatile SingularAttribute<Funcionario, Date> asoDtInicio;
    public static volatile SingularAttribute<Funcionario, String> foneResid;

}