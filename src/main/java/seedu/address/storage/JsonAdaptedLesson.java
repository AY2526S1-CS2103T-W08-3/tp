package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Lesson;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    private final String lessonName;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given {@code lessonName}.
     */
    @JsonCreator
    public JsonAdaptedLesson(String lessonName) {
        this.lessonName = lessonName;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        lessonName = source.lessonName;
    }

    @JsonValue
    public String getlessonName() {
        return lessonName;
    }

    /**
     * Converts this Jackson-friendly adapted Lesson object into the model's {@code Lesson} object.
     */
    public Lesson toModelType() throws IllegalValueException {
        return new Lesson(lessonName);
    }

}
