package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_REMOVE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.note.Note;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditStudentCommand extends Command {

    public static final String COMMAND_WORD = "editstudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the student identified "
            + "by the index number used in the displayed student list. "
            + "At least one field to edit must be provided.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_STUDENT_NOTE + "NOTE] "
            + "[" + PREFIX_TAG_ADD + "TAG_TO_ADD]... "
            + "[" + PREFIX_TAG_REMOVE + "TAG_TO_REMOVE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_TAG_ADD + "friend " + PREFIX_TAG_REMOVE + "colleague";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag '%s' does not exist!";
    public static final String MESSAGE_TAG_ALREADY_EXISTS = "Tag '%s' already exists!";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditStudentCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isPersonsDisplayed()) {
            throw new CommandException(String.format(Messages.MESSAGE_LIST_NOT_DISPLAYED, "Student"));
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        validateTagOperations(personToEdit, editPersonDescriptor);

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.setDisplayedListToPersons();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Validates tag add/remove operations.
     * @throws CommandException if attempting to remove a non-existent tag or add a duplicate tag
     */
    private static void validateTagOperations(Person person, EditPersonDescriptor editPersonDescriptor)
            throws CommandException {
        Set<Tag> currentTags = person.getTags();

        // Check if tag exists
        if (editPersonDescriptor.getTagsToRemove().isPresent()) {
            for (Tag tagToRemove : editPersonDescriptor.getTagsToRemove().get()) {
                if (!currentTags.contains(tagToRemove)) {
                    throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagToRemove.tagName));
                }
            }
        }

        // Checks if tag already exists
        if (editPersonDescriptor.getTagsToAdd().isPresent()) {
            for (Tag tagToAdd : editPersonDescriptor.getTagsToAdd().get()) {
                if (currentTags.contains(tagToAdd)) {
                    throw new CommandException(String.format(MESSAGE_TAG_ALREADY_EXISTS, tagToAdd.tagName));
                }
            }
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Note updatedNote = editPersonDescriptor.getNote().orElse(personToEdit.getNote());
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        editPersonDescriptor.getTagsToAdd().ifPresent(updatedTags::addAll);
        editPersonDescriptor.getTagsToRemove().ifPresent(updatedTags::removeAll);

        Set<Lesson> updatedLessons = editPersonDescriptor.getLessons().orElse(personToEdit.getLessons());

        return new Person(personToEdit.getUserId(), updatedName, updatedPhone, updatedEmail,
                updatedNote, updatedLessons, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditStudentCommand)) {
            return false;
        }

        EditStudentCommand otherEditStudentCommand = (EditStudentCommand) other;
        return index.equals(otherEditStudentCommand.index)
                && editPersonDescriptor.equals(otherEditStudentCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Note note;
        private Set<Lesson> lessons;
        private Set<Tag> tagsToAdd;
        private Set<Tag> tagsToRemove;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code lessons}, {@code tagsToAdd} and {@code tagsToRemove} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setNote(toCopy.note);
            setLessons(toCopy.lessons);
            setTagsToAdd(toCopy.tagsToAdd);
            setTagsToRemove(toCopy.tagsToRemove);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, note, lessons, tagsToAdd, tagsToRemove);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        /**
         * Sets {@code lessons} to this object's {@code lessons}.
         * A defensive copy of {@code lessons} is used internally.
         */
        public void setLessons(Set<Lesson> lessons) {
            this.lessons = (lessons != null) ? new HashSet<>(lessons) : null;
        }

        /**
         * Returns an unmodifiable lesson set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code lessons} is null.
         */
        public Optional<Set<Lesson>> getLessons() {
            return (lessons != null) ? Optional.of(Collections.unmodifiableSet(lessons)) : Optional.empty();
        }

        /**
         * Sets {@code tagsToAdd} to this object's {@code tagsToAdd}.
         * A defensive copy of {@code tagsToAdd} is used internally.
         */
        public void setTagsToAdd(Set<Tag> tagsToAdd) {
            this.tagsToAdd = (tagsToAdd != null) ? new HashSet<>(tagsToAdd) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tagsToAdd} is null.
         */
        public Optional<Set<Tag>> getTagsToAdd() {
            return (tagsToAdd != null) ? Optional.of(Collections.unmodifiableSet(tagsToAdd)) : Optional.empty();
        }

        /**
         * Sets {@code tagsToRemove} to this object's {@code tagsToRemove}.
         * A defensive copy of {@code tagsToRemove} is used internally.
         */
        public void setTagsToRemove(Set<Tag> tagsToRemove) {
            this.tagsToRemove = (tagsToRemove != null) ? new HashSet<>(tagsToRemove) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tagsToRemove} is null.
         */
        public Optional<Set<Tag>> getTagsToRemove() {
            return (tagsToRemove != null) ? Optional.of(Collections.unmodifiableSet(tagsToRemove)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(note, otherEditPersonDescriptor.note)
                    && Objects.equals(lessons, otherEditPersonDescriptor.lessons)
                    && Objects.equals(tagsToAdd, otherEditPersonDescriptor.tagsToAdd)
                    && Objects.equals(tagsToRemove, otherEditPersonDescriptor.tagsToRemove);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("note", note)
                    .add("lessons", lessons)
                    .add("tagsToAdd", tagsToAdd)
                    .add("tagsToRemove", tagsToRemove)
                    .toString();
        }
    }
}
