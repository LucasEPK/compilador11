package SyntacticAnalyzer;

import Exceptions.LexicalException;
import Exceptions.SyntacticException;
import FileManager.FileManager;
import LexicalAnalyzer.LexicalAnalyzer;
import LexicalAnalyzer.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que será la encargada de llamar al analizador lèxico
 * cuando sea necesario un nuevo Token
 * @throws LexicalException
 * @author Yeumen Silva
 */
public class SyntacticExecutor {

    private final HashMap<String, String> hashMap = new HashMap<>();
    private final FileManager fileManager;
    private  final LexicalAnalyzer lexicalAnalyzer;


    /**
     * Constructor de la clase que inicia el léxico y la tabla
     * de palabras reservadas
     * @param inputPath path del archivo de entrada
     * @param  outputPath path al archivo de salida
     * @author Yeumen Silva
     */

    public SyntacticExecutor(String inputPath, String outputPath) {
        startHashTable();
        this.fileManager = new FileManager(inputPath, outputPath);
        String file = this.fileManager.getInputFile();
        this.lexicalAnalyzer = new LexicalAnalyzer(file);
    }

    /**
     * Método que inicia nuestra hashMap agregando todas las
     * palabras reservadas de nuestro lenguaje
     * @author Yeumen Silva
     */

    private void startHashTable() {

        this.hashMap.put("start", "start");
        this.hashMap.put("struct", "struct");
        this.hashMap.put("self", "self");
        this.hashMap.put("st", "st");
        this.hashMap.put("pri", "pri");
        this.hashMap.put("nil", "nil");
        this.hashMap.put("new", "new");
        this.hashMap.put("impl", "impl");
        this.hashMap.put("ret", "ret");
        this.hashMap.put("if", "if");
        this.hashMap.put("else", "else");
        this.hashMap.put("while", "while");
        ;
        this.hashMap.put("true", "true");
        this.hashMap.put("false", "false");
        this.hashMap.put("fn", "fn");
        this.hashMap.put("void", "void");
        this.hashMap.put("Array", "Array");
        this.hashMap.put("Str", "Str");
        this.hashMap.put("Bool", "Bool");
        this.hashMap.put("Int", "Int");
        this.hashMap.put("Char", "Char");

    }

    /**
     * Método que devuelve el next token al analizador sintáctico
     * @return Nuevo Token
     * @author Yeumen Silva
     */

    public Token getNextToken() {

        Token token = this.lexicalAnalyzer.getNextToken();

        try {

            // Si no es uno de los tokens válidos, sigo buscando uno

            while ( token.getToken().equals(this.lexicalAnalyzer.getBLANK_SPACE()) ||
                     token.getToken().equals(this.lexicalAnalyzer.getTAB()) ||
                     token.getToken().equals(this.lexicalAnalyzer.getCARRIAGE_RETURN()) ||
                     token.getToken().equals(this.lexicalAnalyzer.getNEW_LINE()) ||
                     token.getToken().equals(this.lexicalAnalyzer.getVERTICAL_TAB()) ||
                     token.getToken().equals(this.lexicalAnalyzer.getSIMPLE_COMMENT())){;

                token = this.lexicalAnalyzer.getNextToken();;


            }

            // Verifico si el lexema es una palabra reservada
            if (this.hashMap.containsKey(token.getLexeme())) {

                //Si esta, entonces es una palabra reservada y cambio su token

                token.setToken(token.getLexeme());
            }


        } catch (LexicalException exception) {


            /*
            Si encuentra un error lexico, llama a metodo
            que sera el encrgado de imprimir el error
             */

            printException(exception);

        }

        return token;

    }

    /**
     * Método que dado un error, lo imprime con el formato pedido
     * @param exception error de tipo léxico
     * @author Yeumen Silva
     */
    private void printException(LexicalException exception) {

        Token tokenException = exception.getToken();

        System.out.println("ERROR: LEXICO");
        System.out.println("| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |");

        System.out.println("| Linea " + tokenException.getRow() +
                " | COLUMNA " + tokenException.getColumn() +
                " | " + exception.getExceptionType() + " " + tokenException.getLexeme());

    }

    public void printException(SyntacticException exception){

        Token tokenException = exception.getToken();

        System.out.println("ERROR: SINTÁCTICO");
        System.out.println("| NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |");

        System.out.println("| Linea " + tokenException.getRow() +
                " | COLUMNA " + tokenException.getColumn() +
                " | " + exception.getExceptionType()   );

    }

}



