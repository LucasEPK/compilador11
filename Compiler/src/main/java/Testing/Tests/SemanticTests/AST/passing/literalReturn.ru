/? Testea si andan los return con literales
struct LitTest {
}
impl LitTest {
	.(){}
	fn retNil() -> void{
		ret nil;
	}

    fn retInt() -> Int{
        ret 3;
    }

    fn retBool() -> Bool{
        ret true;
    }

    fn retBool2() -> Bool{
        ret false;
    }

    fn retStr() -> Str {
        ret "hola";
    }

    fn retChar() -> Char {
        ret 'h';
    }

}
start{
}