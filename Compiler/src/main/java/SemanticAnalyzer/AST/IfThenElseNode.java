package SemanticAnalyzer.AST;


import Exceptions.SemanticExceptions.AST.ConditionNoIsBool;
import Exceptions.SemanticExceptions.AST.MultipleReturnType;
import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Clase representate una sentencia if-else en nustro AST
 * @author Yeumen Silva
 */

public class IfThenElseNode extends  SentenceNode implements Commons {

    ExpressionNode ifNode;

    SentenceNode thenNode;

    SentenceNode elseNode;


    public IfThenElseNode(String struct, String method) {
        super(struct, method);
    }

    public void setIfNode(ExpressionNode ifNode) {
        this.ifNode = ifNode;
    }

    public void setThenNode(SentenceNode thenNode) {
        this.thenNode = thenNode;
    }

    public void setElseNode(SentenceNode elseNode) {
        this.elseNode = elseNode;
    }

    public ExpressionNode getIfNode() {
        return ifNode;
    }

    public SentenceNode getThenNode() {
        return thenNode;
    }

    public SentenceNode getElseNode() {
        return elseNode;
    }


    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     * @autor Yeumen Silva
     */

    @Override
    public String toJson(int tabs) {

            String json = addtabs(tabs) + "{\n";
            json += addtabs(tabs+1) + "\"nombre\": \"" + "IfThenElse" + "\",\n";
            json += addtabs(tabs+1) + "\"Condición\": " + ifNode.toJson(tabs+1) + ",\n";
            json += addtabs(tabs+1) + "\"then\": " + thenNode.toJson(tabs+1) + ",\n";
            if(elseNode != null){
                json += addtabs(tabs+1) + "\"else\": " + elseNode.toJson(tabs+1) + "\n";
            }
            json += addtabs(tabs) + "}\n";
            return json;

    }

    /**
     * Método que consolida un nodo if-else
     * @param ast AST que se va a consolidar
     * @return void
     * @autor Yeumen Silva
     */

    @Override
    public void consolidate(AST ast) {

        if(this.ifNode.getConsolidated() == false){
            this.ifNode.consolidate(ast);
        }
        if(this.thenNode.getConsolidated() == false){
            this.thenNode.consolidate(ast);
        }
        if(this.ifNode.getType().equals("Bool") == false){
            throw new ConditionNoIsBool(this.ifNode.getToken());
        }
        this.setType(thenNode.getType());
        this.setConsolidated(true);

        if(this.elseNode != null){
            if(this.elseNode.getConsolidated() == false){
                this.elseNode.consolidate(ast);
            }
            //Si los tipos de else y de then son diferentes
            if(!this.elseNode.getType().equals(this.thenNode.getType())){
                throw new MultipleReturnType(this.getReferenceToken());
            }

            //Si los tipos de else y de then no son void
            if(!this.elseNode.getType().equals("void") && !this.thenNode.getType().equals("void")){
                //Si los tipos de else y de then son iguales
                if(this.thenNode.getType().equals(this.thenNode.getType())){
                    //Seteo que el tipo del nodo if es el mismo que el tipo del nodo then
                    this.setType(thenNode.getType());
                }else {
                    throw new MultipleReturnType(this.thenNode.getReferenceToken());
                }

            }else if(!this.thenNode.getType().equals("void")){
                this.setType(this.thenNode.getType());
            }else if(!this.elseNode.getType().equals("void")){
                this.setType(this.elseNode.getType());
            }else {
                setType(this.thenNode.getType());
            }
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
}
