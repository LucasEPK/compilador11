import LexicalAnalyzer.LexicalAnalyzer;

public class xd {
    public static void main(String args[]) {
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;AaH_1_4Y55hJ$EOF$"); //NewLine SemiColon, StructID, $EOF$ test debería reconocer a todas
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;aAbj0_$EOF$"); // StructID test deberia reconocerlo
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;012a8$EOF$"); // IntLiteral test deberia reconocerlo
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;*/%$EOF$"); // OpMul test debería reconocerlo
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;\"hola@\"xd$EOF$"); //StrLiteral test, debería reconocerlo
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;'x''\\t'$EOF$"); //CharLiteral test, debería reconocerlo
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;\\r\\t\\v$EOF$"); // CarriageReturn, Tab y VerticalTab test, debería reconocerlos
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;\\?holaYeuEstasLeyendoEsto?\\nxd$EOF$"); // SimpleComment test, debería reconocerlo
        //LexicalAnalyzer xd = new LexicalAnalyzer("\\n;=,==\\n$EOF$"); // Assignment y Equal test, debería reconocerlos
        LexicalAnalyzer xd = new LexicalAnalyzer("\\n;!,!=\\n$EOF$"); // Logical y Equal test, debería reconocerlos
    }
}
