package seedu.address.model.lesson.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonIdNotInitialisedExceptionTest {

    @Test
    public void exception_canBeInstantiated() {
        LessonIdNotInitialisedException ex = new LessonIdNotInitialisedException();
        assertEquals("Static Field in LessonId is not initialised.", ex.getMessage());
    }

    @Test
    public void exception_isThrownCorrectly() {
        assertThrows(LessonIdNotInitialisedException.class, () -> {
            throw new LessonIdNotInitialisedException();
        });
    }
}
