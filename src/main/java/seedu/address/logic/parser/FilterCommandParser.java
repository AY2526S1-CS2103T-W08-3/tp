package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_FILTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_FILTER;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterLessonByStudentCommand;
import seedu.address.logic.commands.FilterStudentByLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Day;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_FILTER, PREFIX_LESSON_FILTER);

        if (!isArgMultimapValid(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LESSON_FILTER, PREFIX_STUDENT_FILTER);

        if (isLessonPrefixPresent(argMultimap)) {
            String lessonArgs = argMultimap.getValue(PREFIX_LESSON_FILTER).get();
            String[] dayAndIndex = lessonArgs.split("\\s+");
            Day day = ParserUtil.parseDay(dayAndIndex[0]);

            if (dayAndIndex.length == 1) {
                return new FilterStudentByLessonCommand(day, null);
            } else {
                return new FilterStudentByLessonCommand(day, ParserUtil.parseIndex(dayAndIndex[1]));
            }

        } else if (isStudentPrefixPresent(argMultimap)) {
            String studentArgs = argMultimap.getValue(PREFIX_STUDENT_FILTER).get();
            String[] nameAndIndex = studentArgs.split("\\s+");
            Name name = ParserUtil.parseName(nameAndIndex[0]);

            if (nameAndIndex.length == 1) {
                return new FilterLessonByStudentCommand(name, null);
            } else {
                return new FilterLessonByStudentCommand(name, ParserUtil.parseIndex(nameAndIndex[1]));
            }
        } else {
            throw new ParseException("No prefixes present");
        }
    }

    /**
     * Checks if the lesson prefix is present in the input argumentMultimap
     */
    private static boolean isLessonPrefixPresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getValue(PREFIX_LESSON_FILTER).isPresent();
    }

    /**
     * Checks if the student prefix is present in the input argumentMultimap
     */
    private static boolean isStudentPrefixPresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getValue(PREFIX_STUDENT_FILTER).isPresent();
    }

    /**
     * Returns true if exactly one of student or lesson prefix is present
     */
    private static boolean isOnlyOnePrefixPresent(ArgumentMultimap argumentMultimap) {
        return isLessonPrefixPresent(argumentMultimap) ^ isStudentPrefixPresent(argumentMultimap);
    }

    /**
     * Checks if the given {@code String} of arguments result in a valid argumentMultimap
     * in the context of the FilterCommand.
     * @return true if there are no preambles before prefixes, and exactly one prefix is present
     */
    private static boolean isArgMultimapValid(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getPreamble().isEmpty()
                && isOnlyOnePrefixPresent(argumentMultimap);
    }
}
