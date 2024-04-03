package mealplanner;

import mealplanner.enums.Categories;
import mealplanner.enums.DaysOfWeek;
import mealplanner.model.Ingredient;
import mealplanner.model.Meal;
import mealplanner.model.Plan;
import mealplanner.service.IngredientService;
import mealplanner.service.MealService;
import mealplanner.service.PlanService;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MealManager {
    private final String WRONG_FORMAT = "Wrong format. Use letters only!";

    private final MealPlannerJDBC jdbc = new MealPlannerJDBC();
    private final Scanner scanner = new Scanner(System.in);
    private final MealService mealService = new MealService();
    private final IngredientService ingredientService = new IngredientService();
    private final PlanService planService = new PlanService();

    public void addMeal(String category) {
        System.out.println("Input the meal's name:");
        boolean isValid;
        do {
            String mealName = scanner.nextLine();
            isValid = Validator.validateName(mealName);
            if (isValid) {

                Meal meal = mealService.add(new Meal(mealName, category));

                addIngredient(meal.getMeal_id());
                System.out.println("The meal has been added!");

            } else {
                System.out.println(WRONG_FORMAT);
            }
        } while (!isValid);
    }

    public void addIngredient(int mealId) {
        System.out.println("Input the ingredients:");
        boolean isValid;
        do {
            String[] ingredients = scanner.nextLine().split(",");
            isValid = Validator.validateIngredients(ingredients);

            if (isValid) {
                for (String ingredient : ingredients) {
                    Ingredient ingredientObj = new Ingredient(mealId, ingredient);
                    ingredientService.add(ingredientObj);
                }
            } else {
                System.out.println(WRONG_FORMAT);
            }
        } while (!isValid);
    }

    public void showMeals(String category) {
        boolean hasMeals = hasMeals(category);

        if (!hasMeals) {
            System.out.println("No meals found.");
        } else {
            jdbc.showMeals(category);
        }
    }

    public boolean hasMeals(String category) {
        String query = "SELECT * FROM meals WHERE category = ?";
        try (PreparedStatement stmt = jdbc.getStatement(query)) {
            stmt.setString(1, category);
            return stmt.executeQuery().next();
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database: " + e.getMessage());
        }
    }

    public void planMeals(DaysOfWeek day) {
        System.out.println(day);
        Map<String, Integer> mealNames = new HashMap<>();
        for (Categories cat : Categories.values()) {
            String category = cat.toString().toLowerCase();
            String selectMeals = "SELECT * FROM meals WHERE category = '" + category + "' ORDER BY meal ASC";

            mealService.getAll(selectMeals).forEach(meal -> {
                mealNames.put(meal.getMeal(), meal.getMeal_id());
                System.out.println(meal.getMeal());
            });

            System.out.println("Choose the " + category + " for " + day + " from the list above:");

            boolean isValid;
            do {
                String meal = scanner.nextLine();
                isValid = mealNames.containsKey(meal);

                if (isValid) {
                    Plan plan = new Plan(day.toString(), cat.toString(), mealNames.get(meal));

                    planService.add(plan);
                } else {
                    System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                }
            } while (!isValid);
        }

        System.out.println("Yeah! We planned the meals for " + day + "." + "\n");
    }

    public void saveMeals(String filename) {
        File file = new File(filename);

        try (FileWriter writer = new FileWriter(file)) {
            String query = "SELECT ingredient, COUNT(ingredient) AS count FROM ingredients JOIN plan ON ingredients.meal_id = plan.meal_id GROUP BY ingredient";
            PreparedStatement statement = jdbc.getStatement(query);
            ResultSet resultSet = statement.executeQuery(); {
                while (resultSet.next()) {
                    String count = resultSet.getInt("count") == 1 ? "" : " x" + resultSet.getString("count");
                    writer.write(resultSet.getString("ingredient") + count + "\n");
                }
            }

            System.out.println("Saved!");
        } catch (Exception e) {
            throw new RuntimeException("Failed to save meals: " + e.getMessage());
        }
    }
}