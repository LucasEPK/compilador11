package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el AST, va a tener como atributo una lista de bloques
 * @author Lucas Moyano
 * */
public class AST implements Commons {

    private List<BlockNode> blockList = new ArrayList<BlockNode>();

    private SymbolTable symbolTable;

    public AST(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    /*
    /**
     * Agrega un nuevo bloque a la lista
     * @return El nuevo bloque añadido a la lista
     * @author Lucas Moyano
     *
    public BlockNode addNewBlock() {
        BlockNode newBlock = new BlockNode(symbolTable);
        blockList.add(newBlock);

        return  newBlock;
    }
    */

    public List<BlockNode> getBlockList() {
        return blockList;
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
