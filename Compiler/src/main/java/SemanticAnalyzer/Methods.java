package SemanticAnalyzer;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Clase que representa nuestros metodos de TinyRu
 * @author Yeumen Silva
 */

public class Methods extends Commons {

    //Nombre método
    private String name;

    //Si es método estático o no
    private boolean isStatic = false;

    //El tipo de retorno
    private Struct giveBack;

    private boolean isGiveBackArray = false;

    //Lista de variables que recibe el método
    private Map<String,Variable> paramsOfMethod = new LinkedHashMap<>();

    //Lista de variables declaradas dentro del método
    private Map<String,Variable> definedVar = new LinkedHashMap<>();

    //Pos del método
    private int pos;

    //Es heredado o no
    private boolean isInherited;

    /**
     * Constructor de la clase Method el cual se usa
     * para construir los métodos predefinidos
     * @param name nombre del método
     * @param isStatic si es estático o no
     * @param giveBack tipo de retorno
     * @param paramsOfMethod parámetros que recibe
     * @param pos posición del método
     * @author Lucas Moyano
     */

    public Methods(String name, boolean isStatic, Struct giveBack, Map<String,Variable> paramsOfMethod, int pos){

        this.name = name;
        this.isStatic  = isStatic;
        this.giveBack = giveBack;
        this.paramsOfMethod = paramsOfMethod;
        this.pos = pos;

    }

    /**
     * Constructor de clase
     * @param name nombre del método
     * @param paramsOfMethod parametros que recibe
     * @author Yeumen Silva
     */

    public Methods(String name, Map<String,Variable> paramsOfMethod){
        this.name = name;
        this.paramsOfMethod = paramsOfMethod;
    }


    /**
     * Constructor vacio
     * @author Lucas Moyano
     */

    public Methods(){

    }

    /**
     * Constructor que setea el nombre del método
     * @param name string
     * @author Yeumen Silva
     */


    public Methods(String name){
        this.name = name;
    }

    /**
     * Método que setea el nombre del método
     * @param name
     * @author Yeumen Silva
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método que setea si el método es heredado o no
     * @param inherited
     * @author Yeumen Silva
     */

    public void setInherited(boolean inherited) {
        isInherited = inherited;
    }

    /**
     * Método que devuelve el nombre de la clase
     * @return string con nombre de la clase
     * @author Yeumen Silva
     */

    public String getName() {
        return name;
    }

    /**
     * Método que devuelve el tipo de retorno del método
     * @return struct con tipo de retorno
     * @author Yeumen Silva
     */
    public Struct getGiveBack() {
        return giveBack;
    }

    /**
     * Método que devuelve si el método es estatica o no
     * @return boolean, true si clase es estatica, false en caso contrario
     * @author Yeumen Silva
     */

    public boolean getIsStatic(){
        return this.isStatic;
    }


    /**
     * Método que setea la lista de variables declaradas
     * @param definedVar
     * @author Yeumen Silva
     */

    public void setDefinedVar(Map<String, Variable> definedVar) {
        this.definedVar = definedVar;
    }

    /**
     * Método que setea si el tipo que devuelve el método es un array
     * @param isGiveBackArray true si es array, false en caso contrario
     * @author Lucas Moyano
     */

    public void setIsGiveBackArray(boolean isGiveBackArray) {
        this.isGiveBackArray = isGiveBackArray;
    }

    /**
     * Método que devuelve si el tipo e retorno es un Array
     * @return booleano true si devuelve array, false en caso contrario
     * @author Lucas Moyano
     */

    public boolean getIsGiveBackArray() { return this.isGiveBackArray;}

    /**
     * Método que devuelve la lista de variables definidas dentro del método
     * @return Lista con variables definidas
     * @author Yeumen Silva
     */

    public Map<String, Variable> getDefinedVar() {
        return definedVar;
    }

    /**
     * Método que agrega una variable a la lista de variables declaradas
     * @param varName nombre de la variable
     * @param var Objeto Variable
     * @author Lucas Moyano
     */

