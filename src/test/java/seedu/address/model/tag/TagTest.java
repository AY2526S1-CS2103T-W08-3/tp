package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void constructor_invalidTagLength_throwsIllegalArgumentException() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 51; i++) {
            stringBuilder.append("X");
        }
        String invalidLongTag = stringBuilder.toString();

        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidLongTag));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName("   ")); // only spaces
        assertFalse(Tag.isValidTagName("friend@")); // contains non-alphanumeric character

        // valid tag names
        assertTrue(Tag.isValidTagName("friend")); // alphabets only
        assertTrue(Tag.isValidTagName("friend123")); // alphanumeric
        assertTrue(Tag.isValidTagName("best friend")); // with space
        assertTrue(Tag.isValidTagName("close friend group")); // with multiple spaces
        assertTrue(Tag.isValidTagName("12345")); // numbers only
    }

    @Test
    public void equals() {
        Tag tag = new Tag("friend");

        // same values -> returns true
        assertTrue(tag.equals(new Tag("friend")));

        // same object -> returns true
        assertTrue(tag.equals(tag));

        // null -> returns false
        assertFalse(tag.equals(null));

        // different types -> returns false
        assertFalse(tag.equals(5.0f));

        // different values -> returns false
        assertFalse(tag.equals(new Tag("colleague")));
    }

}
