package Santos.David.Exception;

public class BillSplitAmountMismatchException extends RuntimeException {
    public BillSplitAmountMismatchException() {
        super("Sum of divisions does not match total value");
    }
}
