package tensor;

public interface Scalar extends Cloneable, Comparable<Scalar> {
    // 12. 값을 지정/조회할 수 있다. (String)
    String getValue();
    void setValue(String value);

    // 14s. 스칼라 객체를 콘솔에 출력할 수 있다.
    String toString();

    // 15s. 스칼라 객체의 동등성 판단을 할 수 있다.
    boolean equals(Object other);

    // 16. 스칼라의 경우 값의 대소 비교를 할 수 있다.
    int compareTo(Scalar other);

    // 17s. 스칼라 객체 복제를 할 수 있다.
    Scalar clone();

    // 18. 스칼라는 다른 스칼라와 덧셈이 가능하다.
    void add(Scalar other);
    // 19. 스칼라는 다른 스칼라와 곱셈이 가능하다.
    void multiply(Scalar other);

    // toRref 에 쓰이는 선행 1로 만들기 위한 역수 변환.
    void inverse();
}
