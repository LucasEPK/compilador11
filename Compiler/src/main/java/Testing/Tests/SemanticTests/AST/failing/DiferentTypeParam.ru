/? Descripción: caso de prueba que verifica que cuando se llame
/? a un meétodo, los tipos de los parametros sean iguales a los del metodo
/? ERROR: SEMÁNTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 14 | COLUMNA 7 | LOS TIPOS DE LA LLAMADA Y LOS PARAMETROS DEL METODO SON DIFERENTES


struct A{
}
impl A{
.(){}
fn m1(Int a) -> void{}
}

start{
A a;
(a.m1(a));
}