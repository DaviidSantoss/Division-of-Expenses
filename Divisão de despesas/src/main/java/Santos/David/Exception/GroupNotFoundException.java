package Santos.David.Exception;

public class GroupNotFoundException extends RuntimeException {

    public GroupNotFoundException() {
        super("Group not found.");
    }
}
