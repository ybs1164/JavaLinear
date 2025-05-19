package tensor;

public interface Scalar extends Cloneable, Comparable<Scalar> {
    String getValue(); // 12
    void setValue(String value); // 12

    void add(Scalar other); // 18
    void multiply(Scalar other); // 19

    Scalar clone();
}
