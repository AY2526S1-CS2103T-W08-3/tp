package seedu.address.model.lesson.exceptions;

/**
 * Signals that the LessonId class is not initialised.
 */
public class LessonIdNotInitialisedException extends RuntimeException {
    public LessonIdNotInitialisedException() {
        super("Static Field in LessonId is not initialised.");
    }
}
