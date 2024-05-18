/? Testea si andan los return con muls
struct MulTest {
}
impl MulTest {
	.(){}

	fn retMul1() -> Int{
	    ret 3 * 3;
    }

    fn retMul2() -> Int{
        ret 3 * 1 / 8 % 5;
    }

}
start{
}