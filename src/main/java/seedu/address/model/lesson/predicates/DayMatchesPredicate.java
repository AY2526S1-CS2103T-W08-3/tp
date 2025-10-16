package seedu.address.model.lesson.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;

/**
 * Tests that a {@code Lesson}'s {@code Day} matches the Day provided
 */
public class DayMatchesPredicate implements Predicate<Lesson> {
    private final Day day;

    public DayMatchesPredicate(Day day) {
        this.day = day;
    }

    @Override
    public boolean test(Lesson lesson) {
        return lesson.getDay() == this.day;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DayMatchesPredicate)) {
            return false;
        }

        return ((DayMatchesPredicate) other).day.equals(day);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("day", day.toString()).toString();
    }
}
