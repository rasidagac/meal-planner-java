package mealplanner;

import java.sql.*;

public class MealPlannerJDBC {
    String url = "jdbc:postgresql:meals_db";
    String user = "postgres";
    String password = "1111";

    public MealPlannerJDBC() {
        createTables();
    }

    Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Connection to database failed: " + e.getMessage());
        }
    }

    public void createTables() {
        String createMeals = """
                CREATE TABLE IF NOT EXISTS meals (
                meal_id INT4 PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                meal VARCHAR,
                category VARCHAR
                )""";

        String createIngredients = """
                CREATE TABLE IF NOT EXISTS ingredients (
                ingredient_id INT4 PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                ingredient VARCHAR,
                meal_id INT
                );""";

        try (Connection connection = getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(createMeals);
            statement.executeUpdate(createIngredients);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create tables: " + e.getMessage());
        }
    }

    public ResultSet createMeal(String meal, String category) {
        String insertMeal = "INSERT INTO meals (meal, category) VALUES (?, ?)";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(insertMeal, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, meal);
            statement.setString(2, category);
            statement.executeUpdate();

            return statement.getGeneratedKeys();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert meal: " + e.getMessage());
        }
    }

    public void createIngredient(String ingredient, int mealId) {
        String insertIngredient = "INSERT INTO ingredients (ingredient, meal_id) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(insertIngredient)) {
            statement.setString(1, ingredient);
            statement.setInt(2, mealId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert ingredient: " + e.getMessage());
        }
    }

    public void showMeals() {
        String selectMeals = "SELECT * FROM meals";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectMeals)) {
            while (resultSet.next()) {
                System.out.println("Category: " + resultSet.getString("category"));
                System.out.println("Name: " + resultSet.getString("meal"));
                showIngredients(resultSet.getInt("meal_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to show meals: " + e.getMessage());
        }
    }

    public void showIngredients(int mealId) {
        String selectIngredients = "SELECT * FROM ingredients WHERE meal_id = ?";
        System.out.println("Ingredients:");
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(selectIngredients)) {
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
