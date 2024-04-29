package SemanticAnalyzer;

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
     */

    public Variable(String name, Struct type, int pos, boolean isArray){
        this.name = name;
        this.type = type;
        this.pos = pos;
        this.isArray = isArray;
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

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean getIsArray() { return isArray;}

    public void setIsArray(boolean isArray) {this.isArray = isArray;}

    public String toJson(int tabs){
        String jsonString = "";
        jsonString += "\n" + addtabs(tabs+1) + "{";
        tabs=tabs+1;
        jsonString += "\n" + addtabs(tabs) + "\"" + "nombre" + "\"" + ": " + "\"" + this.name + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "\"" + "tipo" + "\"" + ": " + "\"" + this.type.getName() + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "\"" + "posicion" + "\"" + ": " +  this.pos  ;
        jsonString += "\n" + addtabs(tabs) + "}" + ",";


        return jsonString;


    }
}
