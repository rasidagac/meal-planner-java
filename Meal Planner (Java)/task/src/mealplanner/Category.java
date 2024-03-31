package mealplanner;

import java.sql.*;

public class Category {
    private final String name;

    private final MealPlannerJDBC jdbc = new MealPlannerJDBC();

    public Category(String name) {
        this.name = name;

    }

    public void addMeal()  {
        Meal meal = new Meal();
        String[] ingredients = meal.getIngredients();
        try (ResultSet resultset = jdbc.createMeal(meal.getName(), name)) {
            while (resultset.next()) {
                int id = resultset.getInt("meal_id");

                for (String ingredient : ingredients) {
                    jdbc.createIngredient(ingredient, id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add meal: " + e.getMessage());
        }
        System.out.println("The meal has been added!");
    }
}
