package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

/**
 * Provides a typical AddressBook instance for testing purposes.
 */
public class TypicalAddressBook {

    /**
     * Returns an {@code AddressBook} with all the typical persons and lessons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }

        for (Lesson lesson : TypicalLessons.getTypicalLessons()) {
            ab.addLesson(lesson);
        }

        return ab;
    }
}
