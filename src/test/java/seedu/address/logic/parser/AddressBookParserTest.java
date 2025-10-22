package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.model.person.PersonTest.assertEqualPersonIgnoringUserId;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.EditStudentCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterLessonByStudentCommand;
import seedu.address.logic.commands.FilterStudentByLessonCommand;
import seedu.address.logic.commands.FindLessonCommand;
import seedu.address.logic.commands.FindStudentCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListLessonCommand;
import seedu.address.logic.commands.ListStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.predicates.DayMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TypicalPersons;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddStudentCommand command = (AddStudentCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEqualPersonIgnoringUserId(new AddStudentCommand(person).getPerson(), command.getPerson());
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteStudentCommand command = (DeleteStudentCommand) parser.parseCommand(
                DeleteStudentCommand.COMMAND_WORD + " " + TypicalPersons.DUPLICATE_NAME
                        + " " + FIRST_INDEX.getOneBased());
        assertEquals(new DeleteStudentCommand(TypicalPersons.DUPLICATE_NAME, FIRST_INDEX), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditStudentCommand command = (EditStudentCommand) parser.parseCommand(EditStudentCommand.COMMAND_WORD + " "
                + FIRST_INDEX.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditStudentCommand(FIRST_INDEX, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        for (String keyword : keywords) {
            FindStudentCommand command = (FindStudentCommand) parser.parseCommand(
                    FindStudentCommand.COMMAND_WORD + " " + keyword
            );
            assertEquals(new FindStudentCommand(new NameContainsKeywordPredicate(keyword)), command);
        }
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListStudentCommand.COMMAND_WORD) instanceof ListStudentCommand);
        assertTrue(parser.parseCommand(ListStudentCommand.COMMAND_WORD + " 3") instanceof ListStudentCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_listLesson() throws Exception {
        assertTrue(parser.parseCommand(ListLessonCommand.COMMAND_WORD) instanceof ListLessonCommand);
        assertTrue(parser.parseCommand(ListLessonCommand.COMMAND_WORD + " 3") instanceof ListLessonCommand);
    }

    @Test
    public void parseCommand_addLesson_invalidFormat() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE), () ->
                parser.parseCommand(AddLessonCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_deleteLesson_invalidFormat() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE), () ->
                parser.parseCommand(DeleteStudentCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_deleteLesson() throws Exception {
        DeleteStudentCommand command = (DeleteStudentCommand) parser.parseCommand(
                DeleteStudentCommand.COMMAND_WORD + " " + TypicalPersons.DUPLICATE_NAME
                        + " " + FIRST_INDEX.getOneBased());
        assertEquals(new DeleteStudentCommand(TypicalPersons.DUPLICATE_NAME, FIRST_INDEX), command);
    }

    @Test
    public void parseCommand_findLesson_invalidFormat() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLessonCommand.MESSAGE_USAGE), () ->
                parser.parseCommand(seedu.address.logic.commands.FindLessonCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_findLesson() throws Exception {
        FindLessonCommand command = (FindLessonCommand) parser.parseCommand(
                FindLessonCommand.COMMAND_WORD + " MON");
        assertEquals(new FindLessonCommand(new DayMatchesPredicate(Day.MON)), command);
    }

    public void parseCommand_filterNoIndex_returnsCorrectCommandType() throws Exception {
        String byStudent = FilterCommand.COMMAND_WORD + " s/john";
        String byLesson = FilterCommand.COMMAND_WORD + " l/mon";
        assertTrue(parser.parseCommand(byStudent) instanceof FilterLessonByStudentCommand);
        assertTrue(parser.parseCommand(byLesson) instanceof FilterStudentByLessonCommand);
    }

    @Test
    public void parseCommand_filterWithIndex_returnsCorrectCommandType() throws Exception {
        String byStudent = FilterCommand.COMMAND_WORD + " s/john 2";
        String byLesson = FilterCommand.COMMAND_WORD + " l/mon 2";
        assertTrue(parser.parseCommand(byStudent) instanceof FilterLessonByStudentCommand);
        assertTrue(parser.parseCommand(byLesson) instanceof FilterStudentByLessonCommand);
    }

    @Test
    public void parseCommand_filterWithInvalidFormat_throwsParseException() {
        String noArguments = FilterCommand.COMMAND_WORD;
        String preamblePresent = FilterCommand.COMMAND_WORD + " preamble s/john";
        String invalidPrefix = FilterCommand.COMMAND_WORD + " x/invalid";
        String moreThanOnePrefix = FilterCommand.COMMAND_WORD + " s/john l/mon";

        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE), () ->
                    parser.parseCommand(noArguments));
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(preamblePresent));
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(invalidPrefix));
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(moreThanOnePrefix));
    }
}
