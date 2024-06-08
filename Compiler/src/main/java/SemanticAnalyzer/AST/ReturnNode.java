package SemanticAnalyzer.AST;


import CodeGeneration.CodeGenerator;
import Exceptions.SemanticExceptions.AST.StructNotFound;
import SemanticAnalyzer.SymbolTable.Struct;
import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Clase representate un return en nuestro AST
 * @author Yeumen Silva
 */

public class ReturnNode extends SentenceNode implements Commons {

    ExpressionNode returnValueNode = null;


    public ReturnNode(String struct, String method) {
        super(struct, method);
    }

    public void setReturnValueNode(ExpressionNode returnValueNode) {
        this.returnValueNode = returnValueNode;
    }

    public ExpressionNode getReturnValueNode() {
        return returnValueNode;
    }

    @Override
    public String toJson(int tabs) {

            String json = addtabs(tabs) + "{\n";
            json += addtabs(tabs+1) + "\"nombre\": \"" + "Return" + "\",\n";
            //imprime type
            json += addtabs(tabs+1) + "\"type\": \"" + this.getType() + "\",\n";
            if (returnValueNode != null) {
                json += addtabs(tabs+1) + "\"return\": \n " + returnValueNode.toJson(tabs+1) + "\n";
            } else {
                json += addtabs(tabs+1) + "\"return\": " + "nil" + "\n";
            }
            json += addtabs(tabs) + "}\n";
            return json;

    }

    @Override
    public void consolidate(AST ast) {

        //Si no tiene return, ya esta consolidado
        if(returnValueNode == null){
            this.setConsolidated(true);
        }else {
            //Si no esta consolidado, lo consolido
            if(returnValueNode.getConsolidated() == false){
                returnValueNode.consolidate(ast);
            }


            this.setType(returnValueNode.getType());
            //Seteo que es un array si lo es
            this.setIsArray(returnValueNode.getIsArray());

            if(this.getType().equals("nil") == false){
                Struct actualStruct = ast.getSymbolTable().getStructs().get(this.getType());
                if(actualStruct == null){
                    throw  new StructNotFound(this.getReferenceToken());
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

    @Override
    public String generateCode(CodeGenerator codeGenerator) {
        String textCode = "";
        textCode += "\t# Return\n";

        //Debemos generar el codigo del returnValueNode
        if(returnValueNode != null){
            textCode += returnValueNode.generateCode(codeGenerator);
        }

        //Generamos el codigo del return
        //Lo guardamos en nuestro registro de activacion
        //En el tope de la pila
        textCode += "\tla $t9,($v0) #cargo en $t9 el valor de retorno\n";
        textCode += "\tpush #Lo pusheo al stack\n";

        //Debo hacer un jump a la direccion de retorno
        //Para eso debo cargar la direccion de retorno en $ra
        textCode += "\tlw $ra,0($fp) #Recupero el return adress\n";
        textCode += "\tjr $ra #Vuelvo al return adress\n";

        textCode += "\t #Fin Return\n";
        return textCode;
    }
}
