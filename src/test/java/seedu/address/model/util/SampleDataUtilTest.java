package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSampleAddressBook_containsAllSamplePersons() {
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(samplePersons.length, addressBook.getPersonList().size());
    }


    @Test
    public void getTagSet_emptyArray_returnsEmptySet() {
        Set<Tag> tags = SampleDataUtil.getTagSet();
        assertNotNull(tags);
        assertTrue(tags.isEmpty());
    }

    @Test
    public void getTagSet_validTags_returnsCorrectSet() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friend", "colleague", "friend");
        assertEquals(2, tags.size());
        assertTrue(tags.contains(new Tag("friend")));
        assertTrue(tags.contains(new Tag("colleague")));
    }
}
