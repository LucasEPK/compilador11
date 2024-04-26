package SemanticAnalyzer;

public class Variable extends Commons {

    //Id que identifica la variable
    private String name;

    //Tipo de la variable
    private Struct type;

    //Posición de la variable
    private int pos;

    /**
     * Constructor de clase
     * @param name id de la variable
     * @param type tipo de la variable
     * @param pos posición de la variable
     */

    public Variable(String name, Struct type, int pos){
        this.name = name;
        this.type = type;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public Struct getType() {
        return type;
    }

    public int getPos() {
        return pos;
    }

    public String toJson(int tabs){
        String jsonString = "";
        jsonString += "\n" + addtabs(tabs+2) + "{";
        tabs=tabs+2;
        jsonString += "\n" + addtabs(tabs) + "\"" + "nombre" + "\"" + ": " + "\"" + this.name + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "\"" + "tipo" + "\"" + ": " + "\"" + this.type.getName() + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "\"" + "posicion" + "\"" + ": " +  this.pos  ;
        tabs=tabs-1;
        jsonString += "\n" + addtabs(tabs) + "}" + ",";


        return jsonString;


    }
}