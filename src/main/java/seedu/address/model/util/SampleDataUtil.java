package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonId;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.note.Note;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
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
            new Person(new UserId(0), new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new Note("Struggling with calculus, needs extra help"), EMPTY_LESSON_SET,
                getTagSet("English")),
            new Person(new UserId(1), new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Note("Excellent progress in algebra"), EMPTY_LESSON_SET,
                getTagSet("Mathematics", "Mandarin")),
            new Person(new UserId(2), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Note("Prefers evening classes"), EMPTY_LESSON_SET,
                getTagSet("Mathematics")),
            new Person(new UserId(3), new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Note("Preparing for final exams"), EMPTY_LESSON_SET,
                getTagSet("Biology")),
            new Person(new UserId(4), new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new Note("Interested in advanced topics"), EMPTY_LESSON_SET,
                getTagSet("Biology")),
            new Person(new UserId(5), new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"),
                new Note("Needs help with essay writing"), EMPTY_LESSON_SET,
                getTagSet("Biology"))
        };
    }

    public static Lesson[] getSampleLessons() {
        return new Lesson[] {
            new Lesson(new LessonId(0), Day.MON, new Time("1000"), new Time("1200"),
                new Venue("COM1-B103"), new Note("Introduction to Programming")),
            new Lesson(new LessonId(1), Day.TUE, new Time("1400"), new Time("1600"),
                new Venue("COM2-0204"), new Note("Data Structures and Algorithms")),
            new Lesson(new LessonId(2), Day.WED, new Time("0900"), new Time("1100"),
                new Venue("S16-0430"), new Note("Calculus I")),
            new Lesson(new LessonId(3), Day.THU, new Time("1500"), new Time("1700"),
                new Venue("LT19"), new Note("Software Engineering")),
            new Lesson(new LessonId(4), Day.FRI, new Time("1300"), new Time("1500"),
                new Venue("COM1-0217"), new Note("Database Systems")),
            new Lesson(new LessonId(5), Day.MON, new Time("1600"), new Time("1800"),
                new Venue("AS6-0426"), new Note("Physics for Computing"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Lesson sampleLesson : getSampleLessons()) {
            sampleAb.addLesson(sampleLesson);
        }

        // Essential for ensuring static ID fields are initialized correctly, change if sample data changes
        sampleAb.setInitialMaxUserId(6);
        sampleAb.setInitialMaxLessonId(6);
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
