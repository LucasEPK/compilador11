package SemanticAnalyzer.AST;


import Exceptions.SemanticExceptions.AST.TypesDontMatch;
import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Clase representate una expresión unaria en nuestro AST
 * @author Yeumen Silva
 */

public class ExpUn extends ExpOp {

    ExpressionNode right;

    public ExpUn(String struct, String method) {
        super(struct, method);
    }

    public ExpUn(String struct, String method, Token operator) {
        super(struct, method, operator);
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }

    public ExpressionNode getRight() {
        return right;
    }

    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */
    @Override
    public String toJson(int tabs) {

            String json = addtabs(tabs) + "{\n";
            json += addtabs(tabs+1) + "\"nombre\": \"" + "ExpUn" + "\",\n";
            json += addtabs(tabs+1) + "\"operator\": \"" + getOperator().getLexeme() + "\",\n";
            json += addtabs(tabs+1) + "\"right\": " + right.toJson(tabs+1) + "\n";
            json += addtabs(tabs) + "}\n";
            return json;
    }

    /**
     * Método que consolida un nodo ExpUn
     * @param ast AST que contiene la información
     * @autor Yeumen Silva
     */

    @Override
    public void consolidate(AST ast) {
        if(right.getConsolidated()  == false){
            right.consolidate(ast);
        }
        switch (getOperator().getLexeme()) {
            case "-", "+", "--", "++":
                //Si el tipo del dato es int, se mantiene el tipo
                if(right.getType().equals("Int"))
                    setType(right.getType());
                else
                    //Sino tiro error ToDo
                    //throw new TypesDontMatch(this.getToken());
                break;
            case "!":
                if(right.getType().equals("Bool"))
                    setType(right.getType());
                else
                    //Sino tiro error ToDo
                    //throw new TypesDontMatch(this.getToken());
                break;
            default:
                setType("nil");
                break;
        }
        this.setConsolidated(true);
    }

    /**
     * Método que agrega una cantidad de tabulaciones a un string
     * @param tabs cantidad de tabulaciones a agregar
     * @return string con las tabulaciones agregadas
     * @autor Yeumen Silva
     */

    @Override
    public String addtabs(int tabs) {
        String tabsString = "";
        for (int i = 0; i < tabs; i++) {
            tabsString += "\t";
        }
        return tabsString;
    }
}
