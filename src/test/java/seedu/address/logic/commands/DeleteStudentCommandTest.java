package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;
import static seedu.address.testutil.TypicalIndexes.SECOND_INDEX;
import static seedu.address.testutil.TypicalPersons.DUPLICATE_NAME;
import static seedu.address.testutil.TypicalPersons.INDEX_DUPLICATE_NAME;
import static seedu.address.testutil.TypicalPersons.UNIQUE_NAME;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_nameOnly_filtersListNoDeletion() {
        Name nameToFilter = DUPLICATE_NAME;
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(nameToFilter, null);

        String expectedMessage = String.format(
                DeleteStudentCommand.MESSAGE_LIST_PERSONS_WITH_NAME, nameToFilter, nameToFilter);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(new NameContainsKeywordPredicate(nameToFilter.fullName));

        assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);

        // Ensure no deletion occurred
        assertTrue(model.getAddressBook().getPersonList().containsAll(getTypicalAddressBook().getPersonList()));
    }

    @Test
    public void execute_nameAndIndex_validIndexDeletesCorrectPerson() {
        Name nameToFilter = DUPLICATE_NAME;
        Person personToDelete = model.getFilteredPersonList().get(INDEX_DUPLICATE_NAME.getZeroBased());

        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(nameToFilter, FIRST_INDEX);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Filter by name and delete
        expectedModel.updateFilteredPersonList(new NameContainsKeywordPredicate(nameToFilter.fullName));
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, SECOND_INDEX);

        Person personToDelete = model.getFilteredPersonList().get(FIRST_INDEX.getZeroBased());
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(UNIQUE_NAME, FIRST_INDEX);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, SECOND_INDEX);

        Index outOfBoundIndex = Index.fromOneBased(5);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(UNIQUE_NAME, outOfBoundIndex);

        assertCommandFailure(deleteStudentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteStudentCommand deleteFirstCommand = new DeleteStudentCommand(DUPLICATE_NAME, FIRST_INDEX);
        DeleteStudentCommand deleteSecondCommand = new DeleteStudentCommand(DUPLICATE_NAME, SECOND_INDEX);
        DeleteStudentCommand deleteFirstDiffNameCommand = new DeleteStudentCommand(new Name("Hello"), FIRST_INDEX);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(DUPLICATE_NAME, FIRST_INDEX);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different name -> returns false
        assertFalse(deleteFirstCommand.equals(deleteFirstDiffNameCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteStudentCommand deleteCommand = new DeleteStudentCommand(DUPLICATE_NAME, targetIndex);
        String expected = DeleteStudentCommand.class.getCanonicalName() + "{name=" + DUPLICATE_NAME
                + ", targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
