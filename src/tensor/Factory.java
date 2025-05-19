package tensor;

public class Factory {
    public static Scalar createScalar(String s) {
        return new ScalarImpl(s);
    }
    public static Scalar createRandomScalar(String min, String max) {
        return new ScalarImpl(min, max);
    }

    public static Vector createVector(int dimension, String value) {
        return new VectorImpl(dimension, value);
    }
    public static Vector createRandomVector(int dimension, String min, String max) {
        return new VectorImpl(dimension, min, max);
    }
    public static Vector createVectorFromArray(String[] values) {
        return new VectorImpl(values);
    }

    public static Matrix createMatrix(int rows, int columns, String value) {
        return new MatrixImpl(rows, columns, value);
    }
    public static Matrix createRandomMatrix(int rows, int columns, String min, String max) {
        return new MatrixImpl(rows, columns, min, max);
    }
    public static Matrix createMatrixFromCsv(String filepath) {
        return new MatrixImpl(filepath);
    }
    public static Matrix createMatrixFromArray(String[][] values) {
        return new MatrixImpl(values);
    }
    public static Matrix createIdentityMatrix(int size) {
        return new MatrixImpl(size);
    }
}
