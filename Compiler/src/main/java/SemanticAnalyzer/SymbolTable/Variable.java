package SemanticAnalyzer.SymbolTable;


/**
 * Clase que representa las variables declaradas en start, métodos
 * y/o enviadas como parametro
 * @author Yeumen Silva
 */

public class Variable extends Commons {

    //Id que identifica la variable
    private String name;

    //Tipo de la variable
    private Struct type;

    //Si es array o no
    private boolean isArray = false;

    //Posición de la variable
    private int pos;

    /**
     * Constructor de clase
     * @param name id de la variable
     * @param type tipo de la variable
     * @param pos posición de la variable
     * @author Lucas Moyano
     */

    public Variable(String name, Struct type, int pos, boolean isArray){
        this.name = name;
        this.type = type;
        this.pos = pos;
        this.isArray = isArray;
    }

    /**
     * Método que devuelve el nombre de la variable
     * @return string nombre
     * @author Yeumen Silva
     */

    public String getName() {
        return name;
    }

    /**
     * Método que devuelve el tipo de la variable
     * @return Struct con el tipo
     * @author Yeumen Silva
     */

    public Struct getType() {
        return type;
    }

    /**
     * Método que devuele la pos de la variable
     * @return int pos
     * @author Yeumen Silva
     */

    public int getPos() {
        return pos;
    }

    /**
     * Método que setea la pos de la variable
     * @param pos int posición en donde esta definida
     * @author Yeumen Silva
     */

    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     * Método que devuelve si el tipo es un array
     * @return boolean, true si es un array,false en caso contrario
     * @author Lucas Moyano
     */

    public boolean getIsArray() { return isArray;}

    /**
     * Método que setea si la variable es un array
     * @param isArray boolean, true si es arrray, false en caso contrario
     * @author Yeumen Silva
     */

    public void setIsArray(boolean isArray) {this.isArray = isArray;}

    /**
     * Método que convierte la variable en formato json
     * @param tabs cantida de tabs de identación
     * @return String con variable en formato json
     * @author Yeumen Silva
     */

    public String toJson(int tabs){
        String jsonString = "";
        jsonString += "\n" + addtabs(tabs+1) + "{";
        tabs=tabs+1;
        jsonString += "\n" + addtabs(tabs) + "\"" + "nombre" + "\"" + ": " + "\"" + this.name + "\"" + ",";
        //Verifico si el tipo es Array
        if(this.isArray){
            jsonString += "\n" + addtabs(tabs) + "\"" + "tipo" + "\"" + ": " + "\"" + "Array" + "\"" + ",";
            jsonString += "\n" + addtabs(tabs) + "\"" + "tipoArray" + "\"" + ": " + "\"" + this.type.getName() + "\"" + ",";
        }
        else {
            jsonString += "\n" + addtabs(tabs) + "\"" + "tipo" + "\"" + ": " + "\"" + this.type.getName() + "\"" + ",";
        }

        jsonString += "\n" + addtabs(tabs) + "\"" + "posicion" + "\"" + ": " +  this.pos  ;
        jsonString += "\n" + addtabs(tabs) + "}" + ",";


        return jsonString;


    }
}
