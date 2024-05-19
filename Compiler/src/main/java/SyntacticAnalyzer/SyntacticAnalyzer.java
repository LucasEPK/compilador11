package SyntacticAnalyzer;

import Exceptions.SyntacticExceptions.SyntacticException;
import LexicalAnalyzer.LexicalAnalyzer;
import LexicalAnalyzer.Token;
import SemanticAnalyzer.AST.*;
import SemanticAnalyzer.SymbolTable.SymbolTable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que será la encargada de ejecutar nuestro análisis sintáctico
 * @author Yeumen Silva
 */

public class    SyntacticAnalyzer {

    private Token actualToken;

    private LexicalAnalyzer lexicalAnalyzer;

    private SymbolTable symbolTable = new SymbolTable();

    private AST ast = new AST(symbolTable);

    /**
     * Constructor del Analizador Sintáctico que inicializa el Executor
     * el cual es el encargado de pasar los tokens y evaluar los errores
     * Léxicos y a su vez se encarga de llamar a la primera regla
     * de nuestra gramática
     * @author Yeumen Silva
     * */

    public SymbolTable startSyntactic(LexicalAnalyzer lexicalAnalyzer){
        this.lexicalAnalyzer = lexicalAnalyzer;
        this.actualToken = lexicalAnalyzer.getNextToken();
        program();
        symbolTable.consolidate();
        return this.symbolTable;

    }

    /**
     * Método que de generar el Json de AST
     * @param inputPath path del archivo de entrada
     * @autor Yeumen Silva
     */
    public void generateASTJson(String inputPath){
        this.ast.toJson(1,inputPath);
    }
    
    /**
     * Método el cual trata de hacer match y actualizar el token actual
     * y el siguiente
     * @param actualname Nombre del lexema actual
     * @author Yeumen Silva
     * */

    private boolean match(String actualname){

        //Verifico si matchea el Token actual con token esperado
        if(Objects.equals(this.actualToken.getToken(),"StructID") ||
                Objects.equals(this.actualToken.getToken(),"ObjID") ||
                Objects.equals(this.actualToken.getToken(),"IntLiteral") ||
                Objects.equals(this.actualToken.getToken(),"StrLiteral") ||
                Objects.equals(this.actualToken.getToken(),"CharLiteral")){
            if(Objects.equals(this.actualToken.getToken(), actualname)){

                this.actualToken = lexicalAnalyzer.getNextToken();
                return true;

            }
            else {
                throw createException(this.actualToken, List.of(actualname), this.actualToken.getToken());
            }
        }

        //Verifico si matchea el lexema del token actual con el lexema esperado
        if(Objects.equals(this.actualToken.getLexeme(), actualname)){

            this.actualToken = lexicalAnalyzer.getNextToken();
            return true;

        }
        else {
            throw createException(this.actualToken, List.of(actualname),this.actualToken.getLexeme());
        }

    }

    /**
     * Método el cual trata de hacer match cuando tenemos
     * más de un match posible
     * @param actualname Nombre del lexema actual
     * @author Yeumen Silva
     * */

    private boolean moreOneMatch(String actualname){
        return Objects.equals(this.actualToken.getLexeme(),actualname);
    }

    private SyntacticException createException(Token token, List<String> waiting, String actual){

        return new SyntacticException(token,waiting,actual);
    }

    /**
     * Método que dado un varargs de Strings que contiene los primeros
     * o los siguientes de alguna regla, vérifica que el léxema de
     * el token actual matche con alguno de los primeros o siguientes
     * @param name varargs con los primeros o siguientes de alguna regla
     * @return booleano representando si pertenece o no
     * a los primeros o siguientes
     * @author Yeumen Silva
     * */

    private boolean verifyEquals(String... name){
        String actualLexeme = this.actualToken.getLexeme();
        String actualToken = this.actualToken.getToken();

        for (String lexeme : name){

            if(Objects.equals(actualLexeme,lexeme) || Objects.equals(actualToken,lexeme)){
                return true;
            }
        }
        return false;
    }

    /**
     * Regla inicial de nuestra gramática
     * @author Yeumen Silva
     * */

    private void program() {

        boolean isFInal = true;
        //Primeros Start
        if (verifyEquals("start")) {
            start();
            if(!Objects.equals(this.actualToken.getLexeme(), "$EOF$")){
                isFInal = false;
            }
        } else {
            //Primeros Lista-Definiciones
            if (verifyEquals("impl","struct")) {
                listaDefiniciones();
                start();
                if(!Objects.equals(this.actualToken.getLexeme(), "$EOF$")){
                    isFInal = false;
                }
            }
            else {
                throw createException(this.actualToken, List.of("start","impl","struct"),this.actualToken.getLexeme());
            }
        }

        if(!isFInal){
            throw createException(this.actualToken,List.of("$EOF$"),this.actualToken.getLexeme());
        }

    }

    /**
     * Regla Start
     * @author Yeumen Silva
     * */

    private void start(){

        //Tabla Simbolos-------------------------------------
        symbolTable.addStart(this.actualToken);
        //-----------------------------------------------------

        //Sintáctico
        match("start");
        bloqueMetodo();
    }

    /**
     * Regla Lista-Definiciones
     * @author Yeumen Silva
     * */

