package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches the String provided.
 * String does not have to be an exact match.
 * Test returns true as long as input String is a subsequence of {@code Name}
 */
public class NameContainsKeywordPredicate implements Predicate<Person> {
    private final String name;

    public NameContainsKeywordPredicate(String name) {
        this.name = name;
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsWordIgnoreCase(person.getName().fullName, name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordPredicate)) {
            return false;
        }

        return ((NameContainsKeywordPredicate) other).name.equals(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name).toString();
    }
}
