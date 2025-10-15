package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson MATH_LESSON = new LessonBuilder()
            .withLessonId(1001)
            .withDay("MON")
            .withStartTime("1400")
            .withEndTime("1600")
            .withVenue("Blk 123 Computing Dr 1")
            .withNote("Algebra basics")
            .build();

    public static final Lesson ENGLISH_LESSON = new LessonBuilder()
            .withLessonId(1002)
            .withDay("TUE")
            .withStartTime("1000")
            .withEndTime("1200")
            .withVenue("Blk 456 English Ave 2")
            .withNote("Grammar review")
            .build();

    public static final Lesson SCIENCE_LESSON = new LessonBuilder()
            .withLessonId(1003)
            .withDay("WED")
            .withStartTime("1500")
            .withEndTime("1700")
            .withVenue("Blk 789 Science Rd 3")
            .withNote("Physics experiment")
            .build();

    private TypicalLessons() {}

    /**
     * Returns a list of typical lessons.
     */
    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(MATH_LESSON, ENGLISH_LESSON, SCIENCE_LESSON));
    }
}
