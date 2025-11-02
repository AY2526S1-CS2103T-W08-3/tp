package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Adds a lesson to the address book.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the address book. "
            + "Parameters: "
            + PREFIX_DAY + "DAY "
            + PREFIX_START_TIME + "STARTTIME "
            + PREFIX_END_TIME + "ENDTIME "
            + "[" + PREFIX_VENUE + "VENUE] "
            + "[" + PREFIX_LESSON_NOTE + "LESSONNOTE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DAY + "Tue "
            + PREFIX_START_TIME + "1500 "
            + PREFIX_END_TIME + "1700 "
            + PREFIX_VENUE + "Ang Mo Kio Block 52 #12-34 "
            + PREFIX_LESSON_NOTE + "English Lesson ";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the address book";

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

        if (model.hasLesson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        model.addLesson(toAdd);
        model.setDisplayedListToLessons();
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)), false, false, true);
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

    public Lesson getLesson() {
        return toAdd;
    }
}
