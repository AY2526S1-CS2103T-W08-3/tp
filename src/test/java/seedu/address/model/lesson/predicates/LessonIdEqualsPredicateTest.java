package seedu.address.model.lesson.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.testutil.LessonBuilder;

public class LessonIdEqualsPredicateTest {
    @Test
    public void equals() {
        LessonId firstId = new LessonId(1);
        LessonId secondId = new LessonId(2);

        LessonIdEqualsPredicate firstPredicate = new LessonIdEqualsPredicate(firstId);
        LessonIdEqualsPredicate secondPredicate = new LessonIdEqualsPredicate(secondId);
        LessonIdEqualsPredicate firstPredicateFromInt = new LessonIdEqualsPredicate(1);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LessonIdEqualsPredicate firstPredicateCopy = new LessonIdEqualsPredicate(new LessonId(1));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // instantiated from int but same lesson id -> returns true
        assertTrue(firstPredicate.equals(firstPredicateFromInt));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different lessonId -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_lessonIdMatches_returnsTrue() {
        LessonId userId = new LessonId(12345);
        LessonIdEqualsPredicate predicate = new LessonIdEqualsPredicate(userId);

        Lesson lesson = new LessonBuilder().withLessonId(12345).build();
        assertTrue(predicate.test(lesson));
    }

    @Test
    public void test_lessonIdDoesNotMatch_returnsFalse() {
        LessonIdEqualsPredicate predicate = new LessonIdEqualsPredicate(new LessonId(123));

        Lesson person = new LessonBuilder().withLessonId(456).build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void toStringMethod() {
        LessonId lessonId = new LessonId(999);
        LessonIdEqualsPredicate predicate = new LessonIdEqualsPredicate(lessonId);

        String expected = LessonIdEqualsPredicate.class.getCanonicalName() + "{lessonId=" + lessonId + "}";
        assertEquals(expected, predicate.toString());
    }
}
