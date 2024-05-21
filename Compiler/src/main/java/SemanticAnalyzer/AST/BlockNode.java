package SemanticAnalyzer.AST;


import Exceptions.SemanticExceptions.AST.MultipleReturn;
import Exceptions.SemanticExceptions.AST.ReturnInStart;
import Exceptions.SemanticExceptions.AST.ReturnTypeDontMatch;
import SemanticAnalyzer.SymbolTable.Methods;
import SemanticAnalyzer.SymbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase representate un bloque de codigo de un metodo
 * @author Yeumen Silva
 */

public class BlockNode extends SentenceNode implements Commons {

    private List<SentenceNode> sentenceList = new ArrayList<SentenceNode>();

    // Esta variable indica si es un bloque derivado de una sentencia
    private boolean isSentenceBlock = false;

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
                throw new ReturnInStart(((ReturnNode) sentence).getReturnValueNode().getToken());
            }
            if(!sentence.getType().equals("void")){
                if(!(sentence instanceof PrimaryNode)){
                    if(getType().equals("void")){
                        setType(sentence.getType());
                    }else {
                        if(!getType().equals(sentence.getType())){
                            //ToDo
                            throw new MultipleReturn(sentence.getReferenceToken());
                        }
                    }
                }
            }
        }

        //Si no es el constructor
        if(!getMethod().equals(".")){
            //Comparo retorno del bloque y del método
            if(!getStruct().equals("start")){
                Methods method = ast.searchMethod(this.struct,this.method,this.getReferenceToken());
                if(method == null){
                    String returnType = "void";
                    if(!returnType.equals(getType())){
                        throw new ReturnTypeDontMatch(this.getReferenceToken());
                    }
                }else {
                    String methodType = method.getGiveBack().getName();
                    boolean hasReturn = false;
                    for(SentenceNode sentence : sentenceList){
                        if(sentence instanceof ReturnNode){
                            hasReturn = true;
                        }
                    }
                    if(!hasReturn && !methodType.equals("void")){
                        System.out.println("chau");
                        //Todo
                        //throw new NoReturn(this.getToken());
                    }
                    if(!methodType.equals(getType())){
                        if(!ast.isSubStruct(getType(),method.getGiveBack().getName())){
                            //ToDo
                            System.out.println("a");
                            //throw new ReturnTypeDontMatch(this.getReferenceToken());

                        }
                    }
                }
            }
            this.setConsolidated(true);
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
     * Esta función se encarga de agregar la sentencia dada a la lista
     * @param sentence sentencia que se quiere agregar a la lista
     * @author Lucas Moyano
     */
    public void addNewSentence(SentenceNode sentence){
        sentenceList.add(sentence);
    }

    public List<SentenceNode> getSentenceList() {
        return sentenceList;
    }

    public void setIsSentenceBlock(boolean isSentenceBlock) {
        this.isSentenceBlock = isSentenceBlock;
    }

    public boolean getIsSentenceBlock(){
        return this.isSentenceBlock;
    }
}
