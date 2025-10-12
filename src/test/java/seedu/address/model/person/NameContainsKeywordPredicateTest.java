package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.predicates.NameContainsKeywordPredicate;
import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String first = "first";
        String second = "second";

        NameContainsKeywordPredicate firstPredicate = new NameContainsKeywordPredicate(first);
        NameContainsKeywordPredicate secondPredicate = new NameContainsKeywordPredicate(second);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordPredicate firstPredicateCopy = new NameContainsKeywordPredicate(first);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // More than one keyword
        predicate = new NameContainsKeywordPredicate("alice tan");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Tan Xiao Ming").build()));

        // Subset string matching
        predicate = new NameContainsKeywordPredicate("lic");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keyword
        predicate = new NameContainsKeywordPredicate("Ice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and note, but does not match name
        Person person = new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withNote("Good student").build();
        List<String> keywords = Arrays.asList("12345", "alice@email.com", "Good", "student");
        keywords.forEach((keyword) -> {
            NameContainsKeywordPredicate testPredicate = new NameContainsKeywordPredicate(keyword);
            assertFalse(testPredicate.test(person));
        });
    }

    @Test
    public void toStringMethod() {
        String keyword = "keyword";
        NameContainsKeywordPredicate predicate = new NameContainsKeywordPredicate(keyword);

        String expected = NameContainsKeywordPredicate.class.getCanonicalName() + "{name=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
