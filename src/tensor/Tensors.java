package tensor;

public class Tensors {
    // 24. 전달받은 두 스칼라의 덧셈이 가능하다.
    public static Scalar add(Scalar a, Scalar b) {
        return ScalarImpl.add(a, b);
    }
    // 25. 전달받은 두 스칼라의 곱셈이 가능하다.
    public static Scalar multiply(Scalar a, Scalar b) {
        return ScalarImpl.multiply(a, b);
    }
    // 26. 전달받은 두 벡터의 덧셈이 가능하다.
    public static Vector add(Vector a, Vector b) {
        return VectorImpl.add(a, b);
    }
    // 27. 전달받은 스칼라와 벡터의 곱셈이 가능하다.
    public static Vector multiply(Scalar scalar, Vector vector) {
        return VectorImpl.multiply(scalar, vector);
    }
    // 28. 전달받은 두 행렬의 덧셈이 가능하다.
    public static Matrix add(Matrix a, Matrix b) {
        return MatrixImpl.add(a, b);
    }
    // 29. 전달받은 두 행렬의 곱셈이 가능하다.
    public static Matrix multiply(Matrix a, Matrix b) {
        return MatrixImpl.multiply(a, b);
    }
    public static Matrix concatColumns(Matrix a, Matrix b) {
        return MatrixImpl.concatColumns(a, b);
    }
    public static Matrix concatRows(Matrix a, Matrix b) {
        return MatrixImpl.concatRows(a, b);
    }
}