    private void listaDefiniciones(){
        //Primeros Struct
        if(verifyEquals("struct")){
            struct();
            listaDefinicionesF();

        }else {
            //Primeros Impl
            if(verifyEquals("impl")){
                impl();
                listaDefinicionesF();
            }
            else {
                throw createException(this.actualToken, List.of("impl","struct"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Lista-Definiciones-F
     * @author Yeumen Silva
     * */

    private void listaDefinicionesF(){

        //Primeros Lista-Definiciones
        if(verifyEquals("impl","struct")){
            listaDefiniciones();
        }
        else {
            //Siguientes Lista-Definiciones-F
            if(verifyEquals("start","$EOF$")){
                // Lambda
            }
            else {
                throw createException(this.actualToken, List.of("start","impl","struct"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Struct
     * @author Yeumen Silva
     * */

    private void struct(){
        match("struct");

        //Semántico--------------------------------------------
        this.symbolTable.addStructByStruct(this.actualToken);
        //-----------------------------------------------------

        match("StructID");
        structF();
    }

    /**
     * Regla Struct-F
     * @author Yeumen Silva
     * */

    private void structF(){

        //Primeros de :
        if(verifyEquals(":")){
            herencia();
            match("{");
            structF1();

        }
        else {
            match("{");
            structF1();
        }
    }

    /**
     * Regla Struct-F1
     * @author Yeumen Silva
     * */

    private void structF1(){
        //Primeros Atributo-Estrella
        if(verifyEquals("Array", "Bool", "Char","Int","Str","StructID"
                ,"pri")){
            atributoEstrella();
            match("}");

        }
        else {
            match("}");
        }
    }

    /**
     * Regla Atributo-Estrella
     * @author Yeumen Silva
     * */

    private void atributoEstrella(){
        atributo();
        atributoEstrellaF();
    }

    /**
     * Regla Atributo-Estrella-F
     * @author Yeumen Silva
     * */

    private void atributoEstrellaF(){

        //Primeros de Atributo-Estrella
        if(verifyEquals("Array" , "Bool" , "Char" , "Int" , "Str" , "StructID"
                , "pri")){
            atributoEstrella();
        }
        else {
            //Siguientes de Atributo-Estrella-F
            if(verifyEquals("}","$EOF$")){
                //lambda
            }
            else {
                throw createException(this.actualToken, List.of("Array" , "Bool" , "Char" , "Int" , "Str" , "StructID"
                        , "pri","}"),this.actualToken.getLexeme());
            }
        }

    }

    /**
     * Regla Impl
     * @author Yeumen Silva
     * */

    private void impl(){
        match("impl");

        //Semántico----------------------------------------
        this.symbolTable.addStructByImpl(this.actualToken);
        //-------------------------------------------------

        match("StructID");
        match("{");
        miembroMas();
        match("}");
    }

    /**
     * Regla Miembro-Mas
     * @author Yeumen Silva
     * */

    private void miembroMas(){
        miembro();
        miembroMasF();
    }

    /**
     * Regla Miembro-Mas-F
     * @author Yeumen Silva
     * */

    private void miembroMasF(){

        //Primeros Miembro-Mas
        if(verifyEquals(".","fn","st")){
            miembroMas();
        }
        else {
            //Siguientes Miembro-Mas-F
            if (verifyEquals("}","$EOF$")){
                //Lambda
            }
            else {
                throw createException(this.actualToken, List.of("." , "fn" , "st" , "}"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Herencia
     * @author Yeumen Silva
     * */

    private void herencia(){
        match(":");

        //Análisis Semantico-------------------------------
        this.symbolTable.addHeritance(this.actualToken);
        //--------------------------------------------------

        tipo();
    }

    /**
     * Regla Miembro
     * @author Yeumen Silva
     * */

    private void miembro(){

        //Primeros Método
        if(verifyEquals("fn","st")){
            metodo();
        }
        //Primeros Constructor
        else {
            if(verifyEquals(".")){
                constructor();
            }
            else {
                throw createException(this.actualToken, List.of("fn" , "st" , "." ),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Constructor
     * @author Yeumen Silva
     * @author Lucas Moyano
     * */

        private void constructor(){
            // Analisis semantico ----------------------------------
            this.symbolTable.addConstructorToStruct(this.actualToken);
            // -----------------------------------------------------
            match(".");
            argumentosFormales();
            bloqueMetodo();
        }

    /**
     * Regla atributo
     * @author Yeumen Silva
     * */

    private void atributo(){

        //Primeros visibilidad
        if(verifyEquals("pri")){
            // Analisis semantico ----------------------------------
            boolean isPublic = false;
            // -----------------------------------------------------
            visibilidad();
            // Analisis semantico ----------------------------------
            String attrType = this.actualToken.getLexeme();
            boolean isArray = false;
            if (Objects.equals(attrType, "Array")){
                isArray = true;
            }
            // -----------------------------------------------------
            attrType = tipo();
            listaDeclaracionVariables(attrType, true, isPublic, isArray);
            match(";");
        }
        else {
            //Primeros Tipo
            if(verifyEquals("Array" , "Bool" , "Char" , "Int" , "Str"
            , "StructID")){
                // Analisis semantico ----------------------------------
                boolean isPublic = true;
                String attrType = this.actualToken.getLexeme();
                boolean isArray = false;
                if (Objects.equals(attrType, "Array")){
                    isArray = true;
                }
                // -----------------------------------------------------
                attrType = tipo();
                listaDeclaracionVariables(attrType, true, isPublic, isArray);
                match(";");
            }
            else {
                throw createException(this.actualToken, List.of("Array" , "Bool" , "Char" , "Int" , "Str" , "StructID"
                        , "pri"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla método
     * @author Yeumen Silva
     * */

    private void metodo(){
        //Primeros Forma-Método
        if(verifyEquals("st")){
            formaMetodo();
            match("fn");

            // Analisis semantico ----------------------------------------
            boolean isStatic = true;
            this.symbolTable.addMethodToStruct(this.actualToken,isStatic);
            // -----------------------------------------------------------

            match("ObjID");
            argumentosFormales();
            match("->");
            //Análisis semántico-----------------------------------------
            String returnType = this.actualToken.getLexeme();
            boolean isArray = false;
            if (Objects.equals(returnType, "Array")){
                isArray = true;
            }
            Token typeToken = this.actualToken;
            //------------------------------------------------------------
            returnType = tipoMetodo();
            //Análisis semántico-----------------------------------------
            this.symbolTable.addReturnToMethod(typeToken, returnType, isArray);
            //------------------------------------------------------------
            bloqueMetodo();
        }
        else {
            //Primeros de fn
            if(verifyEquals("fn")){
                match("fn");

                // Analisis semantico ----------------------------------
                boolean isEstatic = false;
                this.symbolTable.addMethodToStruct(this.actualToken,isEstatic);
                // -----------------------------------------------------

                match("ObjID");
                argumentosFormales();
                match("->");

                //Análisis semántico-----------------------------------------
                String returnType = this.actualToken.getLexeme();
                boolean isArray = false;
                if (Objects.equals(returnType, "Array")){
                    isArray = true;
                }
                Token typeToken = this.actualToken;
                //------------------------------------------------------------

                returnType = tipoMetodo();
                //Análisis semántico-----------------------------------------
                this.symbolTable.addReturnToMethod(typeToken, returnType, isArray);
                //------------------------------------------------------------
                bloqueMetodo();

            }
            else {
                throw createException(this.actualToken, List.of("st" , "fn" ),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla visibilidad
     * @author Yeumen Silva
     * */

    private void visibilidad(){
        match("pri");
    }

    /**
     * Regla Forma-Metodo
     * @author Yeumen Silva
     * */

    private void formaMetodo(){
        match("st");
    }

    /**
     * Regla Bloque-Metodo
     * Crea un nuevo bloque para el AST
     * @author Yeumen Silva
     * @author Lucas Moyano
     * */

    private void bloqueMetodo(){
        match("{");
        // Analisis Semantico AST -------------------------
        BlockNode newBlock = ast.addNewBlock();
        // ------------------------------------------------
        bloqueMetodoF(newBlock);
    }

    /**
     * Regla Bloque-Metodo-F
     * @param block bloque del ast en donde estamos
     * @author Yeumen Silva
     * @author Lucas Moyano
     * */

    private void bloqueMetodoF(BlockNode block){

        //Primeros Decl-Var-Locales-Estrella
        if(verifyEquals("Array" , "Bool" , "Char" , "Int" , "Str"
                , "StructID")){
            declVarLocalesEstrella();
            bloqueMetodoF1();
        }else {
            //Primeros Sentencia-Estrella
            if (verifyEquals("(" , ";" , "ObjID" , "if"
                    , "ret" , "self" , "while", "{")){
                sentenciaEstrella(block);
                match("}");
            }else {
                if(verifyEquals("}")) {
                    match("}");
                }
                else {
                    throw createException(this.actualToken, List.of("Array" , "Bool" , "Char" , "Int" , "Str" , "StructID"
                            , "(" , ";" , "ObjID" , "if", "ret"
                            , "self" , "while", "{","}"),this.actualToken.getLexeme());
                }
            }
        }
    }

    /**
     * Regla Bloque-Metodo-F1
     * @author Yeumen Silva
     * */

    private void bloqueMetodoF1(){
        //Primeros }
        if(verifyEquals("}")){
            match("}");
        }
        else {
            //Primeros Sentencia-Estrella
            if(verifyEquals("(" , ";" , "ObjID" , "if"
                    , "ret" , "self" , "while", "{")){
                sentenciaEstrella(null);
                match("}");
            }
            else {
                throw createException(this.actualToken, List.of("}" , "(" , ";" , "ObjID" , "if"
                        , "ret" , "self" , "while", "{"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla DeclVar-Locales-Estrella
     * @author Yeumen Silva
     * */

    private void declVarLocalesEstrella(){
        declVarLocales();
        declVarLocalesEstrellaF();
    }

    /**
     * Regla DeclVar-Locales-Estrella-F
     * @author Yeumen Silva
     * */

    private void declVarLocalesEstrellaF(){

        //Primeros Decl-Var-Locales-Estrella
        if(verifyEquals("Array" , "Bool" , "Char" , "Int" , "Str"
                , "StructID")){
            declVarLocalesEstrella();
        }
        else {
            //Siguientes de Decl-Var-Locales-Estrella-F
            if(verifyEquals("(" , ";" , "ObjID" , "if" ,
                    "ret" , "self" , "while" , "{" , "}" , "$EOF$")){
                //Lambda
            }
            else {
                throw createException(this.actualToken, List.of("Array" , "Bool" , "Char" , "Int" , "Str"
                        , "StructID","(" , ";" , "ObjID" , "if" ,
                        "ret" , "self" , "while" , "{" , "}" ),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Sentencia-Estrella
     * @param block bloque del ast en donde estamos
     * @author Yeumen Silva
     * */

    private void sentenciaEstrella(BlockNode block){
        sentencia(block);
        sentenciaEstrellaF();
    }

    /**
     * Regla Sentencia-Estrella-F
     * @author Yeumen Silva
     * */

    private void sentenciaEstrellaF(){
        //Primeros Sentencia-Estrella
        if(verifyEquals("(" , ";" , "ObjID" , "if"
                , "ret" , "self" , "while", "{")){
            sentenciaEstrella(null);
        }
        else {
            //Siguientes Sentencia-Estrella-F
            if (verifyEquals("}","$EOF$")){
                //Lambda
            }
            else {
                throw createException(this.actualToken, List.of("(" , ";" , "ObjID" , "if"
                        , "ret" , "self" , "while", "{","}"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla DeclVar-Locales
     * @author Yeumen Silva
     * */

    private void declVarLocales(){
        // Analisis semantico ----------------------------------
        String varType = this.actualToken.getLexeme();
        boolean isArray = false;
        if (Objects.equals(varType, "Array")){
            isArray = true;
        }
        // -----------------------------------------------------
        varType = tipo();
        listaDeclaracionVariables(varType, false, true, isArray);
        match(";");
    }

    /**
     * Regla Lista-Declaracion-Variables
     * @param type este es el tipo de variable/atributo
     * @param isAttribute indica si es un atributo, en caso contrario se asume que es una variable
     * @param isPublic indica si el atributo es publico (no se usa en caso de ser una variable)
     * @author Yeumen Silva
     * @author Lucas Moyano
     * */

    private void listaDeclaracionVariables(String type, boolean isAttribute, boolean isPublic, boolean isArray){

        // Analisis semantico ----------------------------------
        // Esto se hace porque llegado a este punto no podemos saber si estamos declarando variables o atributos
        if (isAttribute){
            this.symbolTable.addAttrToStruct(this.actualToken, type, isPublic, isArray);
        } else {
            this.symbolTable.addVarToMethod(this.actualToken, type, isArray);
        }
        // -----------------------------------------------------
        match("ObjID");
        listaDeclaracionVariablesF(type, isAttribute, isPublic, isArray);
    }

    /**
     * Regla Lista-Declaracion-Variables-F
     * @param type este es el tipo de variable/atributo
     * @param isAttribute indica si es un atributo, en caso contrario se asume que es una variable
     * @param isPublic indica si el atributo es publico (no se usa en caso de ser una variable)
     * @author Yeumen Silva
     * */

    private void listaDeclaracionVariablesF(String type, boolean isAttribute, boolean isPublic, boolean isArray){
        //Primeros ,
        if(verifyEquals(",")){
            match(",");
            listaDeclaracionVariables(type, isAttribute, isPublic, isArray);
        }
        else {
            //Siguientes Lista-Declaracion-Variables-F
            if(verifyEquals(";","$EOF$")){
                //Lambda
            }
            else {
                throw createException(this.actualToken, List.of(",",";"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Argumentos-Formales
     * @author Yeumen Silva
     * */

    private void argumentosFormales(){
        match("(");
        argumentosFormalesF();
    }

    /**
     * Regla Argumentos-Formales-F
     * @author Yeumen Silva
     * */

    private void argumentosFormalesF(){

        //Primeros Lista-Argumentos-Formales
        if (verifyEquals("Array" , "Bool" , "Char" , "Int" , "Str" ,
                "StructID")){
            listaArgumentosFormales();
            match(")");
        }else {
            //Primeros )
            if(verifyEquals(")")){
                match(")");
            }
            else {
                throw createException(this.actualToken, List.of("Array" , "Bool" , "Char" , "Int" , "Str" ,
                        "StructID",")"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Lista-Argumentos-Formales
     * @author Yeumen Silva
     * */

    private void listaArgumentosFormales(){
        argumentoFormal();
        listaArgumentosFormalesF();
    }

    /**
     * Regla Lista-Argumentos-Formales-F
     * @author Yeumen Silva
     * */

    private void listaArgumentosFormalesF(){

        //Primeros ,
        if(verifyEquals(",")){
            match(",");
            listaArgumentosFormales();
        }
        else{
            //Siguientes Lista-Argumentos-Formales-F
            if(verifyEquals(")","$EOF$")){
                //Lambda
            }
            else {
                throw createException(this.actualToken, List.of("," ,")"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Argumento-Formal
     * @author Yeumen Silva
     * @author Lucas Moyano
     * */

    private void argumentoFormal(){
        // Analisis semantico ----------------------------------
        String paramType = actualToken.getLexeme();
        boolean isArray = false;
        if (Objects.equals(paramType, "Array")){
            isArray = true;
        }
        //-----------------------------------------------------
        paramType = tipo();
        // Analisis semantico ----------------------------------
        this.symbolTable.addParameterToMethod(this.actualToken, paramType, isArray);
        //-----------------------------------------------------
        match("ObjID");
    }

    /**
     * Regla Tipo-Metodo
     * @author Yeumen Silva
     * */

    private String tipoMetodo(){

        //Primeros Tipo
        if (verifyEquals("Array" , "Bool" , "Char" , "Int" , "Str" ,
                "StructID")){
            // Analisis semantico ----------------------------------
            // el tipo del metodo ya se guardó en la tabla
            // -----------------------------------------------------
            return tipo();
        }
        else {
            //Primeros void
            if (verifyEquals("void")){
                // Analisis semantico ----------------------------------
                String type = this.actualToken.getLexeme();
                // -----------------------------------------------------
                match("void");
                // Analisis semantico ----------------------------------
                return type;
                // -----------------------------------------------------
            }
            else {
                throw createException(this.actualToken, List.of("Array" , "Bool" , "Char" , "Int" , "Str" ,
                        "StructID","void"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Tipo
     * @author Yeumen Silva
     * */

    private String tipo(){
        //Primeros Tipo-Primitivo
        if (verifyEquals("Bool" , "Char" , "Int" , "Str")){
            return tipoPrimitivo();
        }
        else {
            //Primeros Tipo-Referencia
            if(verifyEquals("StructID")){
                return tipoReferencia();
            }
            else {
                //Primeros Tipo-Arreglo
                if(verifyEquals("Array")){
                    return tipoArreglo();
                }
                else {
                    throw createException(this.actualToken, List.of("Array" , "Bool" , "Char" , "Int" , "Str" ,
                            "StructID"),this.actualToken.getLexeme());
                }
            }
        }
    }

    /**
     * Regla Tipo-Primitivo
     * @author Yeumen Silva
     * */

    private String tipoPrimitivo(){

        if(moreOneMatch("Str") || moreOneMatch("Bool")
                || moreOneMatch("Int") || moreOneMatch("Char")){
            // Analisis semantico ----------------------------------
            String type = this.actualToken.getLexeme();
            // -----------------------------------------------------
            this.actualToken = this.lexicalAnalyzer.getNextToken();
            // Analisis semantico ----------------------------------
            return type;
            // -----------------------------------------------------
        }
        else {
            throw createException(this.actualToken, List.of( "Bool" , "Char" , "Int" , "Str"),this.actualToken.getLexeme());
        }

    }

    /**
     * Regla Tipo-Referencia
     * @author Yeumen Silva
     * */

    private String tipoReferencia(){
        // Analisis semantico ----------------------------------
        String type = actualToken.getLexeme();
        //-----------------------------------------------------
        match("StructID");
        // Analisis semantico ----------------------------------
        return type;
        //-----------------------------------------------------
    }


    /**
     * Regla Tipo-Arreglo
     * @author Yeumen Silva
     * */
    private String tipoArreglo(){
        match("Array");
        return tipoPrimitivo();
    }

    /**
     * Regla Sentencia
     * @param block bloque del ast en donde estamos
     * @author Yeumen Silva
     * */

    private SentenceNode sentencia(BlockNode block){

        //Primeros ;
        if (verifyEquals(";")){
            match(";");
        }
        else {
            //Primeros Asignacion
            if(verifyEquals("ObjID","self")){
                // Analisis Semantico AST -------------------------
                AsignationNode newAsignationSentence = new AsignationNode(symbolTable.getCurrentStruct().getName(),
                        symbolTable.getCurrentMethod().getName());
                if (block != null) { // Esto se usa para cuando el nodo necesite ser linkeado al bloque
                    block.addNewSentence(newAsignationSentence);
                }
                // -----------------------------------------------------
                asignacion(newAsignationSentence);
                match(";");

                return newAsignationSentence;
            }
            else {
                //Primeros Sentencia-Simple
                if(verifyEquals("(")){
                    ExpressionNode sentence = sentenciaSimple();
                    match(";");

                    // AST-------------
                    if (block != null ){ // Esto se usa para cuando el nodo necesite ser linkeado al bloque
                        block.addNewSentence(sentence);
                    }
                    return sentence;
                    // -----------------
                }
                else {
                    //Primeros if
                    if (verifyEquals("if")){
                        // Analisis Semantico AST -------------------------
                        IfThenElseNode newIfThenElseSentence = new IfThenElseNode(symbolTable.getCurrentStruct().getName(),
                                symbolTable.getCurrentMethod().getName());
                        if (block != null) { // Esto se usa para cuando el nodo necesite ser linkeado al bloque
                            block.addNewSentence(newIfThenElseSentence);
                        }
                        // -----------------------------------------------------
                        match("if");
                        match("(");
                        ExpressionNode expNode = expresion();
                        match(")");
                        SentenceNode sentence = sentencia(null);
                        SentenceNode sentenceR = sentenciaF();
                        // Analisis Semantico AST -------------------------
                        newIfThenElseSentence.setIfNode(expNode);
                        newIfThenElseSentence.setThenNode(sentence);
                        newIfThenElseSentence.setElseNode(sentenceR);
                        return newIfThenElseSentence;
                        // ------------------------------------------------
                    }
                    else {
                        //Primeros while
                        if (verifyEquals("while")){

                            // Analisis Semantico AST -------------------------
                            WhileNode newWhileSentence = new WhileNode(symbolTable.getCurrentStruct().getName(),
                                    symbolTable.getCurrentMethod().getName());
                            if (block != null) { // Esto se usa para cuando el nodo necesite ser linkeado al bloque
                                block.addNewSentence(newWhileSentence);
                            }
                            // -----------------------------------------------------

                            match("while");
                            match("(");
                            ExpressionNode expNode = expresion();
                            match(")");
                            SentenceNode sentence = sentencia(null);

                            // Analisis Semantico AST -------------------------
                            newWhileSentence.setWhileNode(expNode);
                            newWhileSentence.setDoNode(sentence);
                            return newWhileSentence;
                            // -----------------------------------------------------
                        }
                        else {
                            //Primeros Bloque
                            if (verifyEquals("{")){
                                // AST----------------------------------------------------------------------------------
                                BlockNode newBlockSentence = new BlockNode(symbolTable.getCurrentStruct().getName(),
                                        symbolTable.getCurrentMethod().getName());
                                if (block != null) {
                                    block.addNewSentence(newBlockSentence);
                                }
                                //------------------------------------------------------------------------------------

                                bloque(newBlockSentence);
                                // AST----------------------
                                return newBlockSentence;
                                // -------------------------
                            }
                            else {
                                //Primeros ret
                                if (verifyEquals("ret")){
                                    match("ret");
                                    // Analisis Semantico AST -------------------------
                                    ReturnNode newReturnSentence = new ReturnNode(symbolTable.getCurrentStruct().getName(),
                                            symbolTable.getCurrentMethod().getName());
                                    if (block != null) { // Esto se usa para cuando el nodo necesite ser linkeado al bloque
                                        block.addNewSentence(newReturnSentence);
                                    }
                                    // ------------------------------------------------

                                    sentenciaF1(newReturnSentence);
                                    // AST----------------------
                                    return newReturnSentence;
                                    // ------------------------
                                }
                                else {
                                    throw createException(this.actualToken, List.of(";" , "ObjID" , "self" , "(" , "if" ,
                                            "while","{","ret"),this.actualToken.getLexeme());
                                }
                            }
                        }
                    }
                }

            }

        }
        return null;
    }

    /**
     * Regla Sentencia-F
     * @author Yeumen Silva
     * */

    private SentenceNode sentenciaF(){
        // AST----------------
        SentenceNode sentence = null;
        // --------------------

        //Primeros else
        if (verifyEquals("else")){
            match("else");
            sentence = sentencia(null);
        }
        /*Aca deberian estar los siguientes de SentenciaF
        pero como tambien tienen "else", lo mejor es patear el error
        para daspues, ya que con los siguientes solo hacemos lambda
         */

        // AST----------------
        return sentence;
    }

    /**
     * Regla Sentencia-F1
     * Además añade un return value al nodo Return
     * @param newReturnSentence nodo return del AST en donde estamos
     * @author Yeumen Silva
     * */

    private void sentenciaF1(ReturnNode newReturnSentence){

        //Primeros ;
        if (verifyEquals(";")){
            match(";");
        }
        else {
            //Primeros Expresion
            if (verifyEquals("!" , "(" , "+" , "++" , "-" , "--"
                    , "StrLiteral", "CharLiteral" , "false" , "ObjID"
                    , "StructID" , "IntLiteral" , "new" , "nil" , "self" , "true")){
                ExpressionNode expNode = expresion();
                // Analisis Semantico AST -------------------------
                newReturnSentence.setReturnValueNode(expNode);
                // ------------------------------------------------
                match(";");
            }
            else {
                throw createException(this.actualToken, List.of(";" , "!" , "(" , "+" , "++" , "-" , "--"
                        , "StrLiteral", "CharLiteral" , "false" , "ObjID"
                        , "StructID" , "IntLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Bloque
     * @author Yeumen Silva
     * */

    private void bloque(BlockNode block){
        match("{");
        bloqueF(block);

    }

    /**
     * Regla Bloque-F
     * @author Yeumen Silva
     * */

    private void bloqueF(BlockNode block){
        //Primeros }
        if(verifyEquals("}")){
            match("}");
        }
        else {
            //Primeros Setntencia-Estrella
            if(verifyEquals("(" , ";" , "ObjID" , "if" , "ret" ,
                    "self" , "while" , "{")){
                sentenciaEstrella(block);
                match("}");
            }
            else {
                throw createException(this.actualToken, List.of("}" ,"(" , ";" , "ObjID" , "if" , "ret" ,
                        "self" , "while" , "{"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Asignación
     * @param newAsignationSentence nodo Asignation del AST en donde estamos
     * @author Yeumen Silva
     * */

    private void asignacion(AsignationNode newAsignationSentence){
        //Primeros AccesoVar-Simple
        if (verifyEquals("ObjID")){
            ExpressionNode expNode1 = accesoVarSimple();
            match("=");
            ExpressionNode expNode2 = expresion();

            // AST --------------------------------------
            newAsignationSentence.setLeft(expNode1);
            newAsignationSentence.setRight(expNode2);
            // ---------------------------------------------
        }
        else {
            //`Primeros AccesoSelf-Simple
            if(verifyEquals("self")){
                ExpressionNode expNode1 = accesoSelfSimple();
                match("=");
                ExpressionNode expNode2 = expresion();

                // AST --------------------------------------
                newAsignationSentence.setLeft(expNode1);
                newAsignationSentence.setRight(expNode2);
                // ---------------------------------------------
            }
            else {
                throw createException(this.actualToken, List.of("ObjID" , "self"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla AccesoVar-Simple
     * @author Yeumen Silva
     * */

    private ExpressionNode accesoVarSimple(){
        // Analisis semantico ----------------------------------
        // acá ya debería existir este objeto
        // -----------------------------------------------------
        match("ObjID");
        accesoVarSimpleF();
        // TODO: AST
        return null;
    }

    /**
     * Regla AccesoVar-Simple-F
     * @author Yeumen Silva
     * */

    private void accesoVarSimpleF(){
        //Primeros Encadenado-Simple-Estrella
        if(verifyEquals(".")){
            encadenadoSimpleEstrella();
        }
        else {
            //Primeros [
            if(verifyEquals("[")){
                match("[");
                expresion();
                match("]");
            }
            else {
                //Siguientes AccesoVar-Simple-F
                if(verifyEquals("=","$EOF$")){
                    //Lambda
                }
                else {
                    throw createException(this.actualToken, List.of("[" , "="),this.actualToken.getLexeme());
                }
            }
        }
    }

    /**
     * Regla Encadenado-Simple-Estrella
     * @author Yeumen Silva
     * */

    private void encadenadoSimpleEstrella(){
        encadenadoSimple();
        encadenadoSimpleEstrellaF();
    }

    /**
     * Regla Encadenado-Simple-Estrella
     * @author Yeumen Silva
     * */

    private void encadenadoSimpleEstrellaF(){
        //Primeros Encadenado-Simple-Estrella
        if (verifyEquals(".")){
            encadenadoSimpleEstrella();
        }
        else {
            //Siguientes Encadenado-Simple-Estrella-F
            if(verifyEquals("=","$EOF$")){
                //Lambda
            }
            else {
                throw createException(this.actualToken, List.of("." , "="),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla AccesoSelf-Simple
     * @author Yeumen Silva
     * */

    private ExpressionNode accesoSelfSimple(){
        match("self");
        accesoSelfSimpleF();

        // TODO: AST
        return null;
    }

    /**
     * Regla AccesoSelf-Simple
     * @author Yeumen Silva
     * */

    private void accesoSelfSimpleF(){
        //Primeros Encadenado-Simple-Estrella
        if (verifyEquals(".")){
            encadenadoSimpleEstrella();
        }else {
            //Siguientes AccesoSelf-Simple-F
            if(verifyEquals("=","$EOF$")){
                //Lambda
            }
            else {
                throw createException(this.actualToken, List.of("." , "="),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Regla Encadenado-Simple
     * @author Yeumen Silva
     * */

    private void encadenadoSimple(){
        match(".");
        // Analisis semantico ----------------------------------
        // ya debería existir este objeto
        // -----------------------------------------------------
        match("ObjID");
    }

    /**
     * Regla Sentencia-Simple
     * @author Yeumen Silva
     * */

    private ExpressionNode sentenciaSimple(){
        match("(");
        ExpressionNode expNode = expresion();
        match(")");

        // AST------------------
        return expNode;
    }

    /**
     * Regla Expresion
     * @return un nodo expresión del AST
     * @author Yeumen Silva
     * */
    private ExpressionNode expresion(){
        ExpressionNode expNode = expOr();
        // Analisis Semantico AST -------------------------
        return expNode;
        // ------------------------------------------------
    }

    /**
     * Función para la regla 55 <ExpOr> de la Gramatica
     * es el encargado de agregar al AST las operaciones binarias con ||
     * @return un nodo expresión del AST
     * @author Lucas Moyano
     * */
    private ExpressionNode expOr() {
        String[] firstExpAnd = {"!", "(", "+" , "++" , "-" , "--" ,
                "StrLiteral" , "CharLiteral" , "false" , "ObjID" , "StructID" ,
                "IntLiteral" , "new" , "nil" , "self" , "true"};

        if(verifyEquals(firstExpAnd)){
            ExpressionNode expNode = expAnd();
            ExpBin expBinNode = expOrF();
            // Analisis Semantico AST -------------------------
            if (expBinNode != null) { // Hay expresiones or
                expBinNode.setLeft(expNode); // el lado derecho ya se seteó en expMulF
                return expBinNode;
            } else { // En este caso no hay expresiones or
                return expNode;
            }
            // ------------------------------------------------
        } else {
            throw createException(this.actualToken, List.of("!", "(", "+" , "++" , "-" , "--" ,
                    "StrLiteral" , "CharLiteral" , "false" , "ObjID" , "StructID" ,
                    "IntLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
        }

    }

    /**
     * Función para la regla 56 <ExpOr-F> de la Gramatica
     * @return una operación binaria o null
     * @author Lucas Moyano
     * */
    private ExpBin expOrF() {
        String[] followExpOrF = {")" , "," , ";" , "]" , "$EOF$"};
        String[] firstExpOrR = {"||"};

        // AST---------------------
        ExpBin expBinNode = null;
        // ------------------------

        if(verifyEquals(followExpOrF)){ // Esto es por Lambda
            //Lambda
        } else {
            if (verifyEquals(firstExpOrR)){
                expBinNode = expOrR();
            } else {
                throw createException(this.actualToken, List.of("||", ")" , "," ,
                        ";" , "]" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST---------------------
        return expBinNode;
    }

    /**
     * Función para la regla 57 <ExpOrR> de la Gramatica
     * @return un nodo expresión binaria con el tipo y el nodo derecho definido
     * @author Lucas Moyano
     * */
    private ExpBin expOrR(){
        // AST---------------------
        ExpBin expBinNode = null;
        Token operator = this.actualToken;
        // ------------------------

        match("||");
        ExpressionNode expNode = expAnd();
        ExpBin expBinNodeR = expOrRF();

        // AST----------------------------------------------------------------------------
        expBinNode = new ExpBin(symbolTable.getCurrentStruct().getName(),
                symbolTable.getCurrentMethod().getName(), operator);
        if (expBinNodeR == null) { // Caso base
            expBinNode.setRight(expNode);
        } else { // Caso recursivo
            expBinNodeR.setLeft(expNode); // seteamos la izq de la recursión
            expBinNode.setRight(expBinNodeR); // y la derecha del expbin actual conectandolo con la recursión
        }
        expBinNode.setType("Bool"); // Esto es porque el resultado va a ser booleano
        return expBinNode;
        // --------------------------------------------------------------------------------
    }

    /**
     * Función para la regla 58 <ExpOrR-F> de la Gramatica
     * @return una expresión binaria o null
     * @author Lucas Moyano
     * */
    private ExpBin expOrRF() {
        String[] followExpOrRF = {")" , "," , ";" , "]" , "$EOF$"};
        String[] firstExpOrR = {"||"};

        // AST----------------------
        ExpBin expBinNode = null;
        // --------------------------

        if (verifyEquals(followExpOrRF)) {
            // Lambda
        } else {
            if (verifyEquals(firstExpOrR)) {
                expBinNode = expOrR();
            } else {
                throw createException(this.actualToken, List.of("||" ,")" , "," ,
                        ";" , "]" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST----------------
        return expBinNode;
    }

    /**
     * Función para la regla 59 <ExpAnd> de la Gramatica
     * es el encargado de agregar al AST las operaciones binarias con &&
     * @return un nodo expresión del AST
     * @author Lucas Moyano
     * */
    private ExpressionNode expAnd() {
        String[] firstExpIgual = {"!" , "(" , "+" , "++" , "-" , "--" , "StrLiteral" , "CharLiteral" ,
                "false" , "ObjID" , "StructID" , "IntLiteral" , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpIgual)) {
            ExpressionNode expNode = expIgual();
            ExpBin expBinNode = expAndF();
            // Analisis Semantico AST -------------------------
            if (expBinNode != null) { // Hay expresiones and
                expBinNode.setLeft(expNode); // el lado derecho ya se seteó en expMulF
                return expBinNode;
            } else { // En este caso no hay expresiones and
                return expNode;
            }
            // ------------------------------------------------
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" , "-" , "--" , "StrLiteral" , "CharLiteral" ,
                    "false" , "ObjID" , "StructID" , "IntLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 60 <ExpAnd-F> de la Gramatica
     * @return una operacion binaria o null
     * @author Lucas Moyano
     * */
    private ExpBin expAndF() {
        String[] followExpAndF = {")" , "," , ";" , "]" , "||" , "$EOF$"};
        String[] firstExpAndR = {"&&"};

        // AST---------------------
        ExpBin expBinNode = null;
        // ------------------------

        if(verifyEquals(followExpAndF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpAndR)) {
                expBinNode = expAndR();
            } else {
                throw createException(this.actualToken, List.of("&&", ")" , "," , ";" ,
                        "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST---------------------
        return expBinNode;
    }

    /**
     * Función para la regla 61 <ExpAndR> de la Gramatica
     * @return un nodo expresión binaria con el tipo y el nodo derecho definido
     * @author Lucas Moyano
     * */
    private ExpBin expAndR() {

        // AST---------------------
        ExpBin expBinNode = null;
        Token operator = this.actualToken;
        // ------------------------
        match("&&");
        ExpressionNode expNode = expIgual();
        ExpBin expBinNodeR = expAndRF();

        // AST----------------------------------------------------------------------------
        expBinNode = new ExpBin(symbolTable.getCurrentStruct().getName(),
                symbolTable.getCurrentMethod().getName(), operator);
        if (expBinNodeR == null) { // Caso base
            expBinNode.setRight(expNode);
        } else { // Caso recursivo
            expBinNodeR.setLeft(expNode); // seteamos la izq de la recursión
            expBinNode.setRight(expBinNodeR); // y la derecha del expbin actual, conectadola con la recursion
        }
        expBinNode.setType("Bool"); // Esto es porque el resultado va a ser booleano

        return expBinNode;
        // --------------------------------------------------------------------------------
    }

    /**
     * Función para la regla 62 <ExpAndR-F> de la Gramatica
     * @return una expresión binaria o null
     * @author Lucas Moyano
     * */
    private ExpBin expAndRF() {
        String[] followExpAndRF = {")" , "," , ";" , "]" , "||" , "$EOF$"};
        String[] firstExpAndR = {"&&"};

        // AST----------------------
        ExpBin expBinNode = null;
        // --------------------------

        if(verifyEquals(followExpAndRF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpAndR)) {
                expBinNode = expAndR();
            } else {
                throw createException(this.actualToken, List.of("&&" , ")" , "," , ";" ,
                        "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST----------------
        return expBinNode;
    }

    /**
     * Función para la regla 63 <ExpIgual> de la Gramatica
     * es el encargado de agregar al AST las operaciones binarias con == !=
     * @return un nodo Expresión del AST
     * @author Lucas Moyano
     * */
    private ExpressionNode expIgual() {
        String[] firstExpCompuesta = {"!" , "(" , "+" , "++" , "-"
                , "--" , "StrLiteral" , "CharLiteral" , "false"
                , "ObjID" , "StructID" , "IntLiteral" , "new" , "nil"
                , "self" , "true"};

        if (verifyEquals(firstExpCompuesta)) {
            ExpressionNode expNode = expCompuesta();
            ExpBin expBinNode= expIgualF();
            // Analisis Semantico AST -------------------------
            if (expBinNode != null) { // Hay expresiones igual
                expBinNode.setLeft(expNode); // el lado derecho ya se seteó en expMulF
                return expBinNode;
            } else { // En este caso no hay expresiones igual
                return expNode;
            }
            // ------------------------------------------------
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" , "-"
                    , "--" , "StrLiteral" , "CharLiteral" , "false"
                    , "ObjID" , "StructID" , "IntLiteral" , "new" , "nil"
                    , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 64 <ExpIgual-F> de la Gramatica
     * @return una operación binaria o null
     * @author Lucas Moyano
     * */
    private ExpBin expIgualF() {
        String[] followExpIgualF = {"&&" , ")" , "," , ";" , "]" , "||" , "$EOF$"};
        String[] firstExpIgualR = {"!=" , "=="};

        // AST---------------------
        ExpBin expBinNode = null;
        // ------------------------

        if(verifyEquals(followExpIgualF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpIgualR)) {
                expBinNode = expIgualR();
            } else {
                throw createException(this.actualToken, List.of("!=" , "==" ,"&&" ,
                        ")" , "," , ";" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST---------------------
        return expBinNode;
    }

    /**
     * Función para la regla 65 <ExpIgualR> de la Gramatica
     * @return un nodo expresión binaria con el tipo y el nodo derecho definido
     * @author Lucas Moyano
     * */
    private ExpBin expIgualR() {
        String[] firstOpIgual = {"!=" , "=="};

        // AST---------------------
        ExpBin expBinNode = null;
        // ------------------------

        if (verifyEquals(firstOpIgual)){
            Token operator = opIgual();
            ExpressionNode expNode = expCompuesta();
            ExpBin expBinNodeR = expIgualRF();
            // AST----------------------------------------------------------------------------
            expBinNode = new ExpBin(symbolTable.getCurrentStruct().getName(),
                    symbolTable.getCurrentMethod().getName(), operator);
            if (expBinNodeR == null) { // Caso base
                expBinNode.setRight(expNode);
            } else { // Caso recursivo
                expBinNodeR.setLeft(expNode); // seteamos la izq de la recursión
                expBinNode.setRight(expBinNodeR); // y la derecha del expbin actual
            }
            expBinNode.setType("Bool"); // Esto es porque el resultado va a ser booleano
            // --------------------------------------------------------------------------------
        } else {
            throw createException(this.actualToken, List.of("!=" , "=="),this.actualToken.getLexeme());
        }

        // AST-----------------
        return expBinNode;
    }

    /**
     * Función para la regla 66 <ExpIgualR-F> de la Gramatica
     * @return una expresión binaria o null
     * @author Lucas Moyano
     * */
    private ExpBin expIgualRF() {
        String[] followExpIgualRF = {"&&" , ")" , "," ,
                ";" , "]" , "||" , "$EOF$"};
        String[] firstExpIgualR = {"!=" , "=="};

        // AST----------------------
        ExpBin expBinNode = null;
        // --------------------------

        if(verifyEquals(followExpIgualRF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpIgualR)) {
                expBinNode = expIgualR();
            } else {
                throw createException(this.actualToken, List.of("!=" , "==", "&&" , ")" , "," ,
                        ";" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST----------------
        return expBinNode;
    }

    /**
     * Función para la regla 67 <ExpCompuesta> de la Gramatica
     * @return un nodo expresión del AST
     * @author Lucas Moyano
     * */
    private ExpressionNode expCompuesta() {
        String[] firstExpAd = {"!" , "(" , "+" , "++"
                , "-" , "--" , "StrLiteral" , "CharLiteral"
                , "false" , "ObjID" , "StructID" , "IntLiteral"
                , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpAd)) {
            ExpressionNode expNode = expAd();
            ExpBin expBinNode = expCompuestaF();
            // Analisis Semantico AST -------------------------
            if (expBinNode != null){ // Caso con expresion compuesta
                expBinNode.setLeft(expNode);
                return expBinNode;
            } else { // Caso sin expresion compuesta
                return expNode;
            }
            // ------------------------------------------------
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++"
                    , "-" , "--" , "StrLiteral" , "CharLiteral"
                    , "false" , "ObjID" , "StructID" , "IntLiteral"
                    , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 68 <ExpCompuestaF> de la Gramatica
     * @return una expresion binaria con el derecho y el tipo seteados o null
     * @author Lucas Moyano
     * */
    private ExpBin expCompuestaF() {
        String[] followExpCompuestaF = {"!=" , "&&" , ")" ,
                "," , ";" , "==" ,
                "]" , "||" , "$EOF$"};
        String[] firstOpCompuesto = {"<" , "<=" , ">" , ">="};

        // AST----------------------
        ExpBin expBinNode = null;
        // --------------------------

        if (verifyEquals(followExpCompuestaF)) {
            //Lambda
        } else {
            if (verifyEquals(firstOpCompuesto)) {
                Token operator = opCompuesto();
                ExpressionNode expNode = expAd();
                // AST-----------------------------------------------------------
                expBinNode = new ExpBin(symbolTable.getCurrentStruct().getName(),
                        symbolTable.getCurrentMethod().getName(), operator);
                expBinNode.setType("Bool");
                expBinNode.setRight(expNode);
                // -------------------------------------------------------------
            } else {
                throw createException(this.actualToken, List.of("<" , "<=" , ">" , ">=",
                        "!=" , "&&" , ")" ,
                        "," , ";" , "==" ,
                        "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST----------
        return expBinNode;
    }

    /**
     * Función para la regla 69 <ExpAd> de la Gramatica
     * @return un nodo expresión del AST
     * @author Lucas Moyano
     * */
    private ExpressionNode expAd() {
        String[] firstExpMul = {"!" , "(" , "+" , "++" ,
                "-" , "--" , "StrLiteral" , "CharLiteral" ,
                "false" , "ObjID" , "StructID" , "IntLiteral" ,
                "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpMul)) {
            ExpressionNode expNode = expMul();
            ExpBin expBinNode = expAdF();

            // Analisis Semantico AST -------------------------
            if (expBinNode != null) { // Hay expresiones ad
                expBinNode.setLeft(expNode); // el lado derecho ya se seteó en expAdF
                return expBinNode;
            } else { // En este caso no hay expresiones ad
                return expNode;
            }
            // ------------------------------------------------
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" ,
                    "-" , "--" , "StrLiteral" , "CharLiteral" ,
                    "false" , "ObjID" , "StructID" , "IntLiteral" ,
                    "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 70 <ExpAdF> de la Gramatica
     * @return una expresion binaria sin el izquierdo definido o un null
     * @author Lucas Moyano
     * */
    private ExpBin expAdF() {
        String[] followExpAdF = {"!=" , "&&" , ")" ,
                "," , ";" , "<" , "<=" , "==" ,
                ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstExpAdR = {"+" , "-"};

        // AST----------------------
        ExpBin expBinNode = null;
        // --------------------------

        if (verifyEquals(followExpAdF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpAdR)) {
                expBinNode = expAdR();
            } else {
                throw createException(this.actualToken, List.of("+" , "-", "!=" , "&&" , ")" ,
                        "," , ";" , "<" , "<=" , "==" ,
                        ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST-------------
        return expBinNode;
    }

    /**
     * Función para la regla 71 <ExpAdR> de la Gramatica
     * @return una expresion binaria con el derecho seteado
     * @author Lucas Moyano
     * */
    private ExpBin expAdR() {
        String[] firstOpAd = {"+" , "-"};

        // AST---------------------
        ExpBin expBinNode = null;
        // ------------------------

        if (verifyEquals(firstOpAd)) {
            Token operator = opAd();
            ExpressionNode expNode = expMul();
            ExpBin expBinNodeR = expAdRF();

            // AST----------------------------------------------------------------------------
            expBinNode = new ExpBin(symbolTable.getCurrentStruct().getName(),
                    symbolTable.getCurrentMethod().getName(), operator);
            if (expBinNodeR == null) { // Caso base
                expBinNode.setRight(expNode);
            } else { // Caso recursivo
                expBinNodeR.setLeft(expNode); // seteamos la izq de la recursión
                expBinNode.setRight(expBinNodeR); // y la derecha del expbin actual
            }
            // TODO: definir tipo expbin, si es necesario
            // --------------------------------------------------------------------------------
        } else {
            throw createException(this.actualToken, List.of("+" , "-"),this.actualToken.getLexeme());
        }

        // AST---------------
        return expBinNode;
    }

    /**
     * Función para la regla 72 <ExpAdRF> de la Gramatica
     * @return una expresión binaria o null
     * @author Lucas Moyano
     * */
    private ExpBin expAdRF() {
        String[] followExpAdRF = {"!=" , "&&" , ")" , "," ,
                ";" , "<" , "<=" , "==" , ">" , ">=" ,
                "]" , "||" , "$EOF$"};
        String[] firstExpAdR = {"+" , "-"};

        // AST----------------------
        ExpBin expBinNode = null;
        // --------------------------

        if (verifyEquals(followExpAdRF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpAdR)) {
                expBinNode = expAdR();
            } else {
                throw createException(this.actualToken, List.of("+" , "-", "!=" , "&&" , ")" , "," ,
                        ";" , "<" , "<=" , "==" , ">" , ">=" ,
                        "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST-------------
        return expBinNode;
    }

    /**
     * Función para la regla 73 <ExpMul> de la Gramatica
     * es el encargado de agregar las operaciones binarias con * / % al AST
     * @return un nodo expresión del AST
     * @author Lucas Moyano
     * */
    private ExpressionNode expMul() {
        String[] firstExpUn = {"!" , "(" , "+" , "++" , "-" ,
                "--" , "StrLiteral" , "CharLiteral" , "false" ,
                "ObjID" , "StructID" , "IntLiteral" , "new" ,
                "nil" , "self" , "true"};

        if (verifyEquals(firstExpUn)) {
            ExpressionNode expNode = expUn(null);
            ExpBin expBinNode = expMulF();
            // Analisis Semantico AST -------------------------
            if (expBinNode != null) { // Hay expresiones muls
                expBinNode.setLeft(expNode); // el lado derecho ya se seteó en expMulF
                return expBinNode;
            } else { // En este caso no hay expresiones muls
                return expNode;
            }
            // ------------------------------------------------
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" , "-" ,
                    "--" , "StrLiteral" , "CharLiteral" , "false" ,
                    "ObjID" , "StructID" , "IntLiteral" , "new" ,
                    "nil" , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 74 <ExpMulF> de la Gramatica
     * @return una operacion binaria o null
     * @author Lucas Moyano
     * */
    private ExpBin expMulF() {
        String[] followExpMulF = {"!=" , "&&" , ")" ,
                "+" , "," , "-" , ";" , "<" ,
                "<=" , "==" , ">" , ">=" , "]" ,
                "||" , "$EOF$"};
        String[] firstMulR = {"%" , "*" , "/"};

        // AST---------------------
        ExpBin expBinNode = null;
        // ------------------------

        if (verifyEquals(followExpMulF)) {
            //Lambda
        } else {
            if (verifyEquals(firstMulR)) {
                expBinNode = expMulR();
            } else {
                throw createException(this.actualToken, List.of("%" , "*" , "/", "!=" , "&&" , ")" ,
                        "+" , "," , "-" , ";" , "<" ,
                        "<=" , "==" , ">" , ">=" , "]" ,
                        "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST---------------------
        return expBinNode;
    }

    /**
     * Función para la regla 75 <ExpMulR> de la Gramatica
     * @return un nodo expresión binaria con el tipo y el nodo derecho definido
     * @author Lucas Moyano
     * */
    private ExpBin expMulR() {
        String[] firstOpMul = {"%" , "*" , "/"};

        // AST---------------------
        ExpBin expBinNode = null;
        // ------------------------

        if (verifyEquals(firstOpMul)) {
            Token operator = opMul();
            ExpressionNode expNode = expUn(null);
            ExpBin expBinNodeR = expMulRF();
            // AST----------------------------------------------------------------------------
            expBinNode = new ExpBin(symbolTable.getCurrentStruct().getName(),
                    symbolTable.getCurrentMethod().getName(), operator);
            if (expBinNodeR == null) { // Caso base
                expBinNode.setRight(expNode);
            } else { // Caso recursivo
                expBinNodeR.setLeft(expNode); // seteamos la izq de la recursión
                expBinNode.setRight(expBinNodeR); // y la derecha del expbin actual conectandolo con la recursión
            }
            expBinNode.setType("Int"); // Esto es porque todas las operaciones deben ser enteras
            // para que funcionen los operadores * / %
            // --------------------------------------------------------------------------------
        } else {
            throw createException(this.actualToken, List.of("%" , "*" , "/"),this.actualToken.getLexeme());
        }

        // AST-----------------
        return expBinNode;
    }

    /**
     * Función para la regla 76 <ExpMulRF> de la Gramatica
     * @return una expresión binaria o null
     * @author Lucas Moyano
     * */
    private ExpBin expMulRF() {
        String[] followExpMulRF = {"!=" , "&&" , ")" , "+" ,
                "," , "-" , ";" , "<" , "<=" , "==" ,
                ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstExpMulR = {"%" , "*" , "/"};

        // AST----------------------
        ExpBin expBinNode = null;
        // --------------------------

        if (verifyEquals(followExpMulRF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpMulR)) {
                expBinNode = expMulR();
            } else {
                throw createException(this.actualToken, List.of("%" , "*" , "/", "!=" , "&&" , ")" , "+" ,
                        "," , "-" , ";" , "<" , "<=" , "==" ,
                        ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST----------------
        return expBinNode;
    }

    /**
     * Función para la regla 77 <ExpUn> de la Gramatica
     * además se fija si se ha realizado una expresión unaria
     * @param operator token que nos sirve a identificar si se ha realizado una expresión unaria
     * @return un nodo expresión del AST
     * @author Lucas Moyano
     * */
    private ExpressionNode expUn(Token operator) {
        String[] firstOpUnario = {"!" , "+" , "++" , "-" , "--"};
        String[] firstOperando = {"(" , "StrLiteral" ,
                "CharLiteral" , "false" , "ObjID" , "StructID" ,
                "IntLiteral" , "new" , "nil" , "self" , "true"};

        // AST------------------------------
        ExpressionNode expNode;
        // ----------------------------------

        if (verifyEquals(firstOpUnario)) {
            operator = opUnario();
            expNode = expUn(operator);
        } else {
            if (verifyEquals(firstOperando)) {
                expNode = operando();

                // Analisis Semantico AST -------------------------
                if (operator != null) { // Esto significa que se ha hecho una expresión unaria
                    // devolvemos la expresión unaria
                    ExpressionNode newExpNode = new ExpUn(symbolTable.getCurrentStruct().getName(),
                            symbolTable.getCurrentMethod().getName(), operator, expNode);
                    return newExpNode;
                }
                // ------------------------------------------------
            } else {
                throw createException(this.actualToken, List.of("!" , "+" , "++" , "-" , "--", "(" , "StrLiteral" ,
                        "CharLiteral" , "false" , "ObjID" , "StructID" ,
                        "IntLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
            }
        }

        return expNode;
    }

    /**
     * Función para la regla 78 <OpIgual> de la Gramatica
     * @return devuelve el token del operador
     * @author Lucas Moyano
     * */
    private Token opIgual() {
        String[] firstEqual = {"=="};

        // AST --------------------------------------------------
        Token operator = this.actualToken;
        // ------------------------------------------------------

        if (verifyEquals(firstEqual)){
            match("==");
        } else {
            match("!=");
        }

        // AST --------------------------------------------------
        return operator;
        // ------------------------------------------------------
    }

    /**
     * Función para la regla 79 <OpCompuesto> de la Gramatica
     * @return devuelve el token del operador
     * @author Lucas Moyano
     * */
    private Token opCompuesto() {
        String[] firstLesser = {"<"};
        String[] firstGreater = {">"};
        String[] firstLesserEqual = {"<="};

        // AST --------------------------------------------------
        Token operator = this.actualToken;
        // ------------------------------------------------------

        if (verifyEquals(firstLesser)) {
            match("<");
        } else {
            if (verifyEquals(firstGreater)) {
                match(">");
            } else {
                if (verifyEquals(firstLesserEqual)) {
                    match("<=");
                } else {
                    match(">=");
                }
            }
        }

        // AST ------------------------------
        return operator;
    }

    /**
     * Función para la regla 80 <OpAd> de la Gramatica
     * @return devuelve el token del operador
     * @author Lucas Moyano
     * */
    private Token opAd() {
        String[] firstPlus = {"+"};

        // AST --------------------------------------------------
        Token operator = this.actualToken;
        // ------------------------------------------------------

        if (verifyEquals(firstPlus)) {
            match("+");
        } else {
            match("-");
        }

        // AST ------------------
        return operator;
    }

    /**
     * Función para la regla 81 <OpUnario> de la Gramatica
     * @return devuelve el token del operador
     * @author Lucas Moyano
     * */
    private Token opUnario() {
        String[] firstPlus = {"+"};
        String[] firstMinus = {"-"};
        String[] firstExclamation = {"!"};
        String[] firstPlusPlus = {"++"};

        // AST --------------------------------------------------
        Token operator = this.actualToken;
        // ------------------------------------------------------

        if (verifyEquals(firstPlus)){
            match("+");
        } else {
            if (verifyEquals(firstMinus)){
                match("-");
            } else {
                if (verifyEquals(firstExclamation)){
                    match("!");
                } else {
                    if (verifyEquals(firstPlusPlus)){
                        match("++");
                    } else {
                        match("--");
                    }
                }
            }
        }

        // AST-------------------------
        return operator;
        // ------------------------------
    }

    /**
     * Función para la regla 82 <OpMul> de la Gramatica
     * @return devuelve el token del operador
     * @author Lucas Moyano
     * */
    private Token opMul() {
        String[] firstMultiplication = {"*"};
        String[] firstDivision = {"/"};

        // AST --------------------------------------------------
        Token operator = this.actualToken;
        // ------------------------------------------------------

        if (verifyEquals(firstMultiplication)){
            match("*");
        } else {
            if (verifyEquals(firstDivision)){
                match("/");
            } else {
                match("%");
            }
        }
        // AST--------------------------
        return operator;
    }

    /**
     * Función para la regla 83 <Operando> de la Gramatica
     * @return un nodo expresión
     * @author Lucas Moyano
     * */
    private ExpressionNode operando() {
        String[] firstLiteral = {"StrLiteral" ,
                "CharLiteral" , "false" , "IntLiteral" ,
                "nil" , "true"};
        String[] firstPrimario = {"(" , "ObjID" , "StructID" ,
                "new" , "self"};

        if (verifyEquals(firstLiteral)){
            ExpressionNode expNode = literal();
            // Analisis Semantico AST -------------------------
            return expNode;
            // ------------------------------------------------
        } else {
            if (verifyEquals(firstPrimario)){
                PrimaryNode primaryNode = primario();
                PrimaryNode chained = operandoF();

                // AST----------------------------
                primaryNode.setLastRight(chained);
                primaryNode.setType(chained.getType());
                return primaryNode;
                // -------------------------------
            } else {
                throw createException(this.actualToken, List.of("StrLiteral" ,
                        "CharLiteral" , "false" , "IntLiteral" ,
                        "nil" , "true", "(" , "ObjID" , "StructID" ,
                        "new" , "self"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 84 <OperandoF> de la Gramatica
     * @return un encadenado o null
     * @author Lucas Moyano
     * */
    private PrimaryNode operandoF() {
        String[] followOperandoF = {"!=" , "%" , "&&" ,
                ")" , "*" , "+" , "," , "-" , "/" , ";" ,
                "<" , "<=" , "==" , ">" , ">=" , "]" , "||" ,
                "$EOF$"};
        String[] firstEncadenado = {"."};

        // AST---------------------
        PrimaryNode chained = null;
        // --------------------------

        if (verifyEquals(followOperandoF)) {
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                chained = encadenado();
            } else {
                throw createException(this.actualToken, List.of(".", "!=" , "%" , "&&" ,
                        ")" , "*" , "+" , "," , "-" , "/" , ";" ,
                        "<" , "<=" , "==" , ">" , ">=" , "]" , "||" ,
                        "$EOF$"),this.actualToken.getLexeme());
            }
        }

        return chained;
    }

    /**
     * Función para la regla 85 <Literal> de la Gramatica
     * @author Lucas Moyano
     * @return un LiteralNode con el tipo y el token correctos
     * */
    private ExpressionNode literal(){
        String[] firstNil = {"nil"};
        String[] firstTrue = {"true"};
        String[] firstFalse = {"false"};
        String[] firstIntLiteral = {"IntLiteral"};
        String[] firstStrLiteral = {"StrLiteral"};
        // Analisis Semantico AST -------------------------
        LiteralNode newLiteral = new LiteralNode(symbolTable.getCurrentStruct().getName()
                , symbolTable.getCurrentMethod().getName());
        // ------------------------------------------------

        if (verifyEquals(firstNil)){
            // Analisis Semantico AST -------------------------
            newLiteral.setToken(actualToken);
            newLiteral.setType("nil");
            // ------------------------------------------------
            match("nil");
        }else {
            if (verifyEquals(firstTrue)){
                // Analisis Semantico AST -------------------------
                newLiteral.setToken(actualToken);
                newLiteral.setType("Bool");
                // ------------------------------------------------
                match("true");
            } else {
                if (verifyEquals(firstFalse)) {
                    // Analisis Semantico AST -------------------------
                    newLiteral.setToken(actualToken);
                    newLiteral.setType("Bool");
                    // ------------------------------------------------
                    match("false");
                } else {
                    if (verifyEquals(firstIntLiteral)){
                        // Analisis Semantico AST -------------------------
                        newLiteral.setToken(actualToken);
                        newLiteral.setType("Int");
                        // ------------------------------------------------
                        match("IntLiteral");
                    } else {
                        if (verifyEquals(firstStrLiteral)){
                            // Analisis Semantico AST -------------------------
                            newLiteral.setToken(actualToken);
                            newLiteral.setType("Str");
                            // ------------------------------------------------
                            match("StrLiteral");
                        } else {
                            // Analisis Semantico AST -------------------------
                            newLiteral.setToken(actualToken);
                            newLiteral.setType("Char");
                            // ------------------------------------------------
                            match("CharLiteral");
                        }
                    }
                }
            }
        }
        return newLiteral;
    }

    /**
     * Función para la regla 86 <Primario> de la Gramatica
     * @return un primario del AST
     * @author Lucas Moyano
     * */
    private PrimaryNode primario() {
        String[] firstExpresionParentizada = {"("};
        String[] firstAccesoSelf = {"self"};
        String[] firstAccesoVarAndMethod = {"ObjID"};
        String[] firstLlamadaMetodoEstatico = {"StructID"};
        String[] firstLlamadaConstructor = {"new"};

        PrimaryNode primaryNode = null;

        if (verifyEquals(firstExpresionParentizada)) {
            primaryNode = expresionParentizada();
        } else {
            if (verifyEquals(firstAccesoSelf)){
                primaryNode = accesoSelf();
            } else {
                if (verifyEquals(firstAccesoVarAndMethod)){
                    // Analisis semantico ----------------------------------
                    // ya debería existir este objeto
                    // -----------------------------------------------------
                    // AST-----------------------------------
                    Token savedToken = this.actualToken; // se guarda el token porque todavia no sabemos si es un Array o un Id
                    // --------------------------------------

                    match("ObjID");
                    primaryNode = primarioF(savedToken);

                    if (primaryNode == null) { // Esto pasa cuando es solo una variable sin nada más
                        primaryNode = new IdNode(symbolTable.getCurrentStruct().getName(),
                                symbolTable.getCurrentMethod().getName(), savedToken);
                        ((IdNode)primaryNode).setIdType(IdType.VARIABLE);
                    }
                }
                else {
                    if (verifyEquals(firstLlamadaMetodoEstatico)){
                        primaryNode = llamadaMetodoEstatico();
                    }
                    else {
                        if (verifyEquals(firstLlamadaConstructor)){
                            primaryNode = llamadaConstructor();
                        }
                        else {
                            throw createException(this.actualToken, List.of("(", "self", "ObjID", "StructID", "new"),this.actualToken.getLexeme());
                        }
                    }
                }
            }
        }

        // AST-------------------
        return primaryNode;
    }

    /**
     * @param savedToken el token del ObjId
     * @return un primario
     * @author Lucas Moyano
     * */
    private PrimaryNode primarioF(Token savedToken){
        String[] firstAccesoVarF = {".","["};
        String[] firstArgumentosActuales = {"("};
        String[] followPrimarioF = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};

        // AST----------------------
        PrimaryNode primaryNode = null;
        // -----------------------------------

        if(verifyEquals(firstAccesoVarF)){
            primaryNode = accesoVarF(savedToken);
        }
        else{
           if(verifyEquals(firstArgumentosActuales)){
             List<ExpressionNode> arguments = argumentosActuales();
             PrimaryNode chained = llamadaMetodoF();


             // AST-----------------------------------------
             primaryNode = new IdNode(symbolTable.getCurrentStruct().getName(),
                       symbolTable.getCurrentMethod().getName(), savedToken);
             ((IdNode)primaryNode).setIdType(IdType.METHOD);
             ((IdNode)primaryNode).setArguments(arguments);
             primaryNode.setRight(chained);
             // -------------------------------
           }
           else {
               if(verifyEquals(followPrimarioF)){
                   //Lambda
               }
               else {
                   throw createException(this.actualToken,List.of("!=" ,
                           "%" ,".", "(", "[",  "&&" , ")" , "*" , "+" , "," , "-"
                           , "/" , ";" , "<" , "<=" , "==" , ">" ,
                           ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
               }
           }
        }

        // AST-----------------------
        return primaryNode;
    }


    /**
     * Función para la regla 87 <ExpresionParentizada> de la Gramatica
     * @return una expresión parentizada
     * @author Lucas Moyano
     * */
    private PrimaryNode expresionParentizada() {


        match("(");
        ExpressionNode expNode = expresion();
        match(")");
        PrimaryNode chained = expresionParentizadaF();
        // AST------------------------
        ParenthizedExpression parenthizedExpression = new ParenthizedExpression(symbolTable.getCurrentStruct().getName(),
                symbolTable.getCurrentMethod().getName(), expNode.getToken());
        parenthizedExpression.setParenthizedExpression(expNode);
        parenthizedExpression.setRight(chained);

        return parenthizedExpression;
        // -----------------------------
    }

    /**
     * Función para la regla 88 <ExpresionParentizadaF> de la Gramatica
     * @return un encadenado o null
     * @author Lucas Moyano
     * */
    private PrimaryNode expresionParentizadaF() {
        String[] followExpresionParentizadaF = {"!=" , "%"
                , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "=="
                , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        // AST-----------------------
        PrimaryNode chained = null;
        // --------------------------

        if (verifyEquals(followExpresionParentizadaF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                chained = encadenado();
            } else {
                throw createException(this.actualToken, List.of(".", "!=" , "%"
                        , "&&" , ")" , "*" , "+" , "," , "-"
                        , "/" , ";" , "<" , "<=" , "=="
                        , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST--------------
        return chained;
    }

    /**
     * Función para la regla 89 <AccesoSelf> de la Gramatica
     * @return un id node de tipo self
     * @author Lucas Moyano
     * */
    private PrimaryNode accesoSelf() {
        // AST--------------------------------------------------------------------
        IdNode selfNode = new IdNode(symbolTable.getCurrentStruct().getName(),
                symbolTable.getCurrentMethod().getName(), this.actualToken);
        selfNode.setIdType(IdType.SELF);
        // -----------------------------------------------------------------------
        match("self");
        PrimaryNode chained = accesoSelfF();

        // AST--------------------------------
        selfNode.setRight(chained);
        return selfNode;
    }

    /**
     * Función para la regla 90 <AccesoSelfF> de la Gramatica
     * @return un encadenado
     * @author Lucas Moyano
     * */
    private PrimaryNode accesoSelfF() {
        String[] followAccesoSelfF = {"!=" , "%" ,
                "&&" , ")" , "*" , "+" , "," ,
                "-" , "/" , ";" , "<" , "<=" ,
                "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        // AST------------------------
        PrimaryNode chained = null;
        // ----------------------------

        if (verifyEquals(followAccesoSelfF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                chained = encadenado();
            } else {
                throw createException(this.actualToken, List.of(".", "!=" , "%" ,
                        "&&" , ")" , "*" , "+" , "," ,
                        "-" , "/" , ";" , "<" , "<=" ,
                        "==" , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        return chained;
    }

    /**
     * Función para la regla 91 <AccesoVar> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoVar() {
        // TODO: wtf porque no se llega nunca a esta regla
        match("ObjID");
        accesoVarF(null);
    }

    /**
     * Función para la regla 92 <AccesoVarF> de la Gramatica
     * @return un IdNode variable o un array
     * @author Lucas Moyano
     * */
    private PrimaryNode accesoVarF(Token savedToken) {
        String[] followAccesoVarF = {"!=" , "%" , "&&" , ")" ,
                "*" , "+" , "," , "-" , "/" , ";" , "<" ,
                "<=" , "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};
        String[] firstBracket = {"["};

        // AST-----------------------------
        PrimaryNode primaryNode = null;
        // -------------------------------

        if (verifyEquals(followAccesoVarF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                PrimaryNode chained = encadenado();

                // AST-------------------------
                primaryNode = new IdNode(symbolTable.getCurrentStruct().getName(),
                        symbolTable.getCurrentMethod().getName(), savedToken);
                primaryNode.setRight(chained);
                ((IdNode)primaryNode).setIdType(IdType.VARIABLE);
                // ------------------------------
            } else {
                if (verifyEquals(firstBracket)){
                    match("[");
                    ExpressionNode expNode = expresion();
                    match("]");
                    PrimaryNode chained = accesoVarF1();
                    // AST-----------------------------
                    primaryNode = new ArrayNode(symbolTable.getCurrentStruct().getName(),
                            symbolTable.getCurrentMethod().getName(), savedToken);
                    ((ArrayNode) primaryNode).setLength(expNode);
                    primaryNode.setRight(chained);
                    // ---------------------------------
                } else {
                    throw createException(this.actualToken, List.of("[", "!=" , "%" , "&&" , ")" ,
                            "*" , "+" , "," , "-" , "." , "/" , ";" , "<" ,
                            "<=" , "==" , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
                }
            }
        }

        // AST-------------------
        return primaryNode;
    }

    /**
     * Función para la regla 93 <AccesoVarF1> de la Gramatica
     * @return un encadenado
     * @author Lucas Moyano
     * */
    private PrimaryNode accesoVarF1() {
        String[] followAccesoVarF1 = {"!=" , "%" , "&&" ,
                ")" , "*" , "+" , "," , "-" , "/" ,
                ";" , "<" , "<=" , "==" , ">" , ">=" , "]" ,
                "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        // AST--------------
        PrimaryNode chained = null;
        // -------------------

        if (verifyEquals(followAccesoVarF1)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                chained = encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" , "%" , "&&" ,
                        ")" , "*" , "+" , "," , "-" , "." , "/" ,
                        ";" , "<" , "<=" , "==" , ">" , ">=" , "]" ,
                        "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        return chained;
    }

    /**
     * Función para la regla 94 <Llamada-Método> de la Gramatica
     * @return un id node con un metodo
     * @author Lucas Moyano
     * */
    private IdNode llamadaMetodo() {
        // Analisis semantico ----------------------------------
        // ya debería existir el metodo
        // -----------------------------------------------------

        // AST--------------------------
        IdNode method = new IdNode(symbolTable.getCurrentStruct().getName(),
                symbolTable.getCurrentMethod().getName(), this.actualToken);
        method.setIdType(IdType.METHOD);
        // -----------------------------

        match("ObjID");
        List<ExpressionNode> arguments = argumentosActuales();
        PrimaryNode chained = llamadaMetodoF();

        // AST-----------------------------
        method.setArguments(arguments);
        method.setRight(chained);
        return method;
    }

    /**
     * Función para la regla 95 <Llamada-Método-F> de la Gramatica
     * @return un encadenado
     * @author Lucas Moyano
     * */
    private PrimaryNode llamadaMetodoF() {
        String[] followLlamadaMetodoF = {"!=" , "%" , "&&" , ")" , "*" ,
                        "+" , "," , "-" , "/" , ";" ,
                "<" , "<=" , "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        // AST----------------------
        PrimaryNode chained = null;
        // --------------------------

        if (verifyEquals(followLlamadaMetodoF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                chained = encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" , "%" , "&&" , ")" , "*" ,
                        "+" , "," , "-" , "." , "/" , ";" ,
                        "<" , "<=" , "==" , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST-------------
        return chained;
    }

    /**
     * Función para la regla 96 <Llamada-Método-Estático> de la Gramatica
     * @return un primary node con un metodo estatico seteado
     * @author Lucas Moyano
     * */
    private IdNode llamadaMetodoEstatico() {
        // Analisis semantico ----------------------------------
        // ya debería existir ese metodo
        // -----------------------------------------------------

        // AST-------------------------------------------------
        IdNode staticMethod = new IdNode(symbolTable.getCurrentStruct().getName(),
                symbolTable.getCurrentMethod().getName(), this.actualToken);
        staticMethod.setIdType(IdType.STATIC_METHOD);
        // ----------------------------------------------------
        match("StructID");
        match(".");
        PrimaryNode chained1 = llamadaMetodo();
        PrimaryNode chained2 = llamadaMetodoEstaticoF();

        // AST------------------------------------
        staticMethod.setRight(chained1);
        staticMethod.setLastRight(chained2);
        return staticMethod;
    }

    /**
     * Función para la regla 97 <Llamada-Método-Estático-F> de la Gramatica
     * @return un encadenado
     * @author Lucas Moyano
     * */
    private PrimaryNode llamadaMetodoEstaticoF() {
        String[] followLlamadaMetodoEstaticoF = {"!=" , "%" ,
                "&&" , ")" , "*" , "+" , "," , "-" ,
                "/" , ";" , "<" , "<=" , "==" , ">" , ">=" ,
                "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        // aST-------------------------
        PrimaryNode chained = null;
        // -------------------------------

        if (verifyEquals(followLlamadaMetodoEstaticoF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                chained = encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" , "%" ,
                        "&&" , ")" , "*" , "+" , "," , "-" , "." ,
                        "/" , ";" , "<" , "<=" , "==" , ">" , ">=" ,
                        "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST--------------
        return chained;
    }

    /**
     * Función para la regla 98 <Llamada-Constructor> de la Gramatica
     * @return un nodo primario con el constructor
     * @author Lucas Moyano
     * */
    private PrimaryNode llamadaConstructor() {
        match("new");
        PrimaryNode constructor = llamadaConstructorF();

        // AST------------------
        return constructor;
    }

    /**
     * Función para la regla 99 <Llamada-Constructor-F> de la Gramatica
     * @return un nodo primario con el constructor
     * @author Lucas Moyano
     * */
    private PrimaryNode llamadaConstructorF() {
        String[] firstTipoPrimitivo = {"Bool" , "Char" , "Int" , "Str"};

        // aST---------------------------
        PrimaryNode constructor = null;
        // --------------------------------

        if (verifyEquals(firstTipoPrimitivo)){
            // AST-----------------
            constructor = new ArrayNode(symbolTable.getCurrentStruct().getName(),
                    symbolTable.getCurrentMethod().getName(), this.actualToken);
            // ----------------------------------

            tipoPrimitivo();
            match("[");
            ExpressionNode expNode = expresion();
            match("]");

            // AST----------------------
            ((ArrayNode)constructor).setLength(expNode);

        } else {
            // Analisis semantico ----------------------------------
            // como es un constructor debería ya estar inicializada la variable/el atributo en la TS
            // -----------------------------------------------------
            // AST-------------------------------------------------------------------
            constructor = new IdNode(symbolTable.getCurrentStruct().getName(),
                    symbolTable.getCurrentMethod().getName(), this.actualToken);
            // ----------------------------------------------------------------------

            match("StructID");
            List<ExpressionNode> arguments = argumentosActuales();
            llamadaConstructorF1();

            // AST----------------------------
            ((IdNode)constructor).setIdType(IdType.CONSTRUCTOR);
            ((IdNode)constructor).setArguments(arguments);
            // --------------------------------
        }

        // AST------------------
        return constructor;
    }

    /**
     * Función para la regla 100 <Llamada-Constructor-F1> de la Gramatica
     * @return un encadenado o null
     * @author Lucas Moyano
     * */
    private PrimaryNode llamadaConstructorF1() {
        String[] followLlamadaConstructorF1 = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," ,
                "-" , "/" , ";" , "<" , "<=" ,
                "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        // AST--------------------
        PrimaryNode chained = null;

        if (verifyEquals(followLlamadaConstructorF1)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                chained = encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" ,
                        "%" , "&&" , ")" , "*" , "+" , "," ,
                        "-" , "." , "/" , ";" , "<" , "<=" ,
                        "==" , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST----------------
        return chained;
    }

    /**
     * Función para la regla 101 <Argumentos-Actuales> de la Gramatica
     * @return los argumentos del metodo
     * @author Lucas Moyano
     * */
    private List<ExpressionNode> argumentosActuales() {
        match("(");
        List<ExpressionNode> arguments = argumentosActualesF();

        // AST-------------------
        return arguments;
    }

    /**
     * Función para la regla 102 <Argumentos-Actuales-F> de la Gramatica
     * @return los argumentos del metodo
     * @author Lucas Moyano
     * */
    private List<ExpressionNode> argumentosActualesF() {
        String[] firstListaExpresiones = {"!" , "(" ,
                "+" , "++" , "-" , "--" , "StrLiteral" ,
                "CharLiteral" , "false" , "ObjID" , "StructID" ,
                "IntLiteral" , "new" , "nil" , "self" , "true"};

        List<ExpressionNode> arguments = null;

        if (verifyEquals(firstListaExpresiones)){
            arguments = listaExpresiones(null);
            match(")");
        } else {
            match(")");
        }

        return arguments;
    }

    /**
     * Función para la regla 103 <Lista-Expresiones> de la Gramatica
     * @return la lista de argumentos
     * @author Lucas Moyano
     * */
    private List<ExpressionNode> listaExpresiones(List<ExpressionNode> argumentList) {
        String[] firstExpresion = {"!" , "(" , "+" ,
                "++" , "-" , "--" , "StrLiteral" ,
                "CharLiteral" , "false" , "ObjID" , "StructID" ,
                "IntLiteral" , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpresion)){
            // AST-------------------------
            if (argumentList == null) {// caso base, inicializa la lista de argumentos
                argumentList = new ArrayList<ExpressionNode>();
            }
            // ------------------------------
            ExpressionNode expNode = expresion();
            // AST----------------------------
            argumentList.add(expNode); // esto va añadir recursivamente
            // -------------------------------
            listaExpresionesF(argumentList);

        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" ,
                    "++" , "-" , "--" , "StrLiteral" ,
                    "CharLiteral" , "false" , "ObjID" , "StructID" ,
                    "IntLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
        }

        // AST-------------------
        // este return se va a utilizar solo al final de la recursión para recibir la lista entera
        return argumentList;
    }

    /**
     * Función para la regla 104 <Lista-Expresiones-F> de la Gramatica
     * @param argumentList lista de argumentos a la cual se quiere añadir recursivamente elementos
     * @author Lucas Moyano
     * */
    private void listaExpresionesF(List<ExpressionNode> argumentList) {
        String[] followListaExpresionesF = {")" , "$EOF$"};

        if (verifyEquals(followListaExpresionesF)){
            //Lambda
        } else {
            match(",");
            listaExpresiones(argumentList);
        }
    }

    /**
     * Función para la regla 105 <Encadenado> de la Gramatica
     * @return un encadenado
     * @author Lucas Moyano
     * */
    private PrimaryNode encadenado(){

        match(".");
        PrimaryNode mainChained = encadenadoF();

        // AST---------------
        return mainChained;
    }

    /**
     * Función para la regla 106 <Encadenado-F> de la Gramatica
     * @return un encadenado
     * @author Lucas Moyano
     * */
    private PrimaryNode encadenadoF(){
        // Analisis semantico ----------------------------------
        // ya debería existir el objeto
        // -----------------------------------------------------
        // AST---------------------------
        Token savedToken = this.actualToken;
        // ------------------------------
        match("ObjID");
        PrimaryNode mainChained = encadenadoF1(savedToken);

        // AST-------------
        return mainChained;
    }

    /**
     * Función para la regla 107 <Encadenado-F1> de la Gramatica
     * @return un encadenado o null
     * @author Lucas Moyano
     * */

    private PrimaryNode encadenadoF1(Token savedToken){
        String[] firstArgActuales = {"("};
        String[] firstAccesVarEncadF = {".", "[" };
        String[] followEncadenadoF1 = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};

        // AST-------------------------
        PrimaryNode mainChained = null;
        // -----------------------------

        if(verifyEquals(firstArgActuales)){
            List<ExpressionNode> arguments = argumentosActuales();
            PrimaryNode chained = llamadaMetodoEncadenadoF();

            // AST-------------------------
            mainChained = new IdNode(symbolTable.getCurrentStruct().getName(),
                    symbolTable.getCurrentMethod().getName(), savedToken);
            ((IdNode)mainChained).setArguments(arguments);
            ((IdNode)mainChained).setIdType(IdType.METHOD);
            mainChained.setRight(chained);
            // -----------------------------
        }
        else {
            if(verifyEquals(firstAccesVarEncadF)){
                mainChained = accesoVariableEncadenadoF(savedToken);
            }
            else {
                if(verifyEquals(followEncadenadoF1)){
                    //Lambda
                }
                else {
                    throw createException(this.actualToken, List.of("(",".","["),this.actualToken.getLexeme());
                }

            }
        }

        // AST-----------------------
        return mainChained;
    }

    /**
     * Función para la regla 107 <Llamada-Método-Encadenado> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaMetodoEncadenado() {
        // Analisis semantico ----------------------------------
        // ya debería existir el metodo
        // -----------------------------------------------------
        match("ObjID");
        argumentosActuales();
        llamadaMetodoEncadenadoF();
    }

    /**
     * Función para la regla 108 <Llamada-Método-Encadenado-F> de la Gramatica
     * @return un encadenado o null
     * @author Lucas Moyano
     * */
    private PrimaryNode llamadaMetodoEncadenadoF() {
        String[] followLlamadaMetodoEncadenadoF = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        // AST------------------------------
        PrimaryNode chained = null;
        // -----------------------------------

        if (verifyEquals(followLlamadaMetodoEncadenadoF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                chained = encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" ,
                        "%" , "&&" , ")" , "*" , "+" , "," , "-" ,
                        "." , "/" , ";" , "<" , "<=" , "==" , ">" ,
                        ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        // AST-------------------
        return chained;
    }

    /**
     * Función para la regla 109 <Acceso-Variable-Encadenado> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoVariableEncadenado() {
        // Analisis semantico ----------------------------------
        // ya debería existir la variable
        // -----------------------------------------------------
        match("ObjID");
        accesoVariableEncadenadoF(null);
    }

    /**
     * Función para la regla 110 <Acceso-Variable-Encadenado-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private PrimaryNode accesoVariableEncadenadoF(Token savedToken) {
        String[] followAccesoVariableEncadenadoF = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        // AST--------------------------
        PrimaryNode mainChained = null;
        //--------------------------------

        if (verifyEquals(followAccesoVariableEncadenadoF)) {
            // Lambda
        } else {
            if (verifyEquals(firstEncadenado)) {
                mainChained = encadenado();
            } else {
                match("[");
                ExpressionNode expNode = expresion();
                match("]");
                PrimaryNode chained = accesoVariableEncadenadoF1();

                // AST-----------------------
                mainChained = new ArrayNode(symbolTable.getCurrentStruct().getName(),
                        symbolTable.getCurrentMethod().getName(), savedToken);
                ((ArrayNode)mainChained).setLength(expNode);
                mainChained.setRight(chained);
                // ------------------------------------------
            }
        }

        // AST-----------------
        return mainChained;
    }

    /**
     * Función para la regla 111 <Acceso-Variable-Encadenado-F1> de la Gramatica
     * @return un encadenado o null
     * @author Lucas Moyano
     * */
    private PrimaryNode accesoVariableEncadenadoF1() {
        String[] followAccesoVariableEncadenadoF1 = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        // AST-----------------
        PrimaryNode chained = null;
        // ----------------------------

        if (verifyEquals(followAccesoVariableEncadenadoF1)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                chained = encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" ,
                        "%" , "&&" , ")" , "*" , "+" , "," , "-" ,
                        "." , "/" , ";" , "<" , "<=" , "==" , ">" ,
                        ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }

        return chained;
    }
}
