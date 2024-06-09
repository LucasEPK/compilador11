package SemanticAnalyzer.AST;


import CodeGeneration.CodeGenerator;
import Exceptions.SemanticExceptions.AST.*;
import SemanticAnalyzer.SymbolTable.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Clase representate un bloque de codigo de un metodo
 * @author Yeumen Silva
 */

public class BlockNode extends SentenceNode implements Commons {

    private List<SentenceNode> sentenceList = new ArrayList<SentenceNode>();

    // Esta variable indica si es un bloque derivado de una sentencia
    private boolean isSentenceBlock = false;

    public BlockNode(String struct, String method) {
        super(struct, method);
    }

    /**
     * Constructor de la clase que recibe una lista de sentencias
     * @param struct nombre de la clase
     * @param method nombre del metodo
     * @param sentenceList lista de sentencias que contiene el bloque
     */
    public BlockNode(String struct, String method, ArrayList<SentenceNode> sentenceList) {
        super(struct, method);
        this.sentenceList = sentenceList;
    }

    /**
     * Método que recorre la lista de sentencias y llama al método toJson de cada una
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return void
     * @autor Yeumen Silva
     */

    @Override
    public String toJson(int tabs) {

        String json = addtabs(tabs) + "{\n";
        json += addtabs(tabs+1) + "\"nombre\": \"" + "Block" + "\",\n";
        json += addtabs(tabs+1) + "\"class\": \"" + getStruct() + "\",\n";
        json += addtabs(tabs+1) + "\"method\": \"" + getMethod() + "\",\n";
        json += addtabs(tabs+1) + "\"sentences\": [\n";
        int size = 0;
        for (SentenceNode sentence : sentenceList) {
            json += sentence.toJson(tabs+2);

            if(size < sentenceList.size()-1){
                json += ",\n";
            }
            size++;
        }

        json += addtabs(tabs+1) + "]\n";
        json += addtabs(tabs) + "}";

        return json;
    }

