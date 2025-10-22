package seedu.address.model.lesson.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Lesson} contains the specified {@code Person}.
 * Test returns true if the {@code Person} is a student of the {@code Lesson}.
 */
public class LessonContainsStudentPredicate implements Predicate<Lesson> {

    private final Person person;

    public LessonContainsStudentPredicate(Person person) {
        this.person = person;
    }

    @Override
    public boolean test(Lesson lesson) {
        return lesson.hasPerson(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LessonContainsStudentPredicate)) {
            return false;
        }

        return ((LessonContainsStudentPredicate) other).person.equals(person);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("student", person).toString();
    }
}
