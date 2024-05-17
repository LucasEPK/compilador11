package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

import java.util.ArrayList;
import java.util.List;

public class IdNode extends PrimaryNode{
    private List<ExpressionNode> arguments = new ArrayList<ExpressionNode>();

    public IdNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }


}
