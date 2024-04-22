package SemanticAnalyzer;


import java.util.LinkedHashMap;
import java.util.Map;

public class Struct extends Commons {

    //Id de la clase
    private String name;

    //Clase de la cual hereda
    private Struct inheritFrom = null;

    //Lista de atributos
    private Map<String, Attributes> attributes = new LinkedHashMap<>();

    //Lista de métodos
    private Map<String,Methods> methods = new LinkedHashMap();

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

    public void setMethods(Map<String,Methods> methods) {
        this.methods = methods;
    }


    public String toJson(int tabs){

        //Concateno todos los datos del struct
        String jsonSting = "";
        jsonSting = jsonSting + "\n" + addtabs(tabs) + "\"nombre\": " + "\""  + this.name + "\"" + ",";
        jsonSting = jsonSting + "\n" + addtabs(tabs) + "\"heredaDe\": " + "\"" + this.inheritFrom + "\"" + ",";
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

        //ToDo agregar constructor
        //ToDo ver como agregar herencia


        return jsonSting;
    }


}

