package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ContainsNamePredicateTest {

    @Test
    public void equals() {
        String first = "first";
        String second = "second";

        ContainsNamePredicate firstPredicate = new ContainsNamePredicate(first);
        ContainsNamePredicate secondPredicate = new ContainsNamePredicate(second);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsNamePredicate firstPredicateCopy = new ContainsNamePredicate(first);
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
        ContainsNamePredicate predicate = new ContainsNamePredicate("Alice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // More than one keyword
        predicate = new ContainsNamePredicate("alice tan");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Tan Xiao Ming").build()));

        // Subset string matching
        predicate = new ContainsNamePredicate("lic");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keyword
        predicate = new ContainsNamePredicate("Ice");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        ContainsNamePredicate predicate = new ContainsNamePredicate("Carol");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and note, but does not match name
        Person person = new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withNote("Good student").build();
        List<String> keywords = Arrays.asList("12345", "alice@email.com", "Good", "student");
        keywords.forEach((keyword) -> {
            ContainsNamePredicate testPredicate = new ContainsNamePredicate(keyword);
            assertFalse(testPredicate.test(person));
        });
    }

    @Test
    public void toStringMethod() {
        String keyword = "keyword";
        ContainsNamePredicate predicate = new ContainsNamePredicate(keyword);

        String expected = ContainsNamePredicate.class.getCanonicalName() + "{name=" + keyword + "}";
        assertEquals(expected, predicate.toString());
    }
}
