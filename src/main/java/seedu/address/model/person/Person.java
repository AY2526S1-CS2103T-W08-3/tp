package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    public static final String MESSAGE_LESSON_NOT_FOUND = "Cannot replace lesson as it is not found.";
    private static final Name PLACEHOLDER_NAME = new Name("placeholder");
    private static final Phone PLACEHOLDER_PHONE = new Phone("7522346537");
    private static final Email PLACEHOLDER_EMAIL = new Email("placeholder@example.com");
    private static final Note PLACEHOLDER_NOTE = new Note("placeholder");
    private static final Set<Tag> PLACEHOLDER_TAGS = new HashSet<>();

    // Identity fields
    private final UserId userId;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Note note;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Lesson> lessons = new HashSet<>();

    /**
     * Every field must not be null
     * This constructor is only exclusively used by storage and edit person
     */
    public Person(UserId userId, Name name, Phone phone, Email email, Note note, Set<Lesson> lessons, Set<Tag> tags) {
        requireAllNonNull(userId, name, phone, email, note, tags);
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.lessons.addAll(lessons);
        this.tags.addAll(tags);
    }

    /**
     * Every field except lesson must not be null
     */
    public Person(UserId userId, Name name, Phone phone, Email email, Note note, Set<Tag> tags) {
        requireAllNonNull(userId, name, phone, email, note, tags);
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.tags.addAll(tags);
    }

    public static Person getPlaceholderPerson(UserId userId) {
        return new Person(userId, PLACEHOLDER_NAME, PLACEHOLDER_PHONE, PLACEHOLDER_EMAIL,
                PLACEHOLDER_NOTE, PLACEHOLDER_TAGS);
    }

    public UserId getUserId() {
        return userId;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Returns an immutable lesson set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Lesson> getLessons() {
        return Collections.unmodifiableSet(lessons);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Replaces a lesson for this person with a new lesson.
     *
     * @param lessonToReplace the lesson to be replaced
     * @param replacedLesson the new lesson
     * @throws IllegalValueException if the lesson to replace is not found for this person
     */
    public void replaceLesson(Lesson lessonToReplace, Lesson replacedLesson) throws IllegalValueException {
        if (!lessons.contains(lessonToReplace)) {
            throw new IllegalValueException(MESSAGE_LESSON_NOT_FOUND);
        }

        lessons.remove(lessonToReplace);
        lessons.add(replacedLesson);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getUserId().equals(getUserId());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && userId.equals(otherPerson.userId)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && note.equals(otherPerson.note)
                && lessons.equals(otherPerson.lessons)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("note", note)
                .add("lessons", lessons)
                .add("tags", tags)
                .toString();
    }

}
