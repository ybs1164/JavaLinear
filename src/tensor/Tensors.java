package tensor;

public class Tensors {
    public static Scalar add(Scalar a, Scalar b) {
        return ScalarImpl.add(a, b);
    }
    public static Scalar multiply(Scalar a, Scalar b) {
        return ScalarImpl.multiply(a, b);
    }
    public static Vector add(Vector a, Vector b) {
        return VectorImpl.add(a, b);
    }
    public static Vector multiply(Scalar scalar, Vector vector) {
        return VectorImpl.multiply(scalar, vector);
    }
    public static Matrix add(Matrix a, Matrix b) {
        return MatrixImpl.add(a, b);
    }
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
