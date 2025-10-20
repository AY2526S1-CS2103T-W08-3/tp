package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.lesson.Lesson;

/**
 * A compact UI component that displays basic information of a {@code Lesson} in a square card format.
 * This is used within PersonCard to show lessons assigned to a student.
 */
public class LessonCardSmall extends UiPart<Region> {

    private static final String FXML = "LessonCardSmall.fxml";

    public final Lesson lesson;

    @FXML
    private VBox lessonCardSmall;
    @FXML
    private Label lessonId;
    @FXML
    private Label day;
    @FXML
    private Label time;
    @FXML
    private Label venue;
    @FXML
    private Label note;

    /**
     * Creates a {@code LessonCardSmall} with the given {@code Lesson}.
     */
    public LessonCardSmall(Lesson lesson) {
        super(FXML);
        this.lesson = lesson;
        lessonId.setText("ID: " + lesson.getLessonId().toString());
        day.setText(lesson.getDay().toString());
        time.setText(lesson.getStartTime() + " - " + lesson.getEndTime());
        venue.setText(lesson.getVenue().value);
        note.setText(lesson.getNote().value);
    }
}
