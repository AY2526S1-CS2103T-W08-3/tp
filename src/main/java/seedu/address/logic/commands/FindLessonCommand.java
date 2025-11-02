package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.lesson.predicates.DayMatchesPredicate;

/**
 * Finds and lists all lessons in address book by day.
 * Keyword matching is case insensitive.
 */
public class FindLessonCommand extends Command {

    public static final String COMMAND_WORD = "findlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all lessons identified by day "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: DAY\n"
            + "Example: " + COMMAND_WORD + " MON";

    private final DayMatchesPredicate predicate;

    public FindLessonCommand(DayMatchesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLessonList(predicate);
        model.setDisplayedListToLessons();
        return new CommandResult(
                String.format(Messages.MESSAGE_LESSONS_LISTED_OVERVIEW, model.getFilteredLessonList().size()),
                false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindLessonCommand)) {
            return false;
        }

        FindLessonCommand otherFindCommand = (FindLessonCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
