package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.UserId;

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
    private final List<Integer> studentIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("lessonId") Integer lessonId,
                             @JsonProperty("day") String day,
                             @JsonProperty("startTime") String startTime,
                             @JsonProperty("endTime") String endTime,
                             @JsonProperty("venue") String venue,
                             @JsonProperty("note") String note,
                             @JsonProperty("studentIds") List<Integer> studentIds) {
        this.lessonId = lessonId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.note = note;
        if (studentIds != null) {
            this.studentIds.addAll(studentIds);
        }
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
        studentIds.addAll(source.getStudents().stream()
                .map(s -> s.getUserId().value)
                .collect(Collectors.toList()));
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
        final Day modelDay;
        try {
            modelDay = Day.fromString(day);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }
        final Time modelStartTime = new Time(startTime);
        final Time modelEndTime = new Time(endTime);
        final Venue modelVenue = new Venue(venue);
        final Note modelNote = new Note(note);

        // This is done as a workaround to the circular references between Person and Lesson
        // The placeholder persons will be overriden on initialization
        final Set<Person> modelPersons = studentIds.stream()
                .map(id -> Person.getPlaceholderPerson(new UserId(id)))
                .collect(Collectors.toSet());

        return new Lesson(modelLessonId, modelDay, modelStartTime, modelEndTime, modelVenue, modelNote, modelPersons);
    }

}
