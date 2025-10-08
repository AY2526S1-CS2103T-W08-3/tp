package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's unique user ID in the address book.
 * Guarantees: immutable; is always valid (non-null).
 */
public class UserId {
    /** The integer value of the user ID. */
    public final Integer value;
    public static final int USER_ID_LENGTH = 4;

    /**
     * Constructs a {@code UserId} with the specified integer value.
     *
     * @param userId A valid user ID integer. Must not be null.
     * @throws NullPointerException if {@code userId} is null.
     */
    public UserId(Integer userId) {
        requireNonNull(userId);
        value = userId;
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
     * @return The user ID value as a padded 4-digit String.
     */
    @Override
    public String toString() {
        int len = value.toString().length();
        int zerosToPad = USER_ID_LENGTH - len;
        return "0".repeat(Math.max(0, zerosToPad)) + value;
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
