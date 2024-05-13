package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public class ASTNode {


    //Token que contiene token, lexema, fila y columna
    Token token;

    //Nombre de la clase a la cual pertence el nodo
    String struct;

    //Método al cual pertenece el nodo

    String method;

    //Nombre del nodo

    String nodeName;

    //Tipo del nodo

    String type;

    /**
     * Constructor que asigna token, struct y método
     * @param token token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     * @author Yeumen Silva
     */

    public ASTNode(Token token, String struct, String method){
        this.token = token;
        this.struct = struct;
        this.method = method;
    }

    /**
     * Constructor que asigna token, struct y método
     * @param token token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     * @param nodeName nombre del nodo
     * @author Yeumen Silva
     */


    public ASTNode(Token token, String struct, String method, String nodeName){
        this.token = token;
        this.struct = struct;
        this.method = method;
        this.nodeName = nodeName;
    }

    /**
     * Método para setear el tipo
     * @param type string con tipo
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Método que devuelve el token
     * @return Token
     */
    public Token getToken() {
        return token;
    }

    /**
     * Método que devuelve el type
     * @return String con tipo
     */

    public String getType() {
        return type;
    }
}
