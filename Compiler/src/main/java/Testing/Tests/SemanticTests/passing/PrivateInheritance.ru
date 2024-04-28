/? Descripción: caso de prueba que chequea que una variable privada sea heredada
/? Salida esperada:
/? El json construido correctamente con la variable v4 en B y C

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