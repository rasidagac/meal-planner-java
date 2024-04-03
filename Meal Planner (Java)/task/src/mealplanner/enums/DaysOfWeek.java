package mealplanner.enums;

public enum DaysOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

    public static boolean contains(String test) {
        for (DaysOfWeek day : DaysOfWeek.values()) {
            if (day.toString().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }
}
