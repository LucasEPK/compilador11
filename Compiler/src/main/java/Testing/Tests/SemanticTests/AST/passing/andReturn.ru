/? Descripción: caso de prueba que verifica si andan los return con and
/? Salida esperada:
/? CORRECTO: SEMANTICO - SENTENCIAS

struct AndTest {
}
impl AndTest {
	.(){}

	fn retAnd() -> Bool{
	    ret true && false;
    }

    fn retAnd2() -> Bool{
        ret false && true && false && true;
    }

}
start{
}