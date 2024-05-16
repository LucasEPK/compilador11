package SemanticAnalyzer.AST;


/**
 * Clase representate de las sentencias en nuestro AST
 * @author Yeumen Silva
 */

public abstract class SentenceNode implements Commons {

    String struct;

    String method;

    public SentenceNode( String struct, String method ) {

        this.struct = struct;
        this.method = method;
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

}
