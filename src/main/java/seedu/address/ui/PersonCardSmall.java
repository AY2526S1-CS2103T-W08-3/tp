package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * A compact UI component that displays basic information of a {@code Person} in a card format.
 * This is used within LessonCard to show students assigned to a lesson.
 */
public class PersonCardSmall extends UiPart<Region> {

    private static final String FXML = "PersonCardSmall.fxml";

    public final Person person;

    @FXML
    private VBox personCardSmall;
    @FXML
    private Label userId;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label note;

    /**
     * Creates a {@code PersonCardSmall} with the given {@code Person}.
     */
    public PersonCardSmall(Person person) {
        super(FXML);
        this.person = person;
        userId.setText("ID: " + person.getUserId().toString());
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        note.setText(person.getNote().value);
    }
}
