/? Caso de prueba en donde se intenta asignar void a una variable
/? Salida esperada:
/? ERROR: SEMANTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 15 | COLUMNA 9 | NO ES POSIBLE ASIGNAR VOID A UNA VARIABLE


struct A{}
impl A{
.(){}
fn m1() -> void{}
}
start{
    A a;
    a = a.m1();
}