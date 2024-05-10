package SemanticAnalyzer.SymbolTable;

/**
 * Clase que será la encargada de representar los atributos declarados
 * en cada clase
 * @author Yeumen Silva
 */

public class Attributes extends Variable {


    //Si es o no pública
    private boolean isPublic = true;

    //Es heredado o no
    private boolean isInherited;


    /**
     * Constructor de clase
     *
     * @param name id de la variable
     * @param type tipo de la variable
     * @param pos  posición de la variable
     */
    public Attributes(String name, Struct type, int pos, boolean isPublic, boolean isArray) {
        super(name, type, pos, isArray);
        this.isPublic = isPublic;
    }

    /**
     * Método que setea si el atributo es privado o público
     * @param isPublic
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * método que setea si el atirubto es heredado o no
     * @param inherited booleano
     */

    public void setInherited(boolean inherited) {
        isInherited = inherited;
    }

    /**
     * Constructor de formato Json para atributos
     * @param tabs cantidad de tabs de identación
     * @return string con json de atributos
     * @author Yeumen Silva
     */

    public String toJson(int tabs){

        String jsonString = "";

        jsonString += "\n" + addtabs(tabs) + "{";
        jsonString += "\n" + addtabs(tabs) + "\"" + "nombre" + "\"" +  ": " + "\"" + this.getName() + "\"" + ",";
        //Verifico si el tipo es Array
        if(this.getIsArray()){
            jsonString += "\n" + addtabs(tabs) + "\"" + "tipo" + "\"" +  ": "  + "\"" + "Array" + "\"" + ",";
            jsonString += "\n" + addtabs(tabs) + "\"" + "tipoArray" + "\"" +  ": "  + "\"" + this.getType().getName() + "\"" + ",";
        }
        else {
            jsonString += "\n" + addtabs(tabs) + "\"" + "tipo" + "\"" +  ": "  + "\"" + this.getType().getName() + "\"" + ",";
        }

        jsonString += "\n" + addtabs(tabs) + "\"" + "public" + "\"" +   ": " + this.isPublic +",";
        jsonString += "\n" + addtabs(tabs) + "\"" + "posicion" + "\"" +  ": " + this.getPos();
        jsonString += "\n" + addtabs(tabs) + "}" + ",";

        return jsonString;
    }
}
