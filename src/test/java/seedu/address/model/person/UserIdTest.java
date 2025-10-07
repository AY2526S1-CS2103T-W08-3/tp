package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UserIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UserId(null));
    }

    @Test
    public void constructor_validUserId_success() {
        UserId userId = new UserId(2103);
        assertEquals(2103, userId.value);
    }

    @Test
    public void getValue_returnsCorrectValue() {
        UserId userId = new UserId(2103);
        assertEquals(2103, userId.getValue());
    }

    @Test
    public void toString_returnsCorrectString() {
        UserId userId = new UserId(2103);
        assertEquals("2103", userId.toString());
    }

    @Test
    public void equals() {
        UserId userId = new UserId(2103);

        // same values -> returns true
        assertTrue(userId.equals(new UserId(2103)));

        // same object -> returns true
        assertTrue(userId.equals(userId));

        // null -> returns false
        assertFalse(userId.equals(null));

        // different types -> returns false
        assertFalse(userId.equals(5.0f));

        // different values -> returns false
        assertFalse(userId.equals(new UserId(0)));
    }

    @Test
    public void hashCode_sameValue_returnsSameHashCode() {
        UserId userId1 = new UserId(2103);
        UserId userId2 = new UserId(2103);
        assertEquals(userId1.hashCode(), userId2.hashCode());
    }
}
