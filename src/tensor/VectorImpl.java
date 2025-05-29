package tensor;

import java.util.ArrayList;

public class VectorImpl implements Vector {
    ArrayList<Scalar> vector;

    VectorImpl() {
        vector = new ArrayList<Scalar>();
    }
    VectorImpl(int dimension, String value) {
        this();
        if (dimension < 0) {
            throw new TensorInvalidDimensionException();
        }
        for (int i = 0; i < dimension; i++) {
            vector.add(new ScalarImpl(value));
        }
    }
    VectorImpl(int dimension, String min, String max) {
        this();
        if (dimension < 0) {
            throw new TensorInvalidDimensionException();
        }
        for (int i = 0; i < dimension; i++) {
            vector.add(new ScalarImpl(min, max));
        }
    }
    VectorImpl(String[] values) {
        this();
        for (String value : values) {
            vector.add(new ScalarImpl(value));
        }
    }

    @Override
    public Scalar getVectorElement(int index) {
        if (!checkIndex(index)) {
            throw new TensorInvalidIndexException("wrong index");
        }
        return vector.get(index);
    }

    @Override
    public void setVectorElement(int index, Scalar value) {
        if (!checkIndex(index)) {
            throw new TensorInvalidIndexException("wrong index");
        }
        vector.set(index, value);
    }

    @Override
    public String toString() {
        String s = getVectorElement(0).toString();
        for (int i=1; i<getVectorSize(); i++) {
            s += ", " + getVectorElement(i);
        }
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector v) {
            if (getVectorSize() != v.getVectorSize()) {
                return false;
            }
            for (int i = 0; i < getVectorSize(); i++) {
                if (!getVectorElement(i).equals(v.getVectorElement(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getVectorSize() {
        return vector.size();
    }

    @Override
    public VectorImpl clone() {
        String[] values = new String[getVectorSize()];
        for (int i=0; i<getVectorSize(); i++) {
            values[i] = getVectorElement(i).getValue();
        }
        return new VectorImpl(values);
    }

    @Override
    public void add(Vector other) {
        if (getVectorSize() != other.getVectorSize()) {
            throw new TensorSizeMismatchException();
        }
        for (int i=0; i<getVectorSize(); i++) {
            getVectorElement(i).add(other.getVectorElement(i));
        }
    }

    @Override
    public void multiply(Scalar other) {
        for (int i=0; i<getVectorSize(); i++) {
            getVectorElement(i).multiply(other);
        }
    }

    public static Vector add(Vector a, Vector b) {
        Vector c = a.clone();
        c.add(b);
        return c;
    }

    public static Vector multiply(Scalar scalar, Vector vector) {
        Vector c = vector.clone();
        c.multiply(scalar);
        return c;
    }

    @Override
    public Matrix toColumnMatrix() {
        String[][] values = new String[getVectorSize()][1];
        for (int i=0; i<getVectorSize(); i++) {
            values[i][0] = getVectorElement(i).getValue();
        }
        return new MatrixImpl(values);
    }

    @Override
    public Matrix toRowMatrix() {
        String[][] values = new String[1][getVectorSize()];
        for (int i=0; i<getVectorSize(); i++) {
            values[0][i] = getVectorElement(i).getValue();
        }
        return new MatrixImpl(values);
    }

    private boolean checkIndex(int index) {
        return index >= 0 && index < getVectorSize();
    }
}
