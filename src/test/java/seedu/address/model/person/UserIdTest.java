package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;


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

    @Test
    public void constructor_IdsDifferent() throws IllegalValueException {
        UserId.setMaxUserId(0);
        UserId id1 = new UserId();
        UserId id2 = new UserId();
        assertEquals(0, id1.getValue(), "User Id of first user should be 0.");
        assertEquals(1, id2.getValue(), "User Id of second user should be 1.");
    }

    @Test
    public void constructor_IdsDifferent_smallSample() throws IllegalValueException {
        final int sampleSize = 100;
        Set<Integer> ids = new HashSet<>();

        UserId.setMaxUserId(0);

        for (int i = 0; i < sampleSize; i++) {
            UserId userId = new UserId();
            int value = userId.getValue();
            assertTrue(value >= 0, "ID out of range");
            ids.add(value);
        }

        // No duplicates should exist
        assertEquals(sampleSize, ids.size(), "IDs should be unique in this sample");
    }

    @Test
    public void toString_returnsNumericString() throws IllegalValueException {
        UserId userId = new UserId();
        String str = userId.toString();
        assertTrue(str.matches("\\d+"), "toString() should return digits only");
        int parsed = Integer.parseInt(str);
        assertTrue(parsed >= 0,
                "String form should be in range");
    }
}
