package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.MATH_LESSON;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;

public class AddLessonCommandTest {

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(null));
    }

    @Test
    public void execute_lessonAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLessonAdded modelStub = new ModelStubAcceptingLessonAdded();
        Lesson validLesson = new Lesson(
                new LessonId(1001),
                Day.MON,
                new Time("1400"),
                new Time("1600"),
                new Venue("Blk 123 Computing Dr 1"),
                new Note("Algebra basics")
        );

        CommandResult commandResult = new AddLessonCommand(validLesson).execute(modelStub);

        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, Messages.format(validLesson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validLesson), modelStub.lessonsAdded);
    }

    @Test
    public void execute_duplicateLesson_throwsCommandException() {
        Lesson validLesson = new Lesson(
                new LessonId(1001),
                Day.MON,
                new Time("1400"),
                new Time("1600"),
                new Venue("Blk 123 Computing Dr 1"),
                new Note("Algebra basics")
        );
        AddLessonCommand addLessonCommand = new AddLessonCommand(validLesson);
        ModelStub modelStub = new ModelStubWithLesson(validLesson);

        assertThrows(CommandException.class,
                AddLessonCommand.MESSAGE_DUPLICATE_LESSON, () -> addLessonCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Lesson mathLesson = new Lesson(
                new LessonId(1001),
                Day.MON,
                new Time("1400"),
                new Time("1600"),
                new Venue("Blk 123 Computing Dr 1"),
                new Note("Algebra basics")
        );
        Lesson englishLesson = new Lesson(
                new LessonId(1002),
                Day.TUE,
                new Time("1000"),
                new Time("1200"),
                new Venue("Blk 456 English Ave 2"),
                new Note("Grammar review")
        );
        AddLessonCommand addMathCommand = new AddLessonCommand(mathLesson);
        AddLessonCommand addEnglishCommand = new AddLessonCommand(englishLesson);

        // same object -> returns true
        assertTrue(addMathCommand.equals(addMathCommand));

        // same values -> returns true
        AddLessonCommand addMathCommandCopy = new AddLessonCommand(mathLesson);
        assertTrue(addMathCommand.equals(addMathCommandCopy));

        // different types -> returns false
        assertFalse(addMathCommand.equals(1));

        // null -> returns false
        assertFalse(addMathCommand.equals(null));

        // different lesson -> returns false
        assertFalse(addMathCommand.equals(addEnglishCommand));
    }

    @Test
    public void toStringMethod() {
        AddLessonCommand addLessonCommand = new AddLessonCommand(MATH_LESSON);
        String expected = AddLessonCommand.class.getCanonicalName() + "{toAdd=" + MATH_LESSON + "}";
        assertEquals(expected, addLessonCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLesson(Lesson target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLesson(Lesson target, Lesson editedLesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void assign(Person student, Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unassign(Person student, Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void refreshLists() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDisplayedListToPersons() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDisplayedListToLessons() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isPersonsDisplayed() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isLessonsDisplayed() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single lesson.
     */
    private class ModelStubWithLesson extends ModelStub {
        private final Lesson lesson;

        ModelStubWithLesson(Lesson lesson) {
            requireNonNull(lesson);
            this.lesson = lesson;
        }

        @Override
        public boolean hasLesson(Lesson lesson) {
            requireNonNull(lesson);
            return this.lesson.isSameLesson(lesson);
        }
    }

    /**
     * A Model stub that always accept the lesson being added.
     */
    private class ModelStubAcceptingLessonAdded extends ModelStub {
        final ArrayList<Lesson> lessonsAdded = new ArrayList<>();

        @Override
        public void addLesson(Lesson lesson) {
            requireNonNull(lesson);
            lessonsAdded.add(lesson);
        }

        @Override
        public boolean hasLesson(Lesson lesson) {
            requireNonNull(lesson);
            return lessonsAdded.stream().anyMatch(lesson::isSameLesson);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
