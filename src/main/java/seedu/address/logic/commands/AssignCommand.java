package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.predicates.DayMatchesPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordPredicate;

/**
 * Assigns a student to a lesson, and that lesson back to the student.
 */
public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a student identified by name using its displayed index from the filtered name list "
            + "to a lesson identified by day using its displayed index from the filtered lesson list, and vice versa.\n"
            + "Parameters: NAME, INDEX1 (must be positive integer), DAY, INDEX2 (must be positive integer)\n"
            + "NAME is required minimally.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Bob " + PREFIX_INDEX_1 + "1 "
            + PREFIX_DAY + "Mon " + PREFIX_INDEX_2 + "2 (Full Example, Instantly assigns)";
    public static final String MESSAGE_LIST_PERSONS_WITH_NAME = "Here is the list of students "
            + "containing: \"%s\". Enter \"assign n/%s i1/{i1} d/%s \" to view lessons you can assign to "
            + "student {i1} from %s.";
    public static final String MESSAGE_INPUT_DAY_PARAMETER = "Enter \"assign n/%s i1/%d d/DAY\" "
            + "to view lessons you can assign to the student from DAY.";
    public static final String MESSAGE_LIST_LESSONS_WITH_DAY = "Here is the list of lessons "
            + "on: \"%s\". Enter \"assign n/%s i1/%s d/%s i2/{i2} \" to assign the student to the lesson "
            + "and vice versa.";
    public static final String MESSAGE_ASSIGN_SUCCESS = "Successfully assigned %s to %s";

    private final Name name;
    private final Index studentIndex;
    private final Day day;
    private final Index lessonIndex;

    /**
     * Creates a AssignCommand to assign the specified {@code Person} to a {@code Lesson}.
     */
    public AssignCommand(Name name, Index studentIndex, Day day, Index lessonIndex) {
        requireNonNull(name);
        this.name = name;
        this.studentIndex = studentIndex;
        this.day = day;
        this.lessonIndex = lessonIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(new NameContainsKeywordPredicate(name.toString()));
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        if (lastShownPersonList.isEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_USERS_FOUND, name));
        }

        if (studentIndex == null) {
            String dayString = day == null ? "DAY" : day.toString();
            return new CommandResult(String.format(MESSAGE_LIST_PERSONS_WITH_NAME, name, name, dayString, dayString),
                    false, false, false);
        }

        if (studentIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (day == null) {
            int index = studentIndex.getOneBased();
            return new CommandResult(String.format(MESSAGE_INPUT_DAY_PARAMETER, name, index),
                    false, false, false);
        }

        model.updateFilteredLessonList(new DayMatchesPredicate(day));
        List<Lesson> lastShownLessonList = model.getFilteredLessonList();

        if (lastShownLessonList.isEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_LESSONS_FOUND, day));
        }

        if (lessonIndex == null) {
            return new CommandResult(String.format(MESSAGE_LIST_LESSONS_WITH_DAY, day, name,
                    studentIndex.getOneBased(), day), false, false, true);
        }

        if (lessonIndex.getZeroBased() >= lastShownLessonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Person studentToAssign = lastShownPersonList.get(studentIndex.getZeroBased());
        Lesson lessonToAssign = lastShownLessonList.get(lessonIndex.getZeroBased());

        if (studentToAssign.hasLesson(lessonToAssign)) {
            throw new CommandException(Messages.MESSAGE_STUDENT_ALREADY_ASSIGNED_LESSON);
        }

        model.assign(studentToAssign, lessonToAssign);
        model.refreshLists();
        return new CommandResult(String.format(MESSAGE_ASSIGN_SUCCESS, Messages.format(lessonToAssign),
                Messages.format(studentToAssign)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand e = (AssignCommand) other;
        return name.equals(e.name)
                && Objects.equals(studentIndex, e.studentIndex) // Use Objects.equals to handle null indices
                && day.equals(e.day)
                && Objects.equals(lessonIndex, e.lessonIndex); // Use Objects.equals to handle null indices
    }
}
