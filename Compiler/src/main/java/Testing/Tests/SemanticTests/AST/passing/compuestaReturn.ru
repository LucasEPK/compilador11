/? Descripción: caso de prueba que verifica si andan los return con compuesta y mezclado con mul y ad
/? Salida esperada:
/? CORRECTO: SEMANTICO - SENTENCIAS

struct CompuestaTest {
}
impl CompuestaTest {
	.(){}

	fn retCo1() -> Bool{
	    ret 3 < 3;
    }

    fn retCo2() -> Bool{
        ret 2 <= 3 - 4 - 5 - 6;
    }

    fn retCo3() -> Bool{
        ret 3 + 1 / 8 >= 5;
    }

}
start{
}