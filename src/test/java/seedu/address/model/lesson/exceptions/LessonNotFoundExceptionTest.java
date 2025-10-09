package seedu.address.model.lesson.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LessonNotFoundExceptionTest {
    @Test
    public void testExceptionMessage() {
        LessonNotFoundException exception = new LessonNotFoundException();
        assertEquals("Lesson not found in the list", exception.getMessage());
    }
}