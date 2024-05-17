package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase abstracta que encapsula Arrays y Ids en el AST
 * @author Yeumen Silva
 */

public abstract class PrimaryNode extends Operands {

    PrimaryNode right;


    public PrimaryNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    public PrimaryNode(String struct, String method, Token token, String type) {
        super(struct, method, token, type);
    }

    public PrimaryNode getRight() {
        return right;
    }

    public void setRight(PrimaryNode right) {
        this.right = right;
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
