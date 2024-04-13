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
        String[] firstExpOrR = {"||"};

        if (verifyEquals(firstExpOrR)) {
            match("||");
            expAnd();
            expOrRF();
        } else {
            // TODO: tirar error
        }
    }
}
