package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;

/**
 * Represents a Lesson in the address book.
 * Guarantees: immutable; is always valid.
 *
 */
public class Lesson {
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Cannot replace student as it is not found.";
    private static final Day PLACEHOLDER_DAY = Day.MON;
    private static final Time PLACEHOLDER_TIME = new Time("0000");
    private static final Venue PLACEHOLDER_VENUE = new Venue("placeholder");
    private static final Note PLACEHOLDER_NOTE = new Note("placeholder");

    private final LessonId lessonId;
    private final Day day;
    private final Time startTime;
    private final Time endTime;
    private final Venue venue;
    private final Note note;

    private final Set<Person> students = new HashSet<>();

    /**
     * Initializes a Lesson object
     * Every field except students must not be null
     */
    public Lesson(LessonId lessonId, Day day, Time startTime, Time endTime, Venue venue, Note note) {
        requireAllNonNull(lessonId, day, startTime, endTime, venue, note);
        this.lessonId = lessonId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.note = note;
    }

    /**
     * Initializes a Lesson object
     * Every field must not be null
     */
    public Lesson(LessonId lessonId, Day day, Time startTime, Time endTime, Venue venue,
            Note note, Set<Person> students) {
        requireAllNonNull(lessonId, day, startTime, endTime, venue, note, students);
        this.lessonId = lessonId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.note = note;
        this.students.addAll(students);
    }

    public static Lesson getPlaceholderLesson(LessonId lessonId) {
        return new Lesson(lessonId, PLACEHOLDER_DAY, PLACEHOLDER_TIME, PLACEHOLDER_TIME,
                PLACEHOLDER_VENUE, PLACEHOLDER_NOTE);
    }

    /**
     * Returns true if both lessons have the same id.
     * This defines a weaker notion of equality between two lessons.
     */
    public boolean isSameLesson(Lesson otherLesson) {
        if (otherLesson == this) {
            return true;
        }

        return otherLesson != null
                && otherLesson.getLessonId().equals(getLessonId());
    }

    /**
     * Returns true if the input student is a student of this lesson
     */
    public boolean hasPerson(Person person) {
        return students.contains(person);
    }

    /**
     * Adds a student to this lesson's set of students.
     *
     * @param student the student to add
     */
    public void addStudent(Person student) {
        students.add(student);
    }

    /**
     * Removes a student from this lesson's set of students.
     *
     * @param student the student to remove
     */
    public void removeStudent(Person student) {
        students.remove(student);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lesson)) {
            return false;
        }

        // No need to check if students storage is equal for simplicity and to avoid bugs for this two-way rls
        Lesson otherLesson = (Lesson) other;
        return lessonId.equals(otherLesson.lessonId)
                && day.equals(otherLesson.day)
                && startTime.equals(otherLesson.startTime)
                && endTime.equals(otherLesson.endTime)
                && venue.equals(otherLesson.venue)
                && note.equals(otherLesson.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId);
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("lessonId", lessonId)
                .add("day", day)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("venue", venue)
                .add("note", note)
                .toString();
    }

    public Day getDay() {
        return day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Venue getVenue() {
        return venue;
    }

    public LessonId getLessonId() {
        return lessonId;
    }

    /**
     * Returns an immutable student set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    /**
     * Replaces a student in the lesson with a new student.
     *
     * @param studentToReplace the student to be replaced
     * @param replacedStudent the new student
     * @throws IllegalValueException if the student to replace is not found in the lesson
     */
    public void replaceStudent(Person studentToReplace, Person replacedStudent) throws IllegalValueException {
        if (!students.contains(studentToReplace)) {
            throw new IllegalValueException(MESSAGE_STUDENT_NOT_FOUND);
        }

        students.remove(studentToReplace);
        students.add(replacedStudent);
    }

    public Note getNote() {
        return note;
    }

}
