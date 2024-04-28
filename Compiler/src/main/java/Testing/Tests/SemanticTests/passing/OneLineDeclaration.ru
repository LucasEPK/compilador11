/? Descripción: caso de prueba que chequea que no se reconozca como error declaraciones de varias
/? variables/atributos del mismo tipo en una linea
/? Salida esperada:
/? CORRECTO: SEMANTICO - DECLARACIONES
struct Hola {
    pri Int xd, xdnt, xdddd;
}
impl Hola {
    fn m1() -> void {
        Str ola, la, a;
    }
    .(){}
}
start {}