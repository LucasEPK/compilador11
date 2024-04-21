package SemanticAnalyzer;

import java.util.HashMap;

public class Methods extends Json {

    //Nombre método
    private String name;

    //Si es método estático o no
    private boolean isStatic = false;

    //El tipo de retorno
    private Struct giveBack;

    //Lista de variables que recibe el método
    private HashMap<String,Variable> paramsOfMethod;

    //Lista de atributos declarados en el método
    private HashMap<String, Attributes> atributesOfMethod;

    //Pos del método
    private int pos;

    /**
     * Constructor de la clase Method el cual se usa
     * para construir los métodos predefinidos
     * @param name nombre del método
     * @param isStatic si es estático o no
     * @param giveBack tipo de retorno
     * @param paramsOfMethod parámetros que recibe
     * @param pos posición del método
     */

    public Methods(String name, boolean isStatic, Struct giveBack, HashMap<String,Variable> paramsOfMethod, int pos){

        this.name = name;
        this.isStatic  = isStatic;
        this.giveBack = giveBack;
        this.paramsOfMethod = paramsOfMethod;
        this.pos = pos;

    }


}
