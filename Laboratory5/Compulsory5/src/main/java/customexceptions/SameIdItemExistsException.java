package customexceptions;

public class SameIdItemExistsException extends Exception {
    public SameIdItemExistsException(Exception exception) {
        super("Can't add the item.", exception);
    }
}
