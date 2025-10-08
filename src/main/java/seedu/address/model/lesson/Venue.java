package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

public class Venue {

    public final String value;

    /**
     * Constructs a {@code Venue}
     *
     * @param venue A valid venue string.
     */
    public Venue(String venue) {
        requireNonNull(venue);
        this.value = venue;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof Venue)) {
            return false;
        }

        Venue otherVenue = (Venue) other;
        return value.equals(otherVenue.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
