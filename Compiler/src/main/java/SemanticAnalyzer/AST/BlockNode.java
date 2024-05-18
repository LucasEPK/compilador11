package SemanticAnalyzer.AST;


import Exceptions.SemanticExceptions.AST.ReturnInStart;
import SemanticAnalyzer.SymbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase representate un bloque de codigo de un metodo
 * @author Yeumen Silva
 */

public class BlockNode extends SentenceNode implements Commons {

    private List<SentenceNode> sentenceList = new ArrayList<SentenceNode>();

    public BlockNode(String struct, String method) {
        super(struct, method);
    }

    /**
     * Constructor de la clase que recibe una lista de sentencias
     * @param struct nombre de la clase
     * @param method nombre del metodo
     * @param sentenceList lista de sentencias que contiene el bloque
     */
    public BlockNode(String struct, String method, ArrayList<SentenceNode> sentenceList) {
        super(struct, method);
        this.sentenceList = sentenceList;
    }

    /**
     * Método que recorre la lista de sentencias y llama al método toJson de cada una
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return void
     * @autor Yeumen Silva
     */

    @Override
    public String toJson(int tabs) {

        String json = addtabs(tabs) + "{\n";
        json += addtabs(tabs+1) + "\"nombre\": \"" + "Block" + "\",\n";
        json += addtabs(tabs+1) + "\"class\": \"" + getStruct() + "\",\n";
        json += addtabs(tabs+1) + "\"method\": \"" + getMethod() + "\",\n";
        json += addtabs(tabs+1) + "\"sentences\": [\n";
        int size = 0;
        for (SentenceNode sentence : sentenceList) {
            json += sentence.toJson(tabs+2);

            if(size < sentenceList.size()-1){
                json += ",\n";
            }
            size++;
        }

        json += addtabs(tabs+1) + "]\n";
        json += addtabs(tabs) + "}";

        return json;
    }

    /**
     * Método que recorre sentencia a sentencia y llama al método consolidate de cada una
     * @param ast AST que se va a consolidar
     * @return void
     * @autor Yeumen Silva
     */

    @Override
    public void consolidate(AST ast) {
        //Llamo al método consolidate de cada sentencia
        for(SentenceNode sentence : sentenceList){
            sentence.consolidate(ast);
        }

        //Llamo al método consolidate de cada sentencia para verificar los tipos de return
        for(SentenceNode sentence : sentenceList){
            //Verifico que start no tenga return
            if (sentence instanceof ReturnNode && sentence.getMethod().equals("start") && sentence.getStruct().equals("start")){
                //ToDo
                //throw new ReturnInStart(sentence.getToken());
            }
        }





    }

    /**
     * Método que agrega una cantidad de tabulaciones a un string
     * @param tabs cantidad de tabulaciones a agregar
     * @return string con las tabulaciones agregadas
     * @autor Yeumen Silva
     */

    @Override
    public String addtabs(int tabs) {
        String tabsString = "";
        for (int i = 0; i < tabs; i++) {
            tabsString += "\t";
        }
        return tabsString;
    }


    /**
     * Esta función se encarga de elegir el tipo de sentencia que se va a agregar en la lista
     * después de eso la agrega a la sentenceList
     * @param sentenceType el tipo de sentencia que se quiere en String
     * @return devuelve la nueva sentencia creada
     * @author Lucas Moyano
     */
    public SentenceNode addNewSentence(String sentenceType){
        SentenceNode newSentence = null;

        switch (sentenceType) {
            case "Asignation":
                newSentence = new AsignationNode(getStruct(), getMethod());
                break;
            case "ExpBin":
                newSentence = new ExpBin(getStruct(), getMethod());
                break;
            case "ExpUn":
                newSentence = new ExpUn(getStruct(), getMethod());
                break;
            case "IfThenElse":
                newSentence = new IfThenElseNode(getStruct(), getMethod());
                break;
            case "Return":
                newSentence = new ReturnNode(getStruct(), getMethod());
                break;
            case "While":
                newSentence = new WhileNode(getStruct(), getMethod());
                break;
            default:
                assert newSentence != null: "ERROR: el tipo de sentencia no existe";
        }

        sentenceList.add(newSentence);

        return newSentence;
    }

    public List<SentenceNode> getSentenceList() {
        return sentenceList;
    }
}
