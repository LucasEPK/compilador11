package SemanticAnalyzer.AST;


import LexicalAnalyzer.Token;

/**
 * Clase representate de las sentencias en nuestro AST
 * @author Yeumen Silva
 */

public abstract class SentenceNode implements Commons {

    String struct;

    String method;

    // Variable que indica si la sentencia ya fue consolidada
    boolean consolidated = false;

    private String type  = "void";


    // Esta variable se usa para errores y saber donde empieza la sentencia
    private Token referenceToken;

    //Booleano para saber si el type es un Array o no
    private boolean isArray = false;

    public SentenceNode( String struct, String method ) {

        this.struct = struct;
        this.method = method;
    }



    public boolean getIsArray() {
        return isArray;
    }
    public boolean getConsolidated() {
        return consolidated;
    }

    public String getType() {
        return type;
    }

    public String getMethod() {
        return method;
    }

    public String getStruct() {
        return struct;
    }

    public Token getReferenceToken() {
        return referenceToken;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setStruct(String struct) {
        this.struct = struct;
    }

    public void setIsArray(boolean isArray) {
        this.isArray = isArray;
    }


    public void setConsolidated(boolean consolidated) {
        this.consolidated =  consolidated;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReferenceToken(Token referenceToken) {
        this.referenceToken = referenceToken;
    }
}
