/? Testea si andan los return con igual
struct IgualTest {
}
impl IgualTest {
	.(){}

	fn retIgu() -> Bool{
	    ret 3 == 3;
    }

    fn retIgu2() -> Bool{
        ret 2 != 3;
    }

    fn retIgu3() -> Bool{
        ret 3 == 3 == true;
    }

}
start{
}