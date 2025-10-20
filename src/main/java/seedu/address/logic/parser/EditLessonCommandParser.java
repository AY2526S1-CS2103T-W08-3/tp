package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_NOTE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLessonCommand;
import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditLessonCommand object
 */
public class EditLessonCommandParser implements Parser<EditLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditLessonCommand
     * and returns an EditLessonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public EditLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_VENUE, PREFIX_LESSON_NOTE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLessonCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_VENUE, PREFIX_LESSON_NOTE);

        EditLessonDescriptor editLessonDescriptor = new EditLessonDescriptor();

        if (argMultimap.getValue(PREFIX_DAY).isPresent()) {
            editLessonDescriptor.setDay(ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get()));
        }
        if (argMultimap.getValue(PREFIX_START_TIME).isPresent()) {
            editLessonDescriptor.setStartTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_END_TIME).isPresent()) {
            editLessonDescriptor.setEndTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_VENUE).isPresent()) {
            editLessonDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get()));
        }
        if (argMultimap.getValue(PREFIX_LESSON_NOTE).isPresent()) {
            editLessonDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_LESSON_NOTE).get()));
        }

        if (!editLessonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditLessonCommand.MESSAGE_NOT_EDITED);
        }

        return new EditLessonCommand(index, editLessonDescriptor);
    }
}
