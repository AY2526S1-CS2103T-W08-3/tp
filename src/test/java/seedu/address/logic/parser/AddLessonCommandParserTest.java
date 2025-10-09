package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_NOTE_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_NOTE_DESC_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STARTTIME_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.STARTTIME_DESC_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_NOTE_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_SCIENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.note.Note;
import seedu.address.testutil.LessonBuilder;

public class AddLessonCommandParserTest {
    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        // whitespace only preamble
        AddLessonCommand result = parser.parse(PREAMBLE_WHITESPACE + DAY_DESC_MATH + STARTTIME_DESC_MATH
                + ENDTIME_DESC_MATH + VENUE_DESC_MATH + LESSON_NOTE_DESC_MATH);

        Lesson actualLesson = result.getLesson();

        // Verify all fields except lessonId (which is randomly generated)
        assertEquals(Day.valueOf(VALID_DAY_MATH), actualLesson.getDay());
        assertEquals(new Time(VALID_STARTTIME_MATH), actualLesson.getStartTime());
        assertEquals(new Time(VALID_ENDTIME_MATH), actualLesson.getEndTime());
        assertEquals(new Venue(VALID_VENUE_MATH), actualLesson.getVenue());
        assertEquals(VALID_LESSON_NOTE_MATH, actualLesson.getNote().value);
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws ParseException {
        // no venue and note
        AddLessonCommand result = parser.parse(DAY_DESC_MATH + STARTTIME_DESC_MATH + ENDTIME_DESC_MATH);

        Lesson actualLesson = result.getLesson();

        // Verify all fields except lessonId
        assertEquals(Day.valueOf(VALID_DAY_MATH), actualLesson.getDay());
        assertEquals(new Time(VALID_STARTTIME_MATH), actualLesson.getStartTime());
        assertEquals(new Time(VALID_ENDTIME_MATH), actualLesson.getEndTime());
        assertEquals(new Venue(""), actualLesson.getVenue());
        assertEquals(new Note(""), actualLesson.getNote());
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);

        // missing day prefix
        assertParseFailure(parser, STARTTIME_DESC_MATH + ENDTIME_DESC_MATH + VENUE_DESC_MATH,
                expectedMessage);

        // missing start time prefix
        assertParseFailure(parser, DAY_DESC_MATH + ENDTIME_DESC_MATH + VENUE_DESC_MATH,
                expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, DAY_DESC_MATH + STARTTIME_DESC_MATH + VENUE_DESC_MATH,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DAY_MATH + VALID_STARTTIME_MATH + VALID_ENDTIME_MATH,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid day
        assertParseFailure(parser, INVALID_DAY_DESC + STARTTIME_DESC_MATH + ENDTIME_DESC_MATH
                + VENUE_DESC_MATH + LESSON_NOTE_DESC_MATH, Day.MESSAGE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, DAY_DESC_MATH + INVALID_STARTTIME_DESC + ENDTIME_DESC_MATH
                + VENUE_DESC_MATH + LESSON_NOTE_DESC_MATH, Time.MESSAGE_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser, DAY_DESC_MATH + STARTTIME_DESC_MATH + INVALID_ENDTIME_DESC
                + VENUE_DESC_MATH + LESSON_NOTE_DESC_MATH, Time.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_DAY_DESC + INVALID_STARTTIME_DESC + ENDTIME_DESC_MATH,
                Day.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DAY_DESC_MATH + STARTTIME_DESC_MATH
                + ENDTIME_DESC_MATH + VENUE_DESC_MATH + LESSON_NOTE_DESC_MATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedLessonString = DAY_DESC_MATH + STARTTIME_DESC_MATH
                + ENDTIME_DESC_MATH + VENUE_DESC_MATH + LESSON_NOTE_DESC_MATH;

        // multiple days
        assertParseFailure(parser, DAY_DESC_SCIENCE + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY));

        // multiple start times
        assertParseFailure(parser, STARTTIME_DESC_SCIENCE + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STARTTIME));

        // multiple end times
        assertParseFailure(parser, ENDTIME_DESC_SCIENCE + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ENDTIME));

        // multiple venues
        assertParseFailure(parser, VENUE_DESC_SCIENCE + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE));

        // multiple notes
        assertParseFailure(parser, LESSON_NOTE_DESC_SCIENCE + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LESSON_NOTE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedLessonString + DAY_DESC_SCIENCE + STARTTIME_DESC_SCIENCE
                        + ENDTIME_DESC_SCIENCE + validExpectedLessonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY, PREFIX_STARTTIME,
                        PREFIX_ENDTIME, PREFIX_VENUE, PREFIX_LESSON_NOTE));
    }
}