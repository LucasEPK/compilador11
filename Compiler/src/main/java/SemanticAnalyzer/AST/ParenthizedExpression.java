package SemanticAnalyzer.AST;

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

    @Override
    public String addtabs(int tabs) {
        return "";
    }
}
