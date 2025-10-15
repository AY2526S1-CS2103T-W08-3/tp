package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonTest;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonTest;
import seedu.address.testutil.TypicalAddressBook;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");
    private static final Path TYPICAL_LESSONS_FILE = TEST_DATA_FOLDER.resolve("typicalLessonsAddressBook.json");
    private static final Path INVALID_LESSON_FILE = TEST_DATA_FOLDER.resolve("invalidLessonAddressBook.json");
    private static final Path DUPLICATE_LESSON_FILE = TEST_DATA_FOLDER.resolve("duplicateLessonAddressBook.json");
    private static final Path CIRCULAR_REFERENCE_FILE = TEST_DATA_FOLDER.resolve("circularReferenceAddressBook.json");
    private static final Path PERSON_WITH_NONEXISTENT_LESSON_ID_FILE =
                TEST_DATA_FOLDER.resolve("personWithNonExistentLessonId.json");
    private static final Path LESSON_WITH_NONEXISTENT_STUDENT_ID_FILE =
                TEST_DATA_FOLDER.resolve("lessonWithNonExistentStudentId.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalAddressBook = TypicalAddressBook.getTypicalAddressBook();
        ObservableList<Person> typicalPersons = typicalAddressBook.getPersonList();
        ObservableList<Person> addressBookPersons = addressBookFromFile.getPersonList();

        assertEquals(typicalPersons.size(), addressBookPersons.size(),
                "AddressBooks differ in number of persons");

        for (int i = 0; i < typicalPersons.size(); i++) {
            Person expectedPerson = typicalPersons.get(i);
            Person actualPerson = addressBookPersons.get(i);
            PersonTest.assertEqualPerson(expectedPerson, actualPerson);
        }
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalLessonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_LESSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalAddressBook = TypicalAddressBook.getTypicalAddressBook();
        ObservableList<Lesson> typicalLessons = typicalAddressBook.getLessonList();
        ObservableList<Lesson> addressBookLessons = addressBookFromFile.getLessonList();

        assertEquals(typicalLessons.size(), addressBookLessons.size(),
                "AddressBooks differ in number of lessons");

        for (int i = 0; i < typicalLessons.size(); i++) {
            Lesson expectedLesson = typicalLessons.get(i);
            Lesson actualLesson = addressBookLessons.get(i);
            LessonTest.assertEqualLesson(expectedLesson, actualLesson);
        }
    }

    @Test
    public void toModelType_invalidLessonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_LESSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateLessons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_LESSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_LESSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_circularReference_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(CIRCULAR_REFERENCE_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBook = dataFromFile.toModelType();

        // Verify we have exactly 1 person and 1 lesson
        assertEquals(1, addressBook.getPersonList().size(), "Should have exactly 1 person");
        assertEquals(1, addressBook.getLessonList().size(), "Should have exactly 1 lesson");

        // Get the person and lesson from the address book
        Person person = addressBook.getPersonList().get(0);
        Lesson lesson = addressBook.getLessonList().get(0);

        // Verify basic attributes
        assertEquals(100, person.getUserId().value, "Person should have userId 100");
        assertEquals(2000, lesson.getLessonId().value, "Lesson should have lessonId 2000");

        // Verify the person has the lesson in their lesson set
        assertEquals(1, person.getLessons().size(), "Person should have 1 lesson");
        Lesson lessonFromPerson = person.getLessons().iterator().next();

        // Verify the lesson from person is the same object as the lesson in the address book
        assertEquals(lesson, lessonFromPerson, "Lesson from person should equal the original lesson");

        // Verify the lesson has the person in their students set
        assertEquals(1, lesson.getStudents().size(), "Lesson should have 1 student");
        Person studentFromLesson = lesson.getStudents().iterator().next();

        // Verify the person from lesson is the same object as the person in the address book
        assertEquals(person, studentFromLesson, "Student from lesson should equal the original person");
    }

    @Test
    public void toModelType_personWithNonExistentLessonId_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(PERSON_WITH_NONEXISTENT_LESSON_ID_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_lessonWithNonExistentStudentId_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(LESSON_WITH_NONEXISTENT_STUDENT_ID_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
