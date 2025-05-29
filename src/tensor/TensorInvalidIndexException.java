package tensor;

public class TensorInvalidIndexException extends RuntimeException {
    TensorInvalidIndexException(String message) {
        super(message);
    }
}
