/? Testea si andan los return con and
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