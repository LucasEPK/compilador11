/? Descripción: caso de prueba generico
/? Salida esperada:
/? CORRECTO: SEMANTICO - DECLARACIONES

struct Derivada : Base{
pri Int x;
}
impl Derivada{
.(){}
fn m2(Str a)-> void{ }
}
struct Base {
Str s;
pri Int a;
}
impl Base{
.(){}
fn m1()->void{
}
}
start{
Base b1;
b1 = new Derivada();
(b1.m1());
}