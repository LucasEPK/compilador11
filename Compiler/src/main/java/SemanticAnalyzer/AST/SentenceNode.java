package SemanticAnalyzer.AST;


/**
 * Clase representate de las sentencias en nuestro AST
 * @author Yeumen Silva
 */

public abstract class SentenceNode implements Commons {

    String struct;

    String method;

    // Variable que indica si la sentencia ya fue consolidada
    boolean consolidated = false;

    String type  = "void";

    public SentenceNode( String struct, String method ) {

        this.struct = struct;
        this.method = method;
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


    public void setMethod(String method) {
        this.method = method;
    }

    public void setStruct(String struct) {
        this.struct = struct;
    }

    public void setConsolidated(boolean consolidated) {
        this.consolidated =  consolidated;
    }

    public void setType(String type) {
        this.type = type;
    }
}
