package SemanticAnalyzer;

public class Attributes extends Variable {


    //Si es o no pública
    private boolean isPublic;

    //Es heredado o no
    private boolean isInherited;


    /**
     * Constructor de clase
     *
     * @param name id de la variable
     * @param type tipo de la variable
     * @param pos  posición de la variable
     */
    public Attributes(String name, Struct type, int pos) {
        super(name, type, pos);
    }

    /**
     * Método que setea si el atributo es privado o público
     * @param isPublic
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void setInherited(boolean inherited) {
        isInherited = inherited;
    }

    public String toJson(int tabs){

        String jsonString = "";

        jsonString += "\n" + addtabs(tabs) + "{";
        tabs=tabs+1;
        jsonString += "\n" + addtabs(tabs) + "nombre: " + "\"" + this.getName() + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "tipo: " + "\"" + this.getType().getName() + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "public: " + "\"" + this.isPublic + "\"" + ",";
        jsonString += "\n" + addtabs(tabs) + "posicion" + "\"" + this.getPos() + "\"";
        tabs=tabs-1;
        jsonString += "\n" + addtabs(tabs) + "}" + ",";

        return jsonString;
    }
}
