package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_LESSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertLessonCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalLessons.ENGLISH_LESSON;
import static seedu.address.testutil.TypicalLessons.HISTORY_LESSON;
import static seedu.address.testutil.TypicalLessons.MATH_LESSON;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.predicates.DayMatchesPredicate;

public class FindLessonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        DayMatchesPredicate firstPredicate = new DayMatchesPredicate(Day.MON);
        DayMatchesPredicate secondPredicate = new DayMatchesPredicate(Day.TUE);

        FindLessonCommand findFirstCommand = new FindLessonCommand(firstPredicate);
        FindLessonCommand findSecondCommand = new FindLessonCommand(secondPredicate);

        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(findFirstCommand.equals(new FindLessonCommand(firstPredicate)));
        assertFalse(findFirstCommand.equals(1));
        assertFalse(findFirstCommand.equals(null));
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noLessonsOnDay_zeroLessonsFound() {
        DayMatchesPredicate predicate = new DayMatchesPredicate(Day.SUN);
        FindLessonCommand command = new FindLessonCommand(predicate);

        expectedModel.updateFilteredLessonList(predicate);
        String expectedMessage = String.format(MESSAGE_LESSONS_LISTED_OVERVIEW, 0); // SUN has none
        CommandResult expected = new CommandResult(expectedMessage, false, false, true);

        assertLessonCommandSuccess(command, model, expected, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLessonList());
    }

    @Test
    public void execute_monLessons_multipleLessonsFound() {
        DayMatchesPredicate predicate = new DayMatchesPredicate(Day.MON);
        FindLessonCommand command = new FindLessonCommand(predicate);

        expectedModel.updateFilteredLessonList(predicate);
        String expectedMessage = String.format(MESSAGE_LESSONS_LISTED_OVERVIEW, 2); // MON has 2
        CommandResult expected = new CommandResult(expectedMessage, false, false, true);

        assertLessonCommandSuccess(command, model, expected, expectedModel);
        // Order should follow TypicalAddressBook insertion order: MATH then HISTORY
        assertEquals(Arrays.asList(MATH_LESSON, HISTORY_LESSON), model.getFilteredLessonList());
    }

    @Test
    public void execute_tueLessons_oneLessonFound() {
        DayMatchesPredicate predicate = new DayMatchesPredicate(Day.TUE);
        FindLessonCommand command = new FindLessonCommand(predicate);

        expectedModel.updateFilteredLessonList(predicate);
        String expectedMessage = String.format(MESSAGE_LESSONS_LISTED_OVERVIEW, 1); // TUE has 1
        CommandResult expected = new CommandResult(expectedMessage, false, false, true);

        assertLessonCommandSuccess(command, model, expected, expectedModel);
        assertEquals(Collections.singletonList(ENGLISH_LESSON), model.getFilteredLessonList());
    }

    @Test
    public void toStringMethod() {
        DayMatchesPredicate predicate = new DayMatchesPredicate(Day.WED);
        FindLessonCommand findLessonCommand = new FindLessonCommand(predicate);
        String expected = FindLessonCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findLessonCommand.toString());
    }
}
