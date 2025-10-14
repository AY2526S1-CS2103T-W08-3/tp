package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UserId;

/**
 * Tests that a {@code Person}'s {@code UserId} matches the {@code UserId} or integer provided.
 * Test returns true if and only if the UserId or integer provided is exactly that the {@code Person}'s
 */
public class UserIdEqualsPredicate implements Predicate<Person> {
    private final UserId userId;

    public UserIdEqualsPredicate(int num) {
        this.userId = new UserId(num);
    }

    public UserIdEqualsPredicate(UserId userId) {
        this.userId = userId;
    }

    @Override
    public boolean test(Person person) {
        return person.getUserId().equals(userId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserIdEqualsPredicate)) {
            return false;
        }

        return ((UserIdEqualsPredicate) other).userId.equals(userId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("userId", userId).toString();
    }
}
