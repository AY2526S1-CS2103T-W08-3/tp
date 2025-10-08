package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;

/**
 * Adds a lesson to the address book.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the address book. "
            + "Parameters: "
            + PREFIX_DAY + "DAY "
            + PREFIX_STARTTIME + "STARTTIME "
            + PREFIX_ENDTIME + "ENDTIME "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_LESSON_NOTE + "LESSONNOTE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DAY + "Tue "
            + PREFIX_STARTTIME + "1500 "
            + PREFIX_ENDTIME + "1700 "
            + PREFIX_VENUE + "Ang Mo Kio Block 52 #12-34 "
            + PREFIX_LESSON_NOTE + "English Lesson ";

    private final Lesson toAdd;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}
     */
    public AddLessonCommand(Lesson lesson) {
        requireNonNull(lesson);
        this.toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        return new CommandResult("Lesson Created!");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof AddLessonCommand)) {
            return false;
        }

        AddLessonCommand otherAddLessonCommand = (AddLessonCommand) other;
        return toAdd.equals(otherAddLessonCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
