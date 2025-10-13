package seedu.address.model.person.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UserIdNotInitialisedExceptionTest {

    @Test
    public void exception_canBeInstantiated() {
        UserIdNotInitialisedException ex = new UserIdNotInitialisedException();
        assertEquals("Static Field in UserId is not initialised.", ex.getMessage());
    }

    @Test
    public void exception_isThrownCorrectly() {
        assertThrows(UserIdNotInitialisedException.class, () -> {
            throw new UserIdNotInitialisedException();
        });
    }
}
