package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UserId;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Set<Lesson> EMPTY_LESSON_SET = new HashSet<>();

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new UserId(2103), new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Note("Struggling with calculus, needs extra help"), EMPTY_LESSON_SET,
                getTagSet("friends")),
            new Person(new UserId(2103), new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Note("Excellent progress in algebra"), EMPTY_LESSON_SET,
                getTagSet("colleagues", "friends")),
            new Person(new UserId(2103), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Note("Prefers evening classes"), EMPTY_LESSON_SET,
                getTagSet("neighbours")),
            new Person(new UserId(2103), new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Note("Preparing for final exams"), EMPTY_LESSON_SET,
                getTagSet("family")),
            new Person(new UserId(2103), new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new Note("Interested in advanced topics"), EMPTY_LESSON_SET,
                getTagSet("classmates")),
            new Person(new UserId(2103), new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"),
                new Note("Needs help with essay writing"), EMPTY_LESSON_SET,
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }


    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
