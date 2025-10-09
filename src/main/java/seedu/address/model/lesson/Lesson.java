package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;

/**
 * Represents a Lesson in the address book.
 * Guarantees: immutable; is always valid.
 *
 */
public class Lesson {

    private final LessonId lessonId;
    private final Day day;
    private final Time startTime;
    private final Time endTime;
    private final Venue venue;
    private final Note note;

    private final Set<Person> students = new HashSet<>();

    /**
     * Initializes a Lesson object
     * Every field must not be null
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


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lesson)) {
            return false;
        }

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

    public Note getNote() {
        return note;
    }

}
