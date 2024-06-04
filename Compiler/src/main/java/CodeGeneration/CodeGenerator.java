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

    // Booleano para determinar si se van a escribir los comentarios con nop al principio o no
    private boolean generateComments = false;

    /**
     * Constructor de la clase CodeGenerator
     * @param ast Árbol de sintaxis abstracta
     * @param path Path del archivo donde se guardará el código intermedio
     * @autor Yeumen Silva, Lucas Moyano
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
        writeMacros();
        writeStaticData();
        writeText();
        saveASMCode();
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

    /**
     * Agrega las macros push y pop al codigo generado
     * @author Lucas Moyano
     * */
    private void writeMacros() {
        code += "# Macros\n" +
                ".macro push\t\t\t# hace push en el stack y guarda t9 en el stack\n" +
                "\tsw $t9, 0($sp)\n" +
                "\taddiu $sp, $sp, -4\n" +
                ".end_macro \n" +
                ".macro pop\t\t\t# hace pop en el stack y guarda el elemento popeado en t9\n" +
                "\tlw $t9, 4($sp)\n" +
                "\taddiu $sp, $sp, 4\n" +
                ".end_macro\n\n";
    }


    /**
     * Escribe los datos del .data en el .asm
     * @author Lucas Moyano
     * */
    private void writeStaticData() {
        code += ".data\n";
        code += "\n";
    }

    /**
     * Escribe la parte del .text en el .asm
     * @author Lucas Moyano
     * */
    private void writeText() {
        code += ".text\n" +
                ".globl main\n" +
                "main:\n";

        code += "# Termino ejecución\n" +
                "\tli $v0, 10\n" +
                "\tsyscall";
    }

    /**
     * agrega al ultimo un comentario con una instrucción nop para debugear
     * @param comment string que representa un comentario en el codigo
     * @author Lucas Moyano
     * */
    private void addNopComment(String comment) {
        if (generateComments) {
            code += "nop #" + comment + "\n";
        }
    }

    public String getCode() {
        return code;
    }
}
