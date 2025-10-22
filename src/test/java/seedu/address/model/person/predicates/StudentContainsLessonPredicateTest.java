package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLessons.ENGLISH_LESSON;
import static seedu.address.testutil.TypicalLessons.MATH_LESSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getStudentWithLessons;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

public class StudentContainsLessonPredicateTest {

    @Test
    public void equals() {
        Lesson mathLesson = MATH_LESSON;
        Lesson englishLesson = ENGLISH_LESSON;

        StudentContainsLessonPredicate firstPredicate = new StudentContainsLessonPredicate(mathLesson);
        StudentContainsLessonPredicate secondPredicate = new StudentContainsLessonPredicate(englishLesson);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StudentContainsLessonPredicate firstPredicateCopy = new StudentContainsLessonPredicate(mathLesson);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different lesson -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personContainsLesson_returnsTrue() {
        // Person who attends MATH_LESSON should match predicate
        StudentContainsLessonPredicate predicate = new StudentContainsLessonPredicate(MATH_LESSON);
        Person aliceWithMath = getStudentWithLessons(ALICE, MATH_LESSON);
        assertTrue(predicate.test(aliceWithMath));
    }

    @Test
    public void test_personDoesNotContainLesson_returnsFalse() {
        // Person who does not attend MATH_LESSON should fail predicate
        StudentContainsLessonPredicate predicate = new StudentContainsLessonPredicate(MATH_LESSON);
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void test_multipleLessons_worksCorrectly() {
        StudentContainsLessonPredicate mathPredicate = new StudentContainsLessonPredicate(MATH_LESSON);
        StudentContainsLessonPredicate englishPredicate = new StudentContainsLessonPredicate(ENGLISH_LESSON);
        Person aliceWithMathAndEnglish = getStudentWithLessons(ALICE, MATH_LESSON, ENGLISH_LESSON);
        assertTrue(mathPredicate.test(aliceWithMathAndEnglish));
        assertTrue(englishPredicate.test(aliceWithMathAndEnglish));
    }

    @Test
    public void toStringMethod() {
        Lesson lesson = MATH_LESSON;
        StudentContainsLessonPredicate predicate = new StudentContainsLessonPredicate(lesson);

        String expected = StudentContainsLessonPredicate.class.getCanonicalName()
                + "{lesson=" + lesson.toString() + "}";
        assertEquals(expected, predicate.toString());
    }
}
