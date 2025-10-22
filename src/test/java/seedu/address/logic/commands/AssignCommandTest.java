package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;
import static seedu.address.testutil.TypicalLessons.EXAMPLE_DAY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DUPLICATE_NAME;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Day;
import seedu.address.model.person.Name;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AssignCommand.
 */
public class AssignCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_nameOnly_filtersListNoAssignment() throws Exception {
        Name nameToFilter = DUPLICATE_NAME;
        Day dayToFilter = EXAMPLE_DAY;
        AssignCommand assignCommand = new AssignCommand(nameToFilter, null, dayToFilter, null);

        CommandResult result = assignCommand.execute(model);

        String expectedMessage = String.format(
                AssignCommand.MESSAGE_LIST_PERSONS_WITH_NAME, nameToFilter, nameToFilter, dayToFilter, dayToFilter);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_nameAndStudentIndex_filtersLessonsNoAssignment() throws Exception {
        Name nameToFilter = DUPLICATE_NAME;
        Day dayToFilter = EXAMPLE_DAY;
        AssignCommand assignCommand = new AssignCommand(nameToFilter, FIRST_INDEX, dayToFilter, null);

        CommandResult result = assignCommand.execute(model);

        String expectedMessage = String.format(
                AssignCommand.MESSAGE_LIST_LESSONS_WITH_DAY, dayToFilter, nameToFilter,
                FIRST_INDEX.getOneBased(), dayToFilter);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidStudentIndex_throwsCommandException() {
        Name nameToFilter = DUPLICATE_NAME;
        Day dayToFilter = EXAMPLE_DAY;
        Index invalidStudentIndex = Index.fromOneBased(999);

        AssignCommand assignCommand = new AssignCommand(nameToFilter, invalidStudentIndex,
                dayToFilter, null);

        assertThrows(CommandException.class, () -> assignCommand.execute(model));
    }

    @Test
    public void execute_invalidLessonIndex_throwsCommandException() {
        Name nameToFilter = DUPLICATE_NAME;
        Day dayToFilter = EXAMPLE_DAY;
        Index validStudentIndex = FIRST_INDEX;
        Index invalidLessonIndex = Index.fromOneBased(999);

        AssignCommand assignCommand = new AssignCommand(nameToFilter, validStudentIndex,
                dayToFilter, invalidLessonIndex);

        assertThrows(CommandException.class, () -> assignCommand.execute(model));
    }

    @Test
    public void equals() {
        Name nameAlice = ALICE.getName();
        Name nameBenson = BENSON.getName();
        Day dayMon = Day.MON;
        Day dayTue = Day.TUE;

        AssignCommand assignCommand1 = new AssignCommand(nameAlice, FIRST_INDEX, dayMon, FIRST_INDEX);
        AssignCommand assignCommand2 = new AssignCommand(nameAlice, FIRST_INDEX, dayMon, FIRST_INDEX);
        AssignCommand assignCommand3 = new AssignCommand(nameBenson, FIRST_INDEX, dayMon, FIRST_INDEX);
        AssignCommand assignCommand4 = new AssignCommand(nameAlice, FIRST_INDEX, dayTue, FIRST_INDEX);

        // same object -> returns true
        assertTrue(assignCommand1.equals(assignCommand1));

        // same values -> returns true
        assertTrue(assignCommand1.equals(assignCommand2));

        // different name -> returns false
        assertFalse(assignCommand1.equals(assignCommand3));

        // different day -> returns false
        assertFalse(assignCommand1.equals(assignCommand4));

        // null -> returns false
        assertFalse(assignCommand1.equals(null));

        // different type -> returns false
        assertFalse(assignCommand1.equals(5));
    }
}
