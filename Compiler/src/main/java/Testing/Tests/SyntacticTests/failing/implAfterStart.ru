/? Descripción: este caso de prueba se fija si el lenguaje tira error cuando
/? tiene un impl después de un start
/? Salida esperada:
/? ERROR: SINTÁCTICO
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 8 | COLUMNA 1 | Se esperaba: $EOF$ y llegó: impl
start {}
impl Bruh {.(){}}