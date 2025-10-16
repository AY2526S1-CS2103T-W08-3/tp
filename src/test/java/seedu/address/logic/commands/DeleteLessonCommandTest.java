package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLessonAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;
import static seedu.address.testutil.TypicalIndexes.SECOND_INDEX;
import static seedu.address.testutil.TypicalLessons.DUPLICATE_DAY;
import static seedu.address.testutil.TypicalLessons.UNIQUE_DAY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.predicates.DayMatchesPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteLessonCommand}.
 */
public class DeleteLessonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_dayOnly_filtersListNoDeletion() {
        Day dayToFilter = DUPLICATE_DAY;
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(dayToFilter, null);

        String expectedMessage = String.format(
                DeleteLessonCommand.MESSAGE_LIST_LESSONS_WITH_NAME, dayToFilter, dayToFilter);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredLessonList(new DayMatchesPredicate(dayToFilter));

        assertCommandSuccess(deleteLessonCommand, model, expectedCommandResult, expectedModel);

        // Ensure no deletion occurred
        assertTrue(model.getAddressBook().getLessonList().containsAll(getTypicalAddressBook().getLessonList()));
    }

    @Test
    public void execute_dayAndIndex_validIndexDeletesCorrectLesson() {
        Day dayToFilter = DUPLICATE_DAY;

        // First, filter the model by day to get the correct lesson
        model.updateFilteredLessonList(new DayMatchesPredicate(dayToFilter));
        Lesson lessonToDelete = model.getFilteredLessonList().get(FIRST_INDEX.getZeroBased());

        // Reset the model for the actual test
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(dayToFilter, FIRST_INDEX);

        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                Messages.format(lessonToDelete));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Filter by day and delete
        expectedModel.updateFilteredLessonList(new DayMatchesPredicate(dayToFilter));
        expectedModel.deleteLesson(lessonToDelete);

        assertCommandSuccess(deleteLessonCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showLessonAtIndex(model, SECOND_INDEX);

        Lesson lessonToDelete = model.getFilteredLessonList().get(FIRST_INDEX.getZeroBased());
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(UNIQUE_DAY, FIRST_INDEX);

        String expectedMessage = String.format(DeleteLessonCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                Messages.format(lessonToDelete));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteLesson(lessonToDelete);
        showNoLesson(expectedModel);

        assertCommandSuccess(deleteLessonCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // Filter to show only TUE lessons (there's only 1 - ENGLISH_LESSON)
        model.updateFilteredLessonList(new DayMatchesPredicate(UNIQUE_DAY));

        Index outOfBoundIndex = Index.fromOneBased(2);
        // The filtered list should have only 1 lesson (for TUE)
        assertTrue(model.getFilteredLessonList().size() == 1);
        // But outOfBoundIndex is 2, which is out of bounds for the filtered list
        assertTrue(outOfBoundIndex.getZeroBased() >= model.getFilteredLessonList().size());

        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(UNIQUE_DAY, outOfBoundIndex);

        assertCommandFailure(deleteLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteLessonCommand deleteFirstCommand = new DeleteLessonCommand(DUPLICATE_DAY, FIRST_INDEX);
        DeleteLessonCommand deleteSecondCommand = new DeleteLessonCommand(DUPLICATE_DAY, SECOND_INDEX);
        DeleteLessonCommand deleteFirstDiffDayCommand = new DeleteLessonCommand(Day.WED, FIRST_INDEX);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteLessonCommand deleteFirstCommandCopy = new DeleteLessonCommand(DUPLICATE_DAY, FIRST_INDEX);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different lesson -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different day -> returns false
        assertFalse(deleteFirstCommand.equals(deleteFirstDiffDayCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(DUPLICATE_DAY, targetIndex);
        String expected = DeleteLessonCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteLessonCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no lessons.
     */
    private void showNoLesson(Model model) {
        model.updateFilteredLessonList(l -> false);

        assertTrue(model.getFilteredLessonList().isEmpty());
    }

}
