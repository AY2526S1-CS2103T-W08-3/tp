package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @param args Expects args to be of the form " {name} {index}"
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
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

            Name name = ParserUtil.parseName(argParts[0]);

            if (argParts.length == 1) {
                return new DeleteCommand(name, null);
            }

            Index index = ParserUtil.parseIndex(argParts[1]);
            return new DeleteCommand(name, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
