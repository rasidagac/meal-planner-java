package mealplanner;

import java.sql.*;

public class MealPlannerJDBC {
    private static final String URL = "jdbc:postgresql:meals_db";
    private static final String USER = "postgres";
    private static final String PASS = "1111";

    public PreparedStatement getStatement(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASS);

        return connection.prepareStatement(query);
    }

    public void showMeals(String category) {
        String selectMeals = "SELECT * FROM meals WHERE category = ?";
        try (PreparedStatement statement = getStatement(selectMeals)) {
             statement.setString(1, category);

             System.out.println("Category: " + category + "\n");

             ResultSet resultSet = statement.executeQuery(); {
                while (resultSet.next()) {
                    System.out.println("Name: " + resultSet.getString("meal"));
                    showIngredients(resultSet.getInt("meal_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to show meals: " + e.getMessage());
        }
    }

    public void showIngredients(int mealId) {
        String selectIngredients = "SELECT * FROM ingredients WHERE meal_id = ?";
        System.out.println("Ingredients:");
        try (PreparedStatement statement = getStatement(selectIngredients)) {
                statement.setInt(1, mealId);

             ResultSet resultSet = statement.executeQuery(); {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("ingredient"));
                }
            }
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to show ingredients: " + e.getMessage());
        }
    }


}
