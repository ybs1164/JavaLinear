package tensor;

import java.math.BigDecimal;
import java.math.RoundingMode;

class ScalarImpl implements Scalar {
    private BigDecimal value;

    ScalarImpl() {
        this("0");
    }
    ScalarImpl(String s) {
        try {
            value = new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new TensorInvalidInputException("wrong input");
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

            // TODO : range check

            value = b.subtract(a).multiply(c).add(a);
        } catch (NumberFormatException e) {
            throw new TensorInvalidInputException("wrong input");
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
            throw new TensorInvalidInputException("wrong input");
        }
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Scalar s) {
            return compareTo(s) == 0;
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

    @Override
    public void inverse() {
        value = (new BigDecimal("1")).divide(value, 2, RoundingMode.UNNECESSARY);
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
