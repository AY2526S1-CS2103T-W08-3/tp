package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.address.model.lesson.Lesson;
import seedu.address.testutil.TypicalLessons;

public class LessonListPanelTest extends FxTestBase {

    private static class TestCell extends LessonListPanel.LessonListViewCell {
        TestCell(LessonListPanel outer) {
            outer.super();
        }
        public void callUpdate(Lesson item, boolean empty) {
            super.updateItem(item, empty);
        }
    }

    @Test
    public void constructor_setsItemsAndCellFactory() {
        var list = FXCollections.observableArrayList(
                TypicalLessons.MATH_LESSON, TypicalLessons.ENGLISH_LESSON);

        LessonListPanel panel = callOnFx(() -> {
            return new LessonListPanel(list);
        });

        ListCell<Lesson> cell = callOnFx(() -> {
            ListView<Lesson> probe = new ListView<>();
            probe.setCellFactory(lv -> panel.new LessonListViewCell());
            return probe.getCellFactory().call(probe);
        });

        assertNotNull(cell);
    }

    @Test
    public void updateItem_nullOrEmpty_clearsGraphic() {
        LessonListPanel panel = callOnFx(() -> {
            return new LessonListPanel(FXCollections.observableArrayList());
        });

        TestCell cell = callOnFx(() -> {
            return new TestCell(panel);
        });

        runOnFx(() -> {
            cell.callUpdate(null, true);
        });

        assertEquals(null, cell.getGraphic());
        assertEquals(null, cell.getText());
    }

    @Test
    public void updateItem_withLesson_setsGraphic() {
        Lesson lesson = TypicalLessons.MATH_LESSON;

        LessonListPanel panel = callOnFx(() -> {
            return new LessonListPanel(FXCollections.observableArrayList(lesson));
        });

        TestCell cell = callOnFx(() -> {
            return new TestCell(panel);
        });

        runOnFx(() -> {
            cell.callUpdate(lesson, false);
        });

        assertNotNull(cell.getGraphic());
    }
}
