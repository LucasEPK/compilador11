package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el AST, va a tener como atributo una lista de bloques
 * @author Lucas Moyano
 * */
public class AST implements Commons {

    private List<BlockNode> blockList = new ArrayList<BlockNode>();

    private SymbolTable symbolTable;

    public AST(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }


    /**
     * Agrega un nuevo bloque a la lista
     * @return El nuevo bloque añadido a la lista
     * @author Lucas Moyano
     */
    public BlockNode addNewBlock() {
        BlockNode newBlock = new BlockNode(symbolTable.getCurrentStruct().getName(), symbolTable.getCurrentMethod().getName());
        blockList.add(newBlock);

        return  newBlock;
    }

    public List<BlockNode> getBlockList() {
        return blockList;
    }


    /**
     * Método que recorre la lista de bloques y llama al método toJson de cada uno
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return void
     * @autor Yeumen Silva
     * */
    public String toJson(int tabs,String imputPath) {
        String json = "{\n";
        json += addtabs(tabs) + "\"nombre\": + \"" + imputPath + "\",\n";
        json += addtabs(tabs) + "\"clases\": [\n";

        //Recorro todos los bloques del AST
        for(BlockNode block : blockList) {
            json += block.toJson(tabs+1);
        }

        json += addtabs(tabs) + "]\n";
        json += "}\n";

        //Llamo a funcion para gaurdar json como archivo
        saveJson(json,imputPath);

        return json;
    }

    @Override
    public String toJson(int tabs) {
        return null;
    }

    @Override
    public void consolidate() {

    }

    /**
     * Método que dada una cantida de tabs, devuelve un string
     * @param tabs int que representa cantidad de tabs
     * @return string con tabs
     * @autor Yeumen Silva
     */

    @Override
    public String addtabs(int tabs){
        String tabString = "";
        for (int i = 0; i < tabs; i++) {
            tabString+="\t";
        }
        return  tabString;
    }

    /**
     * Método que se encarga de guardar el json en un archivo
     * @param jsonString String con el json
     * @param inputPath String con el path del archivo de entrada
     * @autor Yeumen Silva
     */

    private void saveJson(String jsonString, String inputPath){
        String output = inputPath.replace(".ru", ".ast.json");
        try {
            // Crear un FileWriter para escribir en el archivo de salida
            FileWriter escritor = new FileWriter(output);

            // Escribir la salida en el archivo
            escritor.write(jsonString);

            // Cerrar el FileWriter
            escritor.close();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de salida: " + e.getMessage());
        }
    }
}
