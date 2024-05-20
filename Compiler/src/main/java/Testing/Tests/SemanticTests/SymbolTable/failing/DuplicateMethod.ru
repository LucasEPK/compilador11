/? Descripción: caso de prueba que verifica que un impl no tenga dos
/? metodos con el mismo nombre
/? Salida esperada:
/? ERROR: SEMÁNTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 12 | COLUMNA 4 | EL MÉTODO YA ESTA DECLARADO

struct A{}
impl A{
.(){}
fn hola(Int a) -> void{}
fn hola(Char b) -> Bool{}}
start{}