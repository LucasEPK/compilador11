
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
    v1 = 1;
    v1 = v1 + olaVar.m1();
    (olaVar.m1());
    while (v1 == 2) {
        (2+2);
    }
}