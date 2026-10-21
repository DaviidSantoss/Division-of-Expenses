package Santos.David.Exception;

public class UserDisableExcpetion extends RuntimeException {
    public UserDisableExcpetion() {
        super("User is disable");
    }
}
