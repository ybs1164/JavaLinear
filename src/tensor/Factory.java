package tensor;

public class Factory {
    // 1. 값(String)을 지정하여 스칼라를 생성할 수 있다.
    public static Scalar createScalar(String s) {
        return new ScalarImpl(s);
    }
    // 2. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라를 생성할 수 있다.
    public static Scalar createRandomScalar(String min, String max) {
        return new ScalarImpl(min, max);
    }

    // 3. 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터를 생성할 수 있다.
    public static Vector createVector(int dimension, String value) {
        return new VectorImpl(dimension, value);
    }
    // 4. i 이상 j 미만의 무작위 값을 요소로 하는 n-차원 벡터를 생성할 수 있다.
    public static Vector createRandomVector(int dimension, String min, String max) {
        return new VectorImpl(dimension, min, max);
    }
    // 5. 1차원 배열로부터 n-차원 벡터를 생성할 수 있다.
    public static Vector createVectorFromArray(String[] values) {
        return new VectorImpl(values);
    }

    // 6. 지정한 하나의 값을 모든 요소의 값으로 하는 m x n 행렬을 생성할 수 있다.
    public static Matrix createMatrix(int rows, int columns, String value) {
        return new MatrixImpl(rows, columns, value);
    }
    // 7. i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬을 생성할 수 있다.
    public static Matrix createRandomMatrix(int rows, int columns, String min, String max) {
        return new MatrixImpl(rows, columns, min, max);
    }
    // 8. csv 파일로부터 m x n 행렬을 생성할 수 있다.
    public static Matrix createMatrixFromCsv(String filepath) {
        return new MatrixImpl(filepath);
    }
    // 9. 2차원 배열로부터 m x n 행렬을 생성할 수 있다.
    public static Matrix createMatrixFromArray(String[][] values) {
        return new MatrixImpl(values);
    }
    // 10. 단위 행렬을 생성할 수 있다.
    public static Matrix createIdentityMatrix(int size) {
        return new MatrixImpl(size);
    }
}
