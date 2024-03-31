package mealplanner;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MealManager {
    private final MealPlannerJDBC jdbc = new MealPlannerJDBC();

    public void addMeal(String category) {
        Category cat = new Category(category);
        cat.addMeal();
    }

    public void showMeals() {
        jdbc.showMeals();
    }

    public boolean hasMeals() {
        try(Connection con = jdbc.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM meals")) {
            return stmt.executeQuery().next();
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database: " + e.getMessage());
        }
    }
}