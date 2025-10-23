package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Day;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand
     * and returns an AssignCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INDEX_1,
                PREFIX_DAY, PREFIX_INDEX_2);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DAY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_INDEX_1, PREFIX_DAY, PREFIX_INDEX_2);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Index studentIndex = argMultimap.getValue(PREFIX_INDEX_1).isPresent()
                ? ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX_1).get())
                : null;
        Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        Index lessonIndex = argMultimap.getValue(PREFIX_INDEX_2).isPresent()
                ? ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX_2).get())
                : null;

        return new AssignCommand(name, studentIndex, day, lessonIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
