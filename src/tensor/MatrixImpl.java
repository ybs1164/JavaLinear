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
            while ((line = br.readLine()) != null) {
                var row = new ArrayList<Scalar>();

                String[] lineArr = line.split(",");

                for (String s : lineArr) {
                    row.add(new ScalarImpl(s.strip()));
                }
                matrix.add(row);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    MatrixImpl(String[][] values) {
        this();
        int rowSize = -1;
        for (String[] row : values) {
            var rowList = new ArrayList<Scalar>();
            if (rowSize > 0 && row.length != rowSize) {
                throw new TensorInvalidInputException();
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
            throw new TensorInvalidIndexException();
        }
        return matrix.get(row).get(col);
    }

    @Override
    public void setMatrixElement(int row, int col, Scalar value) {
        if (!checkIndices(row, col)) {
            throw new TensorInvalidIndexException();
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
            throw new MatrixMulMismatchException();
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
            throw new TensorInvalidIndexException();
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
            throw new TensorInvalidIndexException();
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
            throw new TensorInvalidIndexException();
        }
        if (startRow > endRow || startCol > endCol) {
            throw new TensorInvalidInputException();
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
            throw new TensorInvalidIndexException();
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
            throw new NonSquareMatrixException();
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
                if (getMatrixElement(i, j).getValue().equals("0")) {
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
                if (getMatrixElement(i, j).getValue().equals("0")) {
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
                    if (getMatrixElement(i, j).getValue().equals("0")) {
                        return false;
                    }
                } else {
                    if (!getMatrixElement(i, j).getValue().equals("0")) {
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
                if (!getMatrixElement(i, j).getValue().equals("0")) {
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
            getMatrixElement(row1, i).setValue(r1.getVectorElement(i).getValue());
            getMatrixElement(row2, i).setValue(r2.getVectorElement(i).getValue());
        }
    }

    @Override
    public void swapColumns(int col1, int col2) {
        Vector c1 = extractColumn(col1);
        Vector c2 = extractColumn(col2);
        for (int i=0; i<getMatrixRowCount(); i++) {
            getMatrixElement(i, col1).setValue(c1.getVectorElement(i).getValue());
            getMatrixElement(i, col2).setValue(c2.getVectorElement(i).getValue());
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
            getMatrixElement(targetRow, i).multiply(sourceScalar);
        }
    }

    @Override
    public void addScaledColumn(int targetCol, int sourceCol, Scalar scalar) {
        for (int i=0; i<getMatrixRowCount(); i++) {
            Scalar sourceScalar = getMatrixElement(i, sourceCol).clone();
            sourceScalar.multiply(scalar);
            getMatrixElement(i, targetCol).multiply(sourceScalar);
        }
    }

    @Override
    public Matrix toRref() {
        Matrix copy = clone();
        int rowCount = getMatrixRowCount();
        int colCount = getMatrixColumnCount();
        int pivotRow = 0;
        int pivotCol = 0;

        for (; pivotCol < colCount; pivotCol++) { // to Ref
            int targetRow = pivotRow;
            while (targetRow < rowCount) { // find non-zero value row
                if (!getMatrixElement(targetRow, pivotCol).getValue().equals("0")) {
                    break;
                }
                targetRow++;
            }

            if (targetRow == rowCount) { // not exist non-zero values
                continue;
            }

            copy.swapRows(targetRow, pivotRow);

            Scalar lead = copy.getMatrixElement(pivotRow, pivotCol).clone();

            // TODO : lead divide in targetRow
            scaleRow(pivotRow, lead);
        }
        // TODO : Ref -> Rref

        return copy;
    }

    @Override
    public boolean isRref() {
        return false;
    }

    @Override
    public Scalar determinant() {
        return null;
    }

    @Override
    public Matrix inverse() {
        return null;
    }

    @Override
    public int compareTo(Matrix o) {
        return 0;
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
