package SemanticAnalyzer.AST;


import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Clase representate un return en nuestro AST
 * @author Yeumen Silva
 */

public class ReturnNode extends SentenceNode implements Commons {

    ExpressionNode returnValueNode = null;


    public ReturnNode(String struct, String method) {
        super(struct, method);
    }

    public void setReturnValueNode(ExpressionNode returnValueNode) {
        this.returnValueNode = returnValueNode;
    }

    public ExpressionNode getReturnValueNode() {
        return returnValueNode;
    }

    @Override
    public String toJson(int tabs) {

            String json = addtabs(tabs) + "{\n";
            json += addtabs(tabs+1) + "\"nombre\": \"" + "Return" + "\",\n";
            json += addtabs(tabs+1) + "\"class\": \"" + getStruct() + "\",\n";
            json += addtabs(tabs+1) + "\"method\": \"" + getMethod() + "\",\n";
            if (returnValueNode != null) {
                json += addtabs(tabs+1) + "\"return\": \n " + returnValueNode.toJson(tabs+1) + "\n";
            } else {
                json += addtabs(tabs+1) + "\"return\": " + "nil" + "\n";
            }
            json += addtabs(tabs) + "}\n";
            return json;

    }

    @Override
    public void consolidate(AST ast) {

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
