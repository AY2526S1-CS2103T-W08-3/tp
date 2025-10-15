package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.exceptions.LessonIdNotInitialisedException;

public class LessonIdTest {

    @Test
    public void constructor_invalidLessonId_failure() {
        LessonId.resetForTest();
        assertThrows(LessonIdNotInitialisedException.class, () -> new LessonId());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonId(null));
    }

    @Test
    public void constructor_validLessonId_success() {
        LessonId lessonId = new LessonId(2103);
        assertEquals(2103, lessonId.value);
    }

    @Test
    public void getValue_returnsCorrectValue() {
        LessonId lessonId = new LessonId(2103);
        assertEquals(2103, lessonId.getValue());
    }

    @Test
    public void toString_returnsCorrectString() {
        LessonId lessonId = new LessonId(2103);
        assertEquals("2103", lessonId.toString());
    }

    @Test
    public void equals() {
        LessonId lessonId = new LessonId(2103);

        // same values -> returns true
        assertTrue(lessonId.equals(new LessonId(2103)));

        // same object -> returns true
        assertTrue(lessonId.equals(lessonId));

        // null -> returns false
        assertFalse(lessonId.equals(null));

        // different types -> returns false
        assertFalse(lessonId.equals(5.0f));

        // different values -> returns false
        assertFalse(lessonId.equals(new LessonId(0)));
    }

    @Test
    public void hashCode_sameValue_returnsSameHashCode() {
        LessonId lessonId1 = new LessonId(2103);
        LessonId lessonId2 = new LessonId(2103);
        assertEquals(lessonId1.hashCode(), lessonId2.hashCode());
    }

    @Test
    public void constructor_idsDifferent_oneSample() {
        LessonId.setMaxLessonId(0);
        LessonId lessonId1 = new LessonId();
        LessonId lessonId2 = new LessonId();
        assertEquals(0, lessonId1.getValue(), "Lesson Id of first lesson should be 0.");
        assertEquals(1, lessonId2.getValue(), "Lesson Id of second lesson should be 1.");
    }

    @Test
    public void constructor_idsDifferent_multipleSamples() {
        final int sampleSize = 100;
        Set<Integer> ids = new HashSet<>();

        LessonId.setMaxLessonId(0);

        for (int i = 0; i < sampleSize; i++) {
            LessonId lessonId = new LessonId();
            int value = lessonId.getValue();
            assertTrue(value >= 0, "ID out of range");
            ids.add(value);
        }

        // No duplicates should exist
        assertEquals(sampleSize, ids.size(), "IDs should be unique in this sample");
    }

    @Test
    public void toString_returnsNumericString() {
        LessonId.setMaxLessonId(0);
        LessonId lessonId = new LessonId();
        String str = lessonId.toString();
        assertTrue(str.matches("\\d+"), "toString() should return digits only");
        int parsed = Integer.parseInt(str);
        assertEquals(0, parsed, "String form should be in range");
    }

    @Test
    public void setMaxLessonId_invalidLessonId() {
        assertThrows(IllegalArgumentException.class, () -> LessonId.setMaxLessonId(-1));
    }

    @Test
    public void getMaxLessonId_returnsCorrectValue() {
        LessonId.setMaxLessonId(0);
        assertEquals(0, LessonId.getMaxLessonId());
    }
}
