package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.model.lesson.Day;

public class DeleteLessonCommandParserTest {

    private DeleteLessonCommandParser parser = new DeleteLessonCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteLessonCommand() {
        assertParseSuccess(parser, " d/Mon", new DeleteLessonCommand(Day.MON, null));
    }

    @Test
    public void parse_noIndex_returnsDeleteLessonCommandWithNullTarget() {
        assertParseSuccess(parser, " d/Tue", new DeleteLessonCommand(Day.TUE, null));
    }

    @Test
    public void parse_validArgsWithIndex_returnsDeleteLessonCommand() {
        assertParseSuccess(parser, " d/Mon i/1", new DeleteLessonCommand(Day.MON, Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing day prefix
        assertParseFailure(parser, " Mon",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));

        // Invalid day value
        assertParseFailure(parser, " d//",
                String.format(Day.MESSAGE_CONSTRAINTS, DeleteLessonCommand.MESSAGE_USAGE));

        // Only index, no day
        assertParseFailure(parser, " i/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));

        // Invalid index (0)
        assertParseFailure(parser, " d/Mon i/0",
                String.format(MESSAGE_INVALID_INDEX, DeleteLessonCommand.MESSAGE_USAGE));

        // Empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));

        // Preamble not allowed
        assertParseFailure(parser, " extra d/Mon i/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE));
    }
}
