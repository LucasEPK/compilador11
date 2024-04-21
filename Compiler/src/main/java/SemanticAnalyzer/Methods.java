package SemanticAnalyzer;

import java.util.List;
import java.util.Set;

public class Methods {

    //Nombre método
    private String name;

    //Si es método estático o no
    private boolean isStatic;

    //El tipo de retorno
    private String giveBack;

    //Lista de parametros que recibe el método
    private Set<Params> paramsOfMethod;

    //Lista de atributos declarados en el método
    private Set<Atributes> atributesOfMethod;

    //Pos del método
    private int pos;


}
