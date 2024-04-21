package SemanticAnalyzer;

import java.util.HashMap;
import java.util.Set;

public class Struct extends Json {

    //Id de la clase
    private String name;

    //Clase de la cual hereda
    private Class inerithFrom;

    //Lista de atributos
    private HashMap<String,Atributes> atributes;

    //Lista de métodos
    private HashMap<String,Methods> methods;

    //Indica si la clase fue o no consolidada

    private boolean isConsolidate = false;


    /**
     * Constructor con solo id de clase
     * @param name id de la clase
     */

    public Struct(String name){
        this.name = name;
    }


    /**
     * Método que retorna el nombre de la clase
     * @return id del struct
     */
    public String getName() {
        return name;
    }

    /**
     * Método que modifica el set de métodos
     * @param methods lista de métodos
     */

    public void setMethods(HashMap<String,Methods> methods) {
        this.methods = methods;
    }
}
