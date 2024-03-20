package mealplanner;

import java.util.ArrayList;

public class Category {
    private final String name;

    private final ArrayList<Meal> meals = new ArrayList<>();

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    private void setMeals(Meal meals) {
        this.meals.add(meals);
    }

    public Category( String name) {
        this.name = name;
    }

    public void addMeal() {
        setMeals(new Meal());
        System.out.println("The meal has been added!");
    }

    public void showMeal() {
        for (Meal meal : meals) {
            System.out.println("Category: " + name);
            System.out.println("Name: " + meal.getName());
            System.out.println("Ingredients:");
            for (String ingredient : meal.getIngredients()) {
                System.out.println(ingredient);
            }
        }
    }
}
