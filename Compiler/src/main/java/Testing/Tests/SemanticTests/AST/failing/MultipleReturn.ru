/? Descripción: caso de prueba que verifica que los tipos de los return sean iguales
/? Salida esperada:
/? ERROR: SEMANTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 7 | COLUMNA 4 | LOS TIPOS DE RETORNO NO COINCIDEN
struct A{}
impl A{
.(){}
fn m1() -> Int{
if(true){
ret 1;
}else{
ret true;}
}
}

start{}