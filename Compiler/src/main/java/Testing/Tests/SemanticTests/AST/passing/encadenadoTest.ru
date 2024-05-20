/? Descripción: caso de prueba que verifica si el encadenado funciona bien
/? además testea diferentes tipos de asignaciones
/? Salida esperada:
/? CORRECTO: SEMANTICO - DECLARACIONES

struct Arreglo {
    Array Int arreglo;
}
impl Arreglo {

	.(){
	    self.arreglo = new Int[2+1+1+1-2];
	}

}
impl Encadenado1o {
    .(){
        self.bruh = new Encadenado2o();
    }
}
struct Encadenado1o {
    Encadenado2o bruh;
}
struct Encadenado2o {
}
impl Encadenado2o {
    .() {}
    fn test(Bool p1, Char p2, Str p3) -> Encadenado3o {
        Encadenado3o encadenado3;
        encadenado3 = new Encadenado3o();
        ret encadenado3;
    }
}
struct Encadenado3o {
    Array Int ola;
}
impl Encadenado3o {
    .(){
        Int xd;
        xd = 5;
        self.ola = new Int[xd-1];
        ola[1] = 11;
    }
}
start{
    Arreglo hola;
    Encadenado1o xd;
    hola = new Arreglo();
    arregloXd = hola.arreglo;
    arregloXd[0] = xd.bruh.test(true, 'h', "xd").ola[1];
}