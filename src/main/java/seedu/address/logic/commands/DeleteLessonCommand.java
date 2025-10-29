package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.predicates.DayMatchesPredicate;

/**
 * Deletes a lesson using a list filtered by the day that it is held
 */
public class DeleteLessonCommand extends Command {

    public static final String COMMAND_WORD = "deletelesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a lesson  identified by day using it's displayed index from the filtered name list .\n"
            + "Parameters: DAY, INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " MON " + " 1";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";
    public static final String MESSAGE_LIST_LESSONS_WITH_NAME = "Here are a list of lessons "
            + "on the day: \"%s\". Enter \"deletelesson %s {i}\" to delete the i'th lesson in this list.";

    private final Day day;
    private final Index targetIndex; //null if no target index provided to delete

    /**
     * Creates a DeleteLessonCommand to delete the specified {@code Lesson}
     */
    public DeleteLessonCommand(Day day, Index targetIndex) {
        this.day = day;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(day);

        if (!model.isLessonsDisplayed()) {
            throw new CommandException(String.format(Messages.MESSAGE_LIST_NOT_DISPLAYED, "Lesson"));
        }

        model.updateFilteredLessonList(new DayMatchesPredicate(day));
        List<Lesson> lastShownList = model.getFilteredLessonList();

        // List is empty
        if (lastShownList.isEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_LESSONS_FOUND, day));
        }

        // No delete index provided, display list and message without deleting
        if (targetIndex == null) {
            return new CommandResult(String.format(MESSAGE_LIST_LESSONS_WITH_NAME, day, day),
                    false, false, true);
        }

        // If target index provided is larger than filtered list size
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson lessonToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteLesson(lessonToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, Messages.format(lessonToDelete)),
                false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteLessonCommand)) {
            return false;
        }
        DeleteLessonCommand otherCommand = (DeleteLessonCommand) other;

        return (Objects.equals(targetIndex, otherCommand.targetIndex))
                && day.equals(otherCommand.day);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
