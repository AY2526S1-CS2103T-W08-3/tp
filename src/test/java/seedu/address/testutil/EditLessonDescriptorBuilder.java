package seedu.address.testutil;

import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.Venue;
import seedu.address.model.note.Note;

/**
 * A utility class to help with building EditLessonDescriptor objects.
 */
public class EditLessonDescriptorBuilder {

    private EditLessonDescriptor descriptor;

    public EditLessonDescriptorBuilder() {
        descriptor = new EditLessonDescriptor();
    }

    public EditLessonDescriptorBuilder(EditLessonDescriptor descriptor) {
        this.descriptor = new EditLessonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLessonDescriptor} with fields containing {@code lesson}'s details.
     */
    public EditLessonDescriptorBuilder(Lesson lesson) {
        descriptor = new EditLessonDescriptor();
        descriptor.setDay(lesson.getDay());
        descriptor.setStartTime(lesson.getStartTime());
        descriptor.setEndTime(lesson.getEndTime());
        descriptor.setVenue(lesson.getVenue());
        descriptor.setNote(lesson.getNote());
    }

    /**
     * Sets the {@code Day} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withDay(String day) {
        if (Day.isValidDay(day)) {
            descriptor.setDay(Day.fromString(day));
        }
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new Time(startTime));
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withEndTime(String endTime) {
        descriptor.setEndTime(new Time(endTime));
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withVenue(String venue) {
        descriptor.setVenue(new Venue(venue));
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    public EditLessonDescriptor build() {
        return descriptor;
    }
}
