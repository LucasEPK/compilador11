/? Descripción: caso de prueba que verifica que no se puede heredar de un
/? Struct inexistente
/? Salida esperada:
/? ERROR: SEMÁNTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 8 | COLUMNA 12 | EL STRUCT NO ESTA DEFINIDO

struct A : B{}
impl A{
.(){}}
start{}