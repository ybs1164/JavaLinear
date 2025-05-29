package tensor;

public interface Matrix extends Cloneable {
    // 11m. 특정 위치의 요소를 지정/조회할 수 있다. (Scalar)
    Scalar getMatrixElement(int row, int col);
    void setMatrixElement(int row, int col, Scalar value);

    // 13m. 크기 정보를 조회할 수 있다.
    int getMatrixRowCount();
    int getMatrixColumnCount();

    // 14m. 행렬 객체를 콘솔에 출력할 수 있다.
    String toString();

    // 15m. 행렬 객체의 동등성 판단을 할 수 있다.
    boolean equals(Object other);

    // 17m. 행렬 객체 복제를 할 수 있다.
    Matrix clone();

    // 22. 행렬은 다른 행렬과 덧셈이 가능하다.
    void add(Matrix other);
    // 23. 행렬은 다른 행렬과 곱셈이 가능하다.
    void multiply(Matrix other);
    // 오른쪽 행렬로서 곱해지는 경우.
    void multiplyRight(Matrix other);

    // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다.
    void concatColumns(Matrix other);
    // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다.
    void concatRows(Matrix other);

    // 34. 행렬은 특정 행을 벡터 형태로 추출해 줄 수 있다.
    Vector extractRow(int rowIndex);
    // 35. 행렬은 특정 열을 벡터 형태로 추춣해 줄 수 있다.
    Vector extractColumn(int columnIndex);

    // 36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
    Matrix subMatrix(int startRow, int endRow, int startCol, int endCol);
    // 37. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
    Matrix minor(int rowToExclude, int colToExclude);

    // 38. 행렬은 전치행렬을 (새로 생성하여) 구해줄 수 있다.
    Matrix transpose();
    // 39. 행렬은 대각 요소의 합을 구해줄 수 있다.
    Scalar trace();

    // 40. 행렬은 자신이 정사각 행렬인지 여부를 판별해 줄 수 있다.
    boolean isSquare();
    // 41. 행렬은 자신이 상삼각 행렬인지 여부를 판별해 줄 수 있다.
    boolean isUpperTriangular();
    // 42. 행렬은 자신이  하삼각 행렬인지 여부를 판별해 줄 수 있다.
    boolean isLowerTriangular();
    // 43. 행렬은 자신이 단위 행렬인지 여부를 판별해 줄 수 있다.
    boolean isIdentityMatrix();
    // 44. 행렬은 자신이 영 행렬인지 여부를 판별해 줄 수 있다.
    boolean isZeroMatrix();

    // 45. 행렬은 특정 두 행의 위치를 맞교환할 수 있다.
    void swapRows(int row1, int row2);
    // 46. 행렬은 특정 두 열의 위치를 맞교환할 수 있다.
    void swapColumns(int col1, int col2);

    // 47. 행렬은 특정 행에 상수배(스칼라)를 할 수 있다.
    void scaleRow(int row, Scalar scalar);
    // 48. 행렬은 특정 열에 상수배(스칼라)를 할 수 있다.
    void scaleColumn(int column, Scalar scalar);

    // 49. 행렬은 특정 행에 다른 행의 상수배를 더할 수 있다.
    void addScaledRow(int targetRow, int sourceRow, Scalar scalar);
    // 50. 행렬은 특정 열에 다른 열의 상수배를 더할 수 있다.
    void addScaledColumn(int targetCol, int sourceCol, Scalar scalar);

    // 51. 행렬은 자신으로부터 RREF 행렬을 구해서 반환해 줄 수 있다.
    Matrix toRref();
    // 52. 행렬은 자신이 RREF 행렬인지 여부를 판별해 줄 수 있다.
    boolean isRref();

    // 53. 행렬은 자신의 행렬식을 구해줄 수 있다.
    Scalar determinant();
    // 54. 행렬은 자신의 역행렬을 구해줄 수 있다.
    Matrix inverse();
}
