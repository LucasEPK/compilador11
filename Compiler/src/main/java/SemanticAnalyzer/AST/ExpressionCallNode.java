package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public class ExpressionCallNode extends AbstractCallNode{


    AbstractExpressionNode  expressionNode;
    public ExpressionCallNode(Token token, String struct, String method, String name) {
        super(token, struct, method, name);
    }

    public ExpressionCallNode(Token token, String struct, String method, AbstractExpressionNode expressionNode) {
        super(token, struct, method, "ExpressionCallNode");
        this.expressionNode = expressionNode;
        this.setType(expressionNode.getType());
    }
}
