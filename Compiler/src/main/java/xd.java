import LexicalAnalyzer.LexicalAnalyzer;

public class xd {
    public static void main(String args[]) {
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;AaH_1_4Y55hJ$EOF$"); //NewLine SemiColon, StructID, $EOF$ test debería reconocer a todas
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;aAbj0_$EOF$"); // StructID test deberia reconocerlo
        LexicalAnalyzer xd = new LexicalAnalyzer("\\n;012a8$EOF$"); // IntLiteral test deberia reconocerlo
    }
}
