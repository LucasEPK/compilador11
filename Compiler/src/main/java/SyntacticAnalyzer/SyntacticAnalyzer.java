package SyntacticAnalyzer;

import Exceptions.LexicalException;
import LexicalAnalyzer.LexicalAnalyzer;
import LexicalAnalyzer.Token;

import java.util.ArrayList;
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

    private Token nextToken;

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

        // llamada a regla inicial de nuestra gramática
        program();


    }

    /**
     * Método el cual trata de hacer match y actualizar el token actual
     * y el siguiente
     * @param actualname Nombre del lexema actual
     * @author Yeumen Silva
     * */

    private void match(String actualname){

        //Verifico si matchea el lexema del token actual con el lexema esperado
        if(Objects.equals(this.actualToken.getLexeme(), actualname)){

            this.actualToken = syntacticExecutor.getNextToken();

        }
        else {

            // ToDo error
        }
        if(Objects.equals(this.actualToken.getLexeme(), "$EOF$")){

            /* Si el lexema del token actual es $EOF$ significa que no tengo
            nextToken por lo tanto no lo actualizo
             */
        }
        else {
            this.nextToken = syntacticExecutor.getNextToken();
        }
    }

    /**
     * Método que dado un Array de Strings que contiene los primeros
     * de alguna regla, vérifica que el léxema del token actual
     * matche con alguno de los primeros
     * @param listStirngs lista con los primeros de alguna regla
     * @return booleano representando si pertenece o no a los primeros
     * @author Yeumen Silva
     * */

    private boolean verifyEquals(String[] listStirngs){

        String actualLexeme = this.actualToken.getLexeme();

        for (String lexeme : listStirngs){

            if(Objects.equals(actualLexeme,lexeme)){
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

        String[] firstDefinitionList = {"impl", "struct"};
        String[] firstStartList = {"start"};

        if (verifyEquals(firstStartList)) {
            // start();
        } else {

            if (verifyEquals(firstDefinitionList)) {
                // listaDefiniciones()
                // start()
            }
            else {
                // Todo error
            }
        }

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
            // TODO: tirar error
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
                // TODO: tirar error
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
                //TODO: tirar error
            }
        }
    }

    /**
     * Función para la regla 59 <ExpAnd> de la Gramatica
     * @author Lucas Moyano
     * */
    private void expAnd() {
        String[] firstExpIgual = {"!" , "(" , "+" , "++" , "-" , "--" , "StrLiteral" , "charLiteral" , "false" , "id" , "idStruct" , "intLiteral" , "new" , "nil" , "self" , "true"};

        if (verifyEquals(firstExpIgual)) {
            expIgual();
            expAndF();
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
                // TODO: tirar error
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
                // TODO: tirar error
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
                                //TODO: TIRAR ERROR
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
            }
        }
    }
}
