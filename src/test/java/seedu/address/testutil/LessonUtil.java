package seedu.address.testutil;


import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.model.lesson.Lesson;

/**
 * A utility class for Person.
 */
public class LessonUtil {

    /**
     * Returns an add command string for adding the {@code lesson}.
     */
    public static String getAddLessonCommand(Lesson lesson) {
        return AddLessonCommand.COMMAND_WORD + " " + getLessonDetails(lesson);
    }

    /**
     * Returns the part of command string for the given {@code lesson}'s details.
     */
    public static String getLessonDetails(Lesson lesson) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DAY).append(lesson.getDay()).append(" ");
        sb.append(PREFIX_START_TIME).append(lesson.getStartTime()).append(" ");
        sb.append(PREFIX_END_TIME).append(lesson.getEndTime()).append(" ");
        sb.append(PREFIX_VENUE).append(lesson.getVenue()).append(" ");
        sb.append(PREFIX_LESSON_NOTE).append(lesson.getNote()).append(" ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditLessonDescriptor}'s details.
     */
    public static String getEditLessonDescriptorDetails(EditLessonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDay().ifPresent(day -> sb.append(PREFIX_DAY).append(day).append(" "));
        descriptor.getStartTime().ifPresent(start -> sb.append(PREFIX_START_TIME).append(start).append(" "));
        descriptor.getEndTime().ifPresent(end -> sb.append(PREFIX_END_TIME).append(end).append(" "));
        descriptor.getVenue().ifPresent(venue -> sb.append(PREFIX_VENUE).append(venue).append(" "));
        descriptor.getNote().ifPresent(note -> sb.append(PREFIX_LESSON_NOTE).append(note).append(" "));
        return sb.toString();
    }
}
