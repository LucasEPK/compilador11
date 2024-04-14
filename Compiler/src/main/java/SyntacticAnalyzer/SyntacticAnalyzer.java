package SyntacticAnalyzer;

import Exceptions.LexicalException;
import Exceptions.SyntacticException;
import LexicalAnalyzer.Token;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Clase que será la encargada de ejecutar nuestro análisis sintáctico
 * @author Yeumen Silva
 */

public class SyntacticAnalyzer {

    private Token actualToken;
    private SyntacticExecutor syntacticExecutor;


    /**
     * Constructor del Analizador Sintáctico que inicializa el Executor
     * el cual es el encargado de pasar los tokens y evaluar los errores
     * Léxicos y a su vez se encarga de llamar a la primera regla
     * de nuestra gramática
     * @param inputPath Path con archivo de entrada
     * @param outputPath Path con archivo de salida
     * @author Yeumen Silva
     * */

    public SyntacticAnalyzer(String inputPath, String outputPath){

        /* Inicio Executor el cual pasa tokens y
         verifica que no haya errores léxicos
        */
        this.syntacticExecutor = new SyntacticExecutor(inputPath,outputPath);
        this.actualToken = this.syntacticExecutor.getNextToken();

        try {
            // llamada a regla inicial de nuestra gramática
            program();
        }
        catch (SyntacticException exception){
            this.syntacticExecutor.printException(exception);
        }



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

                this.actualToken = syntacticExecutor.getNextToken();
                return true;

            }
            else {
                throw createException(this.actualToken, List.of(actualname), this.actualToken.getToken());
            }
        }

        //Verifico si matchea el lexema del token actual con el lexema esperado
        if(Objects.equals(this.actualToken.getLexeme(), actualname)){

            this.actualToken = syntacticExecutor.getNextToken();
            return true;

        }
        else {
            throw createException(this.actualToken, List.of(actualname),this.actualToken.getLexeme());
        }

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

        for (String lexeme : name){

            if(Objects.equals(actualLexeme,lexeme)){
                return true;
            }
        }
        return false;
    }

    /*

    /**
     * Regla inicial de nuestra gramática
     * @author Yeumen Silva
     * */

    private void program() {

        //Primeros Start
        if (verifyEquals("start")) {
            start();
        } else {
            //Primeros Lista-Definiciones
            if (verifyEquals("impl","struct")) {
                listaDefiniciones();
                start();
            }
            else {
                throw createException(this.actualToken, List.of("start","impl","struct"),this.actualToken.getLexeme());
            }
        }

    }

    /**
     * Regla Start
     * @author Yeumen Silva
     * */

    private void start(){
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

    private void struct(){
        match("struct");
        match("StructID");
        structF();
    }

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

    private void atributoEstrella(){
        atributo();
        atributoEstrellaF();
    }

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

    private void impl(){
        match("impl");
        match("StructID");
        match("{");
        miembroMas();
        match("}");
    }

    private void miembroMas(){
        miembro();
        miembroMasF();
    }

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

    private void herencia(){
        match(":");
        tipo();
    }

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

    private void constructor(){
        match(".");
        argumentosFormales();
        bloqueMetodo();
    }

    private void atributo(){

        //Primeros visibilidad
        if(verifyEquals("pri")){
            visibilidad();
            tipo();
            listaDeclaracionVariables();
            match(";");
        }
        else {
            //Primeros Tipo
            if(verifyEquals("Array" , "Bool" , "Char" , "Int" , "Str"
            , "StructID")){
                tipo();
                listaDeclaracionVariables();
                match(";");
            }
            else {
                throw createException(this.actualToken, List.of("Array" , "Bool" , "Char" , "Int" , "Str" , "StructID"
                        , "pri"),this.actualToken.getLexeme());
            }
        }
    }

    private void metodo(){
        //Primeros Forma-Método
        if(verifyEquals("st")){
            formaMetodo();
            match("fn");
            match("ObjID");
            argumentosFormales();
            match("->");
            tipoMetodo();
            bloqueMetodo();
        }
        else {
            //Primeros de fn
            if(verifyEquals("fn")){
                match("fn");
                match("ObjID");
                argumentosFormales();
                match("->");
                tipoMetodo();
                bloqueMetodo();

            }
            else {
                throw createException(this.actualToken, List.of("st" , "fn" ),this.actualToken.getLexeme());
            }
        }
    }

    private void visibilidad(){
        match("pri");
    }

    private void formaMetodo(){
        match("st");
    }

    private void bloqueMetodo(){
        match("{");
        bloqueMetodoF();

    }

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
                sentenciaEstrella();
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

    private void bloqueMetodoF1(){
        //Primeros }
        if(verifyEquals("}")){
            match("}");
        }
        else {
            //Primeros Sentencia-Estrella
            if(verifyEquals("(" , ";" , "ObjID" , "if"
                    , "ret" , "self" , "while", "{")){
                sentenciaEstrella();
                match("}");
            }
            else {
                throw createException(this.actualToken, List.of("}" , "(" , ";" , "ObjID" , "if"
                        , "ret" , "self" , "while", "{"),this.actualToken.getLexeme());
            }
        }
    }

    private void declVarLocalesEstrella(){
        declVarLocales();
        declVarLocalesEstrellaF();
    }

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

    private void sentenciaEstrella(){
        sentencia();
        sentenciaEstrellaF();
    }

    private void sentenciaEstrellaF(){
        //Primeros Sentencia-Estrella
        if(verifyEquals("(" , ";" , "ObjID" , "if"
                , "ret" , "self" , "while", "{")){
            sentenciaEstrella();
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

    private void declVarLocales(){
        tipo();
        listaDeclaracionVariables();
        match(";");
    }

    private void listaDeclaracionVariables(){
        match("ObjID");
        listaDeclaracionVariablesF();
    }

    private void listaDeclaracionVariablesF(){
        //Primeros ,
        if(verifyEquals(",")){
            match(",");
            listaDeclaracionVariables();
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

    private void argumentosFormales(){
        match("(");
        argumentosFormalesF();
    }

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

    private void listaArgumentosFormales(){
        argumentoFormal();
        listaArgumentosFormalesF();
    }

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

    private void argumentoFormal(){
        tipo();
        match("ObjID");
    }

    private void tipoMetodo(){

        //Primeros Tipo
        if (verifyEquals("Array" , "Bool" , "Char" , "Int" , "Str" ,
                "StructID")){
            tipo();
        }
        else {
            //Primeros void
            if (verifyEquals("void")){
                match("void");
            }
            else {
                throw createException(this.actualToken, List.of("Array" , "Bool" , "Char" , "Int" , "Str" ,
                        "StructID","void"),this.actualToken.getLexeme());
            }
        }
    }

    private void tipo(){
        //Primeros Tipo-Primitivo
        if (verifyEquals("Bool" , "Char" , "Int" , "Str")){
            tipoPrimitivo();
        }
        else {
            //Primeros Tipo-Referencia
            System.out.println(verifyEquals("StructID"));
            if(verifyEquals("StructID")){
                tipoReferencia();
            }
            else {
                //Primeros Tipo-Arreglo
                if(verifyEquals("Array")){
                    tipoArreglo();
                }
                else {
                    throw createException(this.actualToken, List.of("Array" , "Bool" , "Char" , "Int" , "Str" ,
                            "StructID"),this.actualToken.getLexeme());
                }
            }
        }
    }

    private void tipoPrimitivo(){

        if(match("Str") || match("Bool")
                || match("Int") || match("Char")){
            //Correcto
        }
        else {
            throw createException(this.actualToken, List.of( "Bool" , "Char" , "Int" , "Str"),this.actualToken.getLexeme());
        }

    }

    private void tipoReferencia(){
        match("StructID");
    }

    private void tipoArreglo(){
        match("Array");
        tipoPrimitivo();
    }

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
                            if (verifyEquals("}")){
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
                                            "while","}","ret"),this.actualToken.getLexeme());
                                }
                            }
                        }
                    }
                }

            }

        }
    }

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

    private void sentenciaF1(){
        //Primeros ;
        if (verifyEquals(";")){
            match(";");
        }
        else {
            //Primeros Expresion
            if (verifyEquals("!" , "(" , "+" , "++" , "-" , "--"
                    , "StrLiteral", "charLiteral" , "false" , "ObjID"
                    , "StructID" , "intLiteral" , "new" , "nil" , "self" , "true")){
                expresion();
                match(";");
            }
            else {
                throw createException(this.actualToken, List.of(";" , "!" , "(" , "+" , "++" , "-" , "--"
                        , "StrLiteral", "charLiteral" , "false" , "ObjID"
                        , "StructID" , "intLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
            }
        }
    }

    private void bloque(){
        match(";");
        bloqueF();

    }

    private void bloqueF(){
        //Primeros }
        if(verifyEquals("}")){
            match("}");
        }
        else {
            //Primeros Setntencia-Estrella
            if(verifyEquals("(" , ";" , "ObjID" , "if" , "ret" ,
                    "self" , "while" , "{")){
                sentenciaEstrella();
                match("}");
            }
            else {
                throw createException(this.actualToken, List.of("}" ,"(" , ";" , "ObjID" , "if" , "ret" ,
                        "self" , "while" , "{"),this.actualToken.getLexeme());
            }
        }
    }

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

    private void accesoVarSimple(){
        match("id");
        accesoVarSimpleF();
    }

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

    private void encadenadoSimpleEstrella(){
        encadenadoSimple();
        encadenadoSimpleEstrellaF();
    }

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

    private void accesoSelfSimple(){
        match("self");
        accesoSelfSimpleF();
    }

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

    private void encadenadoSimple(){
        match(".");
        match("Objid");
    }

    private void sentenciaSimple(){
        match("(");
        expresion();
        match(")");
    }

    private void expresion(){
        expOr();
    }

    /**
     * Función para la regla 55 <ExpOr> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expOr() {
        String[] firstExpAnd = {"!", "(", "+" , "++" , "-" , "--" ,
                "StrLiteral" , "charLiteral" , "false" , "id" , "idStruct" ,
                "intLiteral" , "new" , "nil" , "self" , "true"};

        if(verifyEquals(firstExpAnd)){
            expAnd();
            expOrF();
        } else {
            throw createException(this.actualToken, List.of("!", "(", "+" , "++" , "-" , "--" ,
                    "StrLiteral" , "charLiteral" , "false" , "id" , "idStruct" ,
                    "intLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
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
        String[] firstExpIgual = {"!" , "(" , "+" , "++" , "-" , "--" , "StrLiteral" , "charLiteral" ,
                "false" , "id" , "idStruct" , "intLiteral" , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpIgual)) {
            expIgual();
            expAndF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" , "-" , "--" , "StrLiteral" , "charLiteral" ,
                    "false" , "id" , "idStruct" , "intLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
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
                , "--" , "StrLiteral" , "charLiteral" , "false"
                , "id" , "idStruct" , "intLiteral" , "new" , "nil"
                , "self" , "true"};

        if (verifyEquals(firstExpCompuesta)) {
            expCompuesta();
            expIgualF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" , "-"
                    , "--" , "StrLiteral" , "charLiteral" , "false"
                    , "id" , "idStruct" , "intLiteral" , "new" , "nil"
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
                , "-" , "--" , "StrLiteral" , "charLiteral"
                , "false" , "id" , "idStruct" , "intLiteral"
                , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpAd)) {
            expAd();
            expCompuestaF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++"
                    , "-" , "--" , "StrLiteral" , "charLiteral"
                    , "false" , "id" , "idStruct" , "intLiteral"
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
                "-" , "--" , "StrLiteral" , "charLiteral" ,
                "false" , "id" , "idStruct" , "intLiteral" ,
                "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpMul)) {
            expMul();
            expAdF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" ,
                    "-" , "--" , "StrLiteral" , "charLiteral" ,
                    "false" , "id" , "idStruct" , "intLiteral" ,
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
                "--" , "StrLiteral" , "charLiteral" , "false" ,
                "id" , "idStruct" , "intLiteral" , "new" ,
                "nil" , "self" , "true"};

        if (verifyEquals(firstExpUn)) {
            expUn();
            expMulF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" , "++" , "-" ,
                    "--" , "StrLiteral" , "charLiteral" , "false" ,
                    "id" , "idStruct" , "intLiteral" , "new" ,
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
                "charLiteral" , "false" , "id" , "idStruct" ,
                "intLiteral" , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstOpUnario)) {
            opUnario();
            expUn();
        } else {
            if (verifyEquals(firstOperando)) {
                operando();
            } else {
                throw createException(this.actualToken, List.of("!" , "+" , "++" , "-" , "--", "(" , "StrLiteral" ,
                        "charLiteral" , "false" , "id" , "idStruct" ,
                        "intLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
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
                "charLiteral" , "false" , "intLiteral" ,
                "nil" , "true"};
        String[] firstPrimario = {"(" , "id" , "idStruct" ,
                "new" , "self"};

        if (verifyEquals(firstLiteral)){
            literal();
        } else {
            if (verifyEquals(firstPrimario)){
                primario();
                operandoF();
            } else {
                throw createException(this.actualToken, List.of("StrLiteral" ,
                        "charLiteral" , "false" , "intLiteral" ,
                        "nil" , "true", "(" , "id" , "idStruct" ,
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
        String[] firstIntLiteral = {"intLiteral"};
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
                        match("intLiteral");
                    } else {
                        if (verifyEquals(firstStrLiteral)){
                            match("StrLiteral");
                        } else {
                            match("charLiteral");
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
        String[] firstAccesoVar = {"id"}; //TODO: puede que haya error por doble id?
        String[] firstLlamadaMetodo = {"id"};
        String[] firstLlamadaMetodoEstatico = {"idStruct"};
        String[] firstLlamadaConstructor = {"new"};

        if (verifyEquals(firstExpresionParentizada)) {
            expresionParentizada();
        } else {
            if (verifyEquals(firstAccesoSelf)){
                accesoSelf();
            } else {
                if (verifyEquals(firstAccesoVar)){
                    accesoVar();
                } else {
                    if (verifyEquals(firstLlamadaMetodo)){
                        llamadaMetodo();
                    } else {
                        if (verifyEquals(firstLlamadaMetodoEstatico)){
                            llamadaMetodoEstatico();
                        } else {
                            if (verifyEquals(firstLlamadaConstructor)){
                                llamadaConstructor();
                            } else {
                                throw createException(this.actualToken, List.of("(", "self", "id", "idStruct", "new"),this.actualToken.getLexeme());
                            }
                        }
                    }
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
                , "." , "/" , ";" , "<" , "<=" , "=="
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
                        , "." , "/" , ";" , "<" , "<=" , "=="
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
                "-" , "." , "/" , ";" , "<" , "<=" ,
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
                        "-" , "." , "/" , ";" , "<" , "<=" ,
                        "==" , ">" , ">=" , "]" , "||" , "$EOF$"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 91 <AccesoVar> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoVar() {
        match("id");
        accesoVarF();
    }

    /**
     * Función para la regla 92 <AccesoVarF> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoVarF() {
        String[] followAccesoVarF = {"!=" , "%" , "&&" , ")" ,
                "*" , "+" , "," , "-" , "." , "/" , ";" , "<" ,
                "<=" , "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."}; // TODO: first y follow tienen ".", no se si puede causar errores
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
                ")" , "*" , "+" , "," , "-" , "." , "/" ,
                ";" , "<" , "<=" , "==" , ">" , ">=" , "]" ,
                "||" , "$EOF$"};
        String[] firstEncadenado = {"."}; // TODO: estos tambien tienen "." los dos

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
        match("id");
        argumentosActuales();
        llamadaMetodoF();
    }

    /**
     * Función para la regla 95 <Llamada-Método-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaMetodoF() {
        String[] followLlamadaMetodoF = {"!=" , "%" , "&&" , ")" , "*" ,
                        "+" , "," , "-" , "." , "/" , ";" ,
                "<" , "<=" , "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."}; // TODO: los dos tienen "."

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
        match("idStruct");
        llamadaMetodo();
        llamadaMetodoEstaticoF();
    }

    /**
     * Función para la regla 97 <Llamada-Método-Estático-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaMetodoEstaticoF() {
        String[] followLlamadaMetodoEstaticoF = {"!=" , "%" ,
                "&&" , ")" , "*" , "+" , "," , "-" , "." ,
                "/" , ";" , "<" , "<=" , "==" , ">" , ">=" ,
                "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."}; // TODO: los dos tienen "."

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
            match("idStruct");
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
                "-" , "." , "/" , ";" , "<" , "<=" ,
                "==" , ">" , ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."}; // todo: los dos tienen "."

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
                "charLiteral" , "false" , "id" , "idStruct" ,
                "intLiteral" , "new" , "nil" , "self" , "true"};

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
                "charLiteral" , "false" , "id" , "idStruct" ,
                "intLiteral" , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpresion)){
            expresion();
            listaExpresionesF();
        } else {
            throw createException(this.actualToken, List.of("!" , "(" , "+" ,
                    "++" , "-" , "--" , "StrLiteral" ,
                    "charLiteral" , "false" , "id" , "idStruct" ,
                    "intLiteral" , "new" , "nil" , "self" , "true"),this.actualToken.getLexeme());
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
        String[] firstLlamadaMetodoEncadenado = {"id"}; // TODO: puede que haga error porque los dos son id?
        String[] firstAccesoVariableEncadenado = {"id"};

        if (verifyEquals(firstLlamadaMetodoEncadenado)){
            llamadaMetodoEncadenado();
        } else {
            if (verifyEquals(firstAccesoVariableEncadenado)){
                accesoVariableEncadenado();
            } else {
                throw createException(this.actualToken, List.of("id"),this.actualToken.getLexeme());
            }
        }
    }

    /**
     * Función para la regla 107 <Llamada-Método-Encadenado> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaMetodoEncadenado() {
        match("id");
        argumentosActuales();
        llamadaMetodoEncadenadoF();
    }

    /**
     * Función para la regla 108 <Llamada-Método-Encadenado-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void llamadaMetodoEncadenadoF() {
        String[] followLlamadaMetodoEncadenadoF = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-" ,
                "." , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."}; // TODO: los dos tienen "."

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
        match("id");
        accesoVariableEncadenadoF();
    }

    /**
     * Función para la regla 110 <Acceso-Variable-Encadenado-F> de la Gramatica
     * @author Lucas Moyano
     * */
    private void accesoVariableEncadenadoF() {
        String[] followAccesoVariableEncadenadoF = {"!=" ,
                "%" , "&&" , ")" , "*" , "+" , "," , "-" ,
                "." , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."}; //TODO : los dos tienen "."

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
                "%" , "&&" , ")" , "*" , "+" , "," , "-" ,
                "." , "/" , ";" , "<" , "<=" , "==" , ">" ,
                ">=" , "]" , "||" , "$EOF$"};
        String[] firstEncadenado = {"."}; //TODO : los dos tienen "."

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
