package SemanticAnalyzer.AST;

import CodeGeneration.CodeGenerator;
import Exceptions.SemanticExceptions.AST.*;
import SemanticAnalyzer.SymbolTable.*;
import LexicalAnalyzer.Token;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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


    /**
     * Agrega un nuevo bloque a la lista
     * @return El nuevo bloque añadido a la lista
     * @author Lucas Moyano
     */
    public BlockNode addNewBlock() {
        BlockNode newBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        blockList.add(newBlock);

        return  newBlock;
    }

    public List<BlockNode> getBlockList() {
        return blockList;
    }


    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    @Override
    public String toJson(int tabs) {
        return null;
    }

    /**
     * Método que recorre la lista de bloques y llama al método toJson de cada uno
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return void
     * @autor Yeumen Silva
     * */
    public String toJson(int tabs,String inputPath) {
        // Añade clases predefinidas al AST sin consolidarlas
        addPredefinedStructsToAST();

        String json = "{\n";
        json += addtabs(tabs) + "\"nombre\": \"" + inputPath + "\",\n";
        json += addtabs(tabs) + "\"clases\": [\n";

        //Recorro todos los bloques del AST
        int size = 0;
        for(BlockNode block : blockList) {
            json += block.toJson(tabs+1);

            if(size < blockList.size()-1) {
                json += ",\n";
            }
            size++;
        }
        json += "\n";
        json += addtabs(tabs) + "]\n";
        json += "}\n";

        //Llamo a funcion para gaurdar json como archivo
        saveJson(json,inputPath);

        return json;
    }

    private void addPredefinedStructsToAST(){
        BlockNode predefinedBlock;

        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("Object");
        predefinedBlock.setMethod(".");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("Int");
        predefinedBlock.setMethod(".");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("Str");
        predefinedBlock.setMethod("concat");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("Str");
        predefinedBlock.setMethod("length");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("Str");
        predefinedBlock.setMethod(".");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("Char");
        predefinedBlock.setMethod(".");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("Bool");
        predefinedBlock.setMethod(".");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("Array");
        predefinedBlock.setMethod("length");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("Array");
        predefinedBlock.setMethod(".");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("out_str");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("out_int");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("out_bool");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("out_char");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("out_array_int");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("out_array_str");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("out_array_bool");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("out_array_char");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("in_str");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("in_int");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("in_bool");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod("in_char");
        blockList.addFirst(predefinedBlock);
        predefinedBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        predefinedBlock.setStruct("IO");
        predefinedBlock.setMethod(".");
        blockList.addFirst(predefinedBlock);

    }

    /**
     * Método que recorre la lista de bloques y llama al método consolidate de cada uno
     * @param ast AST a consolidar
     * @return void
     * @autor Yeumen Silva
     */

    @Override
    public void consolidate(AST ast ) {
        //Recorro todos los bloques del AST
        for(BlockNode block : blockList) {
            block.consolidate(ast);
        }

    }

    /**
     * Método que dada una cantida de tabs, devuelve un string
     * @param tabs int que representa cantidad de tabs
     * @return string con tabs
     * @autor Yeumen Silva
     */

    @Override
    public String addtabs(int tabs){
        String tabString = "";
        for (int i = 0; i < tabs; i++) {
            tabString+="\t";
        }
        return  tabString;
    }

    /**
     * Función que recorre todos los bloques del AST y genera codigo
     * @author Lucas Moyano
     * */
    @Override
    public String generateCode(CodeGenerator codeGenerator) {
        String textCode = "";
        for(BlockNode block : blockList) {
            textCode += block.generateCode(codeGenerator);
        }

        return textCode;
    }

    /**
     * Método que se encarga de guardar el json en un archivo
     * @param jsonString String con el json
     * @param inputPath String con el path del archivo de entrada
     * @autor Yeumen Silva
     */

    private void saveJson(String jsonString, String inputPath){
        String output = inputPath.replace(".ru", ".ast.json");
        try {
            // Crear un FileWriter para escribir en el archivo de salida
            FileWriter escritor = new FileWriter(output);

            // Escribir la salida en el archivo
            escritor.write(jsonString);

            // Cerrar el FileWriter
            escritor.close();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de salida: " + e.getMessage());
        }
    }

    /**
     * Método que verifica si un tipo es primitivo
     * @param type String con el tipo a verificar
     * @return boolean que indica si es primitivo o no
     * @autor Yeumen Silva
     */

    public boolean isPrimitive(String type){
        return type.equals("Int") || type.equals("Str") || type.equals("Char") || type.equals("Bool");
    }

    /**
     * Método que busca un struct en la tabla de simbolos
     * @param structName String con el nombre del struct a buscar
     * @return Struct que se busca
     * @autor Yeumen Silva
     */
    public Struct searchStruct(String structName){
        return symbolTable.getStructs().get(structName);
    }

    /**
     * Método que busca un metodo en un struct
     * @param structName String con el nombre del struct a buscar
     * @param methodName String con el nombre del metodo a buscar
     * @return Metodo que se busca
     * @autor Yeumen Silva
     */

    public Methods searchMethod(String structName, String methodName, Token token){
        Struct actualStruct = symbolTable.getStructs().get(structName);
        if(actualStruct == null){
            System.out.println("No se encontro el struct");
            throw new StructNotFound(token);
        }
        Methods methods = actualStruct.getMethods().get(methodName);
       if(methods == null){
           throw new MethodNotFound(token);
       }
         return methods;
    }

    /**
     * Método que verifica que dada una llamada a un método, los parametros sean correctos
     * @param paramsCall Lista de parametros de la llamada
     * @param struct Struct en la que se llama el metodo
     * @param token Token que se esta analizando
     * @autor Yeumen Silva
     */

    public void checkParametersConstructor(List<ExpressionNode> paramsCall, String struct, Token token){
        //Busco struct en la tabla de simbolos
        Struct actualStruct = searchStruct(struct);
        //Busco metodo en el struct
        Methods methods = actualStruct.getConstructor();
        //Obtengo los parametros del metodo
        Map<String, Variable> paramsDef = methods.getParamsOfMethod();

        //Si las listas tienen distinta cantidad de parametros, entonces error
        if(paramsCall.size() != paramsDef.size()){
            throw new InvalidParamSize(token);
        }

        //Comparo los tipos de los parametros
        Iterator<Variable> it = paramsDef.values().iterator();
        Variable argumentDef;
        String argumentTypeDef, argumentCallType;
        for(ExpressionNode currentParamCall : paramsCall){
            argumentDef = it.next();
            argumentTypeDef = argumentDef.getType().getName();
            argumentCallType = currentParamCall.getType();

            //Si los tipos no son iguales, entonces error
            if(argumentTypeDef.equals(argumentCallType) == false){
                throw new DiferentParamType(currentParamCall.getToken());
            }
        }

    }

    /**
     * Método que verifica que dada una llamada a un método, los parametros sean correctos
     * @param paramsCall Lista de parametros de la llamada
     * @param struct Struct en la que se llama el metodo
     * @param method Metodo que se llama
     * @param token Token que se esta analizando
     * @autor Yeumen Silva
     */

    public void checkParametersMethod(List<ExpressionNode> paramsCall, String struct, String method, Token token) {
        //Busco struct en la tabla de simbolos
        Struct actualStruct = searchStruct(struct);
        //Busco metodo en el struct
        Methods methods = actualStruct.getMethods().get(method);
        //Obtengo los parametros del metodo
        Map<String, Variable> paramsDef = methods.getParamsOfMethod();
        //Si las listas tienen distinta cantidad de parametros, entonces error
        if (paramsCall.size() != paramsDef.size()) {
            throw new InvalidParamSize(token);
        }

        //Comparo los tipos de los parametros
        Iterator<Variable> it = paramsDef.values().iterator();
        Variable argumentDef;
        String argumentTypeDef, argumentCallType;
        for (ExpressionNode currentParamCall : paramsCall) {
            argumentDef = it.next();
            argumentTypeDef = argumentDef.getType().getName();
            argumentCallType = currentParamCall.getType();

            //Si los tipos no son iguales, entonces error
            if (argumentTypeDef.equals(argumentCallType) == false) {
                //Verifico que no sea plimorfismo
                if(this.isSubStruct(argumentTypeDef,argumentCallType) == false){
                    throw new DiferentParamType(currentParamCall.getToken());
                }
            }
        }
    }

    /**
     * Método que busca una variable en la tabla de simbolos
     * @param struct Struct en la que se busca la variable
     * @param method Metodo en el que se busca la variable
     * @param token Token que se esta analizando
     * @return Variable que se busca
     * @autor Yeumen Silva
     */

    public Variable findVariable(String struct, String method, Token token){
        Struct structFound;
        Methods methodFound;
        /*Se deben buscar tanto declaradas en el método
        Como en los parámetros del método
        como en los atributos del struct
         */
        //Si no esta en el start
        if(!struct.equals("start")){
            structFound = symbolTable.getStructs().get(struct);
            if(structFound == null){

                throw new StructNotFound(token);
            }
            //Verifico si es el constructor
            if(method.equals(".")){
                //Si es el constructor busco el constructor
                methodFound = structFound.getConstructor();
            }else{
                //Si no busco el método
                methodFound = structFound.getMethods().get(method);
            }
            if(methodFound == null){

                throw new MethodNotFound(token);
            }


        }else {
            //Si esta en el start busco el método en el start
            structFound = null;
            methodFound = symbolTable.getStart();

        }

        // Busco si la variable esta definida en el método
        Variable foundVar;
        foundVar = methodFound.getDefinedVar().get(token.getLexeme());
        //Si no esta en las variables definidas, busco en los parametros
        if(foundVar == null){
            foundVar = methodFound.getParamsOfMethod().get(token.getLexeme());
            //Si no esta en los parametros, busco en los atributos del struct
            if(foundVar == null){
                if(structFound != null){
                    foundVar = structFound.getAttributes().get(token.getLexeme());
                    //Si es un atributo, debo verificar que si es heredado, que sea publico
                    if(foundVar != null){
                        if(((Attributes) foundVar).GetIInherited() && !((Attributes) foundVar).GetIsPublic()){
                            throw new PrivateVar(token);
                        }
                    }
                    //Si tampoco esta, debe ser un error
                    if(foundVar == null){
                        throw new VariableNotFound(token);
                    }
                }
            }
        }

        return foundVar;
    }

    public boolean isSubStruct(String subType, String SsuperType){
        boolean inherithed = false;
        Struct subStruct = searchStruct(subType);
        Struct superStruct = searchStruct(SsuperType);
        Struct currentStruct = subStruct;
        if(superStruct == null){
            if(subStruct == null){
                inherithed = true;
            }
        }
        else {
            if (subStruct != null){

                currentStruct = superStruct;
                while (!inherithed  && currentStruct.getInheritFrom() != null){
                    if(currentStruct.getInheritFrom().equals(subStruct) ){
                        inherithed = true;
                    }else {
                        currentStruct = currentStruct.getInheritFrom();
                    }
                }
            }
        }
        return inherithed;
    }

    public Variable findVariableSelf(String struct, String currentStruct, Token token){
        Struct structFound;
        Variable foundVar;
        structFound = symbolTable.getStructs().get(struct);
        if(structFound == null){
            throw new StructNotFound(token);
        }
        foundVar = structFound.getAttributes().get(token.getLexeme());
        if(foundVar == null){
            throw new VariableNotFound(token);
        }

        Struct calledStruct  = null;
        Methods calledMethod = null;
        if(currentStruct.equals("start")){
            //Tenemos start definido como método
            calledMethod = symbolTable.getStart();
        }else {
            calledStruct = symbolTable.getStructs().get(currentStruct);
        }
        //Si no accedo a la varaible desde la misma clase
        //Si no es publica, error

        if(calledMethod == null){
            if(!struct.equals(calledStruct.getName()) && !((Attributes) foundVar).GetIsPublic() ){
                throw new PrivateVar(token);
            }
        } else if (calledStruct == null){
            if(!struct.equals("start") && !((Attributes) foundVar).GetIsPublic() ){
                throw new PrivateVar(token);
            }
        }


        if(((Attributes) foundVar).GetIInherited() && !((Attributes) foundVar).GetIsPublic()){
            throw new PrivateVar(token);
        }

        return foundVar;

    }
    public Methods searchConstructor(String struct){
        return this.symbolTable.getStructs().get(struct).getConstructor();
    }
}
