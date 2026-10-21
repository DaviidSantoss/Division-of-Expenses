package Santos.David.Exception;

public class RecordsNotFoundException extends RuntimeException {
    public RecordsNotFoundException() {
        super("No records found for this ID");
    }
}
