/? Descripción: caso de prueba que verifica que no se declaren dos structs
/? de una misma clase
/? Salida esperada:
/? ERROR: SEMÁNTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 11 | COLUMNA 8 | YA EXISTE STRUCT DE ESTA CLASE

struct A{}
impl A{
.(){}}
struct A{}
start{}