package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.FilterLessonByStudentCommand.MESSAGE_FILTER_BY_STUDENT_SUCCESS;
import static seedu.address.logic.commands.FilterLessonByStudentCommand.MESSAGE_LIST_STUDENTS_WITH_NAME;
import static seedu.address.logic.commands.FilterLessonByStudentCommand.MESSAGE_NO_LESSONS_FOUND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.ENGLISH_LESSON;
import static seedu.address.testutil.TypicalLessons.MATH_LESSON;
import static seedu.address.testutil.TypicalLessons.getLessonWithStudents;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getStudentWithLessons;

import org.junit.jupiter.api.Test;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordPredicate;
import seedu.address.testutil.stubs.ModelStub;
import seedu.address.testutil.stubs.ModelStubWithFiltering;

public class FilterLessonByStudentCommandTest {

    private final ModelStub modelStub = new ModelStubWithFiltering();
    private final Person aliceWithMathAndEnglish = getStudentWithLessons(ALICE, MATH_LESSON, ENGLISH_LESSON);
    private final Person bensonWithMath = getStudentWithLessons(BENSON, MATH_LESSON);
    private final Person carlWithEnglish = getStudentWithLessons(CARL, ENGLISH_LESSON);
    private final Person studentWithNoLessons = DANIEL;
    private final Lesson mathWithAliceAndBenson = getLessonWithStudents(MATH_LESSON, ALICE, BENSON);
    private final Lesson englishWithAliceAndCarl = getLessonWithStudents(ENGLISH_LESSON, ALICE, CARL);

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new FilterLessonByStudentCommand(null, Index.fromZeroBased(0)));
    }

    @Test
    public void execute_filteredStudentListEmpty_displaysEmptyMessage() throws Exception {
        Name name = ALICE.getName();
        CommandResult commandResult = new FilterLessonByStudentCommand(name, null)
                .execute(modelStub);

        assertEquals(String.format(Messages.MESSAGE_NO_USERS_FOUND, name),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_noIndex_displaysStudentList() throws Exception {
        populateWithLinks(modelStub);
        Name name = ALICE.getName();

        CommandResult commandResult = new FilterLessonByStudentCommand(name, null)
                .execute(modelStub);

        assertEquals(String.format(MESSAGE_LIST_STUDENTS_WITH_NAME, name, name),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        populateWithLinks(modelStub);
        Name name = ALICE.getName();
        Index invalidIndex = Index.fromOneBased(100);

        FilterLessonByStudentCommand command = new FilterLessonByStudentCommand(name, invalidIndex);
        FilteredList<Person> expectedFilteredPersons = new FilteredList<>(
                modelStub.getFilteredPersonList());
        expectedFilteredPersons.setPredicate(new NameContainsKeywordPredicate(name.toString()));

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                command.execute(modelStub));
        assertEquals(modelStub.getFilteredPersonList(), expectedFilteredPersons);
    }

    @Test
    public void execute_noLessonsFound_displaysMessage() throws Exception {
        populateWithLinks(modelStub);
        Name name = DANIEL.getName();
        Index index = Index.fromOneBased(1);

        CommandResult commandResult = new FilterLessonByStudentCommand(name, index)
                .execute(modelStub);

        assertEquals(String.format(MESSAGE_NO_LESSONS_FOUND, Messages.format(DANIEL)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_filterByStudent_filterSuccess() throws Exception {
        populateWithLinks(modelStub);
        Name name = ALICE.getName();
        Index index = Index.fromOneBased(1);

        CommandResult commandResult = new FilterLessonByStudentCommand(name, index)
                .execute(modelStub);

        assertEquals(String.format(MESSAGE_FILTER_BY_STUDENT_SUCCESS, Messages.format(aliceWithMathAndEnglish)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Name aliceName = ALICE.getName();
        Name bensonName = BENSON.getName();

        FilterLessonByStudentCommand firstCommand =
                new FilterLessonByStudentCommand(aliceName, Index.fromZeroBased(0));
        FilterLessonByStudentCommand secondCommand =
                new FilterLessonByStudentCommand(bensonName, Index.fromZeroBased(0));
        FilterLessonByStudentCommand thirdCommand =
                new FilterLessonByStudentCommand(aliceName, Index.fromZeroBased(1));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        FilterLessonByStudentCommand firstCommandCopy =
                new FilterLessonByStudentCommand(aliceName, Index.fromZeroBased(0));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different name -> returns false
        assertFalse(firstCommand.equals(secondCommand));

        // different index -> returns false
        assertFalse(firstCommand.equals(thirdCommand));
    }

    @Test
    public void toStringMethod() {
        Name aliceName = ALICE.getName();
        Index index = Index.fromZeroBased(0);

        FilterLessonByStudentCommand command = new FilterLessonByStudentCommand(aliceName, index);
        String expected = command.getClass().getCanonicalName()
                + "{name=" + aliceName
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
    }
}
