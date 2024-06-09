package SemanticAnalyzer.AST;

import CodeGeneration.CodeGenerator;
import Exceptions.SemanticExceptions.AST.NoBooleanCondition;
import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Clase representate una sentencia while en nustro AST
 * @author Yeumen Silva
 */

public class WhileNode extends SentenceNode {

    ExpressionNode whileNode;

    SentenceNode doNode;


    public WhileNode(String struct, String method) {
        super(struct, method);
    }

    public void setWhileNode(ExpressionNode whileNode) {
        this.whileNode = whileNode;
    }

    public void setDoNode(SentenceNode doNode) {
        this.doNode = doNode;
    }

    public ExpressionNode getWhileNode() {
        return whileNode;
    }

    public SentenceNode getDoNode() {
        return doNode;
    }

    @Override
    public String toJson(int tabs) {

        String json = addtabs(tabs) + "{\n";
        json += addtabs(tabs+1) + "\"nombre\": \"" + "While" + "\",\n";
        json += addtabs(tabs+1) + "\"Condición\": " + whileNode.toJson(tabs+1) + ",\n";
        json += addtabs(tabs+1) + "\"do\": " + doNode.toJson(tabs+1) + "\n";
        json += addtabs(tabs) + "}\n";
        return json;

    }

    /**
     * Método que consolida un nodo while
     * @param ast AST que contiene la información
     * @autor Yeumen Silva
     */

    @Override
    public void consolidate(AST ast) {

        if(whileNode.getConsolidated() == false){
            whileNode.consolidate(ast);
        }
        if(doNode.getConsolidated() == false){
            doNode.consolidate(ast);
        }
        if(this.whileNode.getType().equals("Bool") == false){
            throw new NoBooleanCondition(this.whileNode.getToken());
        }

        this.setType(this.doNode.getType());
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

    /**
     * Genera codigo mips para el while
     * @return codigo mips del while
     * @author Lucas Moyano
     * */
    @Override
    public String generateCode(CodeGenerator codeGenerator) {
        Token conditionToken = whileNode.getToken();
        String textCode = "\tdefault_while_l" + conditionToken.getRow() + "c" + conditionToken.getColumn()  + ":\n" +
                "\t# Condición while\n";
        textCode += whileNode.generateCode(codeGenerator) +
                "\t# Fin condición while\n";
        textCode += "\tbne $v0, 1, default_while_fin_l" + conditionToken.getRow() + "c" + conditionToken.getColumn()  + "\t#si la condición del while no es verdadera salta todo el while\n" +
                "\t# Cuerpo while\n";
        textCode += doNode.generateCode(codeGenerator) +
                "\t# Fin cuerpo while\n";
        textCode += "\tj default_while_l" + conditionToken.getRow() + "c" + conditionToken.getColumn()  + "\t# vuelve al pricipio del while a chequear la condición\n" +
                "\tdefault_while_fin_l" + conditionToken.getRow() + "c" + conditionToken.getColumn()  + ":\n";

        return textCode;
    }
}
