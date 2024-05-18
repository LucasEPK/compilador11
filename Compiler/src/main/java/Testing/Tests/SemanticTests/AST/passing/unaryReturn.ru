/? Testea si andan los return con unarios
struct LitTest {
}
impl LitTest {
	.(){}

	fn retInt() -> Int{
	    ret -3;
    }

    fn retInt2() -> Int{
        ret +3;
    }

    fn retInt3() -> Int{
        ret ++3;
    }

    fn retInt4() -> Int{
        ret --3;
    }

    fn retBool() -> Bool{
        ret !true;
    }

}
start{
}