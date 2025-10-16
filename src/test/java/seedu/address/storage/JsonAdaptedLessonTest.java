package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.MATH_LESSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Time;

public class JsonAdaptedLessonTest {
    private static final String INVALID_DAY = "Invalid";
    private static final String INVALID_START_TIME = "2500";
    private static final String INVALID_END_TIME = "9999";

    private static final Integer VALID_LESSON_ID = MATH_LESSON.getLessonId().value;
    private static final String VALID_DAY = MATH_LESSON.getDay().toString();
    private static final String VALID_START_TIME = MATH_LESSON.getStartTime().toString();
    private static final String VALID_END_TIME = MATH_LESSON.getEndTime().toString();
    private static final String VALID_VENUE = MATH_LESSON.getVenue().toString();
    private static final String VALID_NOTE = MATH_LESSON.getNote().toString();
    private static final List<Integer> VALID_STUDENT_IDS = new ArrayList<>();

    @Test
    public void toModelType_validLessonDetails_returnsLesson() throws Exception {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(MATH_LESSON);
        assertEquals(MATH_LESSON, lesson.toModelType());
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_LESSON_ID, INVALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE,
                VALID_STUDENT_IDS);
        String expectedMessage = Day.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullDay_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_LESSON_ID, null, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE,
                VALID_STUDENT_IDS);
        String expectedMessage = Day.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_LESSON_ID, VALID_DAY, INVALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE,
                VALID_STUDENT_IDS);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_LESSON_ID, VALID_DAY, null,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE,
                VALID_STUDENT_IDS);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                INVALID_END_TIME, VALID_VENUE, VALID_NOTE,
                VALID_STUDENT_IDS);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                null, VALID_VENUE, VALID_NOTE,
                VALID_STUDENT_IDS);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, null, VALID_NOTE,
                VALID_STUDENT_IDS);
        String expectedMessage = "Venue cannot be null";
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, null,
                VALID_STUDENT_IDS);
        String expectedMessage = "Note cannot be null";
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullLessonId_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                null, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE,
                VALID_STUDENT_IDS);
        String expectedMessage = "Lesson ID cannot be null";
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }
}
