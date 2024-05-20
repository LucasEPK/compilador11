/? Descripción: caso de prueba que verifica que una variable privada sea heredada
/? Salida esperada:
/? CORRECTO: SEMANTICO - DECLARACIONES

struct A{
    Int v1, v2;
}
impl A{.(){}}
struct B : A{
    Str v3;
    pri Bool v4;
}
impl B{.(){}}
struct C : B {
    Int v5;
    Int v6;
}
impl C{.(){}}
start{}