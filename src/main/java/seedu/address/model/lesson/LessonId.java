package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

import java.util.Random;

/**
 * Represents a Lesson's unique user ID in the address book.
 * Guarantees: immutable; is always valid (non-null).
 */
public class LessonId {
    public static final int LESSON_ID_LENGTH = 6;
    public static final int MAX_LESSON_ID = 999999;
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
        Random random = new Random();
        this.value = random.nextInt(MAX_LESSON_ID); // generates 0–999999
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
