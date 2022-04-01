package customexceptions;

/** Exception for the Load Command in case the GameGraph loaded is not in the right format. */
public class InvalidGameException extends Exception{
    public InvalidGameException(String errMsg) {
        super(errMsg);
    }
}
