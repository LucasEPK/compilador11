package SemanticAnalyzer;


import java.util.LinkedHashMap;
import java.util.Map;

public class Methods extends Commons {

    //Nombre método
    private String name;

    //Si es método estático o no
    private boolean isStatic = false;

    //El tipo de retorno
    private Struct giveBack;

    //Lista de variables que recibe el método
    private Map<String,Variable> paramsOfMethod = new LinkedHashMap<>();

    //Lista de variables declaradas dentro del método
    private Map<String,Variable> definedVar;

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
     */

    public Methods(String name, boolean isStatic, Struct giveBack, Map<String,Variable> paramsOfMethod, int pos){

        this.name = name;
        this.isStatic  = isStatic;
        this.giveBack = giveBack;
        this.paramsOfMethod = paramsOfMethod;
        this.pos = pos;

    }

    public Methods(String name, Map<String,Variable> paramsOfMethod){
        this.name = name;
        this.paramsOfMethod = paramsOfMethod;
    }

    /**
     * Constructor vo
     */

    public Methods(){

    }

    /**
     * Constructor que setea el nombre del método
     * @param name string
     */

    public Methods(String name){
        this.name = name;
    }

    /**
     * Método que setea el nombre del método
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método que setea la lista de variables declaradas
     * @param definedVar
     */

    public void setDefinedVar(Map<String, Variable> definedVar) {
        this.definedVar = definedVar;
    }

    /**
     * Método que setea la lista de parámetros del método
     * @param paramsOfMethod
     */

    public void setParamsOfMethod(Map<String, Variable> paramsOfMethod) {
        this.paramsOfMethod = paramsOfMethod;
    }

    /**
     * Método que setea el retorno del método
     * @param giveBack Struct
     */

    public void setGiveBack(Struct giveBack) {
        this.giveBack = giveBack;
    }

    /**
     * Método que setea la pos del método
     * @param pos int
     */

    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     * Método que setea si el método es estaático o mo
     * @param aStatic boolean
     */

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public Map<String, Variable> getParamsOfMethod() {
        return paramsOfMethod;
    }

    public String toJson(int tabs){
        String jsonString = "";
        jsonString += "\n" + addtabs(tabs) + "{";
        tabs=tabs+1;
        jsonString += "\n" + addtabs(tabs) + "\"" + "nombre" + "\"" + ": " + "\"" + this.name + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) +  "\"" + "static" +  "\"" + ": "  + this.isStatic + ",";
        jsonString += "\n" + addtabs(tabs) + "\"" + "retorno" + "\"" +  ": " + "\"" + this.giveBack.getName() + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "\"" + "posicion" + "\"" + ": " +this.pos + ",";
        jsonString += "\n" + addtabs(tabs) + "\"" + "paramF" + "\"" + ": [";
        //Debo imprimir los parametros que recibe el método
        if(this.paramsOfMethod.isEmpty()){
            jsonString += "]";
        }
        else {
            //Llamo a toJson de Variable
            Map<String,Variable> variableMap = this.paramsOfMethod;
            for(Map.Entry<String,Variable> variable : variableMap.entrySet()){

                jsonString +=variable.getValue().toJson(tabs+1);

            }
            jsonString = jsonString.substring(0,jsonString.length()-1);
            jsonString += "\n" + addtabs(tabs) + "]";
        }
        tabs=tabs-1;
        jsonString += "\n" + addtabs(tabs) + "}" + ",";




        return jsonString;
    }


}
