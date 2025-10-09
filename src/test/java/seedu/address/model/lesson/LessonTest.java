package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.note.Note;

public class LessonTest {

    private static final LessonId VALID_LESSON_ID = new LessonId(1001);
    private static final Day VALID_DAY = Day.MON;
    private static final Time VALID_START_TIME = new Time("1400");
    private static final Time VALID_END_TIME = new Time("1600");
    private static final Venue VALID_VENUE = new Venue("Blk 123 Computing Dr 1");
    private static final Note VALID_NOTE = new Note("Algebra basics");

    @Test
    public void constructor_null_throwsNullPointerException() {
        // null lessonId
        assertThrows(NullPointerException.class, () -> new Lesson(null, VALID_DAY,
                VALID_START_TIME, VALID_END_TIME, VALID_VENUE, VALID_NOTE));

        // null day
        assertThrows(NullPointerException.class, () -> new Lesson(VALID_LESSON_ID, null,
                VALID_START_TIME, VALID_END_TIME, VALID_VENUE, VALID_NOTE));

        // null startTime
        assertThrows(NullPointerException.class, () -> new Lesson(VALID_LESSON_ID, VALID_DAY,
                null, VALID_END_TIME, VALID_VENUE, VALID_NOTE));

        // null endTime
        assertThrows(NullPointerException.class, () -> new Lesson(VALID_LESSON_ID, VALID_DAY,
                VALID_START_TIME, null, VALID_VENUE, VALID_NOTE));

        // null venue
        assertThrows(NullPointerException.class, () -> new Lesson(VALID_LESSON_ID, VALID_DAY,
                VALID_START_TIME, VALID_END_TIME, null, VALID_NOTE));

        // null note
        assertThrows(NullPointerException.class, () -> new Lesson(VALID_LESSON_ID, VALID_DAY,
                VALID_START_TIME, VALID_END_TIME, VALID_VENUE, null));
    }

    @Test
    public void constructor_validLesson_success() {
        Lesson lesson = new Lesson(VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);
        assertEquals(VALID_LESSON_ID, lesson.getLessonId());
        assertEquals(VALID_DAY, lesson.getDay());
        assertEquals(VALID_START_TIME, lesson.getStartTime());
        assertEquals(VALID_END_TIME, lesson.getEndTime());
        assertEquals(VALID_VENUE, lesson.getVenue());
        assertEquals(VALID_NOTE, lesson.getNote());
    }

    @Test
    public void isSameLesson() {
        Lesson lesson1 = new Lesson(VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);

        // same object -> returns true
        assertTrue(lesson1.isSameLesson(lesson1));

        // null -> returns false
        assertFalse(lesson1.isSameLesson(null));

        // same lessonId -> returns true
        Lesson lesson2 = new Lesson(VALID_LESSON_ID, Day.TUE, new Time("1000"),
                new Time("1200"), new Venue("Different Venue"), new Note("Different note"));
        assertTrue(lesson1.isSameLesson(lesson2));

        // different lessonId -> returns false
        Lesson lesson3 = new Lesson(new LessonId(1002), VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);
        assertFalse(lesson1.isSameLesson(lesson3));
    }

    @Test
    public void equals() {
        Lesson lesson = new Lesson(VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);

        // same values -> returns true
        Lesson lessonCopy = new Lesson(VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);
        assertTrue(lesson.equals(lessonCopy));

        // same object -> returns true
        assertTrue(lesson.equals(lesson));

        // null -> returns false
        assertFalse(lesson.equals(null));

        // different types -> returns false
        assertFalse(lesson.equals(5.0f));

        // different lessonId -> returns false
        Lesson differentLessonId = new Lesson(new LessonId(1002), VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);
        assertFalse(lesson.equals(differentLessonId));

        // different day -> returns false
        Lesson differentDay = new Lesson(VALID_LESSON_ID, Day.TUE, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);
        assertFalse(lesson.equals(differentDay));

        // different startTime -> returns false
        Lesson differentStartTime = new Lesson(VALID_LESSON_ID, VALID_DAY, new Time("1500"),
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);
        assertFalse(lesson.equals(differentStartTime));

        // different endTime -> returns false
        Lesson differentEndTime = new Lesson(VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                new Time("1700"), VALID_VENUE, VALID_NOTE);
        assertFalse(lesson.equals(differentEndTime));

        // different venue -> returns false
        Lesson differentVenue = new Lesson(VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, new Venue("Different Venue"), VALID_NOTE);
        assertFalse(lesson.equals(differentVenue));

        // different note -> returns false
        Lesson differentNote = new Lesson(VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, new Note("Different note"));
        assertFalse(lesson.equals(differentNote));
    }

    @Test
    public void hashCode_sameLesson_sameHashCode() {
        Lesson lesson1 = new Lesson(VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);
        Lesson lesson2 = new Lesson(VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);

        assertEquals(lesson1.hashCode(), lesson2.hashCode());
    }

    @Test
    public void toStringMethod() {
        Lesson lesson = new Lesson(VALID_LESSON_ID, VALID_DAY, VALID_START_TIME,
                VALID_END_TIME, VALID_VENUE, VALID_NOTE);

        String expected = Lesson.class.getCanonicalName()
                + "{lessonId=" + VALID_LESSON_ID
                + ", day=" + VALID_DAY
                + ", startTime=" + VALID_START_TIME
                + ", endTime=" + VALID_END_TIME
                + ", venue=" + VALID_VENUE
                + ", note=" + VALID_NOTE
                + "}";

        assertEquals(expected, lesson.toString());
    }
}