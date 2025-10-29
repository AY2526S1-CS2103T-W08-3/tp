package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.FIRST_INDEX;
import static seedu.address.testutil.TypicalIndexes.SECOND_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.model.lesson.Day;
import seedu.address.model.person.Name;

/**
 * Contains tests for {@code AssignCommandParser}.
 */
public class AssignCommandParserTest {
    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Name expectedName = new Name(VALID_NAME_AMY);
        Day expectedDay = Day.valueOf(VALID_DAY_MATH);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + " i1/1" + DAY_DESC_MATH + " i2/2",
                new AssignCommand(expectedName, FIRST_INDEX, expectedDay, SECOND_INDEX));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Name expectedName = new Name(VALID_NAME_AMY);
        Day expectedDay = Day.valueOf(VALID_DAY_MATH);

        // no i1 and i2
        assertParseSuccess(parser, NAME_DESC_AMY + DAY_DESC_MATH,
                new AssignCommand(expectedName, null, expectedDay, null));

        // no i2 only
        assertParseSuccess(parser, NAME_DESC_AMY + " i1/1" + DAY_DESC_MATH,
                new AssignCommand(expectedName, FIRST_INDEX, expectedDay, null));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, " i1/1" + DAY_DESC_MATH + " i2/2",
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_AMY + " 1 " + VALID_DAY_MATH + " 2",
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + " i1/1" + DAY_DESC_MATH + " i2/2",
                Name.MESSAGE_CONSTRAINTS);

        // invalid day
        assertParseFailure(parser, NAME_DESC_AMY + " i1/1" + INVALID_DAY_DESC + " i2/2",
                Day.MESSAGE_CONSTRAINTS);

        // invalid i1 index (non-numeric)
        assertParseFailure(parser, NAME_DESC_AMY + " i1/a" + DAY_DESC_MATH + " i2/2",
                ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid i1 index (zero)
        assertParseFailure(parser, NAME_DESC_AMY + " i1/0" + DAY_DESC_MATH + " i2/2",
                ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid i1 index (negative)
        assertParseFailure(parser, NAME_DESC_AMY + " i1/-1" + DAY_DESC_MATH + " i2/2",
                ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid i2 index (non-numeric)
        assertParseFailure(parser, NAME_DESC_AMY + " i1/1" + DAY_DESC_MATH + " i2/b",
                ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid i2 index (zero)
        assertParseFailure(parser, NAME_DESC_AMY + " i1/1" + DAY_DESC_MATH + " i2/0",
                ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid i2 index (negative)
        assertParseFailure(parser, NAME_DESC_AMY + " i1/1" + DAY_DESC_MATH + " i2/-1",
                ParserUtil.MESSAGE_INVALID_INDEX);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + " i1/1" + INVALID_DAY_DESC + " i2/2",
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_AMY + " i1/1" + DAY_DESC_MATH + " i2/2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedAssignString = NAME_DESC_AMY + " i1/1" + DAY_DESC_MATH + " i2/2";

        // multiple names
        assertParseFailure(parser, NAME_DESC_BOB + validExpectedAssignString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple i1
        assertParseFailure(parser, NAME_DESC_AMY + " i1/2 i1/1" + DAY_DESC_MATH + " i2/2",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INDEX_1));

        // multiple days
        assertParseFailure(parser, NAME_DESC_AMY + " i1/1" + DAY_DESC_SCIENCE + DAY_DESC_MATH + " i2/2",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY));

        // multiple i2
        assertParseFailure(parser, NAME_DESC_AMY + " i1/1" + DAY_DESC_MATH + " i2/3 i2/2",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INDEX_2));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedAssignString + NAME_DESC_BOB + " i1/2" + DAY_DESC_SCIENCE + " i2/3",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_INDEX_1, PREFIX_DAY, PREFIX_INDEX_2));
    }
}
