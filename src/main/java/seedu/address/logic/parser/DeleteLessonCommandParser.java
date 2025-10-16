package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Day;

/**
 * Parses input arguments and creates a new DeleteLessonCommand object
 */
public class DeleteLessonCommandParser implements Parser<DeleteLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLessonCommand
     * and returns a DeleteLessonCommand object for execution.
     * @param args Expects args to be of the form " {name} {index}"
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLessonCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] argParts = trimmedArgs.split("\\s+");

            // No arguments provided
            if (argParts.length == 0) {
                throw new ParseException("");
            }

            // Case: first argument is a pure number → invalid name
            if (argParts.length == 1 && argParts[0].matches("\\d+")) {
                throw new ParseException("");
            }

            Day day = ParserUtil.parseDay(argParts[0]);

            if (argParts.length == 1) {
                return new DeleteLessonCommand(day, null);
            }

            Index index = ParserUtil.parseIndex(argParts[1]);
            return new DeleteLessonCommand(day, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE), pe);
        }
    }
}
