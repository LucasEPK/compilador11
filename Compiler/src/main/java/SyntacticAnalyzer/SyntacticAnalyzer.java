package SyntacticAnalyzer;

import Exceptions.SyntacticExceptions.SyntacticException;
import LexicalAnalyzer.LexicalAnalyzer;
import LexicalAnalyzer.Token;
import SemanticAnalyzer.AST.AST;
import SemanticAnalyzer.AST.BlockNode;
import SemanticAnalyzer.AST.SentenceNode;
import SemanticAnalyzer.SymbolTable.SymbolTable;

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
     * @author Yeumen Silva
     * */

    private void bloqueMetodo(){
        match("{");
        bloqueMetodoF();

    }

    /**
     * Regla Bloque-Metodo-F
     * @author Yeumen Silva
     * */

    private void bloqueMetodoF(){

        //Primeros Decl-Var-Locales-Estrella
        if(verifyEquals("Array" , "Bool" , "Char" , "Int" , "Str"
                , "StructID")){
            declVarLocalesEstrella();
            bloqueMetodoF1();
        }else {
            //Primeros Sentencia-Estrella
            if (verifyEquals("(" , ";" , "ObjID" , "if"
                    , "ret" , "self" , "while", "{")){
                //Análisis semántico AST-----------------------------------------
                ArrayList<SentenceNode> sentenceNodeList = new ArrayList<>();
                sentenciaEstrella(sentenceNodeList);
                BlockNode blockNode = new BlockNode(this.symbolTable.getCurrentStruct().getName(),
                        this.symbolTable.getCurrentMethod().getName(),
                        sentenceNodeList);
                this.ast.addBlock(blockNode);
                //---------------------------------------------------------------
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
                //Análisis semántico AST-----------------------------------------
                ArrayList<SentenceNode> sentenceNodeList = new ArrayList<>();
                sentenciaEstrella(sentenceNodeList);
                BlockNode blockNode = new BlockNode(this.symbolTable.getCurrentStruct().getName(),
                        this.symbolTable.getCurrentMethod().getName(),
                        sentenceNodeList);
                this.ast.addBlock(blockNode);
                //---------------------------------------------------------------
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
     * @author Yeumen Silva
     * */

    private void sentenciaEstrella(ArrayList<SentenceNode> sentenceNodeList){
        sentencia();
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
            sentenciaEstrella(sentenceNodeList);
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
     * @author Yeumen Silva
     * */

    private void sentencia(){
        //Primeros ;
        if (verifyEquals(";")){
            match(";");
        }
        else {
            //Primeros Asignacion
            if(verifyEquals("ObjID","self")){
                asignacion();
                match(";");
            }
            else {
                //Primeros Sentencia-Simple
                if(verifyEquals("(")){
                    sentenciaSimple();
                    match(";");
                }
                else {
                    //Primeros if
                    if (verifyEquals("if")){
                        match("if");
                        match("(");
                        expresion();
                        match(")");
                        sentencia();
                        sentenciaF();
                    }
                    else {
                        //Primeros while
                        if (verifyEquals("while")){
                            match("while");
                            match("(");
                            expresion();
                            match(")");
                            sentencia();
                        }
                        else {
                            //Primeros Bloque
                            if (verifyEquals("{")){
                                bloque();
                            }
                            else {
                                //Primeros ret
                                if (verifyEquals("ret")){
                                    match("ret");
                                    sentenciaF1();
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
    }

    /**
     * Regla Sentencia-F
     * @author Yeumen Silva
     * */

    private void sentenciaF(){
        //Primeros else
        if (verifyEquals("else")){
            match("else");
            sentencia();
        }
        /*Aca deberian estar los siguientes de SentenciaF
        pero como tambien tienen "else", lo mejor es patear el error
        para daspues, ya que con los siguientes solo hacemos lambda
         */
    }

    /**
     * Regla Sentencia-F1
     * @author Yeumen Silva
     * */

    private void sentenciaF1(){
        //Primeros ;
        if (verifyEquals(";")){
            match(";");
        }
        else {
            //Primeros Expresion
            if (verifyEquals("!" , "(" , "+" , "++" , "-" , "--"
                    , "StrLiteral", "CharLiteral" , "false" , "ObjID"
                    , "StructID" , "IntLiteral" , "new" , "nil" , "self" , "true")){
                expresion();
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

    private void bloque(){
        match("{");
        bloqueF();

    }

    /**
     * Regla Bloque-F
     * @author Yeumen Silva
     * */

    private void bloqueF(){
        //Primeros }
        if(verifyEquals("}")){
            match("}");
        }
        else {
            //Primeros Setntencia-Estrella
            if(verifyEquals("(" , ";" , "ObjID" , "if" , "ret" ,
                    "self" , "while" , "{")){
                sentenciaEstrella(sentenceNodeList);
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
     * @author Yeumen Silva
     * */

    private void asignacion(){
        //Primeros AccesoVar-Simple
        if (verifyEquals("ObjID")){
            accesoVarSimple();
            match("=");
            expresion();
        }
        else {
            //`Primeros AccesoSelf-Simple
            if(verifyEquals("self")){
                accesoSelfSimple();
                match("=");
                expresion();
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

    private void accesoVarSimple(){
        // Analisis semantico ----------------------------------
        // acá ya debería existir este objeto
        // -----------------------------------------------------
        match("ObjID");
        accesoVarSimpleF();
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

    private void accesoSelfSimple(){
        match("self");
        accesoSelfSimpleF();
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

    private void sentenciaSimple(){
        match("(");
        expresion();
        match(")");
    }

    /**
     * Regla Expresion
     * @author Yeumen Silva
     * */

    private void expresion(){
        expOr();
    }

    /**
     * Función para la regla 55 <ExpOr> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expOr() {
        String[] firstExpAnd = {"!", "(", "+" , "++" , "-" , "--" ,
                "StrLiteral" , "CharLiteral" , "false" , "ObjID" , "StructID" ,
                "IntLiteral" , "new" , "nil" , "self" , "true"};

        if(verifyEquals(firstExpAnd)){
            expAnd();
            expOrF();
        } else {
            throw createException(this.actualToken, List.of("!", "(", "+" , "++" , "-" , "--" ,
                    "StrLiteral" , "CharLiteral" , "false" , "ObjID" , "StructID" ,
                    "IntLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
        }

    }

    /**
     * Función para la regla 56 <ExpOr-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expOrF() {
        String[] followExpOrF = {")" , "," , ";" , "]" , "$EOF$"};
        String[] firstExpOrR = {"||"};

        if(verifyEquals(followExpOrF)){ // Esto es por Lambda
            //Lambda
        } else {
            if (verifyEquals(firstExpOrR)){
                expOrR();
            } else {
                throw createException(this.actualToken, List.of("||", ")" , "," ,
                        ";" , "]" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 57 <ExpOrR> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expOrR(){

        match("||");
        expAnd();
        expOrRF();
    }

    /**
     * Función para la regla 58 <ExpOrR-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expOrRF() {
        String[] followExpOrRF = {")" , "," , ";" , "]" , "$EOF$"};
        String[] firstExpOrR = {"||"};

        if (verifyEquals(followExpOrRF)) {
            // Lambda
        } else {
            if (verifyEquals(firstExpOrR)) {
                expOrR();
            } else {
                throw createException(this.actualToken, List.of("||" ,")" , "," ,
                        ";" , "]" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 59 <ExpAnd> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expAnd() {
        String[] firstExpIgual = {"!" , "(" , "+" , "++" , "-" , "--" , "StrLiteral" , "CharLiteral" ,
                "false" , "ObjID" , "StructID" , "IntLiteral" , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpIgual)) {
            expIgual();
            expAndF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" , "-" , "--" , "StrLiteral" , "CharLiteral" ,
                    "false" , "ObjID" , "StructID" , "IntLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 60 <ExpAnd-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expAndF() {
        String[] followExpAndF = {")" , "," , ";" , "]" , "||" , "$EOF$"};
        String[] firstExpAndR = {"&&"};

        if(verifyEquals(followExpAndF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpAndR)) {
                expAndR();
            } else {
                throw createException(this.actualToken, List.of("&&", ")" , "," , ";" ,
                        "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 61 <ExpAndR> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expAndR() {
        match("&&");
        expIgual();
        expAndRF();
    }

    /**
     * Función para la regla 62 <ExpAndR-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expAndRF() {
        String[] followExpAndRF = {")" , "," , ";" , "]" , "||" , "$EOF$"};
        String[] firstExpAndR = {"&&"};

        if(verifyEquals(followExpAndRF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpAndR)) {
                expAndR();
            } else {
                throw createException(this.actualToken, List.of("&&" , ")" , "," , ";" ,
                        "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 63 <ExpIgual> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expIgual() {
        String[] firstExpCompuesta = {"!" , "(" , "+" , "++" , "-"
                , "--" , "StrLiteral" , "CharLiteral" , "false"
                , "ObjID" , "StructID" , "IntLiteral" , "new" , "nil"
                , "self" , "true"};

        if (verifyEquals(firstExpCompuesta)) {
            expCompuesta();
            expIgualF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" , "-"
                    , "--" , "StrLiteral" , "CharLiteral" , "false"
                    , "ObjID" , "StructID" , "IntLiteral" , "new" , "nil"
                    , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 64 <ExpIgual-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expIgualF() {
        String[] followExpIgualF = {"&&" , ")" , "," , ";" , "]" , "||" , "$EOF$"};
        String[] firstExpIgualR = {"!=" , "=="};

        if(verifyEquals(followExpIgualF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpIgualR)) {
                expIgualR();
            } else {
                throw createException(this.actualToken, List.of("!=" , "==" ,"&&" ,
                        ")" , "," , ";" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 65 <ExpIgualR> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expIgualR() {
        String[] firstOpIgual = {"!=" , "=="};
        if (verifyEquals(firstOpIgual)){
            opIgual();
            expCompuesta();
            expIgualRF();
        } else {
            throw createException(this.actualToken, List.of("!=" , "=="),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 66 <ExpIgualR-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expIgualRF() {
        String[] followExpIgualRF = {"&&" , ")" , "," ,
                ";" , "]" , "||" , "$EOF$"};
        String[] firstExpIgualR = {"!=" , "=="};

        if(verifyEquals(followExpIgualRF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpIgualR)) {
                expIgualR();
            } else {
                throw createException(this.actualToken, List.of("!=" , "==", "&&" , ")" , "," ,
                        ";" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 67 <ExpCompuesta> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expCompuesta() {
        String[] firstExpAd = {"!" , "(" , "+" , "++"
                , "-" , "--" , "StrLiteral" , "CharLiteral"
                , "false" , "ObjID" , "StructID" , "IntLiteral"
                , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpAd)) {
            expAd();
            expCompuestaF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++"
                    , "-" , "--" , "StrLiteral" , "CharLiteral"
                    , "false" , "ObjID" , "StructID" , "IntLiteral"
                    , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 68 <ExpCompuestaF> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expCompuestaF() {
        String[] followExpCompuestaF = {"!=" , "&&" , ")" ,
                "," , ";" , "==" ,
                "]" , "||" , "$EOF$"};
        String[] firstOpCompuesto = {"<" , "<=" , ">" , ">="};

        if (verifyEquals(followExpCompuestaF)) {
            //Lambda
        } else {
            if (verifyEquals(firstOpCompuesto)) {
                opCompuesto();
                expAd();
            } else {
                throw createException(this.actualToken, List.of("<" , "<=" , ">" , ">=",
                        "!=" , "&&" , ")" ,
                        "," , ";" , "==" ,
                        "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 69 <ExpAd> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expAd() {
        String[] firstExpMul = {"!" , "(" , "+" , "++" ,
                "-" , "--" , "StrLiteral" , "CharLiteral" ,
                "false" , "ObjID" , "StructID" , "IntLiteral" ,
                "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpMul)) {
            expMul();
            expAdF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" ,
                    "-" , "--" , "StrLiteral" , "CharLiteral" ,
                    "false" , "ObjID" , "StructID" , "IntLiteral" ,
                    "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 70 <ExpAdF> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expAdF() {
        String[] followExpAdF = {"!=" , "&&" , ")" ,
                "," , ";" , "<" , "<=" , "==" ,
                ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstExpAdR = {"+" , "-"};

        if (verifyEquals(followExpAdF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpAdR)) {
                expAdR();
            } else {
                throw createException(this.actualToken, List.of("+" , "-", "!=" , "&&" , ")" ,
                        "," , ";" , "<" , "<=" , "==" ,
                        ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 71 <ExpAdR> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expAdR() {
        String[] firstOpAd = {"+" , "-"};

        if (verifyEquals(firstOpAd)) {
            opAd();
            expMul();
            expAdRF();
        } else {
            throw createException(this.actualToken, List.of("+" , "-"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 72 <ExpAdRF> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expAdRF() {
        String[] followExpAdRF = {"!=" , "&&" , ")" , "," ,
                ";" , "<" , "<=" , "==" , ">" , ">=" ,
                "]" , "||" , "$EOF$"};
        String[] firstExpAdR = {"+" , "-"};

        if (verifyEquals(followExpAdRF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpAdR)) {
                expAdR();
            } else {
                throw createException(this.actualToken, List.of("+" , "-", "!=" , "&&" , ")" , "," ,
                        ";" , "<" , "<=" , "==" , ">" , ">=" ,
                        "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 73 <ExpMul> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expMul() {
        String[] firstExpUn = {"!" , "(" , "+" , "++" , "-" ,
                "--" , "StrLiteral" , "CharLiteral" , "false" ,
                "ObjID" , "StructID" , "IntLiteral" , "new" ,
                "nil" , "self" , "true"};

        if (verifyEquals(firstExpUn)) {
            expUn();
            expMulF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" , "-" ,
                    "--" , "StrLiteral" , "CharLiteral" , "false" ,
                    "ObjID" , "StructID" , "IntLiteral" , "new" ,
                    "nil" , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 74 <ExpMulF> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expMulF() {
        String[] followExpMulF = {"!=" , "&&" , ")" ,
                "+" , "," , "-" , ";" , "<" ,
                "<=" , "==" , ">" , ">=" , "]" ,
                "||" , "$EOF$"};
        String[] firstMulR = {"%" , "*" , "/"};

        if (verifyEquals(followExpMulF)) {
            //Lambda
        } else {
            if (verifyEquals(firstMulR)) {
                expMulR();
            } else {
                throw createException(this.actualToken, List.of("%" , "*" , "/", "!=" , "&&" , ")" ,
                        "+" , "," , "-" , ";" , "<" ,
                        "<=" , "==" , ">" , ">=" , "]" ,
                        "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 75 <ExpMulR> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expMulR() {
        String[] firstOpMul = {"%" , "*" , "/"};

        if (verifyEquals(firstOpMul)) {
            opMul();
            expUn();
            expMulRF();
        } else {
            throw createException(this.actualToken, List.of("%" , "*" , "/"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 76 <ExpMulRF> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expMulRF() {
        String[] followExpMulRF = {"!=" , "&&" , ")" , "+" ,
                "," , "-" , ";" , "<" , "<=" , "==" ,
                ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstExpMulR = {"%" , "*" , "/"};

        if (verifyEquals(followExpMulRF)) {
            //Lambda
        } else {
            if (verifyEquals(firstExpMulR)) {
                expMulR();
            } else {
                throw createException(this.actualToken, List.of("%" , "*" , "/", "!=" , "&&" , ")" , "+" ,
                        "," , "-" , ";" , "<" , "<=" , "==" ,
                        ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 77 <ExpUn> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expUn() {
        String[] firstOpUnario = {"!" , "+" , "++" , "-" , "--"};
        String[] firstOperando = {"(" , "StrLiteral" ,
                "CharLiteral" , "false" , "ObjID" , "StructID" ,
                "IntLiteral" , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstOpUnario)) {
            opUnario();
            expUn();
        } else {
            if (verifyEquals(firstOperando)) {
                operando();
            } else {
                throw createException(this.actualToken, List.of("!" , "+" , "++" , "-" , "--", "(" , "StrLiteral" ,
                        "CharLiteral" , "false" , "ObjID" , "StructID" ,
                        "IntLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 78 <OpIgual> de la Gramatica
     * @author Lucas Moyano
     * */
    private void opIgual() {
        String[] firstEqual = {"=="};

        if (verifyEquals(firstEqual)){
            match("==");
        } else {
            match("!=");
        }
    }

    /**
     * Función para la regla 79 <OpCompuesto> de la Gramatica
     * @author Lucas Moyano
     * */
    private void opCompuesto() {
        String[] firstLesser = {"<"};
        String[] firstGreater = {">"};
        String[] firstLesserEqual = {"<="};
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
    }

    /**
     * Función para la regla 80 <OpAd> de la Gramatica
     * @author Lucas Moyano
     * */
    private void opAd() {
        String[] firstPlus = {"+"};

        if (verifyEquals(firstPlus)) {
            match("+");
        } else {
            match("-");
        }
    }

    /**
     * Función para la regla 81 <OpUnario> de la Gramatica
     * @author Lucas Moyano
     * */
    private void opUnario() {
        String[] firstPlus = {"+"};
        String[] firstMinus = {"-"};
        String[] firstExclamation = {"!"};
        String[] firstPlusPlus = {"++"};

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
    }

    /**
     * Función para la regla 82 <OpMul> de la Gramatica
     * @author Lucas Moyano
     * */
    private void opMul() {
        String[] firstMultiplication = {"*"};
        String[] firstDivision = {"/"};

        if (verifyEquals(firstMultiplication)){
            match("*");
        } else {
            if (verifyEquals(firstDivision)){
                match("/");
            } else {
                match("%");
            }
        }
    }

    /**
     * Función para la regla 83 <Operando> de la Gramatica
     * @author Lucas Moyano
     * */
    private void operando() {
        String[] firstLiteral = {"StrLiteral" ,
                "CharLiteral" , "false" , "IntLiteral" ,
                "nil" , "true"};
        String[] firstPrimario = {"(" , "ObjID" , "StructID" ,
                "new" , "self"};

        if (verifyEquals(firstLiteral)){
            literal();
        } else {
            if (verifyEquals(firstPrimario)){
                primario();
                operandoF();
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
     * @author Lucas Moyano
     * */
    private void operandoF() {
        String[] followOperandoF = {"!=" , "%" , "&&" ,
                ")" , "*" , "+" , "," , "-" , "/" , ";" ,
                "<" , "<=" , "==" , ">" , ">=" , "]" , "||" ,
                "$EOF$"};
        String[] firstEncadenado = {"."};

        if (verifyEquals(followOperandoF)) {
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                encadenado();
            } else {
                throw createException(this.actualToken, List.of(".", "!=" , "%" , "&&" ,
                        ")" , "*" , "+" , "," , "-" , "/" , ";" ,
                        "<" , "<=" , "==" , ">" , ">=" , "]" , "||" ,
                        "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 85 <Literal> de la Gramatica
     * @author Lucas Moyano
     * */
    private void literal(){
        String[] firstNil = {"nil"};
        String[] firstTrue = {"true"};
        String[] firstFalse = {"false"};
        String[] firstIntLiteral = {"IntLiteral"};
        String[] firstStrLiteral = {"StrLiteral"};

        if (verifyEquals(firstNil)){
            match("nil");
        }else {
            if (verifyEquals(firstTrue)){
                match("true");
            } else {
                if (verifyEquals(firstFalse)) {
                    match("false");
                } else {
                    if (verifyEquals(firstIntLiteral)){
                        match("IntLiteral");
                    } else {
                        if (verifyEquals(firstStrLiteral)){
                            match("StrLiteral");
                        } else {
                            match("CharLiteral");
                        }
                    }
                }
            }
        }
    }

    /**
     * Función para la regla 86 <Primario> de la Gramatica
     * @author Lucas Moyano
     * */
    private void primario() {
        String[] firstExpresionParentizada = {"("};
        String[] firstAccesoSelf = {"self"};
        String[] firstAccesoVarAndMethod = {"ObjID"};
        String[] firstLlamadaMetodoEstatico = {"StructID"};
        String[] firstLlamadaConstructor = {"new"};

        if (verifyEquals(firstExpresionParentizada)) {
            expresionParentizada();
        } else {
            if (verifyEquals(firstAccesoSelf)){
                accesoSelf();
            } else {
                if (verifyEquals(firstAccesoVarAndMethod)){
                    // Analisis semantico ----------------------------------
                    // ya debería existir este objeto
                    // -----------------------------------------------------
                    match("ObjID");
                    primarioF();
                }
                else {
                    if (verifyEquals(firstLlamadaMetodoEstatico)){
                        llamadaMetodoEstatico();
                    }
                    else {
                        if (verifyEquals(firstLlamadaConstructor)){
                            llamadaConstructor();
                        }
                        else {
                            throw createException(this.actualToken, List.of("(", "self", "ObjID", "StructID", "new"),this.actualToken.getLexeme());
                        }
                    }
                }
            }
        }
    }

    private void primarioF(){
        String[] firstAccesoVarF = {".","["};
        String[] firstArgumentosActuales = {"("};
        String[] followPrimarioF = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};

        if(verifyEquals(firstAccesoVarF)){
            accesoVarF();
        }
        else{
           if(verifyEquals(firstArgumentosActuales)){
             argumentosActuales();
             llamadaMetodoF();
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

    }


    /**
     * Función para la regla 87 <ExpresionParentizada> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expresionParentizada() {
        match("(");
        expresion();
        match(")");
        expresionParentizadaF();
    }

    /**
     * Función para la regla 88 <ExpresionParentizadaF> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expresionParentizadaF() {
        String[] followExpresionParentizadaF = {"!=" , "%"
                , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "=="
                , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        if (verifyEquals(followExpresionParentizadaF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                encadenado();
            } else {
                throw createException(this.actualToken, List.of(".", "!=" , "%"
                        , "&&" , ")" , "*" , "+" , "," , "-"
                        , "/" , ";" , "<" , "<=" , "=="
                        , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 89 <AccesoSelf> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoSelf() {
        match("self");
        accesoSelfF();
    }

    /**
     * Función para la regla 90 <AccesoSelfF> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoSelfF() {
        String[] followAccesoSelfF = {"!=" , "%" ,
                "&&" , ")" , "*" , "+" , "," ,
                "-" , "/" , ";" , "<" , "<=" ,
                "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        if (verifyEquals(followAccesoSelfF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                encadenado();
            } else {
                throw createException(this.actualToken, List.of(".", "!=" , "%" ,
                        "&&" , ")" , "*" , "+" , "," ,
                        "-" , "/" , ";" , "<" , "<=" ,
                        "==" , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 91 <AccesoVar> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoVar() {
        // TODO: wtf porque no se llega nunca a esta regla
        match("ObjID");
        accesoVarF();
    }

    /**
     * Función para la regla 92 <AccesoVarF> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoVarF() {
        String[] followAccesoVarF = {"!=" , "%" , "&&" , ")" ,
                "*" , "+" , "," , "-" , "/" , ";" , "<" ,
                "<=" , "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};
        String[] firstBracket = {"["};

        if (verifyEquals(followAccesoVarF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                encadenado();
            } else {
                if (verifyEquals(firstBracket)){
                    match("[");
                    expresion();
                    match("]");
                    accesoVarF1();
                } else {
                    throw createException(this.actualToken, List.of("[", "!=" , "%" , "&&" , ")" ,
                            "*" , "+" , "," , "-" , "." , "/" , ";" , "<" ,
                            "<=" , "==" , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
                }
            }
        }
    }

    /**
     * Función para la regla 93 <AccesoVarF1> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoVarF1() {
        String[] followAccesoVarF1 = {"!=" , "%" , "&&" ,
                ")" , "*" , "+" , "," , "-" , "/" ,
                ";" , "<" , "<=" , "==" , ">" , ">=" , "]" ,
                "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        if (verifyEquals(followAccesoVarF1)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" , "%" , "&&" ,
                        ")" , "*" , "+" , "," , "-" , "." , "/" ,
                        ";" , "<" , "<=" , "==" , ">" , ">=" , "]" ,
                        "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 94 <Llamada-Método> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaMetodo() {
        // Analisis semantico ----------------------------------
        // ya debería existir el metodo
        // -----------------------------------------------------
        match("ObjID");
        argumentosActuales();
        llamadaMetodoF();
    }

    /**
     * Función para la regla 95 <Llamada-Método-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaMetodoF() {
        String[] followLlamadaMetodoF = {"!=" , "%" , "&&" , ")" , "*" ,
                        "+" , "," , "-" , "/" , ";" ,
                "<" , "<=" , "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        if (verifyEquals(followLlamadaMetodoF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" , "%" , "&&" , ")" , "*" ,
                        "+" , "," , "-" , "." , "/" , ";" ,
                        "<" , "<=" , "==" , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 96 <Llamada-Método-Estático> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaMetodoEstatico() {
        // Analisis semantico ----------------------------------
        // ya debería existir ese metodo
        // -----------------------------------------------------
        match("StructID");
        match(".");
        llamadaMetodo();
        llamadaMetodoEstaticoF();
    }

    /**
     * Función para la regla 97 <Llamada-Método-Estático-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaMetodoEstaticoF() {
        String[] followLlamadaMetodoEstaticoF = {"!=" , "%" ,
                "&&" , ")" , "*" , "+" , "," , "-" ,
                "/" , ";" , "<" , "<=" , "==" , ">" , ">=" ,
                "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        if (verifyEquals(followLlamadaMetodoEstaticoF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" , "%" ,
                        "&&" , ")" , "*" , "+" , "," , "-" , "." ,
                        "/" , ";" , "<" , "<=" , "==" , ">" , ">=" ,
                        "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 98 <Llamada-Constructor> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaConstructor() {
        match("new");
        llamadaConstructorF();
    }

    /**
     * Función para la regla 99 <Llamada-Constructor-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaConstructorF() {
        String[] firstTipoPrimitivo = {"Bool" , "Char" , "Int" , "Str"};

        if (verifyEquals(firstTipoPrimitivo)){
            tipoPrimitivo();
            match("[");
            expresion();
            match("]");
        } else {
            // Analisis semantico ----------------------------------
            // como es un constructor debería ya estar inicializada la variable/el atributo en la TS
            // -----------------------------------------------------
            match("StructID");
            argumentosActuales();
            llamadaConstructorF1();
        }
    }

    /**
     * Función para la regla 100 <Llamada-Constructor-F1> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaConstructorF1() {
        String[] followLlamadaConstructorF1 = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," ,
                "-" , "/" , ";" , "<" , "<=" ,
                "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        if (verifyEquals(followLlamadaConstructorF1)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" ,
                        "%" , "&&" , ")" , "*" , "+" , "," ,
                        "-" , "." , "/" , ";" , "<" , "<=" ,
                        "==" , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 101 <Argumentos-Actuales> de la Gramatica
     * @author Lucas Moyano
     * */
    private void argumentosActuales() {
        match("(");
        argumentosActualesF();
    }

    /**
     * Función para la regla 102 <Argumentos-Actuales-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void argumentosActualesF() {
        String[] firstListaExpresiones = {"!" , "(" ,
                "+" , "++" , "-" , "--" , "StrLiteral" ,
                "CharLiteral" , "false" , "ObjID" , "StructID" ,
                "IntLiteral" , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstListaExpresiones)){
            listaExpresiones();
            match(")");
        } else {
            match(")");
        }
    }

    /**
     * Función para la regla 103 <Lista-Expresiones> de la Gramatica
     * @author Lucas Moyano
     * */
    private void listaExpresiones() {
        String[] firstExpresion = {"!" , "(" , "+" ,
                "++" , "-" , "--" , "StrLiteral" ,
                "CharLiteral" , "false" , "ObjID" , "StructID" ,
                "IntLiteral" , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpresion)){
            expresion();
            listaExpresionesF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" ,
                    "++" , "-" , "--" , "StrLiteral" ,
                    "CharLiteral" , "false" , "ObjID" , "StructID" ,
                    "IntLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
        }
    }

    /**
     * Función para la regla 104 <Lista-Expresiones-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void listaExpresionesF() {
        String[] followListaExpresionesF = {")" , "$EOF$"};

        if (verifyEquals(followListaExpresionesF)){
            //Lambda
        } else {
            match(",");
            listaExpresiones();
        }
    }

    /**
     * Función para la regla 105 <Encadenado> de la Gramatica
     * @author Lucas Moyano
     * */
    private void encadenado(){
        match(".");
        encadenadoF();
    }

    /**
     * Función para la regla 106 <Encadenado-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void encadenadoF(){
        // Analisis semantico ----------------------------------
        // ya debería existir el objeto
        // -----------------------------------------------------
        match("ObjID");
        encadenadoF1();
    }

    /**
     * Función para la regla 107 <Encadenado-F1> de la Gramatica
     * @author Lucas Moyano
     * */

    private void encadenadoF1(){
        String[] firstArgActuales = {"("};
        String[] firstAccesVarEncadF = {".", "[" };
        String[] followEncadenadoF1 = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};

        if(verifyEquals(firstArgActuales)){
            argumentosActuales();
            llamadaMetodoEncadenadoF();
        }
        else {
            if(verifyEquals(firstAccesVarEncadF)){
                accesoVariableEncadenadoF();
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
     * @author Lucas Moyano
     * */
    private void llamadaMetodoEncadenadoF() {
        String[] followLlamadaMetodoEncadenadoF = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        if (verifyEquals(followLlamadaMetodoEncadenadoF)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" ,
                        "%" , "&&" , ")" , "*" , "+" , "," , "-" ,
                        "." , "/" , ";" , "<" , "<=" , "==" , ">" ,
                        ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
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
        accesoVariableEncadenadoF();
    }

    /**
     * Función para la regla 110 <Acceso-Variable-Encadenado-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoVariableEncadenadoF() {
        String[] followAccesoVariableEncadenadoF = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        if (verifyEquals(followAccesoVariableEncadenadoF)) {
            // Lambda
        } else {
            if (verifyEquals(firstEncadenado)) {
                encadenado();
            } else {
                match("[");
                expresion();
                match("]");
                accesoVariableEncadenadoF1();
            }
        }
    }

    /**
     * Función para la regla 111 <Acceso-Variable-Encadenado-F1> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoVariableEncadenadoF1() {
        String[] followAccesoVariableEncadenadoF1 = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-"
                , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."};

        if (verifyEquals(followAccesoVariableEncadenadoF1)){
            //Lambda
        } else {
            if (verifyEquals(firstEncadenado)){
                encadenado();
            } else {
                throw createException(this.actualToken, List.of("!=" ,
                        "%" , "&&" , ")" , "*" , "+" , "," , "-" ,
                        "." , "/" , ";" , "<" , "<=" , "==" , ">" ,
                        ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }
}
