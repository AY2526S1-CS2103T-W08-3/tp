package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.TypicalLessons;

public class LessonCardTest extends FxTestBase {

    @Test
    public void constructor_populatesFields() {
        Lesson lesson = TypicalLessons.MATH_LESSON;

        LessonCard card = callOnFx(() -> new LessonCard(lesson, 1));
        Node root = card.getRoot();

        assertNotNull(root);
        HBox cardPane = (HBox) root.lookup("#cardPane");
        assertNotNull(cardPane);

        assertEquals("1. ", ((Label) root.lookup("#id")).getText());
        assertEquals(lesson.getLessonId().toString(), ((Label) root.lookup("#lessonId")).getText());
        assertEquals(lesson.getDay().toString(), ((Label) root.lookup("#day")).getText());
        assertEquals(lesson.getStartTime().toString(), ((Label) root.lookup("#startTime")).getText());
        assertEquals(lesson.getEndTime().toString(), ((Label) root.lookup("#endTime")).getText());
        assertEquals(lesson.getVenue().value, ((Label) root.lookup("#venue")).getText());
        assertEquals(lesson.getNote().value, ((Label) root.lookup("#note")).getText());
    }
}
