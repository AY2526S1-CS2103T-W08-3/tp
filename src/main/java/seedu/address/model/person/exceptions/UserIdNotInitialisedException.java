package seedu.address.model.person.exceptions;

/**
 * Signals that the UserId class is not initialised.
 */
public class UserIdNotInitialisedException extends RuntimeException {
    public UserIdNotInitialisedException() {
        super("Static Field in UserId is not initialised.");
    }
}
