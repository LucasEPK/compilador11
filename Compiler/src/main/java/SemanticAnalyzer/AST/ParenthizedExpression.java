package SemanticAnalyzer.AST;

import CodeGeneration.CodeGenerator;
import LexicalAnalyzer.Token;

public class ParenthizedExpression extends PrimaryNode{
    ExpressionNode parenthizedExpression;

    public ParenthizedExpression(String struct, String method, Token token){
        super(struct, method, token);
    }

    public void setParenthizedExpression(ExpressionNode parenthizedExpression) {
        this.parenthizedExpression = parenthizedExpression;
    }

    public ExpressionNode getParenthizedExpression() {
        return parenthizedExpression;
    }

    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */

    public String toJson(int tabs) {

        String json = addtabs(tabs) + "{\n";
        json += addtabs(tabs+1) + "\"nombre\": \"" + "ParenthizedExpression" + "\",\n";

        //Si el tipo es string y tiene comillas no le agrego comillas
        if(isStringType(getType())) {
            json += addtabs(tabs + 1) + "\"value\": " + getToken().getLexeme() + ",\n";
        }
        else {
            json += addtabs(tabs+1) + "\"value\": \"" + getToken().getLexeme() + "\",\n";
        }

        json += addtabs(tabs+1) + "\"type\": \"" + getType() + "\",\n";
        json += addtabs(tabs+1) + "\"parenthizedExpression\": " + parenthizedExpression.toJson(tabs+1) + "\n";
        json += addtabs(tabs) + "}";
        return json;

    }

    /**
     * Método que agrega tabulaciones al archivo JSON
     * @param tabs Cantidad de tabulaciones a agregar
     * @return String con las tabulaciones agregadas
     */

    @Override
    public String addtabs(int tabs) {
        String s = "";
        for (int i = 0; i < tabs; i++) {
            s += "\t";
        }
        return s;
    }

    @Override
    public void consolidate(AST ast) {
        if(this.parenthizedExpression.getConsolidated() == false){
            this.parenthizedExpression.consolidate(ast);
        }
        setType(this.parenthizedExpression.getType());
        setConsolidated(true);
    }

    @Override
    public String generateCode(CodeGenerator codeGenerator) {
        String textCode = "";
        return textCode;
    }

    /**
     * Método que verifica si el tipo es de tipo String y si empieza y termina con comillas
     * @param type Tipo a verificar
     * @return true si es de tipo String y empieza y termina con comillas, false en caso contrario
     */
    public boolean isStringType(String type){
        //Verifico si es de tipo String y si empieza y termina con comillas
        if(type.equals("Str") && getToken().getLexeme().charAt(0) == '"' && getToken().getLexeme().charAt(getToken().getLexeme().length()-1) == '"'){
            return true;
        }
        return false;
    }
}
