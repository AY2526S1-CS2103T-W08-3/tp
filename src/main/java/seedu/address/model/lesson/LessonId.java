package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

import seedu.address.model.lesson.exceptions.LessonIdNotInitialisedException;

/**
 * Represents a Lesson's unique lesson ID in the address book.
 * Guarantees: immutable; is always valid (non-null).
 */
public class LessonId {
    private static int maxLessonId = -1;
    public final Integer value;

    /**
     * Constructs a {@code LessonId} with the specified integer value
     *
     * @param lessonId A valid lesson ID integer
     */
    public LessonId(Integer lessonId) {
        requireNonNull(lessonId);
        value = lessonId;
    }

    /**
     * Constructs a {@code LessonId} with a randomly generated integer value.
     */
    public LessonId() {
        if (maxLessonId < 0) {
            throw new LessonIdNotInitialisedException();
        }
        this.value = maxLessonId;
        maxLessonId += 1;
    }

    /**
     * Sets the maximum lesson ID
     */
    public static void setMaxLessonId(Integer lessonId) {
        if (lessonId < 0) {
            throw new IllegalArgumentException("Max Lesson ID cannot be negative.");
        }
        maxLessonId = lessonId;
    }

    /**
     * Resets the integer value of maxLessonId to -1.
     */
    static void resetForTest() {
        maxLessonId = -1;
    }

    /**
     * Returns the integer value of the maxLessonId
     */
    public static Integer getMaxLessonId() {
        return maxLessonId;
    }

    /**
     * Returns the integer value of this lesson ID.
     *
     * @return The lesson ID value as an Integer.
     */
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LessonId)) {
            return false;
        }

        LessonId otherId = (LessonId) other;
        return value.equals(otherId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
