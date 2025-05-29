package test;
import tensor.*;


public class Test {
    public static void main(String[] args) {
        // no.01 : 스칼라 생성
        Scalar scalar1 = Factory.createScalar("123");
        System.out.println("no.01 | Scalar 값 생성. : " + scalar1);

        try {
            Scalar wrongScalar = Factory.createScalar("a");
        } catch (TensorInvalidInputException e) {
            System.out.println("예외처리 | 잘못된 Scalar 생성 입력 Factory.createScalar(\"a\") : " + e.getMessage());
        }

        //no.02 : 랜덤 스칼라 생성
        Scalar scalar2 = Factory.createRandomScalar("-5.0", "5.0");
        System.out.println("no.02 | Random Scalar 생성 Factory.createRandomScalar(\"-5.0\", \"5.0\") : " + scalar2);

        try {
            Scalar wrongRandomScalar = Factory.createRandomScalar("5.0", "-5.0");
        } catch (TensorInvalidInputException e) {
            System.out.println("예외처리 | 잘못된 Scalar 범위 Factory.createRandomScalar(\"5.0\", \"-5.0\") : " + e.getMessage());
        }

        //no.03 : 벡터 생성
        Vector vector = Factory.createVector(8, "2");
        System.out.println("no.03 | 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터를 생성 할 수 있다. Factory.createVector(8, \"2\") : " + vector);

        try {
            Vector wrongVector = Factory.createVector(-1, "1");
        } catch (TensorInvalidDimensionException e) {
            System.out.println("예외처리 | 음수 차원 벡터 생성. Factory.createVector(-1, \"1\") : " + e.getMessage());
        }

        //no.04 : 랜덤 벡터 생성
        vector = Factory.createRandomVector(10, "-5.0", "5.0");
        System.out.println("no.04 | Random Vector 생성. Factory.createRandomVector(10, \"-5.0\", \"5.0\") : " + vector);

        //no.05 : 배열값 벡터 생성
        String[] array = {"1.0", "2.0", "3.0", "4.0", "5.0"};
        vector = Factory.createVectorFromArray(array);
        System.out.println("no.05 | 배열 기반 Vector 생성. Factory.createVectorFromArray(array) : " + vector);

        //no.06 : 행렬 생성
        Matrix matrix = Factory.createMatrix(3, 6, "2.0");
        System.out.println("no.06 | 지정한 하나의 값을 모든 요소의 값으로 채워진 Matrix 생성. Factory.createMatrix(3, 6, \"2.0\") : \n" + matrix);

        //no.07 : 랜덤 행렬 생성
        matrix = Factory.createRandomMatrix(3, 5, "-5.0", "5.0");
        System.out.println("no.07 | Random Matrix 생성 (-5.0 ~ 5.0). Factory.createRandomMatrix(3, 5, \"-5.0\", \"5.0\") : \n" + matrix);

        //no.08 : csv 파일 행렬 생성
        System.out.println("no.08 | csv 파일로부터 m x n 행렬을 생성할 수 있다.");
        String filepath = "src/test/3by3matrix.csv";
        try {
            System.out.println("1. matrix from csv file(" + filepath + ")");
            matrix = Factory.createMatrixFromCsv(filepath);
            System.out.println(matrix);
        } catch (TensorInvalidInputException e) {
            System.out.println(e.getMessage());
        }
        try {
            filepath = "src/test/3by3matrix_InvalidValue.csv";
            System.out.println("2. matrix from csv file(" + filepath + ")");
            matrix = Factory.createMatrixFromCsv(filepath);
            System.out.println(matrix);
        } catch (TensorInvalidInputException e) {
            System.out.println(e.getMessage());
        }
        try {
            filepath = "src/test/3by3matrix_InvalidRange.csv";
            System.out.println("3. matrix from csv file(" + filepath + ")");
            matrix = Factory.createMatrixFromCsv(filepath);
            System.out.println(matrix);
        } catch (TensorInvalidInputException e) {
            System.out.println(e.getMessage());
        }
        try {
            filepath = "src/test/3by3matrix_NotExist.csv";
            System.out.println("4. matrix from csv file(" + filepath + ")");
            matrix = Factory.createMatrixFromCsv(filepath);
            System.out.println(matrix);
        } catch (TensorInvalidInputException e) {
            System.out.println(e.getMessage());
        }

        //no.09 : 2차원 배열 행렬 생성
        String[][] array2d = {{"1.0", "5.0"}, {"4.0", "3.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        System.out.println("no.09 | 배열 기반 Matrix 생성. Factory.createMatrixFromArray({{\"1.0\", \"2.0\"}, {\"3.0\", \"4.0\"}}) : " + matrix);

        try {
            String[][] irregularArray = {{"1"}, {"2", "3"}};
            Factory.createMatrixFromArray(irregularArray);
        } catch (TensorInvalidInputException e) {
            System.out.println("예외처리 | 불규칙 배열로부터 행렬 생성 실패 : " + e.getMessage());
        }

        //no.10 : 단위행렬 생성
        matrix = Factory.createIdentityMatrix(5);
        System.out.println("no.10 | 단위행렬 생성. Factory.createIdentityMatrix(5) : " + matrix);

        try {
            Matrix wrongMatrix = Factory.createIdentityMatrix(-1);
        } catch (TensorInvalidDimensionException e) {
            System.out.println("예외처리 | 음수 차원 단위행렬 생성 : \n" + e.getMessage());
        }

        //no.11v : 벡터 특정 위치의 요소 지정/조회
        String[] input11v = {"1", "2", "3", "4", "5", "6"};
        String[] result11v = {"1", "2", "1", "4", "5", "6"};
        vector = Factory.createVectorFromArray(input11v);
        vector.setVectorElement(2, Factory.createScalar("1"));
        System.out.println("no.11v | 요소 변경 후 Vector 전체 비교 : " + vector.equals(Factory.createVectorFromArray(result11v)));
        System.out.println("no.11v | 특정 요소 값 확인 (index 1) : " + vector.getVectorElement(3).equals(Factory.createScalar("4")));

        try {
            System.out.println(vector.getVectorElement(9));
        } catch (TensorInvalidIndexException e) {
            System.out.println("예외처리 | 벡터 인덱스 범위 초과 접근 : " + e.getMessage());
        }

        //no.11m : 행렬 특정 위치의 요소 지정/조회
        String[][] input11m = {{"1", "2", "12"}, {"3", "4", "32"}};
        String[][] result11m = {{"1", "2", "12"}, {"3", "1", "32"}};
        matrix = Factory.createMatrixFromArray(input11m);
        matrix.setMatrixElement(1, 1, Factory.createScalar("1"));
        System.out.println("no.11m | 요소 변경 후 Matrix 전체 비교 : " + matrix.equals(Factory.createMatrixFromArray(result11m)));
        System.out.println("no.11m | 특정 요소 값 확인 index (1, 2) : " + matrix.getMatrixElement(1, 2).equals(Factory.createScalar("32")));

        //no.12 : 스칼라 값 지정/조회
        Scalar scalar = Factory.createScalar("10.0");
        scalar.setValue("8");
        System.out.println("no.12 | Scalar 값 변경 후 확인 : " + scalar.equals(Factory.createScalar("8")));
        System.out.println("no.12 | Scalar 값 출력 : " + scalar.getValue());

        //no.13v : 벡터 크기 반환
        vector = Factory.createRandomVector(8, "-3.0", "8.0");
        System.out.println("no.13v | Vector 크기 확인 : " + (vector.getVectorSize() == 8));

        //no.13m : 행렬 행과 열 크기 반환
        matrix = Factory.createMatrix(3, 4, "1.0");
        System.out.println("no.13m | Matrix row/column size 확인 : " + (matrix.getMatrixRowCount() == 3 && matrix.getMatrixColumnCount() == 4));

        //no.14s : 스칼라 toString 오버라이딩
        scalar = Factory.createScalar("-3.0");
        System.out.println("no.14s | Scalar toString 오버라이딩 출력 : " + scalar);

        //no.14v : 벡터 toString 오버라이딩
        vector = Factory.createVector(5, "-3.0");
        System.out.println("no.14v | Vector toString 오버라이딩 출력 : " + vector);

        //no.14m : 행렬 toString 오버라이딩
        matrix = Factory.createMatrix(3, 5, "3.0");
        System.out.println("no.14m | Matrix toString 오버라이딩 출력 : \n" + matrix);

        //no.15s : 스칼라 객체의 동등성 비교
        scalar1 = Factory.createScalar("5");
        scalar2 = Factory.createScalar("5");
        Scalar scalar3 = Factory.createScalar("2");
        System.out.println("no.15s | Scalar 동일성 비교 - scalar1.equals(scalar2) : " + scalar1.equals(scalar2));
        System.out.println("no.15s | Scalar 동일성 비교 - 두 객체는 다른 객체이다. scalar1 == scalar2 (false) : " + (scalar1 == scalar2));
        System.out.println("no.15s | Scalar 동일성 비교 - scalar1.equals(scalar3) (false) : " + scalar1.equals(scalar3));

        //no.15v : 벡터 객체의 동등성 비교
        Vector vector1 = Factory.createVector(4, "1.0");
        Vector vector2 = Factory.createVector(4, "1.0");
        Vector vector3 = Factory.createVector(6, "2.0");
        System.out.println("no.15v | Vector 동일성 비교 - vector1.equals(vector2) : " + vector1.equals(vector2));
        System.out.println("no.15v | Vector 동일성 비교 - 두 객체는 다른 객체이다. vector1 == vector2 : " + (vector1 == vector2));
        System.out.println("no.15v | Vector 동일성 비교 - vector1.equals(vector3) (false) : " + vector1.equals(vector3));

        //no.15m : 행렬 객체의 동등성 비교
        Matrix matrix1 = Factory.createMatrix(3, 2, "1.0");
        Matrix matrix2 = Factory.createMatrix(3, 2, "1.0");
        Matrix matrix3 = Factory.createMatrix(2, 2, "-1.0");
        System.out.println("no.15m | Matrix 동일성 비교 - matrix1.equals(matrix2) : " + matrix1.equals(matrix2));
        System.out.println("no.15m | Matrix 동일성 비교 - 두 객체는 다른 객체이다. matrix1 == matrix2 : " + (matrix1 == matrix2));
        System.out.println("no.15m | Matrix 동일성 비교 - matrix1.equals(matrix3) (false) : " + matrix1.equals(matrix3));

        //no.16 : 스칼라 값 비교
        System.out.println("no.16 | Scalar 비교 - scalar1 > scalar3 : " + (scalar1.compareTo(scalar3) > 0));
        System.out.println("no.16 | Scalar 비교 - scalar3 < scalar1 : " + (scalar3.compareTo(scalar1) < 0));
        System.out.println("no.16 | Scalar 비교 - scalar1 == scalar2 : " + (scalar1.compareTo(scalar2) == 0));

        //no.17 : 객체 복제
        scalar1 = Factory.createScalar("99");
        scalar2 = scalar1.clone();
        System.out.println("no.17 | Scalar 복제 비교 : " + scalar1.equals(scalar2));
        System.out.println("no.17 | Scalar 복제 비교 (false) : " + (scalar1 == scalar2));

        vector1 = Factory.createVector(5, "2.0");
        vector2 = vector1.clone();
        System.out.println("no.17v | Vector 복제 비교 : " + vector1.equals(vector2));
        System.out.println("no.17v | Vector 복제 비교 (false) : " + (vector1 == vector2));

        matrix1 = Factory.createMatrix(2, 2, "3.0");
        matrix2 = matrix1.clone();
        System.out.println("no.17m | Matrix 복제 비교 : " + matrix1.equals(matrix2));
        System.out.println("no.17m | Matrix 복제 비교 (false) : " + (matrix1 == matrix2));


        //no.18 : 스칼라 덧셈
        scalar1 = Factory.createScalar("5");
        scalar2 = Factory.createScalar("3");
        scalar1.add(scalar2);
        System.out.println("no.18 | Scalar 덧셈 결과 확인 (5 + 3 = 8) : " + scalar1.equals(Factory.createScalar("8")));

        //no.19 : 스칼라 곱셈
        scalar1 = Factory.createScalar("5");
        scalar2 = Factory.createScalar("3");
        scalar1.multiply(scalar2);
        System.out.println("no.19 | Scalar 곱셈 결과 확인 (5 * 3 = 15) : " + scalar1.equals(Factory.createScalar("15")));

        //no.20 : 벡터 덧셈
        vector1 = Factory.createVector(5, "-3.0");
        vector2 = Factory.createVector(5, "1.0");
        vector1.add(vector2);
        System.out.println("no.20 | Vector 덧셈 결과 확인 (-3 + 1 = -2) : " + vector1.equals(Factory.createVector(5, "-2.0")));

        //no.21 : 벡터 곱셈
        Scalar weight = Factory.createScalar("-3.0");
        vector = Factory.createVector(5, "3.0");
        vector.multiply(weight);
        System.out.println("no.21 | Vector * Scalar 곱셈 결과 확인 (-3 * 3 = -9) : " + vector.equals(Factory.createVector(5, "-9.000")));

        //no.22 : 행렬 덧셈
        matrix1 = Factory.createMatrix(2, 2, "5.0");
        matrix2 = Factory.createIdentityMatrix(2);
        matrix1.add(matrix2);
        System.out.println("no.22 | Matrix 덧셈 결과 확인 (2x2) (5 + I) = [[6 5] [5 6]] : "
                + matrix1.equals(Factory.createMatrixFromArray(new String[][]{{"6.0", "5.0"}, {"5.0", "6.0"}})));

        try {
            Matrix m1 = Factory.createMatrix(2, 3, "1.0");
            Matrix m2 = Factory.createMatrix(3, 2, "1.0");
            m1.add(m2);
        } catch (TensorSizeMismatchException e) {
            System.out.println("예외처리 | 행렬 덧셈 크기 불일치 : " + e.getMessage());
        }

        //no.23 : 행렬 곱셈
        String[][] array1 = {{"1.0", "2.0", "3.0", "4.0"}, {"-1.0", "-2.0", "-3.0", "-4.0"}};
        String[][] array2 = {{"1.0", "4.0", "1.0"}, {"1.0", "2.0", "1.0"}, {"1.0", "3.0", "1.0"}, {"1.0", "2.0", "1.0"}};
        matrix1 = Factory.createMatrixFromArray(array1);
        matrix2 = Factory.createMatrixFromArray(array2);
        matrix1.multiply(matrix2);
        System.out.println("no.23 | Matrix 곱셈 결과 확인 : "
                + matrix1.equals(Factory.createMatrixFromArray(new String[][]{{"10.0", "25.0", "10.0"}, {"-10.0", "-25.0", "-10.0"}})));
        matrix1 = Factory.createMatrixFromArray(array2);
        matrix2 = Factory.createMatrixFromArray(array1);
        matrix1.multiplyRight(matrix2);
        System.out.println("no.23 | Matrix 오른쪽 곱셈 결과 확인 : "
                + matrix2.equals(Factory.createMatrixFromArray(new String[][]{{"10.0", "25.0", "10.0"}, {"-10.0", "-25.0", "-10.0"}})));

        try {
            Matrix m1 = Factory.createMatrix(2, 3, "1.0");
            Matrix m2 = Factory.createMatrix(4, 2, "1.0");
            m1.multiply(m2);
        } catch (MatrixMulMismatchException e) {
            System.out.println("예외처리 | 행렬 곱셈 시 행과 열 크기 불일치 : " + e.getMessage());
        }

        //no.24 : static 스칼라 덧셈
        scalar1 = Factory.createScalar("3");
        scalar2 = Factory.createScalar("7");
        System.out.println("no.24 | static Scalar 덧셈 결과 (3 + 7 = 10) : " + Tensors.add(scalar1, scalar2).equals(Factory.createScalar("10")));

        //no.25 : static 스칼라 곱셈
        System.out.println("no.25 | static Scalar 곱셈 결과 (3 * 7 = 21) : " + Tensors.multiply(scalar1, scalar2).equals(Factory.createScalar("21")));

        //no.26 : static 벡터 덧셈
        vector1 = Factory.createVector(3, "1.0");
        vector2 = Factory.createVector(3, "2.0");
        System.out.println("no.26 | static Vector 덧셈 결과 (1+2=3) : " + Tensors.add(vector1, vector2).equals(Factory.createVector(3, "3.0")));

        //no.27 : static 벡터 곱셈
        vector3 = Factory.createVector(3, "3.0");
        scalar = Factory.createScalar("2.0");
        System.out.println("no.27 | static Vector*Scalar 곱셈 결과 (3*2=6) : " + Tensors.multiply(scalar, vector3).equals(Factory.createVector(3, "6.0")));

        //no.28 : static 행렬 덧셈
        matrix1 = Factory.createMatrix(2, 2, "5.0");
        matrix2 = Factory.createIdentityMatrix(2);
        Matrix result = Tensors.add(matrix1, matrix2);
        System.out.println("no.28 | static Matrix 덧셈 결과 : " + result.equals(Factory.createMatrixFromArray(new String[][]{{"6.0", "5.0"}, {"5.0", "6.0"}})));

        //no.29 : static 행렬 곱셈
        matrix1 = Factory.createMatrixFromArray(array1);
        matrix2 = Factory.createMatrixFromArray(array2);
        System.out.println("no.29 | static Matrix 곱셈 결과 (2x4 * 4x3) 값 확인 : " + Tensors.multiply(matrix1, matrix2).equals(Factory.createMatrixFromArray(new String[][]{{"10.0", "25.0", "10.0"}, {"-10.0", "-25.0", "-10.0"}})));

        //no.30 : 벡터 nx1 행렬 변환
        String[] vectorArray = {"1.0", "2.0", "3.0", "5.0"};
        vector = Factory.createVectorFromArray(vectorArray);
        matrix1 = vector.toColumnMatrix();
        System.out.println("no.30 | Vector -> Column Matrix 변환 확인 : " + matrix1.equals(Factory.createMatrixFromArray(new String[][]{{"1.0"}, {"2.0"}, {"3.0"}, {"5.0"}})));

        //no.31 : 벡터 1xn 행렬 변환
        matrix2 = vector.toRowMatrix();
        System.out.println("no.31 | Vector -> Row Matrix 변환 확인 : " + matrix2.equals(Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "3.0", "5.0"}})));

        //no.32 : 행렬 가로 연결
        matrix1 = Factory.createMatrix(2, 2, "1.0");
        matrix2 = Factory.createMatrix(1, 2, "2.0");
        System.out.println("no.33 | Matrix 가로 연결 결과 확인 : " + Tensors.concatRows(matrix1, matrix2).equals(
                Factory.createMatrixFromArray(new String[][]{{"1.0", "1.0"}, {"1.0", "1.0"}, {"2.0", "2.0"}})));

        //no.33 : 행렬 세로 연결
        matrix1 = Factory.createMatrix(3, 3, "4.0");
        matrix2 = Factory.createMatrix(3, 2, "3.0");
        System.out.println("no.32 | Matrix 세로 연결 결과 확인 : " + Tensors.concatColumns(matrix1, matrix2).equals(
                Factory.createMatrixFromArray(new String[][]{{"4.0", "4.0", "4.0", "3.0", "3.0"}, {"4.0", "4.0", "4.0", "3.0", "3.0"}, {"4.0", "4.0", "4.0", "3.0", "3.0"}})));

        //no.34 : 행렬 행 추출
        array2d = new String[][]{{"1.0", "2.0"}, {"5.0", "4.0"}};
        matrix = Factory.createMatrixFromArray(array2d);
        vector = matrix.extractRow(1);
        System.out.println("no.34 | 특정 행 추출 결과 확인 : " + vector.equals(Factory.createVectorFromArray(new String[]{"5.0", "4.0"})));

        //no.35 : 행렬 열 추출
        vector = matrix.extractColumn(0);
        System.out.println("no.35 | 특정 열 추출 결과 확인 : " + vector.equals(Factory.createVectorFromArray(new String[]{"1.0", "5.0"})));

        //no.36 : 부분 행렬 추출
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "5.0"}, {"3.0", "4.0"}});
        Matrix sub = matrix.subMatrix(0, 0, 1, 1);
        System.out.println("no.36 | 부분 행렬 추출 결과 확인 : " + sub.equals(Factory.createMatrixFromArray(new String[][]{{"5.0"}})));

        //no.37 : 마이너 행렬 추출
        Matrix minor = matrix.minor(0, 0);
        System.out.println("no.37 | Minor 행렬 추출 결과 확인 : " + minor.equals(Factory.createMatrixFromArray(new String[][]{{"4.0"}})));

        //no.38 : 전치 행렬
        Matrix transpose = matrix.transpose();
        System.out.println("no.38 | 전치 행렬 결과 확인 : " + transpose.equals(Factory.createMatrixFromArray(new String[][]{{"1.0", "3.0"}, {"5.0", "4.0"}})));

        //no.39 : 행렬 대각 요소 합
        matrix = Factory.createMatrixFromArray(new String[][]{{"5.0", "2.0"}, {"3.0", "4.0"}});
        System.out.println("no.39 | 대각 합(trace) 확인 (1+2=3) : " + matrix.trace().equals(Factory.createScalar("9.0")));

        //no.40 : 정사각 행렬 여부
        matrix = Factory.createMatrix(5, 5, "2.0");
        System.out.println("no.40 | 정사각 행렬 여부 확인 : " + matrix.isSquare());

        //no.41 : 상삼각 행렬 여부
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "3.0"}, {"0.0", "1.0", "3.0"}, {"0.0", "0.0", "1.0"}});
        System.out.println("no.41 | 상삼각 행렬 여부 확인 : " + matrix.isUpperTriangular());

        //no.42 : 하삼각 행렬 여부
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "0.0", "0.0"}, {"2.0", "1.0", "0.0"}, {"3.0", "6.0", "1.0"}});
        System.out.println("no.42 | 하삼각 행렬 여부 확인 : " + matrix.isLowerTriangular());

        //no.43 : 단위 행렬 여부
        matrix = Factory.createIdentityMatrix(6);
        System.out.println("no.43 | 단위 행렬 여부 확인 : " + matrix.isIdentityMatrix());

        //no.44 : 영 행렬 여부
        matrix = Factory.createMatrix(6, 4, "0.0");
        System.out.println("no.44 | 영 행렬 여부 확인 : " + matrix.isZeroMatrix());

        //no.45 : 행 교환
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "1.0"}, {"3.0", "4.0", "3.0"}});
        matrix.swapRows(0, 1);
        System.out.println("no.45 | 행 교환 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"3.0", "4.0", "3.0"}, {"1.0", "2.0", "1.0"}})));

        //no.46 : 열 교환
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "1.0"}, {"3.0", "4.0", "3.0"}});
        matrix.swapColumns(0, 1);
        System.out.println("no.46 | 열 교환 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"2.0", "1.0", "1.0"}, {"4.0", "3.0", "3.0"}})));

        //no.47 : 행 스칼라 곱
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "1.0"}, {"3.0", "4.0", "3.0"}});
        scalar = Factory.createScalar("2.0");
        matrix.scaleRow(1, scalar);
        System.out.println("no.47 | 행 스칼라 배 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "1.0"}, {"6.0", "8.0", "6.0"}})));

        //no.48 : 열 스칼라 곱
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "1.0"}, {"3.0", "4.0", "3.0"}});
        matrix.scaleColumn(1, scalar);
        System.out.println("no.48 | 열 스칼라 배 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"1.0", "4.0", "1.0"}, {"3.0", "8.0", "3.0"}})));

        //no.49 : 행 상수배 덧셈
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "1.0"}, {"3.0", "4.0", "3.0"}});
        scalar = Factory.createScalar("2.0");
        matrix.addScaledRow(0, 1, scalar);
        System.out.println("no.49 | 행 상수배 덧셈 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"7.0", "10.0", "7.0"}, {"3.0", "4.0", "3.0"}})));

        //no.50 : 열 상수배 덧셈
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "1.0"}, {"3.0", "4.0", "3.0"}});
        scalar = Factory.createScalar("2.0");
        matrix.addScaledColumn(2, 1, scalar);
        System.out.println("no.50 | 열 상수배 덧셈 결과 확인 : " + matrix.equals(Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "5.0"}, {"3.0", "4.0", "11.0"}})));

        //no.51 : RREF 변환
        matrix = Factory.createMatrixFromArray(new String[][]{{"0.0", "1.0", "2.0"}, {"1.0", "2.0", "4.0"}});
        Matrix rref = matrix.toRref();
        System.out.println("no.51 | RREF 변환 결과 확인 : \n" + rref);

        //no.52 : RREF 여부
        System.out.println("no.52 | RREF 여부 확인 : " + rref.isRref());
        matrix = Factory.createMatrixFromArray(new String[][]{{"0.0", "1.0", "2.0"}, {"1.0", "2.0", "4.0"}});

        //no.53 : 행렬식
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "2.0", "3.0"}, {"3.0", "4.0", "5.0"}, {"6.0", "7.0", "9.0"}});
        System.out.println("no.53 | 행렬식 확인 : " + matrix.determinant().equals(Factory.createScalar("-2.0")));

        //no.54 : 역행렬 변환
        matrix = Factory.createMatrixFromArray(new String[][]{{"1.0", "0.0", "0.0"}, {"0.0", "1.0", "5.0"}, {"0.0", "0.0", "1.0"}});
        Matrix expectedInv = Factory.createMatrixFromArray(new String[][]{{"1.0", "0.0", "0.0"}, {"0.0", "1.0", "-5.0"}, {"0.0", "0.0", "1.0"}});

        Matrix inv = matrix.inverse();
        System.out.println(inv);
        System.out.println("no.54 | 역행렬 계산 결과 확인 : " + inv.equals(expectedInv));

        try {
            matrix = Factory.createMatrixFromArray(new String[][]{{"3.0", "4.0", "7.0", "8.0"}, {"3.0", "2.0", "6.0", "7.0"}});
            Matrix unexpectedInv = matrix.inverse();
        } catch (MatrixNonSquareException | TensorArithmeticException e) {
            System.out.println("예외 발생: 정사각행렬이 아님 : " + e.getMessage());
        }

        try {
            matrix = Factory.createMatrix(6, 6, "0");
            Matrix unexpectedInv = matrix.inverse();
        } catch (MatrixNonSquareException | TensorArithmeticException e) {
            System.out.println("예외 발생: 역행렬이 존재하지 않음 : " + e.getMessage());
        }
    }
}