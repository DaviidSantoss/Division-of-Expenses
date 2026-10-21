package Santos.David.Exception;

public class GroupRequiredException extends RuntimeException {

    public GroupRequiredException() {
        super("Group cannot be null.");
    }
}
