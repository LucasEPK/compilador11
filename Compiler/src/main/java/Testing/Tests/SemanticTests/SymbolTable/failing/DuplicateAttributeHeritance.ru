/? Descripción: caso de prueba que verifica que no se vuelva a declarar un
/? atributo ya declarado en alguna superclase
/? Salida esperada:
/? ERROR: SEMÁNTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 15 | COLUMNA 6 | EL ATIBUTO YA ESTA DECLARADO EN UNA SUPERCLASE

struct A{
Array Char b;}

impl A{
.(){}}

struct B : A{
Bool b;}

impl B{
.(){}}

start{}