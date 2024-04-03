package mealplanner;

import mealplanner.model.Plan;
import mealplanner.service.MealService;
import mealplanner.service.PlanService;

import java.util.Arrays;
import java.util.List;

public class Validator {

    private static final String validRegex = "^[a-zA-Z ]+$";

    static PlanService planService = new PlanService();

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

    public static boolean validateIsPlanned() {
        String getAllPlans = "SELECT * FROM plan";
        List<Plan> plans = planService.getAll(getAllPlans);

        return !plans.isEmpty();
    }
}
