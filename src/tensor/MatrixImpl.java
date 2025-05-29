package tensor;

import java.io.*;
import java.util.ArrayList;

public class MatrixImpl implements Matrix {
    private ArrayList<ArrayList<Scalar>> matrix = new ArrayList<ArrayList<Scalar>>();

    MatrixImpl() {
        matrix = new ArrayList<ArrayList<Scalar>>();
    }
    MatrixImpl(int rows, int cols, String value) {
        this();
        if (rows < 0 || cols < 0) {
            throw new TensorInvalidDimensionException();
        }
        for (int i = 0; i < rows; i++) {
            var row = new ArrayList<Scalar>();
            for (int j = 0; j < cols; j++) {
                row.add(new ScalarImpl(value));
            }
            matrix.add(row);
        }
    }
    MatrixImpl(int rows, int cols, String min, String max) {
        this();
        if (rows < 0 || cols < 0) {
            throw new TensorInvalidDimensionException();
        }
        for (int i = 0; i < rows; i++) {
            var row = new ArrayList<Scalar>();
            for (int j = 0; j < cols; j++) {
                row.add(new ScalarImpl(min, max));
            }
            matrix.add(row);
        }
    }
    MatrixImpl(String filepath) {
        this();
        try {
            File file = new File(filepath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int rowSize = -1;

            while ((line = br.readLine()) != null) {
                var row = new ArrayList<Scalar>();

                String[] lineArr = line.split(",");

                for (String s : lineArr) {
                    row.add(new ScalarImpl(s.strip()));
                }
                if (rowSize < 0) {
                    rowSize = row.size();
                }
                if (row.size() != rowSize) {
                    throw new TensorInvalidInputException("invalid row size");
                }

                matrix.add(row);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (TensorInvalidInputException e) {
            throw e;
        }
    }
    MatrixImpl(String[][] values) {
        this();
        int rowSize = -1;
        for (String[] row : values) {
            var rowList = new ArrayList<Scalar>();
            if (rowSize > 0 && row.length != rowSize) {
                throw new TensorInvalidInputException("invalid row size");
            }
            for (String value : row) {
                rowList.add(new ScalarImpl(value));
            }
            rowSize = rowList.size();
            matrix.add(rowList);
        }
    }
    MatrixImpl(int size) {
        this();
        if (size < 0) {
            throw new TensorInvalidDimensionException();
        }
        for (int i = 0; i < size; i++) {
            var row = new ArrayList<Scalar>();
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    row.add(new ScalarImpl("1"));
                } else {
                    row.add(new ScalarImpl("0"));
                }
            }
            matrix.add(row);
        }
    }

    @Override
    public Scalar getMatrixElement(int row, int col) {
        if (!checkIndices(row, col)) {
            throw new TensorInvalidIndexException("wrong index");
        }
        return matrix.get(row).get(col);
    }

    @Override
    public void setMatrixElement(int row, int col, Scalar value) {
        if (!checkIndices(row, col)) {
            throw new TensorInvalidIndexException("wrong index");
        }
        matrix.get(row).set(col, value);
    }

    @Override
    public String toString() {
        String s = "";
        for (ArrayList<Scalar> row : matrix) {
            s += row.get(0).toString();
            for (int i=1; i<row.size(); i++) {
                s += ", " + row.get(i);
            }
            s += '\n';
        }
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Matrix m) {
            if (getMatrixRowCount() != m.getMatrixRowCount()) {
                return false;
            }
            if (getMatrixColumnCount() != m.getMatrixColumnCount()) {
                return false;
            }
            for (int i = 0; i < getMatrixRowCount(); i++) {
                for (int j = 0; j < getMatrixColumnCount(); j++) {
                    if (!getMatrixElement(i, j).equals(m.getMatrixElement(i, j))) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getMatrixRowCount() {
        return matrix.size();
    }

    @Override
    public int getMatrixColumnCount() {
        if (getMatrixRowCount() == 0) {
            return 0;
        }
        return matrix.getFirst().size();
    }

    @Override
    public MatrixImpl clone() {
        String[][] values = new String[getMatrixRowCount()][getMatrixColumnCount()];
        for (int i=0; i<getMatrixRowCount(); i++) {
            for (int j=0; j<getMatrixColumnCount(); j++) {
                values[i][j] = getMatrixElement(i, j).getValue();
            }
        }
        return new MatrixImpl(values);
    }

    @Override
    public void add(Matrix other) {
        if (getMatrixRowCount() != other.getMatrixRowCount()) {
            throw new TensorSizeMismatchException();
        }
        if (getMatrixColumnCount() != other.getMatrixColumnCount()) {
            throw new TensorSizeMismatchException();
        }
        for (int i=0; i<getMatrixRowCount(); i++) {
            for (int j=0; j<getMatrixColumnCount(); j++) {
                getMatrixElement(i, j).add(other.getMatrixElement(i, j));
            }
        }
    }

    @Override
    public void multiply(Matrix other) {
        if (getMatrixColumnCount() != other.getMatrixRowCount()) {
            throw new MatrixMulMismatchException("");
        }
        MatrixImpl m = new MatrixImpl(getMatrixRowCount(), other.getMatrixColumnCount(), "0");
        for (int i=0; i<getMatrixRowCount(); i++) {
            for (int j=0; j<other.getMatrixColumnCount(); j++) {
                for (int k=0; k<getMatrixColumnCount(); k++) {
                    Scalar s = getMatrixElement(i, k).clone();
                    s.multiply(other.getMatrixElement(k, j));
                    m.getMatrixElement(i, j).add(s);
                }
            }
        }
        matrix = m.matrix;
    }

    @Override
    public void multiplyRight(Matrix other) {
        other.multiply(this);
    }

    public static Matrix add(Matrix a, Matrix b) {
        Matrix c = a.clone();
        c.add(b);
        return c;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix c = a.clone();
        c.multiply(b);
        return c;
    }

    @Override
    public void concatColumns(Matrix other) {
        if (getMatrixRowCount() != other.getMatrixRowCount()) {
            throw new TensorSizeMismatchException();
        }
        for (int i=0; i<other.getMatrixRowCount(); i++) {
            for (int j=0; j<other.getMatrixColumnCount(); j++) {
                matrix.get(i).add(other.getMatrixElement(i, j));
            }
        }
    }

    @Override
    public void concatRows(Matrix other) {
        if (getMatrixColumnCount() != other.getMatrixColumnCount()) {
            throw new TensorSizeMismatchException();
        }
        for (int i=0; i<other.getMatrixRowCount(); i++) {
            ArrayList<Scalar> newColumn = new ArrayList<Scalar>();
            for (int j=0; j<other.getMatrixColumnCount(); j++) {
                newColumn.add(other.getMatrixElement(i, j));
            }
            matrix.add(newColumn);
        }
    }

    public static Matrix concatColumns(Matrix a, Matrix b) {
        Matrix c = a.clone();
        c.concatColumns(b);
        return c;
    }

    public static Matrix concatRows(Matrix a, Matrix b) {
        Matrix c = a.clone();
        c.concatRows(b);
        return c;
    }

    @Override
    public Vector extractRow(int rowIndex) {
        if (!checkRowIndex(rowIndex)) {
            throw new TensorInvalidIndexException("wrong row index");
        }
        String[] newRow = new String[getMatrixColumnCount()];
        for (int i=0; i<getMatrixColumnCount(); i++) {
            newRow[i] = getMatrixElement(rowIndex, i).getValue();
        }
        return new VectorImpl(newRow);
    }

    @Override
    public Vector extractColumn(int columnIndex) {
        if (!checkColumnIndex(columnIndex)) {
            throw new TensorInvalidIndexException("wrong column index");
        }
        String[] newColumn = new String[getMatrixRowCount()];
        for (int i=0; i<getMatrixRowCount(); i++) {
            newColumn[i] = getMatrixElement(i, columnIndex).getValue();
        }
        return new VectorImpl(newColumn);
    }

    @Override
    public Matrix subMatrix(int startRow, int endRow, int startCol, int endCol) {
        if (!checkIndices(startRow, startCol) || !checkIndices(endRow, endCol)) {
            throw new TensorInvalidIndexException("wrong row index or column index");
        }
        if (startRow > endRow || startCol > endCol) {
            throw new TensorInvalidInputException("wrong row range or column range");
        }
        String[][] newMatrix = new String[endRow - startRow + 1][endCol - startCol + 1];
        for (int i=startRow; i<=endRow; i++) {
            for (int j=startCol; j<=endCol; j++) {
                newMatrix[i-startRow][j-startCol] = getMatrixElement(i, j).getValue();
            }
        }
        return new MatrixImpl(newMatrix);
    }

    @Override
    public Matrix minor(int rowToExclude, int colToExclude) {
        if (!checkIndices(rowToExclude, colToExclude)) {
            throw new TensorInvalidIndexException("wrong row index or column index");
        }
        String[][] newMatrix = new String[getMatrixRowCount()-1][getMatrixColumnCount()-1];
        int k=0;
        for (int i=0; i<getMatrixRowCount(); i++) {
            if (i == rowToExclude) {
                continue;
            }
            int l=0;
            for (int j=0; j<getMatrixColumnCount(); j++) {
                if (j == colToExclude) {
                    continue;
                }
                newMatrix[k][l] = getMatrixElement(i, j).getValue();
                l++;
            }
            k++;
        }
        return new MatrixImpl(newMatrix);
    }

    @Override
    public Matrix transpose() {
        String[][] newMatrix = new String[getMatrixColumnCount()][getMatrixRowCount()];
        for (int i=0; i<getMatrixRowCount(); i++) {
            for (int j=0; j<getMatrixColumnCount(); j++) {
                newMatrix[j][i] = getMatrixElement(i, j).getValue();
            }
        }
        return new MatrixImpl(newMatrix);
    }

    @Override
    public Scalar trace() {
        if (!isSquare()) {
            throw new MatrixNonSquareException();
        }
        Scalar sum = new ScalarImpl("0");
        for (int i=0; i<getMatrixRowCount(); i++) {
            sum.add(getMatrixElement(i, i));
        }
        return sum;
    }

    @Override
    public boolean isSquare() {
        return getMatrixRowCount() == getMatrixColumnCount();
    }

    @Override
    public boolean isUpperTriangular() {
        if (!isSquare()) {
            return false;
        }
        for (int i=1; i<getMatrixRowCount(); i++) {
            for (int j=0; j<i; j++) {
                if (getMatrixElement(i, j).equals(Factory.createScalar("0"))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isLowerTriangular() {
        if (!isSquare()) {
            return false;
        }
        for (int i=0; i<getMatrixRowCount(); i++) {
            for (int j=i+1; j<getMatrixColumnCount(); j++) {
                if (getMatrixElement(i, j).equals(Factory.createScalar("0"))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isIdentityMatrix() {
        if (!isSquare()) {
            return false;
        }
        for (int i=0; i<getMatrixRowCount(); i++) {
            for (int j=0; j<getMatrixColumnCount(); j++) {
                if (i == j) {
                    if (getMatrixElement(i, j).equals(Factory.createScalar("0"))) {
                        return false;
                    }
                } else {
                    if (!getMatrixElement(i, j).equals(Factory.createScalar("0"))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isZeroMatrix() {
        for (int i=0; i<getMatrixRowCount(); i++) {
            for (int j=0; j<getMatrixColumnCount(); j++) {
                if (!getMatrixElement(i, j).equals(Factory.createScalar("0"))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void swapRows(int row1, int row2) {
        Vector r1 = extractRow(row1);
        Vector r2 = extractRow(row2);
        for (int i=0; i<getMatrixColumnCount(); i++) {
            getMatrixElement(row1, i).setValue(r2.getVectorElement(i).getValue());
            getMatrixElement(row2, i).setValue(r1.getVectorElement(i).getValue());
        }
    }

    @Override
    public void swapColumns(int col1, int col2) {
        Vector c1 = extractColumn(col1);
        Vector c2 = extractColumn(col2);
        for (int i=0; i<getMatrixRowCount(); i++) {
            getMatrixElement(i, col1).setValue(c2.getVectorElement(i).getValue());
            getMatrixElement(i, col2).setValue(c1.getVectorElement(i).getValue());
        }
    }

    @Override
    public void scaleRow(int row, Scalar scalar) {
        for (int i=0; i<getMatrixColumnCount(); i++) {
            getMatrixElement(row, i).multiply(scalar);
        }
    }

    @Override
    public void scaleColumn(int column, Scalar scalar) {
        for (int i=0; i<getMatrixRowCount(); i++) {
            getMatrixElement(i, column).multiply(scalar);
        }
    }

    @Override
    public void addScaledRow(int targetRow, int sourceRow, Scalar scalar) {
        for (int i=0; i<getMatrixColumnCount(); i++) {
            Scalar sourceScalar = getMatrixElement(sourceRow, i).clone();
            sourceScalar.multiply(scalar);
            getMatrixElement(targetRow, i).add(sourceScalar);
        }
    }

    @Override
    public void addScaledColumn(int targetCol, int sourceCol, Scalar scalar) {
        for (int i=0; i<getMatrixRowCount(); i++) {
            Scalar sourceScalar = getMatrixElement(i, sourceCol).clone();
            sourceScalar.multiply(scalar);
            getMatrixElement(i, targetCol).add(sourceScalar);
        }
    }

    @Override
    public Matrix toRref() {
        Matrix copy = clone(); // 원본 손상 방지
        int rowCount = copy.getMatrixRowCount();
        int colCount = copy.getMatrixColumnCount();
        int lead = 0;

        for (int r = 0; r < rowCount; r++) {
            if (lead >= colCount) {
                break;
            }

            int i = r;

            // 1. lead 열에 대해 0이 아닌 행 찾기
            while (i < rowCount && copy.getMatrixElement(i, lead).equals(Factory.createScalar("0"))) {
                i++;
            }

            if (i == rowCount) {
                lead++;
                r--;  // 행은 그대로, 다음 열로
                continue;
            }

            // 2. 해당 행을 현재 행으로 올리기
            copy.swapRows(i, r);

            // 3. 선행 1 만들기
            Scalar pivot = copy.getMatrixElement(r, lead).clone();
            pivot.inverse();
            copy.scaleRow(r, pivot);

            // 4. lead 열 제거
            for (int j = 0; j < rowCount; j++) {
                if (j == r) {
                    continue;
                }
                Scalar factor = copy.getMatrixElement(r, lead).clone();
                factor.multiply(Factory.createScalar("-1"));
                factor.multiply(copy.getMatrixElement(j, lead));
                if (!factor.equals(Factory.createScalar("0"))) {
                    copy.addScaledRow(j, r, factor);
                }
            }

            lead++;
        }

        return copy;
    }

    @Override
    public boolean isRref() {
        int rowCount = getMatrixRowCount();
        int colCount = getMatrixColumnCount();

        int leadCol = -1;  // 마지막 선행 1의 열 인덱스

        for (int i = 0; i < rowCount; i++) {
            // 1. 현재 행에서 0이 아닌 첫 번째 요소 찾기
            int currentLead = -1;
            for (int j = 0; j < colCount; j++) {
                Scalar val = getMatrixElement(i, j);
                if (!val.equals(Factory.createScalar("0"))) {
                    currentLead = j;
                    break;
                }
            }

            // 2. 전부 0인 행이면 아래 행도 다 0이어야 함
            if (currentLead == -1) {
                for (int k = i + 1; k < rowCount; k++) {
                    for (int j = 0; j < colCount; j++) {
                        Scalar val = getMatrixElement(k, j);
                        if (!val.equals(Factory.createScalar("0"))) {
                            return false; // 아래에 0 아닌 값 있으면 실패
                        }
                    }
                }
                break; // 이후 행은 확인할 필요 없음
            }

            // 3. 선행 1은 반드시 1이어야 함
            Scalar leadVal = getMatrixElement(i, currentLead);
            if (!leadVal.equals(Factory.createScalar("1"))) {
                return false;
            }

            // 4. 선행 1 열의 다른 값은 모두 0이어야 함
            for (int k = 0; k < rowCount; k++) {
                if (k == i) {
                    continue;
                }
                Scalar val = getMatrixElement(k, currentLead);
                if (!val.equals(Factory.createScalar("0"))) {
                    return false;
                }
            }

            // 5. 선행 1의 열 위치는 계속 오른쪽으로 진행해야 함
            if (currentLead <= leadCol) {
                return false;
            }

            leadCol = currentLead;
        }

        return true;
    }

    @Override
    public Scalar determinant() {
        if (!isSquare()) {
            throw new MatrixNonSquareException();
        }
        return determinant(this);
    }

    private Scalar determinant(Matrix currentMatrix) {
        if (currentMatrix.getMatrixRowCount() == 1) {
            return currentMatrix.getMatrixElement(0, 0).clone();
        }
        if (currentMatrix.getMatrixRowCount() == 2) {
            return Tensors.add(Tensors.multiply(currentMatrix.getMatrixElement(0, 0), currentMatrix.getMatrixElement(1, 1))
                 , Tensors.multiply(Tensors.multiply(currentMatrix.getMatrixElement(1, 0), currentMatrix.getMatrixElement(0, 1)), Factory.createScalar("-1")));
        }
        Scalar sum = Factory.createScalar("0");
        for (int i=0; i<currentMatrix.getMatrixRowCount(); i++) {
            Scalar value = Tensors.multiply(currentMatrix.getMatrixElement(0, i), determinant(currentMatrix.minor(0, i)));
            if (i % 2 == 1) {
                value.multiply(Factory.createScalar("-1"));
            }
            sum.add(value);
        }
        return sum;
    }

    @Override
    public Matrix inverse() {
        if (!isSquare()) {
            throw new MatrixNonSquareException();
        }

        int n = getMatrixRowCount();

        // 1. [A | I] 확장 행렬 만들기
        Matrix identity = Factory.createIdentityMatrix(n);
        Matrix augmented = Tensors.concatColumns(clone(), identity);

        // 2. RREF 변환
        Matrix rref = augmented.toRref();

        // 3. 왼쪽 절반이 단위행렬인지 확인
        if (!rref.subMatrix(0, n-1, 0, n-1).isIdentityMatrix()) {
            throw new TensorArithmeticException();
        }

        return rref.subMatrix(0, n-1, n, 2*n - 1);
    }

    private boolean checkRowIndex(int index) {
        return index >= 0 && index < getMatrixRowCount();
    }

    private boolean checkColumnIndex(int index) {
        return index >= 0 && index < getMatrixColumnCount();
    }

    private boolean checkIndices(int row, int col) {
        return checkRowIndex(row) && checkColumnIndex(col);
    }
}
