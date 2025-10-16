package seedu.address.model.lesson.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLessons.ENGLISH_LESSON;
import static seedu.address.testutil.TypicalLessons.HISTORY_LESSON;
import static seedu.address.testutil.TypicalLessons.MATH_LESSON;
import static seedu.address.testutil.TypicalLessons.SCIENCE_LESSON;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Day;

public class DayMatchesPredicateTest {

    @Test
    public void equals() {
        DayMatchesPredicate firstPredicate = new DayMatchesPredicate(Day.MON);
        DayMatchesPredicate secondPredicate = new DayMatchesPredicate(Day.TUE);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DayMatchesPredicate firstPredicateCopy = new DayMatchesPredicate(Day.MON);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different day -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_lessonMatchesDay_returnsTrue() {
        // MON lesson matches MON predicate
        DayMatchesPredicate predicate = new DayMatchesPredicate(Day.MON);
        assertTrue(predicate.test(MATH_LESSON));

        // Another MON lesson matches MON predicate
        assertTrue(predicate.test(HISTORY_LESSON));
    }

    @Test
    public void test_lessonDoesNotMatchDay_returnsFalse() {
        // TUE lesson does not match MON predicate
        DayMatchesPredicate predicate = new DayMatchesPredicate(Day.MON);
        assertFalse(predicate.test(ENGLISH_LESSON));

        // WED lesson does not match MON predicate
        assertFalse(predicate.test(SCIENCE_LESSON));
    }

    @Test
    public void test_allDays_worksCorrectly() {
        // Test MON
        DayMatchesPredicate monPredicate = new DayMatchesPredicate(Day.MON);
        assertTrue(monPredicate.test(MATH_LESSON));
        assertTrue(monPredicate.test(HISTORY_LESSON));
        assertFalse(monPredicate.test(ENGLISH_LESSON));
        assertFalse(monPredicate.test(SCIENCE_LESSON));

        // Test TUE
        DayMatchesPredicate tuePredicate = new DayMatchesPredicate(Day.TUE);
        assertFalse(tuePredicate.test(MATH_LESSON));
        assertTrue(tuePredicate.test(ENGLISH_LESSON));
        assertFalse(tuePredicate.test(SCIENCE_LESSON));

        // Test WED
        DayMatchesPredicate wedPredicate = new DayMatchesPredicate(Day.WED);
        assertFalse(wedPredicate.test(MATH_LESSON));
        assertFalse(wedPredicate.test(ENGLISH_LESSON));
        assertTrue(wedPredicate.test(SCIENCE_LESSON));
    }

    @Test
    public void toStringMethod() {
        Day day = Day.MON;
        DayMatchesPredicate predicate = new DayMatchesPredicate(day);

        String expected = DayMatchesPredicate.class.getCanonicalName() + "{day=" + day.toString() + "}";
        assertEquals(expected, predicate.toString());
    }
}
