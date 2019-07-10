package entidade;

import entidade.Cliente;
import entidade.LancServPrestado;
import entidade.LancamentoDespesa;
import entidade.LancamentoRecebimento;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(Obra.class)
public class Obra_ { 

    public static volatile SingularAttribute<Obra, String> cidade;
    public static volatile SingularAttribute<Obra, String> observacao;
    public static volatile SingularAttribute<Obra, String> endereco;
    public static volatile SingularAttribute<Obra, String> bairro;
    public static volatile SingularAttribute<Obra, Cliente> cdCliente;
    public static volatile SingularAttribute<Obra, Integer> cep;
    public static volatile CollectionAttribute<Obra, LancamentoRecebimento> lancamentoRecebimentoCollection;
    public static volatile SingularAttribute<Obra, String> uf;
    public static volatile CollectionAttribute<Obra, LancamentoDespesa> lancamentoDespesaCollection;
    public static volatile SingularAttribute<Obra, Integer> cdObra;
    public static volatile SingularAttribute<Obra, String> nmObra;
    public static volatile CollectionAttribute<Obra, LancServPrestado> lancServPrestadoCollection;
    public static volatile SingularAttribute<Obra, Character> status;
    public static volatile SingularAttribute<Obra, Character> tpObra;

}