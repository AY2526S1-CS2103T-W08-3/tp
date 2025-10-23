package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Day;
import seedu.address.model.person.Name;

/**
 * Contains integration tests for the complete workflow of assigning and unassigning a student to/from a lesson.
 */
public class AssignUnassignCommandTest {

    @Test
    public void execute_assignThenUnassign_success() throws Exception {
        // Start with a fresh, clean model (like opening the app for the first time)
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Name aliceName = ALICE.getName();
        Day mondayDay = Day.MON;
        Index studentIndex = Index.fromOneBased(1);
        Index lessonIndex = Index.fromOneBased(1);

        AssignCommand assignCommand = new AssignCommand(aliceName, studentIndex, mondayDay, lessonIndex);
        CommandResult assignResult = assignCommand.execute(model);

        assertTrue(assignResult.getFeedbackToUser().contains("Successfully assigned"));
        assertTrue(assignResult.getFeedbackToUser().contains("Alice Pauline"));

        UnassignCommand unassignCommand = new UnassignCommand(aliceName, studentIndex, mondayDay, lessonIndex);
        CommandResult unassignResult = unassignCommand.execute(model);

        assertTrue(unassignResult.getFeedbackToUser().contains("Successfully unassigned"));
        assertTrue(unassignResult.getFeedbackToUser().contains("Alice Pauline"));
    }
}