    @Override
    public void consolidate(AST ast) {
        //Llamo al método consolidate de cada sentencia
        for(SentenceNode sentence : sentenceList){
            if(sentence.consolidated == false){
                sentence.consolidate(ast);
            }
        }

        //Llamo al método consolidate de cada sentencia para verificar los tipos de return
        for(SentenceNode sentence : sentenceList) {
            //Verifico que start no tenga return
            if (sentence instanceof ReturnNode && sentence.getMethod().equals("start") && sentence.getStruct().equals("start")) {
                if(((ReturnNode) sentence).getReturnValueNode() != null){
                    throw new ReturnInStart(((ReturnNode) sentence).getReturnValueNode().getToken());
                }
            }
        }
        //Verifico que el constructor no tenga return
        if(getMethod().equals(".")){
            boolean hasReturn = false;
            Methods method = ast.searchConstructor(this.struct);
            for(SentenceNode sentence: sentenceList){
                if(sentence instanceof ReturnNode){
                    hasReturn = true;
                }
            }
            if(hasReturn){
                throw new ReturnInConstructor(method.getToken());
            }

        }else if(isSentenceBlock){
            //Caso en el que el bloque sea un IfNode o BlockNode
            boolean hasReturn = false;
            for(SentenceNode sentence : sentenceList){
                if(sentence instanceof ReturnNode){
                    hasReturn = true;
                }
            }

            for(SentenceNode sentence : sentenceList){
                //Verifico que start no tenga return
                if (sentence instanceof ReturnNode && sentence.getMethod().equals("start") && sentence.getStruct().equals("start")){
                    if(((ReturnNode) sentence).getReturnValueNode() != null){
                        throw new ReturnInStart(((ReturnNode) sentence).getReturnValueNode().getToken());
                    }

                }
                if(!sentence.getType().equals("void")){
                    if(!(sentence instanceof PrimaryNode)){
                        if(getType().equals("void")){
                            setType(sentence.getType());
                        }else {
                            if(!getType().equals(sentence.getType()) ){
                                throw new MultipleReturn(sentence.getReferenceToken());
                            }
                        }
                    }
                }
            }

            if(!getMethod().equals(".")){
                if(!getStruct().equals("start")){
                    //Chequeo que si hay return, sea del tipo correcto
                    Methods method = ast.searchMethod(this.struct,this.method,this.getReferenceToken());
                    String methodType = method.getGiveBack().getName();
                    boolean methodReturnArray = method.getIsGiveBackArray();
                    if(hasReturn){
                        if(!methodType.equals(getType()) && ast.isPrimitive(getType()) && getType().equals("nil")){
                            if(!ast.isSubStruct(getType(),method.getGiveBack().getName())){
                                throw new ReturnTypeDontMatch(method.getToken());
                            }
                        } else {
                            if(methodReturnArray != getIsArray()){
                                throw new ReturnTypeDontMatch(method.getToken());
                            }
                        }
                    }
                }
            }



        }else if (isSentenceBlock == false){
            for(SentenceNode sentence : sentenceList){
                //Verifico que start no tenga return
                if (sentence instanceof ReturnNode && sentence.getMethod().equals("start") && sentence.getStruct().equals("start")){
                    throw new ReturnInStart(((ReturnNode) sentence).getReturnValueNode().getToken());
                }
                if(!sentence.getType().equals("void")){
                    if(!(sentence instanceof PrimaryNode)){
                        if(getType().equals("void")){
                            setType(sentence.getType());
                        }else {
                            if(!getType().equals(sentence.getType())){
                                throw new MultipleReturn(sentence.getReferenceToken());
                            }
                        }
                    }
                }
            }
            //Si no es el constructor
            if(!getMethod().equals(".")) {
                //Comparo retorno del bloque y del método
                if (!getStruct().equals("start")) {
                    Methods method = ast.searchMethod(this.struct, this.method, this.getReferenceToken());
                    boolean methodReturnArray = method.getIsGiveBackArray();
                    if (method.getGiveBack() == null) {
                        String returnType = "void";
                        if (!returnType.equals(getType())) {
                            throw new ReturnTypeDontMatch(this.getReferenceToken());
                        }
                    } else {
                        //Recorro las sentencias
                        String methodType = method.getGiveBack().getName();
                        String typeIfThenElse = null;
                        boolean hasReturn = false;
                        for (SentenceNode sentece : sentenceList) {
                            if(sentece instanceof IfThenElseNode){
                                typeIfThenElse = checkIfType((IfThenElseNode) sentece,ast);
                            }
                            if (sentece instanceof ReturnNode) {
                                hasReturn = true;

                                //Si es de tipo array, lo seteo
                                if(sentece.getIsArray()){
                                    this.setIsArray(true);
                                }
                            }
                        }

                        if (typeIfThenElse != null) {
                            if (!typeIfThenElse.equals(methodType) && ast.isPrimitive(getType()) && getType().equals("nil")) {
                                if (!ast.isSubStruct(typeIfThenElse, methodType)) {
                                    throw new ReturnTypeDontMatch(method.getToken());
                                }
                            }else {
                                if(methodReturnArray != getIsArray()){
                                    throw new ReturnTypeDontMatch(method.getToken());
                                }

                            }
                        }
                        if(!methodType.equals(getType())){
                            if(ast.isPrimitive(getType())){
                                throw new ReturnTypeDontMatch(method.getToken());
                            }else if(!getType().equals("nil")){
                                throw new ReturnTypeDontMatch(method.getToken());
                            }
                        }


                        /*
                        //Si los tipos no coinciden
                        //Si el tipo de retorno de retorno es primitivo y el tipo de la sentencia es nil

                        if (!methodType.equals(getType()) && (ast.isPrimitive(getType()) && getType().equals("nil"))) {
                            if (!ast.isSubStruct(getType(), method.getGiveBack().getName())) {
                                throw new ReturnTypeDontMatch(method.getToken());
                            }
                        }else {

                            if(methodReturnArray != getIsArray()){
                                throw new ReturnTypeDontMatch(method.getToken());
                            }
                        }*/


                        if(!hasReturn && !methodType.equals("void")){
                            if(typeIfThenElse == null){
                                throw new NoReturnInMethod(method.getToken());
                            }

                        }
                        //Si el método tiene return, y su retorno es distinto de void
                        if(hasReturn && !getType().equals("void")){
                            //Si el método deberia retornar void
                            if(methodType.equals("void")){
                                throw new ReturnTypeDontMatch(method.getToken());
                            }
                        }


                    }
                }
            }

        }

        this.setConsolidated(true);

    }

