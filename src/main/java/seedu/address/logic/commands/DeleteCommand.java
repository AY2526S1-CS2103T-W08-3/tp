package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordPredicate;

/**
 * Deletes a person identified by name using it's displayed index from the filtered name list of the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a person identified by name using it's displayed index from the filtered name list.\n"
            + "Parameters: NAME, INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " Bob " + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_LIST_PERSONS_WITH_NAME = "Here are a list of persons "
            + "containing: \"%s\". Enter \"delete %s {i}\" to delete the i'th person in this list.";

    private final Name name;
    private final Index targetIndex; // null if no target index provided to delete

    /**
     * Creates a DeleteCommand to delete the specified {@code Person}
     */
    public DeleteCommand(Name name, Index targetIndex) {
        this.name = name;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(name);

        model.updateFilteredPersonList(new NameContainsKeywordPredicate(name.toString()));
        List<Person> lastShownList = model.getFilteredPersonList();

        // List is empty
        if (lastShownList.isEmpty()) {
            return new CommandResult(String.format(Messages.MESSAGE_NO_USERS_FOUND, name));
        }

        // No delete index provided, display list and message without deleting
        if (targetIndex == null) {
            return new CommandResult(String.format(MESSAGE_LIST_PERSONS_WITH_NAME, name, name));
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;

        return (Objects.equals(targetIndex, otherDeleteCommand.targetIndex))
                && name.equals(otherDeleteCommand.name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
