
struct Hola {

}
impl Hola {
    .(){}
    fn m1() -> Int {
        ret 3;
    }
}
start {
    Hola olaVar;
    Int v1;
    olaVar = new Hola();
    v1 = v1 + olaVar.m1();

    if (v1 == 2) {
        (2+2);
    } else {
        (3+3);
    }
}