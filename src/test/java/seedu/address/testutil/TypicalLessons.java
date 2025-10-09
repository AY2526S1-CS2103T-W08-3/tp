package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.note.Note;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson MATH_LESSON = new Lesson(
            new LessonId(1001),
            Day.MON,
            new Time("1400"),
            new Time("1600"),
            new Venue("Blk 123 Computing Dr 1"),
            new Note("Algebra basics")
    );

    public static final Lesson ENGLISH_LESSON = new Lesson(
            new LessonId(1002),
            Day.TUE,
            new Time("1000"),
            new Time("1200"),
            new Venue("Blk 456 English Ave 2"),
            new Note("Grammar review")
    );

    public static final Lesson SCIENCE_LESSON = new Lesson(
            new LessonId(1003),
            Day.WED,
            new Time("1500"),
            new Time("1700"),
            new Venue("Blk 789 Science Rd 3"),
            new Note("Physics experiment")
    );

    private TypicalLessons() {} // prevents instantiation

    /**
     * Returns a list of typical lessons.
     */
    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(MATH_LESSON, ENGLISH_LESSON, SCIENCE_LESSON));
    }
}
