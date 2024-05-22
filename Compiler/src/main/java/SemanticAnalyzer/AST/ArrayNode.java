package SemanticAnalyzer.AST;

import Exceptions.SemanticExceptions.AST.ArrayLengthException;
import Exceptions.SemanticExceptions.AST.NoPrimitiveType;
import Exceptions.SemanticExceptions.AST.VariableNotFound;
import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Variable;

public class ArrayNode extends PrimaryNode{

    private boolean isConstructor = false;

    private ExpressionNode length;

    public ArrayNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    public ArrayNode(String struct, String method, Token token, String type) {
        super(struct, method, token, type);
    }

    public ExpressionNode getLength() {
        return length;
    }

    public void setLength(ExpressionNode length) {
        this.length = length;
    }

    public void setIsConstructor(boolean isConstructor){
        this.isConstructor = isConstructor;
    }

    public boolean getIsConstructor(){
        return this.isConstructor;
    }

    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */
    @Override
    public String toJson(int tabs) {

        String json = addtabs(tabs) + "{\n";
        json += addtabs(tabs+1) + "\"nombre\": \"" + "Array" + "\",\n";
        //Si el tipo es string y tiene comillas no le agrego comillas
        if(isStringType(getType())) {
            json += addtabs(tabs + 1) + "\"value\": " + getToken().getLexeme() + ",\n";
        }else {
            json += addtabs(tabs + 1) + "\"value\": \"" + getToken().getLexeme() + "\",\n";
        }

        json += addtabs(tabs+1) + "\"type\": \"" + getType() + "\",\n";
        json += addtabs(tabs+1) + "\"length\":" + length.toJson(tabs+1) + "\n";
        json += addtabs(tabs) + "}\n";
        return json;
    }

    @Override
    public void consolidate(AST ast) {

        if(this.length.getConsolidated() == false){
            this.length.consolidate(ast);
        }

        if(this.length.getType().equals("Int") == false){
            throw new ArrayLengthException(this.length.getToken());
        }

        if(this.getIsConstructor()){
            //Si el tipo no es un tipo primitivo, es un error (Int,Str,Char,Bool)
            if(this.getToken().getLexeme().equals("Int") == false && this.getToken().getLexeme().equals("Str") == false &&
                    this.getToken().getLexeme().equals("Char") == false && this.getToken().getLexeme().equals("Bool") == false){

                throw new NoPrimitiveType(this.getToken());
            }

            this.setType(this.getToken().getLexeme());


        }else {
            //Busco la variable
            Variable var;
            if(lastCalledType == null){
                var = ast.findVariable(this.struct,this.method,this.token);
            }else {
                var = ast.findVariableSelf(lastCalledType,this.token);
            }

            // Si no encuentro la varaible, es error
            if(var == null){
                throw  new VariableNotFound(this.token);
            }
            //Seteo el tipo del Array
            this.setType(var.getType().getName());



        }

        //Seteo el booleano de isArray
        this.setIsArray(true);

        this.setConsolidated(true);

        //Seteo lastCalledType
        if(right != null){
            right.setLastCalledType(this.getType());
            right.setLastCalledIdType(IdType.ARRAY);
        }



    }


    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */

    @Override
    public String addtabs(int tabs) {
        String tabsString = "";
        for (int i = 0; i < tabs; i++
        ) {
            tabsString += "\t";
        }
        return tabsString;
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
