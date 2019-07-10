package entidade;

import entidade.Obra;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, String> cidade;
    public static volatile SingularAttribute<Cliente, String> observacao;
    public static volatile SingularAttribute<Cliente, String> endereco;
    public static volatile SingularAttribute<Cliente, String> foneComercial;
    public static volatile SingularAttribute<Cliente, String> bairro;
    public static volatile SingularAttribute<Cliente, String> foneResidencial;
    public static volatile SingularAttribute<Cliente, Character> tpPessoa;
    public static volatile SingularAttribute<Cliente, Integer> cdCliente;
    public static volatile SingularAttribute<Cliente, Integer> cep;
    public static volatile SingularAttribute<Cliente, String> nmCliente;
    public static volatile SingularAttribute<Cliente, String> uf;
    public static volatile SingularAttribute<Cliente, String> celular;
    public static volatile SingularAttribute<Cliente, Long> cpfCnpj;
    public static volatile SingularAttribute<Cliente, BigInteger> rgInsc;
    public static volatile CollectionAttribute<Cliente, Obra> obraCollection;
    public static volatile SingularAttribute<Cliente, String> email;

}