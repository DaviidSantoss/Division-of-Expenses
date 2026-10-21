package Santos.David.Exception;

public class ExpenseValueNotFoundException extends RuntimeException {
    public ExpenseValueNotFoundException() {
        super("Expense value not found");
    }
}
