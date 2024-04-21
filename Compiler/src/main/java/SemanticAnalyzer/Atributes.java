package SemanticAnalyzer;

public class Atributes extends Variable {


    //Si es o no pública
    private boolean isPublic;


    /**
     * Constructor de clase
     *
     * @param name id de la variable
     * @param type tipo de la variable
     * @param pos  posición de la variable
     */
    public Atributes(String name, Struct type, int pos) {
        super(name, type, pos);
    }
}
