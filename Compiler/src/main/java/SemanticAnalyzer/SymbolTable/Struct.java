package SemanticAnalyzer.SymbolTable;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Clase que representa nuestros structs de TinyRu
 * @author Yeumen Silva
 */

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
     * @author Yeumen Silva
     */

    public Struct(String name){
        this.name = name;
    }


    /**
     * Método que retorna el nombre de la clase
     * @return id del struct
     * @author Yeumen Silva
     */
    public String getName() {
        return name;
    }


    /**
     * Método que modifica el set de métodos
     * @param methods lista de métodos
     * @author Yeumen Silva
     */

    public void setMethods(Map<String,Methods> methods) {
        this.methods = methods;
    }


    /**
     * Método que define el constructor del Struct
     * @param method
     * @author Yeumen Silva
     */
    public void setConstructor(Methods method){
        this.constructor = method;
    }

    /**
     * Método que define si una clase fue consolidada o no
     * @return booleano, true si fue consolidada, false en caso contrario
     * @author Yeumen Silva
     */

    public boolean getIsConsolidate(){
        return  this.isConsolidate;
    }

    /**
     * Método que devuelve el constructor de la struct
     * @return Method con el constructor
     * @author Yeumen Silva
     */

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

    /**
     * Método que devuelve la lista con los atributos declarados en la clase
     * @return lista con atributos declarados
     * @author Yeumen Silva
     */

    public Map<String, Attributes> getAttributes() {
        return attributes;
    }

    /**
     * Método que agrega un atributo a la lista de atributos declarados
     * @param attributeName nombre del atributo que representa la key
     * @param attribute Objeto atributo
     * @author Lucas Moyano
     */

    public void addAttribute(String attributeName, Attributes attribute) {
        this.attributes.put(attributeName, attribute);
    }

    /**
     * Método que setea si se consololido la tabla
     * @param consolidate
     * @author Yeumen Silva
     */

    public void setConsolidate(boolean consolidate) {
        isConsolidate = consolidate;
    }

    /**
     * Método que setea el nombre de la clase
     * @param name
     * @author Yeumen Silva
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método que setea si el struct ya tiene impl
     * @param haveImpl booleano
     * @author Lucas Moyano
     */

    public void setHaveImpl(boolean haveImpl) {
        this.haveImpl = haveImpl;
    }

    /**
     * Método que retorna si una clase tiene impl o no
     * @return booleano, true si tiene impl, false en caso contrario
     * @author Lucas Moyano
     */

    public boolean getHaveImpl() {return this.haveImpl;}
    /**
     * Método que setea si el struct ya tiene struct
     * @param haveStruct
     * @author Yeumen Silva
     */

    public void setHaveStruct(boolean haveStruct) {
        this.haveStruct = haveStruct;
    }

    /**
     * Método que devuelve si la clase tiene struct
     * @return boolean, true si la clase tiene struct, false en caso contrario
     * @author Yeumen Silva
     */

    public boolean getHaveStruct(){
        return this.haveStruct;
    }

    /**
     * Método que setea si la clase hereda o no
     * @param haveInherit
     * @author Yeumen Silva
     */

    public void setHaveInherit(boolean haveInherit){
        this.haveInherit = haveInherit;
    }

    /**
     * Método que retorna los métodos del struct
     * @return lista con los métodos del struct
     * @author Yeumen Silva
     */
    public Map<String, Methods> getMethods() {
        return methods;
    }

    /**
     * Método que devuelve la clase de la cual hereda
     * @return Struct de clase padre
     * @author Yeumen Silva
     */

    public Struct getInheritFrom() {
        return inheritFrom;
    }

    /**
     * Método que convierte el struct en formato json
     * @param tabs cantida de tabs de identación
     * @return string en formato json
     * @author Yeumen Silva
     */

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
            jsonSting += "],";
        }
        else{
            //De otro modo, voy a llamar a función toJson de methods
            Map<String,Methods> methodsMap = this.methods;

            //Recorro todos los métodos
            for(Map.Entry<String,Methods> methods : methodsMap.entrySet()){
                jsonSting+= methods.getValue().toJson(tabs);
            }
            jsonSting = jsonSting.substring(0,jsonSting.length()-1);
            jsonSting += "\n" + addtabs(tabs-1) + "],";
        }

        jsonSting += addConstructor(tabs-1);




        return jsonSting;
    }

    /**
     * Recorre la lista de metodos del struct y entra en ellos para obtener su nombre
     * @return la vtable con todos los metodos escritos
     * @author Lucas Moyano
     * */
    @Override
    public String genVtables() {
        String generatedText = "";
        String nameWithNoSpaces = this.getName().replace(" ", "_");
        generatedText += nameWithNoSpaces+"_vtable:\n";
        // Recorro la lista de todos structs
        String methodsText = "";
        Map<String,Methods> methods = this.methods;
        Methods currentMethod = null;
        //Debo agregar el constructor tambien
        methodsText += "\t.word "+nameWithNoSpaces+"_";
        methodsText += "constructor\n";
        for (String methodName : methods.keySet()){
            currentMethod = methods.get(methodName);
            // Escribo los metodos de la vtable
            methodsText += "\t.word "+nameWithNoSpaces+"_";
            methodsText += currentMethod.genVtables();
        }


        if (methodsText.equals("")) { // Si la clase no tiene metodos
            generatedText = ""; // Borramos la vtable
        } else {
            generatedText += methodsText; // Añadimos los metodos a la vtable de la clase
            generatedText += "\n"; // Esto sirve para separar diferentes vtables
        }

        return generatedText;
    }

    /**
     * Método que agrega el constructor de la clase en formato json
     * @param tabs cantida de tabs de identación
     * @return string del constructor en formato json
     * @author Yeumen Silva
     */

    private String addConstructor(int tabs){
        String jsonString = "";
        jsonString+= "\n" + addtabs(tabs) + "\"" + "constructor" + "\""  +  ": {";
        jsonString+= "\n" + addtabs(tabs+1) + "\"" + "paramF" + "\"" + ": [";
        if(this.constructor.getParamsOfMethod().isEmpty()){
            jsonString += "],";
        }
        else {
            //Llamo a toJson de variable
            Map<String,Variable> variableMap = this.constructor.getParamsOfMethod();
            for (Map.Entry<String,Variable> variable : variableMap.entrySet()){
                jsonString +=variable.getValue().toJson(tabs+1);
            }
            jsonString = jsonString.substring(0,jsonString.length()-1);
            jsonString += "\n" + addtabs(tabs+1) + "],";
        }

        //Agrego variables definidas dentro del constructor
        jsonString+= "\n" + addtabs(tabs+1) + "\"" + "variables" + "\"" + ": [";
        if(this.constructor.getParamsOfMethod().isEmpty()){
            jsonString += "]";
        }
        else {
            //Llamo a tojson de variable
            for(Variable actualVariable : this.constructor.getDefinedVar().values()){
                jsonString+= actualVariable.toJson(tabs+1);
            }
            jsonString = jsonString.substring(0,jsonString.length()-1);
            jsonString += "\n" + addtabs(tabs+1) + "]";
        }

        jsonString+=  "\n" + addtabs(tabs) + "}";



        return jsonString;
    }


}

