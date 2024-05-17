package SemanticAnalyzer.AST;

public interface Commons {

    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     * @autor Yeumen Silva
     */

    public String toJson(int tabs);


    /**
     * Método que recorre el AST y consolida los nodos
     * @return void
     * @autor Yeumen Silva
     */

    public void consolidate();

    /**
     * Método que agrega una cantidad de tabulaciones a un string
     * @param tabs cantidad de tabulaciones a agregar
     * @return string con las tabulaciones agregadas
     * @autor Yeumen Silva
     */


    String addtabs(int tabs);
}
