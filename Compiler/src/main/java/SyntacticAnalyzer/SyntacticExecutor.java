package SyntacticAnalyzer;

import Exceptions.LexicalExceptions.LexicalException;
import Exceptions.SemanticExceptions.SemanticException;
import Exceptions.SemanticExceptions.SymbolTable.SymbolTableException;
import Exceptions.SyntacticExceptions.SyntacticException;
import FileManager.FileManager;
import LexicalAnalyzer.LexicalAnalyzer;
import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.Json;
import SemanticAnalyzer.SymbolTable.SymbolTable;

import static java.lang.System.exit;

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
        boolean flag = true;
        try {
            lexicalAnalyzer = new LexicalAnalyzer(file);
            symbolTable = this.syntacticAnalyzer.startSyntactic(lexicalAnalyzer);
            //Llamo al método que se encarga de generar el json de la tabla de símbolos
            this.jsonMannager.buildJson(symbolTable,outputPath, inputPath);
            //Llamo al método que se encarga de consolidar y generar el json del AST
            this.syntacticAnalyzer.generateASTJson(inputPath);
            //Llamo al método que se encarga de generar el código ASM
            this.syntacticAnalyzer.generateASMCode(inputPath);
            printCorrectCodeGeneration();
        }
        catch (LexicalException exception){
            this.printExceptionLexical(exception);
            exit(0);
        }
        catch (SyntacticException exception){
            this.printExceptionSyntactic(exception);
            exit(0);
        }
        catch (SemanticException exception){
            this.printExceptionSemantic(exception);
        }



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

    public void printExceptionSemantic(SemanticException exception){

        Token tokenException = exception.getToken();

        if(exception instanceof SymbolTableException){
            System.out.println("ERROR: SEMANTICO - DECLARACIONES");
            System.out.println("| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |");

            System.out.println("| Linea " + tokenException.getRow() +
                    " | COLUMNA " + tokenException.getColumn() +
                    " | " + exception.getExceptionType() );
        }
        else {
            System.out.println("ERROR: SEMANTICO - SENTENCIAS");
            System.out.println("| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |");

            System.out.println("| Linea " + tokenException.getRow() +
                    " | COLUMNA " + tokenException.getColumn() +
                    " | " + exception.getExceptionType() );
        }



    }

    /**
     * Método que si el análisis sintáctico es correcto,
     * muestra el mensaje por pantalla
     * @author Yeumen Silva
     */

    public void printCorrectSyntactic(){
        System.out.println("CORRECTO: ANALISIS SINTACTICO");
    }

    public void printCorrectSemnatic(){
        System.out.println("CORRECTO: SEMANTICO - DECLARACIONES");
    }


    public void printCorrectSemanticSentence(){
        System.out.println("CORRECTO: SEMANTICO - SENTENCIAS");
    }

    public void printCorrectCodeGeneration(){
        System.out.println("CORRECTO: CODIGO ASM GENERADO");
    }



}



