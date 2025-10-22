package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Day EXAMPLE_DAY = Day.MON;
    public static final Day DUPLICATE_DAY = Day.MON; // Multiple lessons on MON
    public static final Day UNIQUE_DAY = Day.TUE; // Only one lesson on TUE
    public static final Index INDEX_DUPLICATE_DAY = Index.fromOneBased(1); // Index for first MON lesson

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

    public static final Lesson HISTORY_LESSON = new LessonBuilder()
            .withLessonId(1004)
            .withDay("MON")
            .withStartTime("0900")
            .withEndTime("1100")
            .withVenue("Blk 321 History Hall")
            .withNote("World War II")
            .build();

    private TypicalLessons() {}

    /**
     * Returns a list of typical lessons.
     */
    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(MATH_LESSON, ENGLISH_LESSON, SCIENCE_LESSON, HISTORY_LESSON));
    }

    /**
     * Returns a copy of the input student and assigns the input lessons ot the student
     */
    public static Lesson getLessonWithStudents(Lesson lesson, Person ... students) {
        return new LessonBuilder(lesson).withStudents(students).build();
    }
}
