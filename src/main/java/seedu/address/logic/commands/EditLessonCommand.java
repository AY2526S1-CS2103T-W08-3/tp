package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.note.Note;

/**
 * Edits the details of an existing lesson in the address book.
 */
public class EditLessonCommand extends Command {

    public static final String COMMAND_WORD = "editlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the lesson identified "
            + "by the index number used in the displayed lesson list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY "
            + PREFIX_START_TIME + "STARTTIME "
            + PREFIX_END_TIME + "ENDTIME "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_LESSON_NOTE + "LESSONNOTE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAY + "Tue "
            + PREFIX_START_TIME + "1500 "
            + PREFIX_END_TIME + "1700 "
            + PREFIX_VENUE + "Ang Mo Kio Block 52 #12-34 "
            + PREFIX_LESSON_NOTE + "English Lesson";

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited Lesson: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in the address book.";

    private final Index index;
    private final EditLessonDescriptor editLessonDescriptor;

    /**
     * @param index of the lesson in the filtered lesson list to edit
     * @param editLessonDescriptor details to edit the lesson with
     */
    public EditLessonCommand(Index index, EditLessonDescriptor editLessonDescriptor) {
        requireNonNull(index);
        requireNonNull(editLessonDescriptor);

        this.index = index;
        this.editLessonDescriptor = new EditLessonDescriptor(editLessonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Lesson> lastShownList = model.getFilteredLessonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson lessonToEdit = lastShownList.get(index.getZeroBased());
        Lesson editedLesson = createEditedLesson(lessonToEdit, editLessonDescriptor);

        if (!lessonToEdit.isSameLesson(editedLesson) && model.hasLesson(editedLesson)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        model.setLesson(lessonToEdit, editedLesson);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS,
                                               Messages.format(editedLesson)), false, false, true);
    }

    /**
     * Creates and returns a {@code Lesson} with the details of {@code lessonToEdit}
     * edited with {@code editLessonDescriptor}.
     */
    private static Lesson createEditedLesson(Lesson lessonToEdit, EditLessonDescriptor editLessonDescriptor) {
        assert lessonToEdit != null;

        Day updatedDay = editLessonDescriptor.getDay().orElse(lessonToEdit.getDay());
        Time updatedStartTime = editLessonDescriptor.getStartTime().orElse(lessonToEdit.getStartTime());
        Time updatedEndTime = editLessonDescriptor.getEndTime().orElse(lessonToEdit.getEndTime());
        Venue updatedVenue = editLessonDescriptor.getVenue().orElse(lessonToEdit.getVenue());
        Note updatedNote = editLessonDescriptor.getNote().orElse(lessonToEdit.getNote());

        return new Lesson(lessonToEdit.getLessonId(), updatedDay, updatedStartTime,
                          updatedEndTime, updatedVenue, updatedNote);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditLessonCommand)) {
            return false;
        }

        EditLessonCommand otherCommand = (EditLessonCommand) other;
        return index.equals(otherCommand.index)
                && editLessonDescriptor.equals(otherCommand.editLessonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editLessonDescriptor", editLessonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the lesson with. Each non-empty field value will replace the
     * corresponding field value of the lesson.
     */
    public static class EditLessonDescriptor {
        private Day day;
        private Time startTime;
        private Time endTime;
        private Venue venue;
        private Note note;

        public EditLessonDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditLessonDescriptor(EditLessonDescriptor toCopy) {
            setDay(toCopy.day);
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
            setVenue(toCopy.venue);
            setNote(toCopy.note);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(day, startTime, endTime, venue, note);
        }

        public void setDay(Day day) {
            this.day = day;
        }

        public Optional<Day> getDay() {
            return Optional.ofNullable(day);
        }

        public void setStartTime(Time startTime) {
            this.startTime = startTime;
        }

        public Optional<Time> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(Time endTime) {
            this.endTime = endTime;
        }

        public Optional<Time> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        public void setVenue(Venue venue) {
            this.venue = venue;
        }

        public Optional<Venue> getVenue() {
            return Optional.ofNullable(venue);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditLessonDescriptor)) {
                return false;
            }

            EditLessonDescriptor otherDescriptor = (EditLessonDescriptor) other;
            return Objects.equals(day, otherDescriptor.day)
                    && Objects.equals(startTime, otherDescriptor.startTime)
                    && Objects.equals(endTime, otherDescriptor.endTime)
                    && Objects.equals(venue, otherDescriptor.venue)
                    && Objects.equals(note, otherDescriptor.note);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("day", day)
                    .add("startTime", startTime)
                    .add("endTime", endTime)
                    .add("venue", venue)
                    .add("note", note)
                    .toString();
        }
    }
}
