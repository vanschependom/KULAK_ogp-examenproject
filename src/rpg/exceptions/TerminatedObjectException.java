package rpg.exceptions;

public class TerminatedObjectException extends Exception {

    private final Object object;

    public TerminatedObjectException(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
