package seedu.address.testutil;

import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.note.Note;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_DAY = "MON";
    public static final String DEFAULT_STARTTIME = "1400";
    public static final String DEFAULT_ENDTIME = "1600";
    public static final String DEFAULT_VENUE = "Blk 123 Computing Dr 1";
    public static final String DEFAULT_NOTE = "Algebra basics";
    public static final Integer DEFAULT_LESSONID = 0;

    private LessonId lessonId;
    private Day day;
    private Time startTime;
    private Time endTime;
    private Venue venue;
    private Note note;

    /**
     * Creates a {@code LessonBuilder} with the default details.
     */
    public LessonBuilder() {
        lessonId = new LessonId(DEFAULT_LESSONID);
        day = Day.valueOf(DEFAULT_DAY);
        startTime = new Time(DEFAULT_STARTTIME);
        endTime = new Time(DEFAULT_ENDTIME);
        venue = new Venue(DEFAULT_VENUE);
        note = new Note(DEFAULT_NOTE);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        lessonId = lessonToCopy.getLessonId();
        day = lessonToCopy.getDay();
        startTime = lessonToCopy.getStartTime();
        endTime = lessonToCopy.getEndTime();
        venue = lessonToCopy.getVenue();
        note = lessonToCopy.getNote();
    }

    /**
     * Sets the {@code LessonId} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withLessonId(Integer lessonId) {
        this.lessonId = new LessonId(lessonId);
        return this;
    }

    /**
     * Sets the {@code Day} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDay(String day) {
        this.day = Day.valueOf(day);
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withStartTime(String startTime) {
        this.startTime = new Time(startTime);
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withEndTime(String endTime) {
        this.endTime = new Time(endTime);
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withNote(String note) {
        this.note = new Note(note);
        return this;
    }

    public Lesson build() {
        return new Lesson(lessonId, day, startTime, endTime, venue, note);
    }
}