    public void addVariable(String varName,Variable var) {
        // añade una variable a la lista de variables
        this.getDefinedVar().put(varName, var);
    }

    /**
     * Método que setea la lista de parámetros del método
     * @param paramsOfMethod
     * @author Yeumen Silva
     */

    public void setParamsOfMethod(Map<String, Variable> paramsOfMethod) {
        this.paramsOfMethod = paramsOfMethod;
    }

    /**
     * Método que agrega un parámetro a la lista de parámetros
     * @param paramName nombre del parámetro
     * @param param Objeto de clase Variable
     * @author Lucas Moyano
     */


    public void addParameter(String paramName, Variable param) {
        this.getParamsOfMethod().put(paramName, param);
    }

    /**
     * Método que setea el retorno del método
     * @param giveBack Struct
     * @author Yeumen Silva
     */

    public void setGiveBack(Struct giveBack) {
        this.giveBack = giveBack;
    }

    /**
     * Método que setea la pos del método
     * @param pos int
     * @author Yeumen Silva
     */

    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     * Método que setea si el método es estaático o mo
     * @param aStatic boolean
     * @author Yeumen Silva
     */

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    /**
     * Método que devuelve los parametros que recibe el método
     * @return lista con los parametros definidos en el método
     * @author Yeumen Silva
     */

    public Map<String, Variable> getParamsOfMethod() {
        return paramsOfMethod;
    }

    /**
     * Constructor de Json para Clase Methods
     * @param tabs cantida de tabs de identación
     * @return String en formato Json
     * @author Yeumen Silva
     */

    public String toJson(int tabs){
        String jsonString = "";
        jsonString += "\n" + addtabs(tabs) + "{";
        tabs=tabs+1;
        jsonString += "\n" + addtabs(tabs) + "\"" + "nombre" + "\"" + ": " + "\"" + this.name + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) +  "\"" + "static" +  "\"" + ": "  + this.isStatic + ",";
        //Si es un array debo imprimir ambos tipos
        if(this.getIsGiveBackArray()){
            jsonString += "\n" + addtabs(tabs) + "\"" + "retorno" + "\"" +  ": " + "\"" + "Array" + "\"" + ",";
            jsonString += "\n" + addtabs(tabs) + "\"" + "TipoArray" + "\"" +  ": " + "\"" + this.giveBack.getName() + "\"" + ",";
        }
        else {
            jsonString += "\n" + addtabs(tabs) + "\"" + "retorno" + "\"" +  ": " + "\"" + this.giveBack.getName() + "\"" + ",";
        }
        jsonString += "\n" + addtabs(tabs) + "\"" + "posicion" + "\"" + ": " +this.pos + ",";
        jsonString += "\n" + addtabs(tabs) + "\"" + "paramF" + "\"" + ": [";
        //Debo imprimir los parametros que recibe el método
        if(this.paramsOfMethod.isEmpty()){
            jsonString += "],";
        }
        else {
            //Llamo a toJson de Variable
            Map<String,Variable> variableMap = this.paramsOfMethod;
            for(Map.Entry<String,Variable> variable : variableMap.entrySet()){

                jsonString +=variable.getValue().toJson(tabs+1);

            }
            jsonString = jsonString.substring(0,jsonString.length()-1);
            jsonString += "\n" + addtabs(tabs) + "],";
        }
        //Debo imprimir las Variables de cada método
        jsonString+= "\n" + addtabs(tabs) + "\"" + "variables" + "\"" + ": [";
        if(this.definedVar.isEmpty()){
            jsonString+="]";
        }else {
            for(Variable variable: this.definedVar.values()){
                jsonString+=variable.toJson(tabs);
            }
            jsonString = jsonString.substring(0,jsonString.length()-1);
            jsonString += "\n" + addtabs(tabs) + "]";
        }
        tabs=tabs-1;
        jsonString += "\n" + addtabs(tabs) + "}" + ",";
        //Debo imprimir




        return jsonString;
    }


}
