struct A {
    B a1;
}
impl A{
    fn m1(B p1)->void
    {
        A v1;
        Object v2;
        v1 = p1;
        a1 = (p1);
        v1 = new C();
        p1 = nil;
        v2 = new Object();
        v2 = p1;
        v1 = self;

    }

    fn m2()->void
    {}
    .(){}
}


struct B : A{}
impl B {
    .(){}
}

struct C : B{}
impl C{.(){ }}


start{}