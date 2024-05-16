package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate de las sentencias en nustro AST
 * @author Yeumen Silva
 */

public abstract class SentenceNode implements Commons {

    String struct;

    String method;

    Token token;

    String type;
    public SentenceNode(Token token, String struct, String method ) {

        this.token = token;
        this.struct = struct;
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public Token getToken() {
        return token;
    }

    public String getMethod() {
        return method;
    }

    public String getStruct() {
        return struct;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setStruct(String struct) {
        this.struct = struct;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
