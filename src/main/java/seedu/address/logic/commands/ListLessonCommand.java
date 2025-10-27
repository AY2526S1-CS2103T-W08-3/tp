package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LESSONS;

import seedu.address.model.Model;

/**
 * Lists all lessons in the address book to the user.
 */
public class ListLessonCommand extends Command {

    public static final String COMMAND_WORD = "listlesson";

    public static final String MESSAGE_SUCCESS = "Listed all lessons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        model.setDisplayedListToLessons();
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
