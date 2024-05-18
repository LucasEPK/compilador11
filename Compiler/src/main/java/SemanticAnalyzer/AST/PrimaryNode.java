package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.SymbolTable;

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
    public String toJson(int tabs) {
        return null;
    }

    @Override
    public void consolidate(AST ast) {


        if(right.getConsolidated() == false){
            right.consolidate(ast);
        }
        this.setType(right.getType());
        this.setConsolidated(true);

    }
}
