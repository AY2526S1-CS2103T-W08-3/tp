package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.FilterStudentByLessonCommand.MESSAGE_FILTER_BY_LESSON_SUCCESS;
import static seedu.address.logic.commands.FilterStudentByLessonCommand.MESSAGE_LIST_LESSONS_WITH_DAY;
import static seedu.address.logic.commands.FilterStudentByLessonCommand.MESSAGE_NO_STUDENTS_FOUND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.ENGLISH_LESSON;
import static seedu.address.testutil.TypicalLessons.MATH_LESSON;
import static seedu.address.testutil.TypicalLessons.SCIENCE_LESSON;
import static seedu.address.testutil.TypicalLessons.getLessonWithStudents;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getStudentWithLessons;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.testutil.stubs.ModelStub;
import seedu.address.testutil.stubs.ModelStubWithFiltering;

public class FilterStudentByLessonCommandTest {

    private final ModelStub modelStub = new ModelStubWithFiltering();
    private final Person aliceWithMathAndEnglish = getStudentWithLessons(ALICE, MATH_LESSON, ENGLISH_LESSON);
    private final Person bensonWithMath = getStudentWithLessons(BENSON, MATH_LESSON);
    private final Person carlWithEnglish = getStudentWithLessons(CARL, ENGLISH_LESSON);
    private final Person studentWithNoLessons = DANIEL;
    private final Lesson mathWithAliceAndBenson = getLessonWithStudents(MATH_LESSON, ALICE, BENSON);
    private final Lesson englishWithAliceAndCarl = getLessonWithStudents(ENGLISH_LESSON, ALICE, CARL);
    private final Lesson lessonWithNoStudents = SCIENCE_LESSON;

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new FilterStudentByLessonCommand(null, Index.fromOneBased(1)));
    }

    @Test
    public void execute_filteredLessonListEmpty_displaysEmptyMessage() throws Exception {
        Index index = Index.fromOneBased(1);
        FilterStudentByLessonCommand command = new FilterStudentByLessonCommand(MATH_LESSON.getDay(), index);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(Messages.MESSAGE_NO_LESSONS_FOUND, MATH_LESSON.getDay()),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_noIndex_displaysLessonList() throws Exception {
        populateWithLinks(modelStub);
        Day day = MATH_LESSON.getDay();

        FilterStudentByLessonCommand command = new FilterStudentByLessonCommand(day, null);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MESSAGE_LIST_LESSONS_WITH_DAY, day, day),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        populateWithLinks(modelStub);

        Index invalidIndex = Index.fromOneBased(100);
        FilterStudentByLessonCommand command =
                new FilterStudentByLessonCommand(MATH_LESSON.getDay(), invalidIndex);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX, () ->
                command.execute(modelStub));
    }

    @Test
    public void execute_noStudentsFound_displaysMessage() throws Exception {
        populateWithLinks(modelStub);

        Index index = Index.fromOneBased(1);
        FilterStudentByLessonCommand command =
                new FilterStudentByLessonCommand(lessonWithNoStudents.getDay(), index);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MESSAGE_NO_STUDENTS_FOUND, Messages.format(lessonWithNoStudents)),
                result.getFeedbackToUser());
    }

    @Test
    public void execute_filterByLesson_filterSuccess() throws Exception {
        populateWithLinks(modelStub);

        Index index = Index.fromOneBased(1);
        FilterStudentByLessonCommand command = new FilterStudentByLessonCommand(MATH_LESSON.getDay(), index);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(MESSAGE_FILTER_BY_LESSON_SUCCESS, Messages.format(MATH_LESSON)),
                result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        FilterStudentByLessonCommand firstCommand =
                new FilterStudentByLessonCommand(MATH_LESSON.getDay(), Index.fromOneBased(1));
        FilterStudentByLessonCommand secondCommand =
                new FilterStudentByLessonCommand(ENGLISH_LESSON.getDay(), Index.fromOneBased(1));
        FilterStudentByLessonCommand thirdCommand =
                new FilterStudentByLessonCommand(MATH_LESSON.getDay(), Index.fromOneBased(2));

        // same object -> true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> true
        FilterStudentByLessonCommand firstCopy =
                new FilterStudentByLessonCommand(MATH_LESSON.getDay(), Index.fromOneBased(1));
        assertTrue(firstCommand.equals(firstCopy));

        // different types -> false
        assertFalse(firstCommand.equals(1));

        // null -> false
        assertFalse(firstCommand.equals(null));

        // different lesson -> false
        assertFalse(firstCommand.equals(secondCommand));

        // different index -> false
        assertFalse(firstCommand.equals(thirdCommand));
    }

    @Test
    public void toStringMethod() {
        Day day = MATH_LESSON.getDay();
        Index index = Index.fromOneBased(1);
        FilterStudentByLessonCommand command = new FilterStudentByLessonCommand(day, index);

        String expected = command.getClass().getCanonicalName()
                + "{day=" + day
                + ", targetIndex=" + index
                + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Populates the model with basic two-way relationships between students and lessons for testing
     */
    public void populateWithLinks(Model model) {
        // Assign some students to lessons and vice versa
        model.addPerson(aliceWithMathAndEnglish);
        model.addPerson(bensonWithMath);
        model.addPerson(carlWithEnglish);
        model.addPerson(studentWithNoLessons);
        model.addLesson(mathWithAliceAndBenson);
        model.addLesson(englishWithAliceAndCarl);
        model.addLesson(lessonWithNoStudents);
    }
}
