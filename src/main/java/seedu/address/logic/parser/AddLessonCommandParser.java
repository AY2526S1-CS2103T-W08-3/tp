package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.person.Note;


public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    public AddLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_STARTTIME,
                PREFIX_ENDTIME, PREFIX_VENUE, PREFIX_LESSON_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_STARTTIME, PREFIX_ENDTIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DAY, PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_VENUE,
                PREFIX_LESSON_NOTE);
        Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        Time startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_STARTTIME).get());
        Time endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_ENDTIME).get());
        Venue venue = argMultimap.getValue(PREFIX_VENUE).isPresent()
                ? ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get())
                : new Venue("");
        Note note = argMultimap.getValue(PREFIX_LESSON_NOTE).isPresent()
                ? ParserUtil.parseNote(argMultimap.getValue(PREFIX_STUDENT_NOTE).get())
                : new Note("");

        // TODO implement non-hardcoded lesson ID
        LessonId lessonId = new LessonId(2103);


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
