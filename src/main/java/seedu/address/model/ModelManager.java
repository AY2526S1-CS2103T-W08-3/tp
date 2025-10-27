package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.person.Person;
import seedu.address.model.person.UserId;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Lesson> filteredLessons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredLessons = new FilteredList<>(this.addressBook.getLessonList());

        // Important to set max IDs to ensure ID numbers are incremented correctly
        UserId.setMaxUserId(addressBook.getInitialMaxUserId());
        LessonId.setMaxLessonId(addressBook.getInitialMaxLessonId());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        Set<Lesson> assignedLessons = target.getLessons();

        for (Lesson lesson : assignedLessons) {
            if (lesson.hasStudent(target)) {
                try {
                    lesson.replaceStudent(target, editedPerson);
                    addressBook.setLesson(lesson, lesson);
                } catch (IllegalValueException e) {
                    throw new PersonNotFoundException();
                }
            }
        }
        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void addLesson(Lesson lesson) {
        addressBook.addLesson(lesson);
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
    }

    @Override
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return addressBook.hasLesson(lesson);
    }

    @Override
    public void deleteLesson(Lesson lesson) {
        addressBook.removeLesson(lesson);
    }

    @Override
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireAllNonNull(target, editedLesson);
        addressBook.setLesson(target, editedLesson);
    }

    /**
     * Assigns a student to a lesson and vice versa.
     * Both the student and lesson must exist in the address book.
     *
     * @param student the student to assign to the lesson
     * @param lesson the lesson to assign to the student
     * @throws AssertionError if the student or lesson does not exist in the address book
     */
    public void assign(Person student, Lesson lesson) {
        if (!addressBook.hasPerson(student) || !addressBook.hasLesson(lesson)) {
            throw new AssertionError("Person or Lesson does not exist in the address book");
        }

        // Get the actual objects from the address book to ensure we modify the correct instances
        Person actualStudent = addressBook.getPersonList().stream()
                .filter(p -> p.isSamePerson(student))
                .findFirst()
                .orElse(null);

        Lesson actualLesson = addressBook.getLessonList().stream()
                .filter(l -> l.isSameLesson(lesson))
                .findFirst()
                .orElse(null);

        if (actualStudent != null && actualLesson != null) {
            actualStudent.addLesson(actualLesson);
            actualLesson.addStudent(actualStudent);
        }
    }

    /**
     * Unassigns a student from a lesson and vice versa.
     * Both the student and lesson must exist in the address book.
     *
     * @param student the student to unassign from the lesson
     * @param lesson the lesson to unassign from the student
     * @throws AssertionError if the student or lesson does not exist in the address book
     */
    public void unassign(Person student, Lesson lesson) {
        if (!addressBook.hasPerson(student) || !addressBook.hasLesson(lesson)) {
            throw new AssertionError("Person or Lesson does not exist in the address book");
        }

        // Get the actual objects from the address book to ensure we modify the correct instances
        Person actualStudent = addressBook.getPersonList().stream()
                .filter(p -> p.isSamePerson(student))
                .findFirst()
                .orElse(null);

        Lesson actualLesson = addressBook.getLessonList().stream()
                .filter(l -> l.isSameLesson(lesson))
                .findFirst()
                .orElse(null);

        if (actualStudent != null && actualLesson != null) {
            actualStudent.removeLesson(actualLesson);
            actualLesson.removeStudent(actualStudent);
        }
    }

    /**
     * Refreshes both the person and lesson lists to show all entries.
     * This is typically called after operations that modify the address book.
     */
    public void refreshLists() {
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }


    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return filteredLessons;
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireNonNull(predicate);
        filteredLessons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredLessons.equals(otherModelManager.filteredLessons);
    }

}
