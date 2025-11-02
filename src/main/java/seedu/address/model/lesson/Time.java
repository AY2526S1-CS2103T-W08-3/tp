package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Time instance
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Time should be in 24-hour format HHMM (e.g. 0930)";

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    public final String value;
    private final LocalTime time;

    /**
     * Constructs a {@code Time}
     *
     * @param time A valid time string in HHMM format.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalTime.parse(time, INPUT_FORMATTER);
        this.value = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        try {
            LocalTime.parse(test, INPUT_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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

        // instanceof handles nulls
        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;
        return time.equals(otherTime.time);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns true if this Time is before or equal to the other Time.
     */
    public boolean isBeforeandEquals(Time other) {
        requireNonNull(other);
        return time.isBefore(other.time) || time.equals(other.time);
    }
}
