package Santos.David.Exception;

public class UserAlreadyInGroupException extends RuntimeException {
    public UserAlreadyInGroupException() {
        super("User already exists in group");
    }
}
