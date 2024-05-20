/? Descripción: caso de prueba que verifica si andan los return con or
/? Salida esperada:
/? CORRECTO: SEMANTICO - DECLARACIONES

struct OrTest {
}
impl OrTest {
	.(){}

	fn retOr() -> Bool{
	    ret true || false;
    }

    fn retOr2() -> Bool{
        ret false || true || false || true;
    }

    fn retOr3() -> Bool{
        ret false || true && false || true;
    }

}
start{
}