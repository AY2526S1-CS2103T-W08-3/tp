package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

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

    @Test
    public void constructor_randomIdWithinRange_success() {
        UserId userId = new UserId();
        int value = userId.getValue();
        assertTrue(value >= 0 && value < 999999,
                "Generated ID should be within 16-bit range (0–999999)");
    }

    // TODO make the test deterministic consistent with changing UserId implementation
    @Test
    public void constructor_randomIdsUsuallyDifferent() {
        UserId id1 = new UserId();
        UserId id2 = new UserId();
        assertNotEquals(id1.getValue(), id2.getValue(),
                "Randomly generated IDs should usually differ");
    }

    // TODO make the test deterministic consistent with changing UserId implementation
    @Test
    public void constructor_randomIdsNoCollisions_smallSample() {
        final int sampleSize = 100;
        Set<Integer> ids = new HashSet<>();

        for (int i = 0; i < sampleSize; i++) {
            UserId userId = new UserId();
            int value = userId.getValue();

            // Ensure it's within 16-bit range
            assertTrue(value >= 0 && value < (1 << 16), "ID out of range");

            ids.add(value);
        }

        // No duplicates should exist
        assertTrue(ids.size() == sampleSize, "Random IDs should be unique in this sample");
    }

    @Test
    public void toString_returnsNumericString() {
        UserId userId = new UserId();
        String str = userId.toString();
        assertTrue(str.matches("\\d+"), "toString() should return digits only");
        int parsed = Integer.parseInt(str);
        assertTrue(parsed >= 0 && parsed < (1 << 16),
                "String form should represent a valid 16-bit number");
    }
}
