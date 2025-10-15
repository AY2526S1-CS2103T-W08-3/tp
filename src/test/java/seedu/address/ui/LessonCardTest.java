package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.note.Note;

/**
 * Tests for {@link LessonCard} constructor behavior.
 */
public class LessonCardTest {

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
    void constructor_setsAllLabels() {
        Lesson lesson = new Lesson(
                new LessonId(405417),
                Day.MON,
                new Time("0900"),
                new Time("1000"),
                new Venue("LT1"),
                new Note("note")
        );

        LessonCard card = onFx(() -> new LessonCard(lesson, 1));

        Node root = card.getRoot();
        Label id = (Label) root.lookup("#id");
        Label lessonId = (Label) root.lookup("#lessonId");
        Label day = (Label) root.lookup("#day");
        Label start = (Label) root.lookup("#startTime");
        Label end = (Label) root.lookup("#endTime");
        Label venue = (Label) root.lookup("#venue");
        Label note = (Label) root.lookup("#note");

        assertEquals("1. ", id.getText());
        assertEquals("405417", lessonId.getText());
        assertEquals("MON", day.getText());
        assertEquals("0900", start.getText());
        assertEquals("1000", end.getText());
        assertEquals("LT1", venue.getText());
        assertEquals("note", note.getText());
    }
}
