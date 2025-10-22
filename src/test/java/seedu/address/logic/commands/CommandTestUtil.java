package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.lesson.predicates.LessonIdEqualsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.UserId;
import seedu.address.model.person.predicates.UserIdEqualsPredicate;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_STUDENT_NOTE_AMY = "Good at math";
    public static final String VALID_STUDENT_NOTE_BOB = "Weak in science";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_DAY_MATH = "MON";
    public static final String VALID_DAY_SCIENCE = "TUE";
    public static final String VALID_STARTTIME_MATH = "1400";
    public static final String VALID_STARTTIME_SCIENCE = "1000";
    public static final String VALID_ENDTIME_MATH = "1600";
    public static final String VALID_ENDTIME_SCIENCE = "1200";
    public static final String VALID_VENUE_MATH = "Blk 123 Computing Dr 1";
    public static final String VALID_VENUE_SCIENCE = "Blk 456 Science Ave 2";
    public static final String VALID_LESSON_NOTE_MATH = "Algebra basics";
    public static final String VALID_LESSON_NOTE_SCIENCE = "Chemistry review";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String STUDENT_NOTE_DESC_AMY = " " + PREFIX_STUDENT_NOTE + VALID_STUDENT_NOTE_AMY;
    public static final String STUDENT_NOTE_DESC_BOB = " " + PREFIX_STUDENT_NOTE + VALID_STUDENT_NOTE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String DAY_DESC_MATH = " " + PREFIX_DAY + VALID_DAY_MATH;
    public static final String DAY_DESC_SCIENCE = " " + PREFIX_DAY + VALID_DAY_SCIENCE;
    public static final String STARTTIME_DESC_MATH = " " + PREFIX_START_TIME + VALID_STARTTIME_MATH;
    public static final String STARTTIME_DESC_SCIENCE = " " + PREFIX_START_TIME + VALID_STARTTIME_SCIENCE;
    public static final String ENDTIME_DESC_MATH = " " + PREFIX_END_TIME + VALID_ENDTIME_MATH;
    public static final String ENDTIME_DESC_SCIENCE = " " + PREFIX_END_TIME + VALID_ENDTIME_SCIENCE;
    public static final String VENUE_DESC_MATH = " " + PREFIX_VENUE + VALID_VENUE_MATH;
    public static final String VENUE_DESC_SCIENCE = " " + PREFIX_VENUE + VALID_VENUE_SCIENCE;
    public static final String LESSON_NOTE_DESC_MATH = " " + PREFIX_LESSON_NOTE + VALID_LESSON_NOTE_MATH;
    public static final String LESSON_NOTE_DESC_SCIENCE = " " + PREFIX_LESSON_NOTE + VALID_LESSON_NOTE_SCIENCE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_DAY_DESC = " " + PREFIX_DAY + "ABC"; // invalid day
    public static final String INVALID_STARTTIME_DESC = " " + PREFIX_START_TIME + "25:00"; // invalid time
    public static final String INVALID_ENDTIME_DESC = " " + PREFIX_END_TIME + "2500"; // invalid time

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditStudentCommand.EditPersonDescriptor DESC_AMY;
    public static final EditStudentCommand.EditPersonDescriptor DESC_BOB;

    public static final EditLessonCommand.EditLessonDescriptor DESC_MATH;
    public static final EditLessonCommand.EditLessonDescriptor DESC_SCIENCE;


    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withNote(VALID_STUDENT_NOTE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withNote(VALID_STUDENT_NOTE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_MATH = new EditLessonDescriptorBuilder().withDay(VALID_DAY_MATH).withStartTime(VALID_STARTTIME_MATH)
                .withEndTime(VALID_ENDTIME_MATH).withVenue(VALID_VENUE_MATH).withNote(VALID_LESSON_NOTE_MATH)
                .build();
        DESC_SCIENCE = new EditLessonDescriptorBuilder().withDay(VALID_DAY_SCIENCE)
                .withStartTime(VALID_STARTTIME_SCIENCE).withEndTime(VALID_ENDTIME_SCIENCE)
                .withVenue(VALID_VENUE_SCIENCE).withNote(VALID_LESSON_NOTE_SCIENCE)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertStudentCommandSuccess(Command command, Model actualModel,
                                                   CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertStudentCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertStudentCommandSuccess(Command command, Model actualModel,
                                                   String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertStudentCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertLessonCommandSuccess(Command command, Model actualModel,
                                                  CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertLessonCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertLessonCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true);
        assertStudentCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final UserId userId = person.getUserId();

        model.updateFilteredPersonList(new UserIdEqualsPredicate(userId));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the lesson at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showLessonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredLessonList().size());

        Lesson lesson = model.getFilteredLessonList().get(targetIndex.getZeroBased());
        final LessonId lessonId = lesson.getLessonId();

        model.updateFilteredLessonList(new LessonIdEqualsPredicate(lessonId));

        assertEquals(1, model.getFilteredLessonList().size());
    }
}