    private String checkIfType(IfThenElseNode sentence, AST ast){
        String elseType = null;
        String thenType = sentence.getThenNode().getType();
        if(sentence.getElseNode() != null){
            elseType = sentence.getElseNode().getType();
        }else {
            return null;
        }
        //Comparo los tipos que tienen then y else
        if(!thenType.equals(elseType)){
            if(!ast.isSubStruct(thenType,elseType)){
                    return null;
            }
        }
        return thenType;
    }



    /*
     * Método que recorre sentencia a sentencia y llama al método consolidate de cada una
     * @param ast AST que se va a consolidar
     * @return void
     * @autor Yeumen Silva


    @Override
    public void consolidate(AST ast) {
        //Llamo al método consolidate de cada sentencia
        for(SentenceNode sentence : sentenceList){
            if(sentence.consolidated == false){
                sentence.consolidate(ast);
            }
        }

        //Llamo al método consolidate de cada sentencia para verificar los tipos de return
        for(SentenceNode sentence : sentenceList){
            //Verifico que start no tenga return
            if (sentence instanceof ReturnNode && sentence.getMethod().equals("start") && sentence.getStruct().equals("start")){
                throw new ReturnInStart(((ReturnNode) sentence).getReturnValueNode().getToken());
            }
            if(!sentence.getType().equals("void")){
                if(!(sentence instanceof PrimaryNode)){
                    if(getType().equals("void")){
                        setType(sentence.getType());
                    }else {
                        if(!getType().equals(sentence.getType())){
                            throw new MultipleReturn(sentence.getReferenceToken());
                        }
                    }
                }
            }
        }

        //Si no es el constructor
        if(!getMethod().equals(".")){
            //Comparo retorno del bloque y del método
            if(!getStruct().equals("start")){
                Methods method = ast.searchMethod(this.struct,this.method,this.getReferenceToken());
                if(method.getGiveBack() == null){
                    String returnType = "void";
                    if(!returnType.equals(getType())){
                        throw new ReturnTypeDontMatch(this.getReferenceToken());
                    }
                }else {
                    String methodType = method.getGiveBack().getName();
                    boolean hasReturn = false;
                    for(SentenceNode sentence : sentenceList){
                        //Verifico si el método tiene return
                        if(sentence instanceof ReturnNode){
                            hasReturn = true;
                        }
                    }
                    if(!hasReturn && !methodType.equals("void")){
                        throw new NoReturnInMethod(method.getToken());
                    }
                    if(!methodType.equals(getType())){
                        if(!ast.isSubStruct(getType(),method.getGiveBack().getName())){
                            throw new ReturnTypeDontMatch(method.getToken());

                        }
                    }
                }
            }

        }else {
            boolean hasReturn = false;
            Methods method = ast.searchConstructor(this.struct);
            for(SentenceNode sentence: sentenceList){
                if(sentence instanceof ReturnNode){
                    hasReturn = true;
                }
            }
            if(hasReturn){
                throw new ReturnInConstructor(method.getToken());
            }
        }

        this.setConsolidated(true);

    }*/
    /**
     * Método que agrega una cantidad de tabulaciones a un string
     * @param tabs cantidad de tabulaciones a agregar
     * @return string con las tabulaciones agregadas
     * @autor Yeumen Silva
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
     * Acá se genera codigo MIPS para todas las funciones, agregandole labels
     * y después se recorren todas las sentencias del bloque para generar el codigo adentro de ellos
     * @return codigo de las funciones
     * @author Lucas Moyano
     * */
    @Override
    public String generateCode(CodeGenerator codeGenerator) {
        String textCode = "";
        boolean isConstructor = false;
        SymbolTable symbolTable = codeGenerator.getSymbolTable();
        // Obtengo las variables declaradas desde la tabla de simbolos
        Map<String, Variable> declaredVariables = symbolTable.getStructMethodDeclaredVariables(this.getStruct(), this.getMethod());
        // Obtengo los atributos declarados desde la tabla de simbolos
        Map<String, Attributes> structAttributes = symbolTable.getStructAttributes(this.getStruct());

        if ( !isSentenceBlock ) { // Esto chequea que los bloques que son sentencias no tengan un label o variables declaradas
            // Este codigo diferencia el start y el constructor entre todos los otros metodos
            if (this.getStruct().equals("start")) { // Es el metodo start
                textCode = "main:\t# METODO START ----------------------------------------------------------\n";
            } else {
                if (this.getMethod().equals(".")) { // Es un constructor
                    textCode = this.getStruct() + "_constructor:\n";
                    isConstructor = true;
                } else { // Es un metodo común
                    textCode = this.getStruct() + "_" + this.getMethod() + ":\n";
                }
            }

            // Esta parte guarda el framepointer anterior en el stack y mete el $ra actual en el stack y apunta el $fp a ese $ra
            textCode += "\t# Actualizacion de framepointer\n" +
                    "\tla $t9, ($fp)\t\t# Metemos el framepointer anterior en el stack\n" +
                    "\tpush\n" +
                    "\tla $t9, ($ra)\t\t# Metemos el return address en el stack\n" +
                    "\tpush\n" +
                    "\taddi $fp, $sp, 4\t# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra\n" +
                    "\t# FIN actualizacion de framepointer\n\n";

            // Si es un bloque sentencia no debería tener variables declaradas adentro


            textCode += "\t# Declaracion de variables\n";
            // Recorro la lista de todas las variables
            Variable currentVariable = null;
            for (String varName : declaredVariables.keySet()){
                currentVariable = declaredVariables.get(varName);
                textCode += currentVariable.generateCode();
            }
            // Declaro atributos al final del stack de variables
            if(isConstructor) { // si es un constructor
                textCode += "\t# Declaracion de atributos\n";
                // Recorro la lista de todos los atributos y reservo espacio en el stack
                Variable currentAttributes = null;
                for (String attrName : structAttributes.keySet()) {
                    currentAttributes = structAttributes.get(attrName);
                    textCode += currentAttributes.generateCode();
                }
            }
            textCode += "\t# FIN declaracion de variables\n";
        }


        for (SentenceNode sentence : sentenceList) {
            textCode += sentence.generateCode(codeGenerator);
        }

        if(isConstructor) { // Esto hace que si es un constructor que devuelva el CIR a quien lo llamó
            int totalStructAttributes = structAttributes.size();
            int totalDeclaredVariables = declaredVariables.size();
            int bytesToAllocate = 4*(totalStructAttributes+1);
            textCode += "\tli $v0, 9\t# Aloco memoria en el heap\n" +
                    "\tli $a0, "+bytesToAllocate+"\t# x bytes en memoria\n" +
                    "\tsyscall\t\t# Con esto tenemos la referencia en $v0\n" +
                    "\tla $t1, "+this.getStruct()+"_vtable\t# Guardamos la dirección de la vtable en la primera posicion del heap\n" +
                    "\tsw $t1, 0($v0)\n";
            // Guardo el valor de los atributos en el heap
            for(int i=0; i<totalStructAttributes; i++){
                // Meto el valor asignado de la variable en el acumulador
                int variableStackPos = -4 * (totalDeclaredVariables+i+1);
                int attributeHeapPos = 4 * (i+2);
                textCode += "\tlw $t0, "+variableStackPos+"($fp)\t# Meto el valor asignado del atributo desde el stack al acumulador ($v0)\n";
                textCode += "\tsw $t0, "+attributeHeapPos+"($v0)\t# Meto el valor del atributo en su posición del heap\n";
            }
            // Acá hacemos que el constructor retorne el CIR
            textCode += "\t# Return de CIR\n";

            //Generamos el codigo del return
            //Lo guardamos en nuestro registro de activacion
            //En el tope de la pila
            textCode += "\tla $t9,($v0) #cargo en $t9 el valor de retorno\n";
            textCode += "\tpush #Lo pusheo al stack\n";

            //Debo hacer un jump a la direccion de retorno
            //Para eso debo cargar la direccion de retorno en $ra
            textCode += "\tlw $ra,0($fp) #Recupero el return address\n";
            textCode += "\tjr $ra #Vuelvo al return address\n";

            textCode += "\t #Fin Return de CIR\n";
        }


        return textCode;
    }

    /**
     * Esta función se encarga de agregar la sentencia dada a la lista
     * @param sentence sentencia que se quiere agregar a la lista
     * @author Lucas Moyano
     */
    public void addNewSentence(SentenceNode sentence){
        sentenceList.add(sentence);
    }

    public List<SentenceNode> getSentenceList() {
        return sentenceList;
    }

    public void setIsSentenceBlock(boolean isSentenceBlock) {
        this.isSentenceBlock = isSentenceBlock;
    }

    public boolean getIsSentenceBlock(){
        return this.isSentenceBlock;
    }
}
