package tensor;

public interface Matrix extends Cloneable, Comparable<Matrix> {
    Scalar getMatrixElement(int row, int col); // 11
    void setMatrixElement(int row, int col, Scalar value); // 11

    int getMatrixRowCount(); // 13
    int getMatrixColumnCount(); // 13

    void add(Matrix other);
    void multiply(Matrix other);

    Matrix clone();

    void concatColumns(Matrix other);
    void concatRows(Matrix other);

    Vector extractRow(int rowIndex);
    Vector extractColumn(int columnIndex);

    Matrix subMatrix(int startRow, int endRow, int startCol, int endCol);
    Matrix minor(int rowToExclude, int colToExclude);

    Matrix transpose();
    Scalar trace();

    boolean isSquare();
    boolean isUpperTriangular();
    boolean isLowerTriangular();
    boolean isIdentityMatrix();
    boolean isZeroMatrix();

    void swapRows(int row1, int row2);
    void swapColumns(int col1, int col2);

    void scaleRow(int row, Scalar scalar);
    void scaleColumn(int column, Scalar scalar);

    void addScaledRow(int targetRow, int sourceRow, Scalar scalar);
    void addScaledColumn(int targetCol, int sourceCol, Scalar scalar);

    Matrix toRref();
    boolean isRref();

    Scalar determinant();
    Matrix inverse();
}
