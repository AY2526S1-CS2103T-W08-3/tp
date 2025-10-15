package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.person.Person;
import seedu.address.model.person.UserId;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_LESSON = "Lessons list contains duplicate lesson(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and lessons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("lessons") List<JsonAdaptedLesson> lessons) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (lessons != null) {
            this.lessons.addAll(lessons);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        lessons.addAll(source.getLessonList().stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        HashMap<UserId, Person> idToPerson = new HashMap<>();
        HashMap<LessonId, Lesson> idToLesson = new HashMap<>();

        int maxUserId = -1;
        int maxLessonId = -1;

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            // Track the largest existing user ID
            maxUserId = Math.max(maxUserId, person.getUserId().value);
            addressBook.addPerson(person);
            idToPerson.put(person.getUserId(), person);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : lessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (addressBook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            // Track the largest existing lesson ID
            maxLessonId = Math.max(maxLessonId, lesson.getLessonId().value);
            addressBook.addLesson(lesson);
            idToLesson.put(lesson.getLessonId(), lesson);
        }

        // Update the placeholder Person and Lesson objects with the actual ones
        for (Person person : addressBook.getPersonList()) {
            Set<Lesson> placeholderLessons = new HashSet<>(person.getLessons());

            for (Lesson placeholderLesson : placeholderLessons) {
                person.replaceLesson(placeholderLesson,
                    idToLesson.get(placeholderLesson.getLessonId()));
            }
        }

        for (Lesson lesson : addressBook.getLessonList()) {
            Set<Person> placeholderStudents = new HashSet<>(lesson.getStudents());

            for (Person placeholderStudent : placeholderStudents) {
                lesson.replaceStudent(placeholderStudent,
                    idToPerson.get(placeholderStudent.getUserId()));
            }
        }

        // Update static max ID tracker for future persons and lessons
        addressBook.setInitialMaxUserId(maxUserId + 1);
        addressBook.setInitialMaxLessonId(maxLessonId + 1);

        return addressBook;
    }
}
