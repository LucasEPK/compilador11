package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public class ParenthizedExpression extends PrimaryNode{
    ExpressionNode parenthizedExpression;

    public ParenthizedExpression(String struct, String method, Token token){
        super(struct, method, token);
    }

    @Override
    public String addtabs(int tabs) {
        return "";
    }
}
