/? Descripción: caso de prueba que chequea que los metodos redefinidos no tiren error
/? y se muestren correctamente en el JSON
/? Salida esperada:
/? CORRECTO: SEMANTICO - DECLARACIONES

struct A{}
impl A{
    fn m1() -> void {}
    fn m2(Int p) -> void {}
}

struct B : A{}
impl B {
    fn m2(Int x) -> void {}
    fn m3() -> void {}
}

struct C : B {}
impl C{
    fn m3() -> void {}
    fn m4() -> void {}
}
start {}