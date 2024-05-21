/? Descripción: caso de prueba que verifica que cuando se llame
/? a un meétodo, se le pase la misma cantidad de parametros que el metodo
/? ERROR: SEMÁNTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 17 | COLUMNA 4 | LA CANTIDAD DE PARAMETROS ES DISTINTA


struct A{
}
impl A{
.(){}
fn m1(Int a) -> void{}
}

start{
A a;
(a.m1());
}