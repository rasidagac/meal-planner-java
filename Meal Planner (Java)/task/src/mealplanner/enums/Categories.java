package mealplanner.enums;

public enum Categories {
    BREAKFAST,
    LUNCH,
    DINNER;

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }

    public static boolean contains(String test) {
        for (Categories day : Categories.values()) {
            if (day.toString().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }
}
