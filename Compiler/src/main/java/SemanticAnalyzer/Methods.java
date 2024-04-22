package SemanticAnalyzer;


import java.util.Map;

public class Methods extends Commons {

    //Nombre método
    private String name;

    //Si es método estático o no
    private boolean isStatic = false;

    //El tipo de retorno
    private Struct giveBack;

    //Lista de variables que recibe el método
    private Map<String,Variable> paramsOfMethod;

    //Lista de atributos declarados en el método
    private Map<String, Attributes> atributesOfMethod;

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

    public Methods(String name, boolean isStatic, Struct giveBack, Map<String,Variable> paramsOfMethod, int pos){

        this.name = name;
        this.isStatic  = isStatic;
        this.giveBack = giveBack;
        this.paramsOfMethod = paramsOfMethod;
        this.pos = pos;

    }

    public String toJson(int tabs){
        String jsonString = "";
        jsonString += "\n" + addtabs(tabs) + "{";
        tabs=tabs+1;
        jsonString += "\n" + addtabs(tabs) + "nombre: " + "\"" + this.name + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "static: " + "\"" + this.isStatic + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "retorno: " + "\"" + this.giveBack.getName() + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "posicion: " + "\"" + this.pos + "\"";
        tabs=tabs-1;
        jsonString += "\n" + addtabs(tabs) + "}" + ",";

        //ToDo agregar variables que recibe el método
        //No se agrega lo declarado dentro del método


        return jsonString;
    }


}
