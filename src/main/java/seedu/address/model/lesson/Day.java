package seedu.address.model.lesson;

/**
 * Represents a day of the week for a lesson
 */
public enum Day {
    MON, TUE, WED, THU, FRI, SAT, SUN;

    public static final String MESSAGE_CONSTRAINTS =
            "Day should be one of: Mon, Tue, Wed, Thu, Fri, Sat, Sun (case-insensitive)";

    /**
     * Returns true if a given string is a valid day
     */
    public static boolean isValidDay(String test) {
        if (test == null) {
            return false;
        }
        try {
            Day.valueOf(test.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Converts a string to a Day enum (case-insensitive)
     * @throws IllegalArgumentException if the string is not a valid day
     */
    public static Day fromString(String day) {
        return Day.valueOf(day.toUpperCase());
    }
}
