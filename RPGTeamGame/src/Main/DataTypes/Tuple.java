package Main.DataTypes;

public class Tuple<A,B>
{
    private final A data1;
    private final B data2;

    public Tuple(A a, B b)
    {
        data1 = a;
        data2 = b;
    }

    public A getData1() {
        return data1;
    }

    public B getData2() {
        return data2;
    }
}
