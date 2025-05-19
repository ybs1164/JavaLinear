package tensor;

public interface Vector extends Cloneable, Comparable<Vector> {
    Scalar getVectorElement(int index); // 11
    void setVectorElement(int index, Scalar value); // 11

    int getVectorSize(); // 13

    void add(Vector other);
    void multiply(Scalar other);

    Vector clone();

    Matrix toColumnMatrix();
    Matrix toRowMatrix();
}
