package seedu.address.model.lesson.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;

/**
 * Tests that a {@code Lesson}'s {@code LessonId} matches the {@code LessonId} or integer provided.
 */
public class LessonIdEqualsPredicate implements Predicate<Lesson> {
    private final LessonId lessonId;

    public LessonIdEqualsPredicate(int num) {
        this.lessonId = new LessonId(num);
    }

    public LessonIdEqualsPredicate(LessonId lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public boolean test(Lesson lesson) {
        return lesson.getLessonId().equals(lessonId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonIdEqualsPredicate)) {
            return false;
        }

        return ((LessonIdEqualsPredicate) other).lessonId.equals(lessonId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("lessonId", lessonId).toString();
    }
}
