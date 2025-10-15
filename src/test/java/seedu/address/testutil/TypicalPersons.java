package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NOTE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Name DUPLICATE_NAME = new Name("Daniel");
    public static final Index INDEX_DUPLICATE_NAME = Index.fromOneBased(4);
    public static final Name UNIQUE_NAME = new Name("Benson");

    public static final Person ALICE = new PersonBuilder()
            .withUserId(1)
            .withName("Alice Pauline")
            .withPhone("94351253")
            .withEmail("alice@example.com")
            .withNote("Attentive student, submits homework on time")
            .withTags("friends")
            .build();
    public static final Person BENSON = new PersonBuilder()
            .withUserId(2)
            .withName("Benson Meier")
            .withPhone("98765432")
            .withEmail("johnd@example.com")
            .withNote("Needs to improve attendance")
            .withTags("owesMoney", "friends")
            .build();
    public static final Person CARL = new PersonBuilder()
            .withUserId(3)
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withNote("Talented in physics")
            .build();
    public static final Person DANIEL = new PersonBuilder()
            .withUserId(4)
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withNote("Struggles with concentration")
            .withTags("friends")
            .build();
    public static final Person ELLE = new PersonBuilder()
            .withUserId(5)
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withNote("Quick learner")
            .build();
    public static final Person FIONA = new PersonBuilder()
            .withUserId(6)
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withNote("Creative thinker")
            .build();
    public static final Person GEORGE = new PersonBuilder()
            .withUserId(7)
            .withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withNote("Athletic and active")
            .build();
    public static final Person DANIEL_2 = new PersonBuilder()
            .withUserId(8)
            .withName("Daniel Tan")
            .withPhone("94351253")
            .withEmail("daniel@example.com")
            .withNote("Attentive student, submits homework on time")
            .withTags("friends")
            .build();
    public static final Person DANIEL_3 = new PersonBuilder()
            .withUserId(9)
            .withName("Daniel Goh")
            .withPhone("94351253")
            .withEmail("daniel@example.com")
            .withNote("Attentive student, submits homework on time")
            .withTags("friends")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withUserId(10)
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withNote("Enjoys group work")
            .build();

    public static final Person IDA = new PersonBuilder()
            .withUserId(11)
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withNote("Quiet but diligent")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withNote(VALID_STUDENT_NOTE_AMY)
            .withTags(VALID_TAG_FRIEND)
            .build();

    public static final Person BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withNote(VALID_STUDENT_NOTE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_MEIER = "Meier";

    private TypicalPersons() {}

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, DANIEL_2, DANIEL_3));
    }
}
