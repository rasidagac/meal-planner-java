package mealplanner.service;

import mealplanner.MealPlannerJDBC;
import mealplanner.dao.Dao;
import mealplanner.model.Ingredient;
import mealplanner.model.Meal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientService implements Dao<Ingredient> {
    private final MealPlannerJDBC jdbc = new MealPlannerJDBC();

    private static final String CREATE_TABLE = """
                CREATE TABLE IF NOT EXISTS ingredients (
                ingredient_id INT4 PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                ingredient VARCHAR,
                meal_id INT
                );""";

    public IngredientService() {
        try (PreparedStatement statement = jdbc.getStatement(CREATE_TABLE))  {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table: " + e.getMessage());
        }
    }

    @Override
    public Ingredient add(Ingredient ingredient) {
        String insertIngredient = "INSERT INTO ingredients (ingredient, meal_id) VALUES (?, ?) RETURNING *";
        try (PreparedStatement statement = jdbc.getStatement(insertIngredient)) {
            statement.setString(1, ingredient.getIngredient());
            statement.setInt(2, ingredient.getMeal_id());

            ResultSet resultSet = statement.executeQuery(); {
                if (resultSet.next()) {
                    return new Ingredient(resultSet.getInt("ingredient_id"), resultSet.getInt("meal_id"), resultSet.getString("ingredient"));
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to add ingredient: " + e.getMessage());
        }
    }

    @Override
    public void delete(int ingredient_id) {
        String deleteIngredient = "DELETE FROM ingredients WHERE ingredient_id = ?";
        try (PreparedStatement statement = jdbc.getStatement(deleteIngredient)) {

            statement.setInt(1, ingredient_id);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete ingredient: " + e.getMessage());
        }
    }

    @Override
    public void update(Ingredient ingredient) {
        String updateIngredient = "UPDATE ingredients SET ingredient = ?, meal_id = ? WHERE ingredient_id = ?";
        try (PreparedStatement statement = jdbc.getStatement(updateIngredient)) {

            statement.setString(1, ingredient.getIngredient());
            statement.setInt(2, ingredient.getMeal_id());
            statement.setInt(3, ingredient.getIngredient_id());
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to update ingredient: " + e.getMessage());
        }
    }

    @Override
    public Ingredient get(int ingredient_id) {
        String selectIngredient = "SELECT * FROM ingredients WHERE ingredient_id = ?";
        try (PreparedStatement statement = jdbc.getStatement(selectIngredient)) {

            statement.setInt(1, ingredient_id);
            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return new Ingredient(resultSet.getInt("ingredient_id"), resultSet.getInt("meal_id"), resultSet.getString("ingredient"));
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get Meal: " + e.getMessage());
        }
    }

    @Override
    public List<Ingredient> getAll(String query) {
        try (PreparedStatement statement = jdbc.getStatement(query)) {

            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();
            List<Ingredient> ingredients = new ArrayList<>();

            while (resultSet.next()) {
                ingredients.add(new Ingredient(resultSet.getInt("ingredient_id"), resultSet.getInt("meal_id"), resultSet.getString("ingredient")));
            }

            return ingredients;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get meals: " + e.getMessage());
        }
    }
}
