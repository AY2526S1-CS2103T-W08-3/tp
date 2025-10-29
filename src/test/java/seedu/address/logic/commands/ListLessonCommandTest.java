package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

/**
 * Unit tests for {@link ListLessonCommand}.
 */
public class ListLessonCommandTest {

    @Test
    void execute_updatesFilteredListAndReturnsSuccess() throws Exception {
        ModelSpy model = new ModelSpy();
        ListLessonCommand cmd = new ListLessonCommand();

        CommandResult result = cmd.execute(model);

        assertEquals(ListLessonCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        assertTrue(result.isShowLessons());
        assertEquals(false, result.isShowHelp());
        assertEquals(false, result.isExit());

        assertTrue(model.updateLessonsCalled, "updateFilteredLessonList should be called");
    }

    @Test
    void execute_setsDisplayedStateToLessons() throws Exception {
        Model realModel = new seedu.address.model.ModelManager();
        ListLessonCommand cmd = new ListLessonCommand();
        CommandResult result = cmd.execute(realModel);
        assertTrue(result.isShowLessons());
        assertTrue(realModel.isLessonsDisplayed());
    }

    private static class ModelSpy implements Model {
        private boolean updateLessonsCalled = false;

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            updateLessonsCalled = true;
        }

        @Override public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError();
        }
        @Override public ReadOnlyUserPrefs getUserPrefs() {
            return new UserPrefs();
        }
        @Override public GuiSettings getGuiSettings() {
            return new GuiSettings();
        }
        @Override public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError();
        }
        @Override public Path getAddressBookFilePath() {
            throw new AssertionError();
        }
        @Override public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError();
        }
        @Override public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError();
        }
        @Override public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
        @Override public boolean hasPerson(Person person) {
            throw new AssertionError();
        }
        @Override public void deletePerson(Person target) {
            throw new AssertionError();
        }
        @Override public void addPerson(Person person) {
            throw new AssertionError();
        }
        @Override public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError();
        }
        @Override public void addLesson(Lesson lesson) {
            throw new AssertionError();
        }
        @Override public boolean hasLesson(Lesson lesson) {
            throw new AssertionError();
        }
        @Override public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }
        @Override public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError();
        }
        @Override public ObservableList<Lesson> getFilteredLessonList() {
            return FXCollections.observableArrayList();
        }
        @Override public void deleteLesson(Lesson target) {
            throw new AssertionError();
        }
        @Override public void setLesson(Lesson target, Lesson editedLesson) {
            throw new AssertionError();
        }
        @Override public void assign(Person student, Lesson lesson) {
            throw new AssertionError();
        }
        @Override public void unassign(Person student, Lesson lesson) {
            throw new AssertionError();
        }
        @Override public void refreshLists() {
            throw new AssertionError();
        }

        @Override public void setDisplayedListToPersons() { }
        @Override public void setDisplayedListToLessons() { }
        @Override public boolean isPersonsDisplayed() {
            return false;
        }
        @Override public boolean isLessonsDisplayed() {
            return true;
        }
    }
}
