package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

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
 * Unassigns a student from a lesson, and that lesson from the student.
 */
public class UnassignCommand extends Command {
    public static final String COMMAND_WORD = "unassign";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unassigns a student identified by name using its displayed index from the filtered name list "
            + "from a lesson identified by day using its displayed index from the filtered lesson list, "
            + "and vice versa.\n"
            + "Parameters: NAME, INDEX1 (must be positive integer), DAY, INDEX2 (must be positive integer)\n"
            + "NAME and DAY is required minimally.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Bob " + PREFIX_INDEX_1 + "1 "
            + PREFIX_DAY + "Mon " + PREFIX_INDEX_2 + "2 (Full Example, Instantly unassigns)";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Unassign command not implemented yet";
    public static final String MESSAGE_LIST_PERSONS_WITH_NAME = "Here is the list of students "
            + "containing: \"%s\". Enter \"unassign n/%s i1/{i1} d/%s \" to view lessons you can unassign "
            + "from student {i1} from %s.";
    public static final String MESSAGE_LIST_LESSONS_WITH_DAY = "Here is the list of lessons "
            + "on: \"%s\". Enter \"unassign n/%s i1/%s d/%s i2/{i2} \" to unassign the student from the "
            + "lesson and vice versa.";
    public static final String MESSAGE_UNASSIGN_SUCCESS = "Successfully unassigned %s from %s";

    private final Name name;
    private final Index studentIndex;
    private final Day day;
    private final Index lessonIndex;

    /**
     * Creates a UnassignCommand to unassign the specified {@code Person} from a {@code Lesson}.
     */
    public UnassignCommand(Name name, Index studentIndex, Day day, Index lessonIndex) {
        requireAllNonNull(name, day);
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

        if (studentIndex == null) {
            if (lastShownPersonList.isEmpty()) {
                return new CommandResult(String.format(Messages.MESSAGE_NO_USERS_FOUND, name));
            }
            return new CommandResult(String.format(MESSAGE_LIST_PERSONS_WITH_NAME, name, name, day, day),
                    false, false, false);
        }

        if (studentIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.updateFilteredLessonList(new DayMatchesPredicate(day));
        List<Lesson> lastShownLessonList = model.getFilteredLessonList();

        if (lessonIndex == null) {
            if (lastShownLessonList.isEmpty()) {
                return new CommandResult(String.format(Messages.MESSAGE_NO_LESSONS_FOUND, day));
            }
            return new CommandResult(String.format(MESSAGE_LIST_LESSONS_WITH_DAY, day, name,
                    studentIndex.getOneBased(), day), false, false, true);
        }

        if (lessonIndex.getZeroBased() >= lastShownLessonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Person studentToUnassign = lastShownPersonList.get(studentIndex.getZeroBased());
        Lesson lessonToUnassign = lastShownLessonList.get(lessonIndex.getZeroBased());

        if (!studentToUnassign.hasLesson(lessonToUnassign)) {
            throw new CommandException(Messages.MESSAGE_STUDENT_NOT_ASSIGNED_LESSON);
        }

        model.unassign(studentToUnassign, lessonToUnassign);
        model.refreshLists();
        
        return new CommandResult(String.format(MESSAGE_UNASSIGN_SUCCESS, Messages.format(lessonToUnassign),
                Messages.format(studentToUnassign)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnassignCommand)) {
            return false;
        }

        UnassignCommand e = (UnassignCommand) other;
        return name.equals(e.name)
                && studentIndex.equals(e.studentIndex)
                && day.equals(day)
                && lessonIndex.equals(lessonIndex);
    }
}
