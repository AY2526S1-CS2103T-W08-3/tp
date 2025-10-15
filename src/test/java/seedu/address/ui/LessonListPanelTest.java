package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.TypicalLessons;

/**
 * Tests for {@link LessonListPanel}.
 */
public class LessonListPanelTest {

    @BeforeAll
    static void initJavaFx() throws Exception {
        try {
            Platform.startup(() -> { });
        } catch (IllegalStateException alreadyStarted) {
            // FX already initialized in this JVM
        }
    }

    private static <T> T onFx(Callable<T> task) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<T> out = new AtomicReference<>();
        AtomicReference<RuntimeException> re = new AtomicReference<>();
        AtomicReference<Error> er = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                out.set(task.call());
            } catch (RuntimeException x) {
                re.set(x);
            } catch (Error x) {
                er.set(x);
            } catch (Exception x) {
                re.set(new RuntimeException(x));
            } finally {
                latch.countDown();
            }
        });

        try {
            if (!latch.await(5, TimeUnit.SECONDS)) {
                throw new RuntimeException("FX task timed out");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(ie);
        }

        if (re.get() != null) {
            throw re.get();
        }
        if (er.get() != null) {
            throw er.get();
        }
        return out.get();
    }

    @Test
    void constructor_smoke_createsRoot() {
        LessonListPanel panel = onFx(() -> new LessonListPanel(FXCollections.observableArrayList()));
        assertNotNull(panel.getRoot());
    }

    @Test
    void updateItem_nullOrEmpty_clearsGraphic() {
        LessonListPanel panel = onFx(() -> new LessonListPanel(FXCollections.observableArrayList()));
        TestCell cell = onFx(() -> new TestCell(panel));

        onFx(() -> {
            cell.callUpdate(null, true);
            return null;
        });

        assertNull(cell.getGraphic());
        assertNull(cell.getText());
    }

    @Test
    void updateItem_withLesson_setsGraphic() {
        Lesson lesson = TypicalLessons.MATH_LESSON;
        LessonListPanel panel = onFx(() -> new LessonListPanel(FXCollections.observableArrayList(lesson)));
        TestCell cell = onFx(() -> new TestCell(panel));

        onFx(() -> {
            cell.callUpdate(lesson, false);
            return null;
        });

        assertNotNull(cell.getGraphic());
        assertNull(cell.getText());
    }

    /** Test helper subclass to expose protected updateItem(...) */
    static class TestCell extends LessonListPanel.LessonListViewCell {
        TestCell(LessonListPanel panel) {
            panel.super();
        }
        void callUpdate(Lesson lesson, boolean empty) {
            super.updateItem(lesson, empty);
        }
    }

}
