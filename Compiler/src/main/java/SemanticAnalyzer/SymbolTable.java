package SemanticAnalyzer;

import LexicalAnalyzer.Token;

import java.lang.reflect.Method;
import java.util.Set;

public class SymbolTable {

    private Set<Struct> structs;

    private Struct currentStruct;

    private Methods currentMethod;

    public SymbolTable(){
        addObject();
    }

    /**
     * Agrega Clase predefinida Object, la cual solo tiene un nombre
     * @author Yeumen Silva
     */

    private void addObject(){
        Struct object = new Struct("Object");

    }

    private void addIO(){

        Struct io = new Struct("IO");
        Methods method = new Methods();

    }
}
