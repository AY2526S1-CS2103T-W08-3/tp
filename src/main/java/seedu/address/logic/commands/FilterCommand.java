package seedu.address.logic.commands;

/**
 * Filters the list of student or lessons based on input arguments
 */
public abstract class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": filters a list of students by lesson, or a list of lessons by student.\n"
            + "filter s/NAME INDEX filters the list of lessons by the specified student, while "
            + "filter l/DAY INDEX filters the list of students by the specified lesson.\n"
            + "Omitting the INDEX parameter for either displays the referenced list of students or lessons.\n"
            + "Example: " + COMMAND_WORD + " s/John 2 or " + COMMAND_WORD + " l/Mon 2.";
}
