package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.note.Note;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand
     * and returns an AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_START_TIME,
                PREFIX_END_TIME, PREFIX_VENUE, PREFIX_LESSON_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DAY, PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_VENUE,
                PREFIX_LESSON_NOTE);
        Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        Time startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        Time endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());
        Venue venue = argMultimap.getValue(PREFIX_VENUE).isPresent()
                ? ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get())
                : new Venue("");
        Note note = argMultimap.getValue(PREFIX_LESSON_NOTE).isPresent()
                ? ParserUtil.parseNote(argMultimap.getValue(PREFIX_LESSON_NOTE).get())
                : new Note("");

        LessonId lessonId = new LessonId();

        Lesson lesson = new Lesson(lessonId, day, startTime, endTime, venue, note);

        return new AddLessonCommand(lesson);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
