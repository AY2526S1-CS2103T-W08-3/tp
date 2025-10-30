package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalLessons;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void addLesson_validLesson_success() {
        Lesson lesson = TypicalLessons.MATH_LESSON;
        modelManager.addLesson(lesson);
        assertTrue(modelManager.getFilteredLessonList().contains(lesson));
    }

    @Test
    public void addLesson_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addLesson(null));
    }

    @Test
    public void getFilteredLessonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredLessonList().remove(0));
    }

    @Test
    public void updateFilteredLessonList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredLessonList(null));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String keyword = ALICE.getName().fullName;
        modelManager.updateFilteredPersonList(new NameContainsKeywordPredicate(keyword));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void getFilteredLessonList_initiallyEmpty_returnsEmptyList() {
        assertEquals(0, modelManager.getFilteredLessonList().size());
    }

    @Test
    public void updateFilteredLessonList_filtersByPredicate_keepsOnlyMatches() {
        Lesson l1 = TypicalLessons.MATH_LESSON;
        Lesson l2 = TypicalLessons.SCIENCE_LESSON;
        modelManager.addLesson(l1);
        modelManager.addLesson(l2);

        Predicate<Lesson> onlyL1 = lesson -> lesson.equals(l1);
        modelManager.updateFilteredLessonList(onlyL1);

        assertEquals(1, modelManager.getFilteredLessonList().size());
        assertTrue(modelManager.getFilteredLessonList().contains(l1));
    }

    @Test
    public void updateFilteredLessonList_showAll_restoresAllLessons() {
        Lesson l1 = TypicalLessons.MATH_LESSON;
        Lesson l2 = TypicalLessons.SCIENCE_LESSON;
        modelManager.addLesson(l1);
        modelManager.addLesson(l2);

        modelManager.updateFilteredLessonList(lesson -> false);
        assertEquals(0, modelManager.getFilteredLessonList().size());

        modelManager.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        assertEquals(2, modelManager.getFilteredLessonList().size());
        assertTrue(modelManager.getFilteredLessonList().contains(l1));
        assertTrue(modelManager.getFilteredLessonList().contains(l2));
    }

    @Test
    public void setLesson_lessonWithAssignedStudents_updatesStudentsLessonSets() {
        // Create a new lesson instance
        Lesson originalLesson = new seedu.address.testutil.LessonBuilder()
                .withLessonId(5001)
                .withDay("MON")
                .withStartTime("1400")
                .withEndTime("1600")
                .withVenue("Test Venue")
                .withNote("Test Note")
                .build();
        modelManager.addLesson(originalLesson);

        // Create new student instances
        Person testAlice = new seedu.address.testutil.PersonBuilder()
                .withUserId(6001)
                .withName("Test Alice")
                .withPhone("91234567")
                .withEmail("testalice@test.com")
                .withNote("Test student")
                .build();
        Person testBenson = new seedu.address.testutil.PersonBuilder()
                .withUserId(6002)
                .withName("Test Benson")
                .withPhone("92345678")
                .withEmail("testbenson@test.com")
                .withNote("Another test student")
                .build();

        modelManager.addPerson(testAlice);
        modelManager.addPerson(testBenson);

        // Get the actual person objects from the model
        seedu.address.model.person.Person actualAlice = modelManager.getFilteredPersonList().stream()
                .filter(p -> p.isSamePerson(testAlice))
                .findFirst()
                .get();
        seedu.address.model.person.Person actualBenson = modelManager.getFilteredPersonList().stream()
                .filter(p -> p.isSamePerson(testBenson))
                .findFirst()
                .get();

        // Get the actual lesson object from the model
        Lesson actualOriginalLesson = modelManager.getFilteredLessonList().stream()
                .filter(l -> l.isSameLesson(originalLesson))
                .findFirst()
                .get();

        // Assign students to the lesson
        modelManager.assign(actualAlice, actualOriginalLesson);
        modelManager.assign(actualBenson, actualOriginalLesson);

        // Verify students have the original lesson
        assertTrue(actualAlice.hasLesson(actualOriginalLesson));
        assertTrue(actualBenson.hasLesson(actualOriginalLesson));

        // Create an edited version of the lesson with different details
        Lesson editedLesson = new seedu.address.testutil.LessonBuilder()
                .withLessonId(actualOriginalLesson.getLessonId().value)
                .withDay("TUE")
                .withStartTime("1500")
                .withEndTime("1700")
                .withVenue("New Venue")
                .withNote("New Note")
                .withStudents(actualAlice, actualBenson)
                .build();

        // Edit the lesson
        modelManager.setLesson(actualOriginalLesson, editedLesson);

        // Verify students no longer have the original lesson
        assertFalse(actualAlice.hasLesson(actualOriginalLesson));
        assertFalse(actualBenson.hasLesson(actualOriginalLesson));

        // Verify students now have the edited lesson
        assertTrue(actualAlice.hasLesson(editedLesson));
        assertTrue(actualBenson.hasLesson(editedLesson));

        // Verify the lesson in the model is updated (check by lesson details since both have same ID)
        Lesson lessonInModel = modelManager.getFilteredLessonList().stream()
                .filter(l -> l.getLessonId().equals(editedLesson.getLessonId()))
                .findFirst()
                .get();
        assertEquals(editedLesson.getDay(), lessonInModel.getDay());
        assertEquals(editedLesson.getVenue(), lessonInModel.getVenue());
        assertEquals(editedLesson.getNote(), lessonInModel.getNote());
    }

    @Test
    public void setPerson_personWithAssignedLessons_updatesLessonStudentSetsAndTriggersUiRefresh() {
        // Create fresh lessons to avoid test interference
        Lesson lesson1 = new seedu.address.testutil.LessonBuilder()
                .withLessonId(9001)
                .withDay("WED")
                .withStartTime("0900")
                .withEndTime("1100")
                .withVenue("Venue 1")
                .withNote("Lesson 1")
                .build();
        Lesson lesson2 = new seedu.address.testutil.LessonBuilder()
                .withLessonId(9002)
                .withDay("THU")
                .withStartTime("1400")
                .withEndTime("1600")
                .withVenue("Venue 2")
                .withNote("Lesson 2")
                .build();

        modelManager.addLesson(lesson1);
        modelManager.addLesson(lesson2);

        // Create fresh person to avoid test interference
        seedu.address.model.person.Person originalPerson = new seedu.address.testutil.PersonBuilder()
                .withUserId(8001)
                .withName("Original Name")
                .withPhone("81234567")
                .withEmail("original@test.com")
                .withNote("Original note")
                .build();

        modelManager.addPerson(originalPerson);

        // Get actual objects from model
        seedu.address.model.person.Person actualPerson = modelManager.getFilteredPersonList().stream()
                .filter(p -> p.getUserId().value == 8001)
                .findFirst()
                .get();
        Lesson actualLesson1 = modelManager.getFilteredLessonList().stream()
                .filter(l -> l.getLessonId().value == 9001)
                .findFirst()
                .get();
        Lesson actualLesson2 = modelManager.getFilteredLessonList().stream()
                .filter(l -> l.getLessonId().value == 9002)
                .findFirst()
                .get();

        // Assign person to lessons
        modelManager.assign(actualPerson, actualLesson1);
        modelManager.assign(actualPerson, actualLesson2);

        // Verify initial state
        assertTrue(actualPerson.hasLesson(actualLesson1));
        assertTrue(actualPerson.hasLesson(actualLesson2));
        assertTrue(actualLesson1.hasStudent(actualPerson));
        assertTrue(actualLesson2.hasStudent(actualPerson));

        // Create edited person with different attributes
        seedu.address.model.person.Person editedPerson = new seedu.address.testutil.PersonBuilder()
                .withUserId(8001)
                .withName("Edited Name")
                .withPhone("89999999")
                .withEmail("edited@test.com")
                .withNote("Edited note")
                .withLessons(actualLesson1, actualLesson2)
                .build();

        // Execute setPerson - this should trigger setLesson for UI refresh
        modelManager.setPerson(actualPerson, editedPerson);

        // Verify lessons' student sets are updated with new person instance
        Lesson lesson1InModel = modelManager.getFilteredLessonList().stream()
                .filter(l -> l.getLessonId().value == 9001)
                .findFirst()
                .get();
        Lesson lesson2InModel = modelManager.getFilteredLessonList().stream()
                .filter(l -> l.getLessonId().value == 9002)
                .findFirst()
                .get();

        // The lessons should not have the old person anymore
        assertFalse(lesson1InModel.hasStudent(actualPerson));
        assertFalse(lesson2InModel.hasStudent(actualPerson));

        // The lessons should have the new edited person
        assertTrue(lesson1InModel.hasStudent(editedPerson));
        assertTrue(lesson2InModel.hasStudent(editedPerson));

        // Verify person in model is updated
        seedu.address.model.person.Person personInModel = modelManager.getFilteredPersonList().stream()
                .filter(p -> p.getUserId().value == 8001)
                .findFirst()
                .get();
        assertEquals(editedPerson.getName(), personInModel.getName());
        assertEquals(editedPerson.getPhone(), personInModel.getPhone());
        assertEquals(editedPerson.getEmail(), personInModel.getEmail());
        assertEquals(editedPerson.getNote(), personInModel.getNote());
    }

    @Test
    public void setPerson_personWithNoAssignedLessons_updatesPersonSuccessfully() {
        // Create a person with no lessons
        Person originalPerson = new seedu.address.testutil.PersonBuilder()
                .withUserId(8002)
                .withName("No Lessons Person")
                .withPhone("82345678")
                .withEmail("nolessons@test.com")
                .withNote("Has no lessons")
                .build();

        modelManager.addPerson(originalPerson);

        Person actualPerson = modelManager.getFilteredPersonList().stream()
                .filter(p -> p.getUserId().value == 8002)
                .findFirst()
                .get();

        // Create edited person
        Person editedPerson = new seedu.address.testutil.PersonBuilder()
                .withUserId(8002)
                .withName("Updated Name")
                .withPhone("89876543")
                .withEmail("updated@test.com")
                .withNote("Still no lessons")
                .build();

        // Execute setPerson - should handle empty lesson set gracefully
        modelManager.setPerson(actualPerson, editedPerson);

        // Verify person is updated
        Person personInModel = modelManager.getFilteredPersonList().stream()
                .filter(p -> p.getUserId().value == 8002)
                .findFirst()
                .get();
        assertEquals(editedPerson.getName(), personInModel.getName());
        assertEquals(editedPerson.getPhone(), personInModel.getPhone());
        assertEquals(0, personInModel.getLessons().size());
    }
}
