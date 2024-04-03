package mealplanner.service;

import mealplanner.MealPlannerJDBC;
import mealplanner.dao.Dao;
import mealplanner.model.Meal;
import mealplanner.model.Plan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanService implements Dao<Plan> {
    private final MealPlannerJDBC jdbc = new MealPlannerJDBC();

    private static final String CREATE_TABLE = """
                CREATE TABLE IF NOT EXISTS plan (
                option VARCHAR,
                category VARCHAR,
                meal_id INT,
                plan_id INT4 PRIMARY KEY GENERATED ALWAYS AS IDENTITY
                );""";

    public PlanService() {
        try (PreparedStatement statement = jdbc.getStatement(CREATE_TABLE)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table: " + e.getMessage());
        }
    }

    @Override
    public Plan add(Plan plan) {
        String insertPlan = "INSERT INTO plan (option, category, meal_id) VALUES (?, ?, ?) RETURNING *";
        try (PreparedStatement statement = jdbc.getStatement(insertPlan)) {
            statement.setString(1, plan.getOption());
            statement.setString(2, plan.getCategory());
            statement.setInt(3, plan.getMeal_id());

            ResultSet resultSet = statement.executeQuery(); {
                if (resultSet.next()) {
                    return new Plan(resultSet.getString("option"), resultSet.getString("category"), resultSet.getInt("meal_id"));
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to add plan: " + e.getMessage());
        }
    }

    @Override
    public void delete(int plan_id) {
        String deletePlan = "DELETE FROM plan WHERE plan_id = ?";
        try (PreparedStatement statement = jdbc.getStatement(deletePlan)) {

            statement.setInt(1, plan_id);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete plan: " + e.getMessage());
        }
    }

    @Override
    public void update(Plan plan) {
        String updatePlan = "UPDATE plan SET option = ?, category = ?, meal_id = ? WHERE plan_id = ?";
        try (PreparedStatement statement = jdbc.getStatement(updatePlan)) {

            statement.setString(1, plan.getOption());
            statement.setString(2, plan.getCategory());
            statement.setInt(3, plan.getMeal_id());
            statement.setInt(4, plan.getPlan_id());
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed to update plan: " + e.getMessage());
        }
    }

    @Override
    public Plan get(int plan_id) {
        String selectPlan = "SELECT * FROM plan WHERE plan_id = ?";
        try (PreparedStatement statement = jdbc.getStatement(selectPlan)) {

            statement.setInt(1, plan_id);
            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return new Plan(resultSet.getString("option"), resultSet.getString("category"), resultSet.getInt("meal_id"));
            } else {
                throw new RuntimeException("Plan not found");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to get plan: " + e.getMessage());
        }
    }

    @Override
    public List<Plan> getAll(String query) {
        try (PreparedStatement statement = jdbc.getStatement(query)) {

            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();
            List<Plan> plans = new ArrayList<>();

            while (resultSet.next()) {
                plans.add(new Plan(resultSet.getString("option"), resultSet.getString("category"), resultSet.getInt("meal_id")));
            }

            return plans;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get plans: " + e.getMessage());
        }
    }
}
