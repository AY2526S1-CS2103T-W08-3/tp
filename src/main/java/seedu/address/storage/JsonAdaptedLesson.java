package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.person.Note;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    private final Integer lessonId;
    private final String day;
    private final String startTime;
    private final String endTime;
    private final String venue;
    private final String note;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("lessonId") Integer lessonId,
                             @JsonProperty("day") String day,
                             @JsonProperty("startTime") String startTime,
                             @JsonProperty("endTime") String endTime,
                             @JsonProperty("venue") String venue,
                             @JsonProperty("note") String note) {
        this.lessonId = lessonId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.note = note;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        lessonId = source.getLessonId().value;
        day = source.getDay().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
        venue = source.getVenue().toString();
        note = source.getNote().toString();
    }

    /**
     * Converts this Jackson-friendly adapted Lesson object into the model's {@code Lesson} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IllegalValueException {
        if (lessonId == null) {
            throw new IllegalValueException("Lesson ID cannot be null");
        }
        if (day == null || !Day.isValidDay(day)) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }
        if (startTime == null || !Time.isValidTime(startTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        if (endTime == null || !Time.isValidTime(endTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        if (venue == null) {
            throw new IllegalValueException("Venue cannot be null");
        }
        if (note == null) {
            throw new IllegalValueException("Note cannot be null");
        }

        final LessonId modelLessonId = new LessonId(lessonId);
        final Day modelDay = Day.fromString(day);
        final Time modelStartTime = new Time(startTime);
        final Time modelEndTime = new Time(endTime);
        final Venue modelVenue = new Venue(venue);
        final Note modelNote = new Note(note);

        return new Lesson(modelLessonId, modelDay, modelStartTime, modelEndTime, modelVenue, modelNote);
    }

}
