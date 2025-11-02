package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.predicates.LessonContainsStudentPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordPredicate;

/**
 * Filters lessons by displaying a list of lessons that contain the specified {@code Person}
 */
public class FilterLessonByStudentCommand extends FilterCommand {

    public static final String MESSAGE_FILTER_BY_STUDENT_SUCCESS = "Filtered lessons by student: %1$s";
    public static final String MESSAGE_LIST_STUDENTS_WITH_NAME = "Here are a list of students "
            + "with name: \"%s\". Enter \"filter n/%s {i}\" to get a list "
            + "of lessons containing the i'th student in this list.";
    public static final String MESSAGE_NO_LESSONS_FOUND = "There are no lessons containing the student: %1$s";

    private final Name name;
    private final Index targetIndex;

    /**
     * Creates a FilterCommand to filter the list of lessons by the specified student
     */
    public FilterLessonByStudentCommand(Name name, Index targetIndex) {
        requireNonNull(name);
        this.name = name;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(new NameContainsKeywordPredicate(name.toString()));
        List<Person> filteredPersonList = model.getFilteredPersonList();

        // List is empty
        if (filteredPersonList.isEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_USERS_FOUND, name));
        }

        // No filter index provided, display student list and message without deleting
        if (targetIndex == null) {
            return new CommandResult(String.format(MESSAGE_LIST_STUDENTS_WITH_NAME, name, name));
        }

        if (targetIndex.getZeroBased() >= filteredPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToFilter = filteredPersonList.get(targetIndex.getZeroBased());
        model.updateFilteredLessonList(new LessonContainsStudentPredicate(personToFilter));

        if (model.getFilteredLessonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_LESSONS_FOUND,
                    Messages.format(personToFilter)),
                    false, false, true);
        }

        return new CommandResult(String.format(MESSAGE_FILTER_BY_STUDENT_SUCCESS,
                Messages.format(personToFilter)), false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterLessonByStudentCommand)) {
            return false;
        }

        FilterLessonByStudentCommand command = (FilterLessonByStudentCommand) other;
        return this.name.equals(command.name) && Objects.equals(this.targetIndex, command.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
