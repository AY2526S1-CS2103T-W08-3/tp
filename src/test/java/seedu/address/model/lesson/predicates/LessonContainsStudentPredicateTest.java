package seedu.address.model.lesson.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalLessons.ENGLISH_LESSON;
import static seedu.address.testutil.TypicalLessons.MATH_LESSON;
import static seedu.address.testutil.TypicalLessons.getLessonWithStudents;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

public class LessonContainsStudentPredicateTest {

    @Test
    public void equals() {
        Person alice = ALICE;
        Person benson = BENSON;

        LessonContainsStudentPredicate firstPredicate = new LessonContainsStudentPredicate(alice);
        LessonContainsStudentPredicate secondPredicate = new LessonContainsStudentPredicate(benson);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LessonContainsStudentPredicate firstPredicateCopy = new LessonContainsStudentPredicate(alice);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different student -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_lessonContainsStudent_returnsTrue() {
        // Lesson that includes ALICE should match predicate
        Lesson lessonWithAlice = getLessonWithStudents(MATH_LESSON, ALICE);

        LessonContainsStudentPredicate predicate = new LessonContainsStudentPredicate(ALICE);
        assertTrue(predicate.test(lessonWithAlice));
    }

    @Test
    public void test_lessonDoesNotContainStudent_returnsFalse() {
        // Lesson without ALICE should fail predicate
        LessonContainsStudentPredicate predicate = new LessonContainsStudentPredicate(ALICE);
        assertFalse(predicate.test(ENGLISH_LESSON));
    }

    @Test
    public void test_multipleStudents_worksCorrectly() {
        Lesson lessonWithStudents = getLessonWithStudents(MATH_LESSON, ALICE, BENSON);
        LessonContainsStudentPredicate alicePredicate = new LessonContainsStudentPredicate(ALICE);
        LessonContainsStudentPredicate bobPredicate = new LessonContainsStudentPredicate(BENSON);

        assertTrue(alicePredicate.test(lessonWithStudents));
        assertTrue(bobPredicate.test(lessonWithStudents));
    }

    @Test
    public void toStringMethod() {
        Person alice = ALICE;
        LessonContainsStudentPredicate predicate = new LessonContainsStudentPredicate(alice);

        String expected = LessonContainsStudentPredicate.class.getCanonicalName()
                + "{student=" + alice.toString() + "}";
        assertEquals(expected, predicate.toString());
    }
}

