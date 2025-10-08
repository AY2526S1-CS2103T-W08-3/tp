package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null));
    }

    @Test
    public void constructor_validLessonName_success() {
        Lesson lesson = new Lesson("Math");
        assertEquals("[Math]", lesson.toString());
    }

    @Test
    public void equals() {
        Lesson lesson = new Lesson("Math");

        // same values -> returns true
        assertTrue(lesson.equals(new Lesson("Math")));

        // same object -> returns true
        assertTrue(lesson.equals(lesson));

        // null -> returns false
        assertFalse(lesson.equals(null));

        // different types -> returns false
        assertFalse(lesson.equals(5.0f));

        // different values -> returns false
        assertFalse(lesson.equals(new Lesson("English")));
    }

}
