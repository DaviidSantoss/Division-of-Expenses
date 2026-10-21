package Santos.David.Exception;

public class GroupAlreadyExistsException extends RuntimeException {

    public GroupAlreadyExistsException(){
        super("This group Already Exists.");
    }
}
