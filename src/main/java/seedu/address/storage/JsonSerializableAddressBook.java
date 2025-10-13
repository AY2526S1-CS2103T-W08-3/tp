package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Lesson;
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
        int maxId = -1;

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            // Track the largest existing ID
            maxId = Math.max(maxId, person.getUserId().value);
            addressBook.addPerson(person);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : lessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (addressBook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            addressBook.addLesson(lesson);
        }

        // Update static max ID tracker for future persons
        UserId.setMaxUserId(maxId + 1);

        return addressBook;
    }
}
