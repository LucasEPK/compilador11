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

    private void expOr(){
        expAnd();
        expOrF();
    }
}
