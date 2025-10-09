package seedu.address.model.lesson.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DuplicateLessonExceptionTest {
    @Test
    public void testExceptionMessage() {
        DuplicateLessonException exception = new DuplicateLessonException();
        assertEquals("Operation would result in duplicate lessons", exception.getMessage());
    }
}
