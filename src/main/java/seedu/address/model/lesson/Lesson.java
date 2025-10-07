package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Lesson in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Lesson {

    public final String lessonName;

    /**
     * Constructs a {@code Lesson}.
     *
     * @param lessonName A valid lesson name.
     */
    public Lesson(String lessonName) {
        requireNonNull(lessonName);
        this.lessonName = lessonName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return lessonName.equals(otherLesson.lessonName);
    }

    @Override
    public int hashCode() {
        return lessonName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + lessonName + ']';
    }

}
