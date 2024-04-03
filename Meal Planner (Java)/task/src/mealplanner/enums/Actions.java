package mealplanner.enums;

public enum Actions {
    ADD,
    SHOW,
    PLAN,
    SAVE,
    EXIT;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public static boolean contains(String test) {
        for (Actions action : Actions.values()) {
            if (action.toString().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }
}
