package SemanticAnalyzer.AST;


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
        for (SentenceNode sentence : sentenceList) {
            json += sentence.toJson(tabs+2);
        }
        json += addtabs(tabs+1) + "]\n";
        json += addtabs(tabs) + "},\n";

        return json;
    }

    @Override
    public void consolidate() {

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
     * Esta función se encarga de agregar la sentencia dada a la lista
     * @param sentence sentencia que se quiere agregar a la lista
     * @author Lucas Moyano
     */
    public void addNewSentence(SentenceNode sentence){
        sentenceList.add(sentence);
    }

    /**
     * Esta función se encarga de crear un AsignationNode y
     * lo agrega a la lista de sentencias
     * @return devuelve la nueva sentencia creada
     * @author Lucas Moyano
     */
    public AsignationNode addNewAsignationSentence() {
        AsignationNode newSentence = new AsignationNode(getStruct(), getMethod());

        sentenceList.add(newSentence);
        return newSentence;
    }

    /**
     * Esta función se encarga de crear un ExpBin y
     * lo agrega a la lista de sentencias
     * @return devuelve la nueva sentencia creada
     * @author Lucas Moyano
     */
    public ExpBin addNewExpBinSentence() {
        ExpBin newSentence = new ExpBin(getStruct(), getMethod());

        sentenceList.add(newSentence);
        return newSentence;
    }

    /**
     * Esta función se encarga de crear un ExpUn y
     * lo agrega a la lista de sentencias
     * @return devuelve la nueva sentencia creada
     * @author Lucas Moyano
     */
    public ExpUn addNewExpUnSentence() {
        ExpUn newSentence = new ExpUn(getStruct(), getMethod());

        sentenceList.add(newSentence);
        return newSentence;
    }

    /**
     * Esta función se encarga de crear un IfThenElseNode y
     * lo agrega a la lista de sentencias
     * @return devuelve la nueva sentencia creada
     * @author Lucas Moyano
     */
    public IfThenElseNode addNewIfThenElseSentence() {
        IfThenElseNode newSentence = new IfThenElseNode(getStruct(), getMethod());

        sentenceList.add(newSentence);
        return newSentence;
    }

    /**
     * Esta función se encarga de crear un ReturnNode y
     * lo agrega a la lista de sentencias
     * @return devuelve la nueva sentencia creada
     * @author Lucas Moyano
     */
    public ReturnNode addNewReturnSentence() {
        ReturnNode newSentence = new ReturnNode(getStruct(), getMethod());

        sentenceList.add(newSentence);
        return newSentence;
    }

    /**
     * Esta función se encarga de crear un WhileNode y
     * lo agrega a la lista de sentencias
     * @return devuelve la nueva sentencia creada
     * @author Lucas Moyano
     */
    public WhileNode addNewWhileSentence() {
        WhileNode newSentence = new WhileNode(getStruct(), getMethod());

        sentenceList.add(newSentence);
        return newSentence;
    }

    public List<SentenceNode> getSentenceList() {
        return sentenceList;
    }
}
