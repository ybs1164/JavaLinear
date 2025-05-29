package tensor;

public interface Vector extends Cloneable {
    // 11v. 특정 위치의 요소를 지정/조회할 수 있다. (Scalar)
    Scalar getVectorElement(int index);
    void setVectorElement(int index, Scalar value);

    // 13v. 크기 정보를 조회할 수 있다.
    int getVectorSize();

    // 14v. 벡터 객체를 콘솔에 출력할 수 있다.
    String toString();

    // 15v. 벡터 객체의 동등성 판단을 할 수 있다.
    boolean equals(Object other);

    // 17v. 벡터 객체 복제를 할 수 있다.
    Vector clone();

    // 20. 벡터는 다른 벡터와 덧셈이 가능하다.
    void add(Vector other);
    // 21. 벡터는 다른 스칼라와 곱셈이 가능하다.
    void multiply(Scalar other);

    // 30. n-차원 벡터 객체는 자신으로부터 nx1 행렬을 생성하여 반환할 수 있다.
    Matrix toColumnMatrix();
    // 31. n-차원 벡터 객체는 자신으로부터 1xn 행렬을 생성하여 반환할 수 있다.
    Matrix toRowMatrix();
}
