/? salida esperada:
/? CORRECTO

struct Bruh {
    Int xd;
}
impl Bruh {
    .(){}
    fn m1()->void{
        xd = 1;
        (m2(xd));
    }
    fn m2(Int v1)->void{
        (IO.out_int(v1));
    }
}
start{
        Bruh bruh1;
        bruh1 = new Bruh();
        (bruh1.m1());
}