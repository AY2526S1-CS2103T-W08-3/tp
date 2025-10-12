package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.UserId;
import seedu.address.testutil.PersonBuilder;

public class UserIdEqualsPredicateTest {

    @Test
    public void equals() {
        UserId firstId = new UserId(1);
        UserId secondId = new UserId(2);

        UserIdEqualsPredicate firstPredicate = new UserIdEqualsPredicate(firstId);
        UserIdEqualsPredicate secondPredicate = new UserIdEqualsPredicate(secondId);
        UserIdEqualsPredicate firstPredicateFromInt = new UserIdEqualsPredicate(1);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UserIdEqualsPredicate firstPredicateCopy = new UserIdEqualsPredicate(new UserId(1));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // instantiated from int but same user id -> returns true
        assertTrue(firstPredicate.equals(firstPredicateFromInt));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different userId -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_userIdMatches_returnsTrue() {
        UserId userId = new UserId(12345);
        UserIdEqualsPredicate predicate = new UserIdEqualsPredicate(userId);

        Person person = new PersonBuilder().withUserId(12345).build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_userIdDoesNotMatch_returnsFalse() {
        UserIdEqualsPredicate predicate = new UserIdEqualsPredicate(new UserId(123));

        Person person = new PersonBuilder().withUserId(456).build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        UserId userId = new UserId(999);
        UserIdEqualsPredicate predicate = new UserIdEqualsPredicate(userId);

        String expected = UserIdEqualsPredicate.class.getCanonicalName() + "{userId=" + userId + "}";
        assertEquals(expected, predicate.toString());
    }
}
