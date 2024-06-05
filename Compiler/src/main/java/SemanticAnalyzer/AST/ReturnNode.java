package SemanticAnalyzer.AST;


import Exceptions.SemanticExceptions.AST.StructNotFound;
import SemanticAnalyzer.SymbolTable.Struct;
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
            //imprime type
            json += addtabs(tabs+1) + "\"type\": \"" + this.getType() + "\",\n";
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

        //Si no tiene return, ya esta consolidado
        if(returnValueNode == null){
            this.setConsolidated(true);
        }else {
            //Si no esta consolidado, lo consolido
            if(returnValueNode.getConsolidated() == false){
                returnValueNode.consolidate(ast);
            }


            this.setType(returnValueNode.getType());
            //Seteo que es un array si lo es
            this.setIsArray(returnValueNode.getIsArray());

            if(this.getType().equals("nil") == false){
                Struct actualStruct = ast.getSymbolTable().getStructs().get(this.getType());
                if(actualStruct == null){
                    throw  new StructNotFound(this.getReferenceToken());
                }
            }


            this.setConsolidated(true);
        }

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

    @Override
    public String generateCode() {
        String textCode = "";
        return textCode;
    }
}
