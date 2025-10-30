package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertLessonCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLessonAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;
import static seedu.address.testutil.TypicalIndexes.SECOND_INDEX;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditLessonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void ensureLessonsDisplayed() {
        model.setDisplayedListToLessons();
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Lesson first = model.getAddressBook().getLessonList().get(0);
        Lesson editedLesson = new LessonBuilder().withLessonId(first.getLessonId().value).build();
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(FIRST_INDEX, descriptor);
        model.setDisplayedListToLessons();

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.format(editedLesson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.setLesson(model.getFilteredLessonList().get(0), editedLesson);

        assertLessonCommandSuccess(editLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastLesson = Index.fromOneBased(model.getFilteredLessonList().size());
        Lesson lastLesson = model.getFilteredLessonList().get(indexLastLesson.getZeroBased());

        LessonBuilder lessonInList = new LessonBuilder(lastLesson);
        Lesson editedLesson = lessonInList.withDay(VALID_DAY_MATH).withStartTime(VALID_STARTTIME_SCIENCE).build();

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withDay(VALID_DAY_MATH).withStartTime(VALID_STARTTIME_SCIENCE).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(indexLastLesson, descriptor);

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.format(editedLesson));
        model.setDisplayedListToLessons();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(lastLesson, editedLesson);

        assertLessonCommandSuccess(editLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditLessonCommand editLessonCommand = new EditLessonCommand(FIRST_INDEX, new EditLessonDescriptor());
        Lesson editedLesson = model.getFilteredLessonList().get(FIRST_INDEX.getZeroBased());

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.format(editedLesson));
        model.setDisplayedListToLessons();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        CommandTestUtil.assertLessonCommandSuccess(editLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showLessonAtIndex(model, FIRST_INDEX);

        Lesson lessonInFilteredList = model.getFilteredLessonList().get(FIRST_INDEX.getZeroBased());
        Lesson editedLesson = new LessonBuilder(lessonInFilteredList).withDay(VALID_DAY_MATH).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(FIRST_INDEX,
                new EditLessonDescriptorBuilder().withDay(VALID_DAY_MATH).build());

        String expectedMessage = String.format(EditLessonCommand.MESSAGE_EDIT_LESSON_SUCCESS,
                Messages.format(editedLesson));
        model.setDisplayedListToLessons();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setLesson(model.getFilteredLessonList().get(0), editedLesson);

        assertLessonCommandSuccess(editLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidLessonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLessonList().size() + 1);
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withDay(VALID_DAY_MATH).build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(outOfBoundIndex, descriptor);
        model.setDisplayedListToLessons();

        assertCommandFailure(editLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidLessonIndexFilteredList_failure() {
        showLessonAtIndex(model, FIRST_INDEX);
        Index outOfBoundIndex = SECOND_INDEX;
        model.setDisplayedListToLessons();
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLessonList().size());

        EditLessonCommand editLessonCommand = new EditLessonCommand(outOfBoundIndex,
                new EditLessonDescriptorBuilder().withDay(VALID_DAY_MATH).build());

        assertCommandFailure(editLessonCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_lessonsListNotDisplayed_failure() {
        model.setDisplayedListToPersons();
        EditLessonCommand editLessonCommand = new EditLessonCommand(FIRST_INDEX, new EditLessonDescriptor());
        assertCommandFailure(editLessonCommand, model,
                String.format(Messages.MESSAGE_LIST_NOT_DISPLAYED, "Lesson"));
    }

    @Test
    public void execute_endTimeBeforeStartTime_failure() {
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withStartTime("1600").withEndTime("1400").build();
        EditLessonCommand editLessonCommand = new EditLessonCommand(FIRST_INDEX, descriptor);
        model.setDisplayedListToLessons();

        assertCommandFailure(editLessonCommand, model, Messages.MESSAGE_END_TIME_CANNOT_BEFORE_START_TIME);
    }

    @Test
    public void equals() {
        final EditLessonCommand standardCommand = new EditLessonCommand(FIRST_INDEX, DESC_MATH);

        // same values -> returns true
        EditLessonDescriptor copyDescriptor = new EditLessonDescriptor(DESC_MATH);
        EditLessonCommand commandWithSameValues = new EditLessonCommand(FIRST_INDEX, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new HelpCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditLessonCommand(SECOND_INDEX, DESC_SCIENCE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditLessonCommand(FIRST_INDEX, DESC_SCIENCE)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();
        EditLessonCommand editLessonCommand = new EditLessonCommand(index, editLessonDescriptor);
        String expected = EditLessonCommand.class.getCanonicalName() + "{index=" + index + ", editLessonDescriptor="
                + editLessonDescriptor + "}";
        assertEquals(expected, editLessonCommand.toString());
    }

}
