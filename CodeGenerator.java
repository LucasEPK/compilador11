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
        code += "\t divisionErrorMessage: .asciiz \"ERROR: DIVISION POR CERO\"\n";
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

        code += ast.generateCode();

        code += "\t# Termino ejecución\n" +
                "\tli $v0, 10\n" +
                "\tsyscall\n\n";

        writeSumFunction();
        writeSubFunction();
        writeMulFunction();
        writeDivisionZero();
        writeDivFunction();
        writeModuleFunction();
    }


    /**
     * Escribe en el codigo la función de suma
     * @author Lucas Moyano
     * */
    private void writeSumFunction() {
        code += "default_sum:\t# sumamos lo que está en el acumulador y lo que podemos popear del stack\n";

        addNopComment("sumamos lo que está en el acumulador y lo que podemos popear del stack");

        code += "\tpop\n" +
                "\tadd $v0, $v0, $t9\n" +
                "\tjr $ra\n\n";
    }

    /**
     * Escribe en el codigo la función de resta
     * @author Lucas Moyano
     * */
    private void writeSubFunction() {
        code += "default_sub:\t# restamos lo que está en el acumulador y lo que podemos popear del stack\n";

        addNopComment("restamos lo que está en el acumulador y lo que podemos popear del stack");

        code += "\tpop\n" +
                "\tsub $v0, $v0, $t9\n" +
                "\tjr $ra\n\n";
    }

    /**
     * Escribe en el codigo la función de multiplicación
     * @autor Lucas Moyano
     */

    private void writeMulFunction() {
        code += "default_mul:\t# multiplicamos lo que está en el acumulador y lo que podemos popear del stack\n";

        addNopComment("multiplicamos lo que está en el acumulador y lo que podemos popear del stack");

        code += "\tpop\n" +
                "\tmul $v0,$v0,$t9\n" +
                "\tjr $ra\n\n";
    }

    private void writeDivFunction() {
        code += "default_div:\t# dividimos lo que está en el acumulador y lo que podemos popear del stack\n";

        addNopComment("dividimos lo que está en el acumulador y lo que podemos popear del stack");

        code += "\tpop\n" +
                "\tbeq $t9,$zero,division_zero #Si el divisor es cero, salto a error\n" +
                "\tdiv $v0, $t9 #El resultado se guarda en registro lo\n" +
                "\tmflo $v0 #Se accede a lo con mflo\n" +
                "\tjr $ra\n\n";
    }

    private void writeDivisionZero() {
        code += "division_zero:\t #Manejo de error de división por cero\n";

        addNopComment("Manejo de error de división por cero");

        code += "\tla $a0, divisionErrorMessage\n" +
                "\tli $v0,4\n" +
                "\tsyscall \n" +
                "\tli $v0,10\n" +
                "\tsyscall\n\n";
    }

    private void writeModuleFunction(){
        code += "default_module:\t# modulo lo que está en el acumulador y lo que podemos popear del stack\n";

        addNopComment("modulo lo que está en el acumulador y lo que podemos popear del stack");

        code += "\tpop\n" +
                "\tbeq $t9,$zero,division_zero #Si el divisor es cero, salto a error\n" +
                "\tdiv $v0, $t9 #El resultado se guarda en registro lo\n" +
                "\tmfhi $v0 #Se accede a lo con mflo\n" +
                "\tjr $ra\n\n";
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
