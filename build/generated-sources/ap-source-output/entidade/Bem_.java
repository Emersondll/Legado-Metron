package entidade;

import entidade.LancServPrestado;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-21T23:53:35")
@StaticMetamodel(Bem.class)
public class Bem_ { 

    public static volatile SingularAttribute<Bem, Integer> cdBem;
    public static volatile SingularAttribute<Bem, Character> tpBem;
    public static volatile SingularAttribute<Bem, String> dsBem;
    public static volatile CollectionAttribute<Bem, LancServPrestado> lancServPrestadoCollection;

}