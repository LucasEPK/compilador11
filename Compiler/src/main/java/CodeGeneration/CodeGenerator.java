package CodeGeneration;

import SemanticAnalyzer.AST.AST;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Esta clase se encarga de generar el código intermedio
 * a partir del árbol de sintaxis abstracta.
 */
public class CodeGenerator {

    //String que contendrá el código intermedio generado
    private String code = "";

    //Árbol de sintaxis abstracta
    private final AST ast;

    //Path del archivo donde se guardará el código intermedio
    private final String path;

    /**
     * Constructor de la clase CodeGenerator
     * @param ast Árbol de sintaxis abstracta
     * @param path Path del archivo donde se guardará el código intermedio
     * @autor Yeumen Silva
     */

    public CodeGenerator(AST ast, String path) {
        this.ast = ast;
        this.path = path;
    }

    /**
     * Método que se encarga de generar el código intermedio
     * a partir del árbol de sintaxis abstracta
     * @autor Yeumen Silva
     */

    public void generateASMCode() {

    }

    /**
     * Método que se encarga de convertir el string con el código intermedio
     * en un archivo .asm
     * @author Yeumen Silva
     */

    private void saveASMCode() {
        String output = this.path.replace(".ru",".asm");
        try {
            // Crear un FileWriter para escribir en el archivo de salida
            FileWriter escritor = new FileWriter(output);

            // Escribir la salida en el archivo
            escritor.write(this.code);

            // Cerrar el FileWriter
            escritor.close();
        }catch (IOException e) {
            System.err.println("Error al escribir en el archivo de salida: " + e.getMessage());
        }
    }



}
