package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Lesson;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class LessonCard extends UiPart<Region> {

    private static final String FXML = "LessonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Lesson lesson;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label lessonId;
    @FXML
    private Label day;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label venue;
    @FXML
    private Label note;
    @FXML
    private FlowPane students;

    /**
     * Creates a {@code LessonCode} with the given {@code Lesson} and index to display.
     */
    public LessonCard(Lesson lesson, int displayedIndex) {
        super(FXML);
        this.lesson = lesson;
        id.setText(displayedIndex + ". ");
        lessonId.setText(lesson.getLessonId().toString());
        day.setText(lesson.getDay().toString());
        startTime.setText(lesson.getStartTime().toString());
        endTime.setText(lesson.getEndTime().toString());
        venue.setText(lesson.getVenue().value);
        note.setText(lesson.getNote().value);
        lesson.getStudents().stream()
                .sorted(Comparator.comparing(
                    student -> student.getUserId().value))
                .forEach(student -> students.getChildren().add(
                    new Label(student.getName().toString())));
    }
}
