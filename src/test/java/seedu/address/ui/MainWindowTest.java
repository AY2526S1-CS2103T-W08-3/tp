package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

public class MainWindowTest {

    private Stage stage;
    private Logic logic;
    private MainWindow mainWindow;

    private static class FakeLogic implements Logic {
        @Override public javafx.collections.ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }
        @Override public javafx.collections.ObservableList<Lesson> getFilteredLessonList() {
            return FXCollections.observableArrayList();
        }
        @Override public GuiSettings getGuiSettings() {
            return new GuiSettings(600, 800, 100, 120);
        }
        @Override public void setGuiSettings(GuiSettings guiSettings) { }
        @Override public ReadOnlyAddressBook getAddressBook() {
            return null;
        }
        @Override public java.nio.file.Path getAddressBookFilePath() {
            return Paths.get("dummy.json");
        }
        @Override public CommandResult execute(String commandText) throws CommandException, ParseException {
            throw new UnsupportedOperationException("Not used in these tests");
        }
    }

    @BeforeAll
    static void initJavaFx() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            /* already started */
        }
    }

    @BeforeEach
    void setup() throws Exception {
        logic = new FakeLogic();
        stage = onFxGet(Stage::new);
        mainWindow = onFxGet(() -> new MainWindow(stage, logic));
    }

    private interface FxSupplier<T> { T get() throws Exception; }

    private static <T> T onFxGet(FxSupplier<T> sup) throws Exception {
        var f = new CompletableFuture<T>();
        Platform.runLater(() -> {
            try {
                f.complete(sup.get());
            } catch (Throwable t) {
                f.completeExceptionally(t);
            }
        });
        return f.get();
    }

    private static void onFxRun(Runnable r) throws Exception {
        var f = new CompletableFuture<Void>();
        Platform.runLater(() -> {
            try {
                r.run();
                f.complete(null);
            } catch (Throwable t) {
                f.completeExceptionally(t);
            }
        });
        f.get();
    }

    private static void invokeNoArgs(Object target, String methodName) {
        try {
            Method m = target.getClass().getDeclaredMethod(methodName);
            m.setAccessible(true);
            m.invoke(target);
        } catch (Exception e) {
            throw new AssertionError("Failed to invoke " + methodName + ": " + e.getMessage(), e);
        }
    }

    private static <T> T getField(Object target, String name, Class<T> type) {
        try {
            var f = target.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return type.cast(f.get(target));
        } catch (Exception e) {
            throw new AssertionError("Failed to read field " + name + ": " + e.getMessage(), e);
        }
    }

    @Test
    void fillInnerParts_defaultsToPersonsPanel() throws Exception {
        onFxRun(() -> mainWindow.fillInnerParts());

        StackPane listPlaceholder = getField(mainWindow, "listPanelPlaceholder", StackPane.class);
        PersonListPanel personPanel = getField(mainWindow, "personListPanel", PersonListPanel.class);

        assertEquals(1, listPlaceholder.getChildren().size());
        Node shown = listPlaceholder.getChildren().get(0);
        assertSame(personPanel.getRoot(), shown, "Default view should be persons panel");
    }

    @Test
    void showLessonsPanelandshowPersonsPanel_swapContent() throws Exception {
        onFxRun(() -> mainWindow.fillInnerParts());

        StackPane listPlaceholder = getField(mainWindow, "listPanelPlaceholder", StackPane.class);
        LessonListPanel lessonPanel = getField(mainWindow, "lessonListPanel", LessonListPanel.class);
        PersonListPanel personPanel = getField(mainWindow, "personListPanel", PersonListPanel.class);

        onFxRun(() -> invokeNoArgs(mainWindow, "showLessonsPanel"));
        assertSame(lessonPanel.getRoot(), listPlaceholder.getChildren().get(0),
                "showLessonsPanel should display the lesson list");

        onFxRun(() -> invokeNoArgs(mainWindow, "showPersonsPanel"));
        assertSame(personPanel.getRoot(), listPlaceholder.getChildren().get(0),
                "showPersonsPanel should display the person list");
    }
}
