package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.predicates.DayMatchesPredicate;
import seedu.address.model.person.predicates.StudentContainsLessonPredicate;

/**
 * Filters students by displaying a list of students that are a part of the specified {@code Lesson}
 */
public class FilterStudentByLessonCommand extends FilterCommand {

    public static final String MESSAGE_FILTER_BY_LESSON_SUCCESS = "Filtered students by lesson: %1$s";
    public static final String MESSAGE_LIST_LESSONS_WITH_DAY = "Here are a list of lessons "
            + "with day: \"%s\". Enter \"filter l/%s {i}\" to get a list "
            + "of students containing the i'th lesson in this list.";
    public static final String MESSAGE_NO_STUDENTS_FOUND = "There are no students containing the lesson: %1$s";

    private final Day day;
    private final Index targetIndex;

    /**
     * Creates a FilterCommand to filter students by the specified lesson
     */
    public FilterStudentByLessonCommand(Day day, Index targetIndex) {
        this.day = day;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(day);

        model.updateFilteredLessonList(new DayMatchesPredicate(day));
        List<Lesson> filteredLessonList = model.getFilteredLessonList();

        // List is empty
        if (filteredLessonList.isEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_LESSONS_FOUND, day));
        }

        // No filter index provided, display lesson list and message without deleting
        if (targetIndex == null) {
            return new CommandResult(String.format(MESSAGE_LIST_LESSONS_WITH_DAY, day, day),
                    false, false, true);
        }

        if (targetIndex.getZeroBased() >= filteredLessonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson lessonToFilter = filteredLessonList.get(targetIndex.getZeroBased());
        model.updateFilteredPersonList(new StudentContainsLessonPredicate(lessonToFilter));

        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_STUDENTS_FOUND,
                    Messages.format(lessonToFilter)));
        }

        return new CommandResult(String.format(MESSAGE_FILTER_BY_LESSON_SUCCESS,
                Messages.format(lessonToFilter)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterStudentByLessonCommand)) {
            return false;
        }

        FilterStudentByLessonCommand command = (FilterStudentByLessonCommand) other;
        return this.day.equals(command.day) && this.targetIndex.equals(command.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("day", day)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
