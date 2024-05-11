package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el AST, va a tener como atributo una lista de bloques
 * @author Lucas Moyano
 * */
public class AST {

    private List<BlockNode> blockList = new ArrayList<BlockNode>();

    /**
     * Agrega un nuevo bloque a la lista
     * @param symbolTable Es la tabla de simbolos
     * @return El nuevo bloque añadido a la lista
     * @author Lucas Moyano
     * */
    public BlockNode addNewBlock(SymbolTable symbolTable) {
        BlockNode newBlock = new BlockNode(symbolTable);
        blockList.add(newBlock);

        return  newBlock;
    }

    public List<BlockNode> getBlockList() {
        return blockList;
    }
}
