package SemanticAnalyzer;


import java.util.LinkedHashMap;
import java.util.Map;

public class Struct extends Commons {

    //Id de la clase
    private String name;

    //Clase de la cual hereda
    private Struct inheritFrom = null;

    //Hereda o no
    private boolean haveInherit;

    //Lista de atributos
    private Map<String, Attributes> attributes = new LinkedHashMap<>();

    //Lista de métodos
    private Map<String,Methods> methods = new LinkedHashMap();

    //Indica si la clase fue o no consolidada

    private boolean isConsolidate = false;

    //Constructor de clase

    Methods constructor ;

    //Indica si ya tiene un struct declarado
    private boolean haveStruct = false;

    //Indica si ya tiene un impl declarado
    private boolean haveImpl = false;




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

    public void setMethods(Map<String,Methods> methods) {
        this.methods = methods;
    }


    /**
     * Método que define el constructor del Struct
     * @param method
     */
    public void setConstructor(Methods method){
        this.constructor = method;
    }

    public boolean getIsConsolidate(){
        return  this.isConsolidate;
    }

    public Methods getConstructor() {
        return constructor;
    }

    /**
     * Método que define la clase de la la cual hereda
     * @param inheritFrom
     */

    public void setInheritFrom(Struct inheritFrom) {
        this.inheritFrom = inheritFrom;
    }

    /**
     * Método que setea los atributos de la clase
     * @param attributes
     */


    public void setAttributes(Map<String, Attributes> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Attributes> getAttributes() {
        return attributes;
    }

    public void addAttribute(String attributeName, Attributes attribute) {
        this.attributes.put(attributeName, attribute);
    }

    /**
     * Método que setea si se consololido la tabla
     * @param consolidate
     */

    public void setConsolidate(boolean consolidate) {
        isConsolidate = consolidate;
    }

    /**
     * Método que setea el nombre de la clase
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método que setea si el struct ya tiene impl
     * @param haveImpl booleano
     */

    public void setHaveImpl(boolean haveImpl) {
        this.haveImpl = haveImpl;
    }

    public boolean getHaveImpl() {return this.haveImpl;}
    /**
     * Método que setea si el struct ya tiene struct
     * @param haveStruct
     */

    public void setHaveStruct(boolean haveStruct) {
        this.haveStruct = haveStruct;
    }

    public boolean getHaveStruct(){
        return this.haveStruct;
    }

    /**
     * Método que setea si la clase hereda o no
     * @param haveInherit
     */

    public void setHaveInherit(boolean haveInherit){
        this.haveInherit = haveInherit;
    }

    /**
     * Método que retorna los métodos del struct
     * @return lista con los métodos del struct
     */
    public Map<String, Methods> getMethods() {
        return methods;
    }

    public Struct getInheritFrom() {
        return inheritFrom;
    }

    public String toJson(int tabs){

        //Concateno todos los datos del struct
        String jsonSting = "";
        jsonSting = jsonSting + "\n" + addtabs(tabs) + "\"nombre\": " + "\""  + this.name + "\"" + ",";
        if(this.inheritFrom == null){
            jsonSting = jsonSting + "\n" + addtabs(tabs) + "\"heredaDe\": " + "\"" + this.inheritFrom + "\"" + ",";
        }
        else {
            jsonSting = jsonSting + "\n" + addtabs(tabs) + "\"heredaDe\": " + "\"" + this.inheritFrom.getName() + "\"" + ",";
        }

        jsonSting = jsonSting + "\n" + addtabs(tabs) + "\"atributos\": [";
        //Si no posee atributos, solo cierro los corchetes
        tabs +=1;
        if(this.attributes.isEmpty()){
            jsonSting+= "],";
        }
        else {
            //De otro modo, voy a llamar a función toJson de attributes
            Map<String,Attributes> attributesMap = this.attributes;

            //Recorro todos los atributos
            for(Map.Entry<String,Attributes> attributes : attributesMap.entrySet()){

                jsonSting+= attributes.getValue().toJson(tabs);

            }
            jsonSting = jsonSting.substring(0,jsonSting.length()-1);
            jsonSting += "\n" + addtabs(tabs-1) + "],";
        }
        tabs -=1;

        jsonSting += "\n" + addtabs(tabs) + "\"" + "metodos"  + "\"" + ": [";
        tabs+=1;
        //Si el struct no tiene métodos
        if(this.methods.isEmpty()){
            jsonSting += "]";
        }
        else{
            //De otro modo, voy a llamar a función toJson de methods
            Map<String,Methods> methodsMap = this.methods;

            //Recorro todos los métodos
            for(Map.Entry<String,Methods> methods : methodsMap.entrySet()){
                jsonSting+= methods.getValue().toJson(tabs);
            }
            jsonSting = jsonSting.substring(0,jsonSting.length()-1);
            jsonSting += "\n" + addtabs(tabs-1) + "]";
        }

        //jsonSting += addConstructor(tabs-1);




        return jsonSting;
    }

    private String addConstructor(int tabs){
        String jsonString = "";
        jsonString+= "\n" + addtabs(tabs) + "\"" + "constructor" + "\""  +  ": {";
        jsonString+= "\n" + addtabs(tabs+1) + "\"" + "paramF" + "\"" + ": [";
        if(this.constructor.getParamsOfMethod().isEmpty()){
            jsonString += "]";
        }
        else {
            //Llamo a toJson de variable
            Map<String,Variable> variableMap = this.constructor.getParamsOfMethod();
            for (Map.Entry<String,Variable> variable : variableMap.entrySet()){
                jsonString +=variable.getValue().toJson(tabs+1);
            }
            jsonString = jsonString.substring(0,jsonString.length()-1);
            jsonString += "\n" + addtabs(tabs) + "]";
        }

        return jsonString;
    }


}

