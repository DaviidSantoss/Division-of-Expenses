package Santos.David.Exception;

public class EmptyGroupException extends RuntimeException {
    public EmptyGroupException() {
        super("Group has no members");
    }
}
