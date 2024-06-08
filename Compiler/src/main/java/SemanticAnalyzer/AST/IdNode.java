package SemanticAnalyzer.AST;

import CodeGeneration.CodeGenerator;
import Exceptions.SemanticExceptions.AST.*;
import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.Methods;
import SemanticAnalyzer.SymbolTable.Struct;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa los id y structsIds en el AST
 * @author Lucas Moyano
 * */
public class
IdNode extends PrimaryNode{
    private List<ExpressionNode> arguments = new ArrayList<>();

    private IdType idType;


    public IdNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public void setArguments(List<ExpressionNode> arguments) {
        this.arguments = arguments;
    }


    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */
    @Override
    public String toJson(int tabs) {
        String json = addtabs(tabs) + "{\n";
        json += addtabs(tabs+1) + "\"nombre\": \"" + "Id" + "\",\n";

        //Si el tipo es string y tiene comillas no le agrego comillas
        if(isStringType(getType())) {
            json += addtabs(tabs + 1) + "\"value\": " + getToken().getLexeme() + ",\n";
        }
        else{
            json += addtabs(tabs+1) + "\"value\": \"" + getToken().getLexeme() + "\",\n";
        }

        json += addtabs(tabs+1) + "\"type\": \"" + getType() + "\",\n";
        //json += addtabs(tabs+1) + "\"idType\": \"" + idType + "\",\n";
        if(right != null){
            json += addtabs(tabs+1) + "\"chained\": " + right.toJson(tabs+1) + ",\n";
        }
        json += addtabs(tabs+1) + "\"arguments\": [\n";
        int size = 0;
        if (arguments != null) {
            for (ExpressionNode argument : arguments) {
                json += argument.toJson(tabs+2);
                if(size < arguments.size()-1){
                    json += ",\n";
                }
                size++;
            }
        }
        json = json.substring(0,json.length()-1);
        json += "\n";
        json += addtabs(tabs+1) + "]\n";
        json += addtabs(tabs) + "}\n";
        return json;
    }

    /**
     * Método que consolida un nodo Id
     * @param ast AST que contiene la información
     */

    @Override
    public void consolidate(AST ast) {


        if(this.idType == IdType.SELF){
            consolidateSelf(ast);
        }
        else if(this.idType == IdType.CONSTRUCTOR){
            consolidateConstructor(ast);
        } else if (this.idType == IdType.METHOD) {
            consolidateMethod(ast);
        } else if (this.idType == IdType.VARIABLE) {
            consolidateVar(ast);
        } else if (this.idType == IdType.STATIC_METHOD) {
            consolidateEstaticMethod(ast);
        }

        if(right != null){
            right.consolidate(ast);
            this.setType(right.getType());
            this.setIsArray(right.getIsArray());
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
        for (int i = 0; i < tabs; i++) {
            tabsString += "\t";
        }
        return tabsString;
    }

    /**
     * Acá generamos codigo mips para funciones y variables
     * @return codigo generado para la funcion/variable
     * @author Lucas Moyano
     * */
    @Override
    public String generateCode(CodeGenerator codeGenerator) {
        String textCode = "";
        SymbolTable symbolTable = codeGenerator.getSymbolTable();
        Methods currentMethod = symbolTable.getStructMethod(this.getStruct(), this.getMethod()); // TODO: cuando sea encadenado acá habría que pasarle el verdadero struct y method, como está ahora no funca probablemente

        if (idType == IdType.METHOD || idType == IdType.STATIC_METHOD || idType == IdType.CONSTRUCTOR) {
            // TODO: agregar parametros al stack
            // TODO: agregar puntero a self
            // TODO: Acá debería estar el codigo de la función
            // Acá desapilamos todo el RA formado por esta función
            int totalParams = currentMethod.getParamsOfMethod().size();
            int totalVariables = currentMethod.getDefinedVar().size();
            textCode += "\t# Desapilamos todo el RA de la función llamada\n";
            // Pop del valor de retorno y guardado en $v0
            textCode += "\tpop\t# Pop del valor de retorno\n"+
                    "\tla $v0, ($t9)\n";
            for(int i=0; i<totalVariables; i++) { // Pop de las variables
                textCode += "\tpop\t# Pop de variable "+i+"\n";
            }
            // Pop del puntero de retorno de la función llamada
            textCode += "\tpop\t# Pop de puntero de retorno $ra de la función llamada\n";

            // Pop del framepointer que perdimos y reestableciemiento del framepointer
            textCode += "\tpop\t# Pop del framepointer anterior que perdimos\n" +
                    "\tadd $fp, $zero, $t9\t# Volvemos a cargar el framepointer correcto\n";

            // Pop del puntero al objeto
            textCode += "\tpop\t# Pop de puntero al objeto\n";

            for(int i=0; i<totalParams; i++) { // Pop de los parametros
                textCode += "\tpop\t# Pop de parametro "+i+"\n";
            }
            textCode += "\t# FIN desapilado de todo el RA de la función llamada\n";
        }
        return textCode;
    }

    /**
     * Método que consolida un nodo Id de tipo self
     * @param ast AST que contiene la información
     *
     */

    private void consolidateSelf(AST ast){
        SymbolTable symbolTable = ast.getSymbolTable();
        //Si llamo a self desde start, entonces es un error

        if(this.getStruct().equals("start")){
            throw  new SelfInStart(this.getToken());
        }



        if(right != null){
            //Seteo el ultimo tipo llamado en right
            right.setLastCalledType(this.getStruct());
            right.setLastCalledIdType(IdType.SELF);
        }

        //Seteo que el tipo de Self es el struct actual
        this.setType(this.getStruct());
        //Seteo que esta consolidado
        this.setConsolidated(true);

    }

    /**
     * Método que consolida un nodo Id de tipo constructor
     * @param ast AST que contiene la información
     *
     */

    private void consolidateConstructor(AST ast){
        //Verifico si recibe o no parametros
        if(this.arguments != null){
            //Consolido los argumentos
            for(ExpressionNode argument : this.arguments){
                if(argument.getConsolidated() == false){
                    argument.consolidate(ast);
                }
            }
        }
        //Verifico que la clase del constructor exista
        Struct actualStruct = ast.searchStruct(this.getToken().getLexeme());
        if(actualStruct == null){
            throw new StructNotFound(this.getToken());
        }



        if(this.arguments != null){
            //Chequeo que se pasen los parámetros correctos
            //Paso argumentos del constructor definido
            // Paso nombre del struct
            // Paso nombre del Método (en este caso el consructor)
            //TODO chequear si es array
            ast.checkParametersConstructor(this.arguments,
                    actualStruct.getName(),
                    this.getToken()
            );
        }else if (actualStruct.getConstructor().getParamsOfMethod().size() > 0){
            throw new InvalidParamSize(this.getToken());
        }


        if(right != null){
            //Seteo el ultimo tipo llamado en right
            right.setLastCalledType(actualStruct.getName());
            right.setLastCalledIdType(IdType.CONSTRUCTOR);
        }
        //El tipo del constructor siempre es la clase a la que pertenece
        this.setType(actualStruct.getName());
        //Seteo que esta consolidado
        this.setConsolidated(true);

    }

    /**
     * Método que consolida un nodo Id de tipo método
     * @param ast AST que contiene la información
     */

    private void consolidateMethod(AST ast){

        //TODO chequear si es array
        //Verifico si recibe o no parametros
        if(this.arguments != null){
            //Consolido los argumentos
            for(ExpressionNode argument : this.arguments){
                if(argument.getConsolidated() == false){
                    argument.consolidate(ast);
                }
            }
        }




        if(this.getLastCalledType() == null){
            //Verifico que el método exista en la misma clase
            //Como existe en la misma clase, busco el método en la clase actual
            //Si estoy en la clase start, es un error
            if(this.getStruct().equals("start")){
                throw new MethodInStart(this.getToken());
            }
            Methods actualMethod = ast.searchMethod(this.getStruct(),this.getToken().getLexeme(),this.getToken());
            if(actualMethod == null){
                throw new MethodNotFound(this.getToken());
            }
            //Chequeo que se pasen los parámetros correctos
            if(this.arguments != null){
                //Paso argumentos del método definido
                // Paso nombre del struct
                // Paso nombre del Método
                ast.checkParametersMethod(this.arguments,
                        this.getStruct(),
                        this.getToken().getLexeme(),
                        this.getToken()
                );

            } else if (actualMethod.getParamsOfMethod().size() > 0) {
                throw new InvalidParamSize(this.getToken());

            }
            //Seteo el tipo del método
            this.setType(actualMethod.getGiveBack().getName());
            if(right != null){
                //Seteo el ultimo tipo llamado en right
                right.setLastCalledType(actualMethod.getGiveBack().getName());
                right.setLastCalledIdType(IdType.METHOD);
            }
            //Seteo que esta consolidado
            this.setConsolidated(true);
        }else {
            //Busco el método en la clase que la llamo

            Methods actualMethod = ast.searchMethod(this.lastCalledType,this.getToken().getLexeme(), this.getToken());


            if(actualMethod == null){
                throw new MethodNotFound(this.getToken());
            }
            if(!actualMethod.getIsStatic() && this.getLastCalledIdType()==IdType.STATIC_METHOD){
                throw new NotEstaticMethod(this.getToken());
            }
            //Chequeo que se pasen los parámetros correctos
            if(this.arguments != null){
                //Paso argumentos del método definido
                // Paso nombre del struct
                // Paso nombre del Método
                ast.checkParametersMethod(this.arguments,
                        this.lastCalledType,
                        this.getToken().getLexeme(),
                        this.getToken()
                );
            } else if (actualMethod.getParamsOfMethod().size() > 0) {
                throw new InvalidParamSize(this.getToken());
            }


            //Seteo el tipo del método
            this.setType(actualMethod.getGiveBack().getName());
            if(right != null){
                //Seteo el ultimo tipo llamado en right
                right.setLastCalledType(actualMethod.getGiveBack().getName());
                //Seteo el idType de right
                right.setLastCalledIdType(IdType.METHOD);
            }
            //Seteo que esta consolidado
            this.setConsolidated(true);
        }

    }

    /**
     * Método que consolida un nodo Id de tipo Variable
     * @param ast
     */

    private void consolidateVar(AST ast){

        Variable varFound = null;
        if(lastCalledType != null){
            switch (this.getLastCalledIdType()){
                case SELF:
                    varFound = ast.findVariableSelf(lastCalledType,struct,this.getToken());
                    break;
                case VARIABLE:
                    varFound = ast.findVariableSelf(this.lastCalledType,struct,this.getToken());
                    break;
                case METHOD:
                    varFound = ast.findVariableSelf(this.lastCalledType,struct,this.getToken());
                    break;
                case STATIC_METHOD:
                    varFound = ast.findVariableSelf(this.lastCalledType,struct,this.getToken());
                    break;
                case CONSTRUCTOR:
                    varFound = ast.findVariableSelf(lastCalledType,struct,this.getToken());
                    if(varFound == null){
                        throw new VariableNotFound(this.getToken());
                    }
                    break;
                case ARRAY:
                    throw new TypesDontMatch(this.getToken());
            }
            //si es estático, debo verificar que el método sea estático
            if(this.getLastCalledIdType() == IdType.STATIC_METHOD){
                Methods method = ast.searchMethod(this.lastCalledType,this.getMethod(),this.getToken());
                if(method.getIsStatic()){
                    throw new NotEstaticMethod(this.getToken());
                }
            }
        }else {
            //Llamada a variable en la misma clase
            varFound = ast.findVariable(this.getStruct(),this.getMethod(),this.getToken());
            if(varFound == null){
                throw new VariableNotFound(this.getToken());
            }

            //Si la variable es un array, entonces seteo que el tipo es un array
            if(varFound.getIsArray()){
                this.setIsArray(true);
            }

        }


        //Seteo el tipo de la variable
        this.setType(varFound.getType().getName());
        this.setConsolidated(true);
        if(right != null){
            //Seteo el ultimo tipo llamado en right
            right.setLastCalledType(varFound.getType().getName());
            right.setLastCalledIdType(IdType.VARIABLE);
        }


    }

    private void consolidateEstaticMethod(AST ast){

        Methods foundMethod;

        foundMethod = ast.searchMethod(this.getToken().getLexeme(),this.right.getToken().getLexeme(), this.getToken());


        Struct actualStruct = ast.searchStruct(this.getToken().getLexeme());
        if(actualStruct == null){
            throw new StructNotFound(this.getToken());
        }

        if(right != null){
            //Seteo el ultimo tipo llamado en right
            right.setLastCalledType(actualStruct.getName());
            right.setLastCalledIdType(IdType.STATIC_METHOD);
        }

        this.setType(actualStruct.getName());
        this.setConsolidated(true);


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
