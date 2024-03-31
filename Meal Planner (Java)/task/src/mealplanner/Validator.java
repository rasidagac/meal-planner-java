package mealplanner;

import java.util.Arrays;
import java.util.List;

public class Validator {

    static final String validRegex = "^[a-zA-Z ]+$";
    static final List<String> validActions = Arrays.asList("add", "show", "exit");
    static final List<String> validCategories = Arrays.asList("breakfast", "lunch", "dinner");
    public static boolean validateCategory(String category) {
        return validCategories.contains(category);
    }

    public static boolean validateAction(String action) {
        return validActions.contains(action);
    }

    public static boolean validateName(String name) {
        return !name.isEmpty() && name.matches(validRegex);
    }

    public static boolean validateIngredients(String[] ingredients) {
        for (String ingredient : ingredients) {
            if (!ingredient.matches(validRegex) || ingredient.trim().isEmpty()){
                return false;
            }
        }
        return true;
    }
}
