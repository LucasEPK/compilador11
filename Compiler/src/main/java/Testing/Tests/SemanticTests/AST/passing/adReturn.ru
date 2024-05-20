/? Descripción: caso de prueba que verifica si andan los return con ad mezclado con mul
/? Salida esperada:
/? CORRECTO: SEMANTICO - SENTENCIAS

struct AdTest {
}
impl AdTest {
	.(){}

	fn retAd1() -> Int{
	    ret 3 + 3;
    }

    fn retAd2() -> Int{
        ret 2 - 3 - 4 - 5 - 6;
    }

    fn retAd3() -> Int{
        ret 3 + 1 / 8 - 5;
    }

}
start{
}