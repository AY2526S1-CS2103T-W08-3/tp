package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindLessonCommand;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.predicates.DayMatchesPredicate;

public class FindLessonCommandParserTest {

    private final FindLessonCommandParser parser = new FindLessonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "   ", 
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArguments_throwsParseException() {
        // Multiple words like "MON TUE"
        assertParseFailure(parser, "MON TUE",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDay_throwsParseException() {
        // Not a valid day
        assertParseFailure(parser, "FRIYAY",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLessonCommand.MESSAGE_USAGE));

        // Random string
        assertParseFailure(parser, "123",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validDay_returnsFindLessonCommand() {
        // Exact uppercase match
        FindLessonCommand expectedCommand = new FindLessonCommand(new DayMatchesPredicate(Day.MON));
        assertParseSuccess(parser, "MON", expectedCommand);
    }

    @Test
    public void parse_validDay_lowercase_returnsFindLessonCommand() {
        // Lowercase input should still work (Day.isValidDay is case-insensitive)
        FindLessonCommand expectedCommand = new FindLessonCommand(new DayMatchesPredicate(Day.MON));
        assertParseSuccess(parser, "mon", expectedCommand);
    }

    @Test
    public void parse_validDay_withExtraSpaces_returnsFindLessonCommand() {
        FindLessonCommand expectedCommand = new FindLessonCommand(new DayMatchesPredicate(Day.MON));
        assertParseSuccess(parser, "   MON   ", expectedCommand);
    }
}
