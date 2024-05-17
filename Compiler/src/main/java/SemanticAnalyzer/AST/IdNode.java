package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa los id y structsIds en el AST
 * @author Lucas Moyano
 * */
public class IdNode extends PrimaryNode{
    private List<ExpressionNode> arguments = new ArrayList<ExpressionNode>();

    public IdNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }


}
