package SyntacticAnalyzer;

import Exceptions.LexicalExceptions.LexicalException;
import Exceptions.SemanticExceptions.SemanticException;
import Exceptions.SyntacticExceptions.SyntacticException;
import FileManager.FileManager;
import LexicalAnalyzer.LexicalAnalyzer;
import LexicalAnalyzer.Token;
import SemanticAnalyzer.Json;
import SemanticAnalyzer.SymbolTable;

import java.util.List;

/**
 * Clase que será la encargada de llamar al analizador lèxico
 * cuando sea necesario un nuevo Token
 * @throws LexicalException
 * @author Yeumen Silva
 */
public class SyntacticExecutor {



    private FileManager fileManager;
    private   LexicalAnalyzer lexicalAnalyzer;

    private  SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer();

    private SymbolTable symbolTable;

    private Json jsonMannager = new Json();


    /**
     * Constructor de la clase que inicia el léxico y la tabla
     * de palabras reservadas
     * @param inputPath path del archivo de entrada
     * @param  outputPath path al archivo de salida
     * @author Yeumen Silva
     */

    public SyntacticExecutor(String inputPath, String outputPath) {
        this.fileManager = new FileManager(inputPath, outputPath);
        String file = this.fileManager.getInputFile();
        try {
            lexicalAnalyzer = new LexicalAnalyzer(file);
            symbolTable = this.syntacticAnalyzer.startSyntactic(lexicalAnalyzer);
        }
        catch (LexicalException exception){
            this.printExceptionLexical(exception);
        }
        catch (SyntacticException exception){
            this.printExceptionSyntactic(exception);
        }
        catch (SemanticException exception){
            this.printExceptionSemanticDecl(exception);
        }

        /*
        if(outputPath == null){
            this.printCorrect();
        }
        else {
            fileManager.saveResults(List.of("CORRECTO: ANALISIS SINTACTICO"));
        }
         */

        this.jsonMannager.buildJson(symbolTable,outputPath, inputPath);

    }


    /**
     * Método que dado un error, lo imprime con el formato pedido
     * @param exception error de tipo léxico
     * @author Yeumen Silva
     */
    private void printExceptionLexical(LexicalException exception) {

        Token tokenException = exception.getToken();

        System.out.println("ERROR: LEXICO");
        System.out.println("| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |");

        System.out.println("| Linea " + tokenException.getRow() +
                " | COLUMNA " + tokenException.getColumn() +
                " | " + exception.getExceptionType() + " " + tokenException.getLexeme());

    }

    /**
     * Método que dado un error, lo imprime con el formato pedido
     * @param exception error de tipo Sintáctico
     * @author Yeumen Silva
     */

    public void printExceptionSyntactic(SyntacticException exception){

        Token tokenException = exception.getToken();

        System.out.println("ERROR: SINTÁCTICO");
        System.out.println("| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |");

        System.out.println("| Linea " + tokenException.getRow() +
                " | COLUMNA " + tokenException.getColumn() +
                " | " + exception.getExceptionType()   );

    }

    public void printExceptionSemanticDecl(SemanticException exception){

        Token tokenException = exception.getToken();

        System.out.println("ERROR: SEMÁNTICO - DECLARACIONES");
        System.out.println("| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |");

        System.out.println("| Linea " + tokenException.getRow() +
                " | COLUMNA " + tokenException.getColumn() +
                " | " + exception.getExceptionType() );

    }

    /**
     * Método que si el análisis sintáctico es correcto,
     * muestra el mensaje por pantalla
     * @author Yeumen Silva
     */

    public void printCorrect(){
        System.out.println("CORRECTO: ANALISIS SINTACTICO");
    }



}



