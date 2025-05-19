package tensor;

import java.math.BigDecimal;

class ScalarImpl implements Scalar {
    private BigDecimal value;

    ScalarImpl() {
        this("0");
    }
    ScalarImpl(String s) {
        try {
            value = new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new TensorInvalidInputException();
        }
    }
    ScalarImpl(BigDecimal b) {
        value = b;
    }
    ScalarImpl(String min, String max) {
        try {
            BigDecimal a = new BigDecimal(min);
            BigDecimal b = new BigDecimal(max);
            BigDecimal c = new BigDecimal(Math.random());

            value = b.subtract(a).multiply(c).add(a);
        } catch (NumberFormatException e) {
            throw new TensorInvalidInputException();
        }
    }

    @Override
    public String getValue() {
        return value.toString();
    }

    @Override
    public void setValue(String value) {
        try {
            this.value = new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new TensorInvalidInputException();
        }
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Scalar s) {
            return getValue().equals(s.getValue());
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Scalar other) {
        return value.compareTo(new BigDecimal(other.getValue()));
    }

    @Override
    public ScalarImpl clone() {
        return new ScalarImpl(getValue());
    }

    @Override
    public void add(Scalar other) {
        value = value.add(new BigDecimal(other.getValue()));
    }

    @Override
    public void multiply(Scalar other) {
        value = value.multiply(new BigDecimal(other.getValue()));
    }

    public static Scalar add(Scalar a, Scalar b) {
        Scalar c = a.clone();
        c.add(b);
        return c;
    }

    public static Scalar multiply(Scalar a, Scalar b) {
        Scalar c = a.clone();
        c.multiply(b);
        return c;
    }
}
