struct A {
    pri B a1;
}
impl A{
    st fn m1(B p1)->Array Int
    {
        A v1;
        Array Int a;
        Object v2;
        v1 = p1;
        a1 = (p1);
        v1 = new C();
        p1 = nil;
        v2 = new Object();
        v2 = p1;
        a = (v1.m2());
        v1 = self;
        ret a;
    }



    st fn m2()->void
    {
    Int a;
    ret a;}
    .(){
    Int a;
    a = 0;
    }
}

struct B : A{}
impl B {
    .(){}
}

struct C : B{}
impl C{.(){ }
fn m7() -> void{}}


start{
Array Int a;
Int b;
Str c;
c = "hola";
b = 2;
a = new Int[c];
(A.m2());
}