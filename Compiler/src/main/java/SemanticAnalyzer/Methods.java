package SemanticAnalyzer;

import java.util.List;
import java.util.Set;

public class Methods extends Json {

    //Nombre método
    private String name;

    //Si es método estático o no
    private boolean isStatic = false;

    //El tipo de retorno
    private String giveBack;

    //Lista de parametros que recibe el método
    private Set<Params> paramsOfMethod;

    //Lista de atributos declarados en el método
    private Set<Atributes> atributesOfMethod;

    //Pos del método
    private int pos;

    /**
     * Constructor de la clase Method el cual se usa
     * para construir los métodos predefinidos
     * @param name nombre del método
     * @param isStatic
     * @param giveBack
     * @param paramsOfMethod
     * @param pos
     */

    public Methods(String name, boolean isStatic, String giveBack, Set<Params> paramsOfMethod, int pos){

        this.name = name;
        this.isStatic  = isStatic;
        this.giveBack = giveBack;
        this.paramsOfMethod = paramsOfMethod;
        this.pos = pos;

    }


}
