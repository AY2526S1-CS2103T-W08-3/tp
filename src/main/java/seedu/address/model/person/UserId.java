package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.model.person.exceptions.UserIdNotInitialisedException;

/**
 * Represents a Person's unique user ID in the address book.
 * Guarantees: immutable; is always valid (non-null).
 */
public class UserId {
    private static int MAX_USER_ID = -1;
    public final Integer value;

    /**
     * Constructs a {@code UserId} with the specified integer value.
     * For copying of already generated Persons.
     *
     * @param userId A valid user ID integer. Must not be null.
     * @throws NullPointerException if {@code userId} is null.
     */
    public UserId(Integer userId) {
        requireNonNull(userId);
        value = userId;
    }

    /**
     * Constructs a {@code UserId} with a randomly generated 16-bit integer value.
     * For generating new Persons.
     */
    public UserId() {
        if (MAX_USER_ID < 0) {
            throw new UserIdNotInitialisedException();
        }
        this.value = MAX_USER_ID;
        MAX_USER_ID += 1;
    }

    /**
     * Sets the integer value of the static field MAX_USER_ID.
     *
     * @param userId The user ID value as an Integer.
     */
    public static void setMaxUserId(Integer userId) {
        if (userId < 0) {
            throw new IllegalArgumentException("Max User ID cannot be negative.");
        }
        MAX_USER_ID = userId;
    }

    /**
     * Gets the integer value of the static field MAX_USER_ID.
     *
     * @return The Max User ID value as an Integer.
     */
    public static Integer getMaxUserId() {
        return MAX_USER_ID;
    }

    /**
     * Returns the integer value of this user ID.
     *
     * @return The user ID value as an Integer.
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Returns the string representation of this user ID.
     *
     * @return The user ID value as a String
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Checks if this user ID is equal to another object.
     * Two user IDs are considered equal if they have the same value.
     *
     * @param other The object to compare with.
     * @return {@code true} if the other object is a UserId with the same value, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UserId
                        && value.equals(((UserId) other).value));
    }

    /**
     * Returns the hash code of this user ID.
     *
     * @return The hash code based on the user ID value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
