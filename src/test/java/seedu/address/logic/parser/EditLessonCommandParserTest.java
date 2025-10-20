package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.ENDTIME_DESC_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENDTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.STARTTIME_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.STARTTIME_DESC_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_SCIENCE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;
import static seedu.address.testutil.TypicalIndexes.SECOND_INDEX;
import static seedu.address.testutil.TypicalIndexes.THIRD_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLessonCommand;
import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Time;
import seedu.address.testutil.EditLessonDescriptorBuilder;

public class EditLessonCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLessonCommand.MESSAGE_USAGE);

    private EditLessonCommandParser parser = new EditLessonCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DAY_MATH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditLessonCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DAY_DESC_MATH, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DAY_DESC_MATH, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DAY_DESC, Day.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_STARTTIME_DESC, Time.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_ENDTIME_DESC, Time.MESSAGE_CONSTRAINTS);

        // multiple invalid values, only first captured
        assertParseFailure(parser, "1" + INVALID_DAY_DESC + INVALID_STARTTIME_DESC + VALID_VENUE_MATH,
                Day.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = SECOND_INDEX;
        String userInput = targetIndex.getOneBased() + DAY_DESC_SCIENCE + STARTTIME_DESC_SCIENCE
                + ENDTIME_DESC_SCIENCE + VENUE_DESC_SCIENCE;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withDay(VALID_DAY_SCIENCE)
                .withStartTime(VALID_STARTTIME_SCIENCE)
                .withEndTime(VALID_ENDTIME_SCIENCE)
                .withVenue(VALID_VENUE_SCIENCE)
                .build();
        EditLessonCommand expectedCommand = new EditLessonCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = FIRST_INDEX;
        String userInput = targetIndex.getOneBased() + DAY_DESC_MATH + STARTTIME_DESC_MATH;

        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withDay(VALID_DAY_MATH)
                .withStartTime(VALID_STARTTIME_MATH)
                .build();
        EditLessonCommand expectedCommand = new EditLessonCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = THIRD_INDEX;

        // day
        String userInput = targetIndex.getOneBased() + DAY_DESC_MATH;
        EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder().withDay(VALID_DAY_MATH).build();
        EditLessonCommand expectedCommand = new EditLessonCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start time
        userInput = targetIndex.getOneBased() + STARTTIME_DESC_MATH;
        descriptor = new EditLessonDescriptorBuilder().withStartTime(VALID_STARTTIME_MATH).build();
        expectedCommand = new EditLessonCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end time
        userInput = targetIndex.getOneBased() + ENDTIME_DESC_MATH;
        descriptor = new EditLessonDescriptorBuilder().withEndTime(VALID_ENDTIME_MATH).build();
        expectedCommand = new EditLessonCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // venue
        userInput = targetIndex.getOneBased() + " " + VENUE_DESC_MATH;
        descriptor = new EditLessonDescriptorBuilder().withVenue(VALID_VENUE_MATH).build();
        expectedCommand = new EditLessonCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
