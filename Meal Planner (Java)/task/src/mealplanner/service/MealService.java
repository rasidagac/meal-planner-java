package mealplanner.service;

import mealplanner.MealPlannerJDBC;
import mealplanner.dao.Dao;
import mealplanner.model.Meal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealService implements Dao<Meal> {
    private final MealPlannerJDBC jdbc = new MealPlannerJDBC();

    private static final String CREATE_TABLE = """
                CREATE TABLE IF NOT EXISTS meals (
                meal_id INT4 PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                meal VARCHAR,
                category VARCHAR
                )""";

    public MealService() {
        try (PreparedStatement statement = jdbc.getStatement(CREATE_TABLE))  {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table: " + e.getMessage());
        }
    }

    @Override
    public Meal add(Meal meal) {
        String insertMeal = "INSERT INTO meals (meal, category) VALUES (?, ?) RETURNING *";

        try (PreparedStatement statement = jdbc.getStatement(insertMeal)) {

            statement.setString(1, meal.getMeal());
            statement.setString(2, meal.getCategory());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Meal(resultSet.getInt("meal_id"), resultSet.getString("meal"), resultSet.getString("category"));
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to add meal: " + e.getMessage());
        }
    }

    @Override
    public void delete(int plan_id) {
        String deleteMeal = "DELETE FROM meals WHERE plan_id = ?";
        try (PreparedStatement statement = jdbc.getStatement(deleteMeal)) {

            statement.setInt(1, plan_id);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete meal: " + e.getMessage());
        }
    }

    @Override
    public void update(Meal meal) {
        String updateMeal = "UPDATE meals SET meal = ?, category = ? WHERE meal_id = ?";
        try (PreparedStatement statement = jdbc.getStatement(updateMeal)) {

            statement.setString(1, meal.getMeal());
            statement.setString(2, meal.getCategory());
            statement.setInt(3, meal.getMeal_id());
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to update plan: " + e.getMessage());
        }
    }

    @Override
    public Meal get(int meal_id) {
        String selectMeal = "SELECT * FROM meals WHERE meal_id = ?";
        try (PreparedStatement statement = jdbc.getStatement(selectMeal)) {

            statement.setInt(1, meal_id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Meal(resultSet.getString("meal"), resultSet.getString("category"));
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get Meal: " + e.getMessage());
        }
    }

    @Override
    public List<Meal> getAll(String query) {
        try (PreparedStatement statement = jdbc.getStatement(query)) {

            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();
            List<Meal> meals = new ArrayList<>();

            while (resultSet.next()) {
                meals.add(new Meal(resultSet.getInt("meal_id"), resultSet.getString("meal"), resultSet.getString("category")));
            }

            return meals;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get meals: " + e.getMessage());
        }
    }
}
