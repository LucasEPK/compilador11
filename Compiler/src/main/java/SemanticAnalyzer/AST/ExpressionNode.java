package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase abstracta representate una expresión en general
 * @author Yeumen Silva
 */

public abstract class ExpressionNode extends  SentenceNode implements Commons {

    private Token tokenOrOperator;
    private String type;
    private String lexeme;

    public ExpressionNode(String struct, String method) {
        super(struct, method);
    }

    public ExpressionNode(String struct, String method, Token token) {
        super(struct, method);
        this.tokenOrOperator = token;
        this.lexeme = token.getLexeme();
    }

    public ExpressionNode(String struct, String method, Token token, String type) {
        super(struct, method);
        this.tokenOrOperator = token;
        this.lexeme = token.getLexeme();
        this.type = type;
    }

    public Token getTokenOrOperator() {
        return tokenOrOperator;
    }

    public String getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTokenOrOperator(Token tokenOrOperator) {
        this.tokenOrOperator = tokenOrOperator;
        this.lexeme = tokenOrOperator.getLexeme();
    }
}
