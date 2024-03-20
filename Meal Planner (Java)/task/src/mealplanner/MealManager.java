package mealplanner;

import java.util.ArrayList;

public class MealManager {
    private final ArrayList<Category> categories = new ArrayList<>();

    public void addMeal(String category) {
        Category cat = new Category(category);
        categories.add(cat);
        cat.addMeal();
    }

    public void showMeals() {
        for (Category category : categories) {
            category.showMeal();
        }
    }

    public boolean hasMeals() {
        for (Category cat : categories) {
            if (!cat.getMeals().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}