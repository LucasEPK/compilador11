package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Clase abstracta que encapsula Arrays y Ids en el AST
 * @author Yeumen Silva
 */

public abstract class PrimaryNode extends Operands {

    PrimaryNode right = null;

    String lastCalledType;

    private IdType lastCalledIdType;

    public PrimaryNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    public PrimaryNode(String struct, String method, Token token, String type) {
        super(struct, method, token, type);
    }

    public PrimaryNode getRight() {
        return right;
    }

    public void setLastCalledIdType(IdType lastCalledIdType) {
        this.lastCalledIdType = lastCalledIdType;
    }

    public IdType getLastCalledIdType() {
        return lastCalledIdType;
    }

    public void setRight(PrimaryNode right) {
        this.right = right;
    }

    public void setLastCalledType(String lastCalledType) {
        this.lastCalledType = lastCalledType;
    }

    public String getLastCalledType() {
        return lastCalledType;
    }

    /**
     * Función que pone como ultimo right de la fila de rights
     * el parametro dado
     * @author Lucas Moyano
     * */
    public void setLastRight(PrimaryNode right) {
        if (this.right != null) {// caso recursivo
            this.right.setLastRight(right);
        } else { // caso base, actua como un setRight normal
            this.right = right;
        }
    }

    @Override
    public String toJson(int tabs) {
        return null;
    }

    @Override
    public void consolidate(AST ast) {

        if(right != null){
            if(right.getConsolidated() == false){
                right.consolidate(ast);
            }
            this.setType(right.getType());
            this.setConsolidated(true);

        }


    }
}
