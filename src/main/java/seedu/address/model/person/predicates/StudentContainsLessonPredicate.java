package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person} contains the specified {@code Lesson}.
 * Test returns true if the {@code Person} is a student of the {@code Lesson}.
 */
public class StudentContainsLessonPredicate implements Predicate<Person> {

    private final Lesson lesson;

    public StudentContainsLessonPredicate(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean test(Person person) {
        return person.hasLesson(lesson);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StudentContainsLessonPredicate)) {
            return false;
        }

        return ((StudentContainsLessonPredicate) other).lesson.equals(lesson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("lesson", lesson).toString();
    }
}
