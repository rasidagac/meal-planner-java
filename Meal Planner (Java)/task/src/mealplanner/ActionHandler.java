package mealplanner;

import mealplanner.enums.Actions;
import mealplanner.enums.Categories;
import mealplanner.enums.DaysOfWeek;
import mealplanner.model.Meal;
import mealplanner.model.Plan;
import mealplanner.service.MealService;
import mealplanner.service.PlanService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActionHandler {
    private final MealManager mealManager;
    private final Scanner scanner;

    private static final String ADD_ACTION = "Which meal do you want to add (breakfast, lunch, dinner)?";
    private static final String WRONG_CATEGORY = "Wrong meal category! Choose from: breakfast, lunch, dinner.";
    private static final String SHOW_ACTION = "Which category do you want to print (breakfast, lunch, dinner)?";
    private static final String WRONG_SAVE = "Unable to save. Plan your meals first.";
    private static final String SAVE_ACTION = "Input a filename:";


    public ActionHandler(MealManager mealManager, Scanner scanner) {
        this.mealManager = mealManager;
        this.scanner = scanner;
    }

    public void handleAction(String action) {
        if (Actions.contains(action)) {
            Actions act = Actions.valueOf(action.toUpperCase());
            switch (act) {
                case ADD -> handleAddAction();
                case SHOW -> handleShowAction();
                case PLAN -> handlePlanAction();
                case SAVE -> handleSaveAction();
                case EXIT -> System.out.println("Bye!");
            }
        }
    }

    private void handleSaveAction() {
        if (Validator.validateIsPlanned()) {
            System.out.println(SAVE_ACTION);
            String filename = scanner.nextLine();
            mealManager.saveMeals(filename);
        } else {
            System.out.println(WRONG_SAVE);
        }
    }

    private void handleAddAction() {
        System.out.println(ADD_ACTION);
        boolean isValid;
        do {
            String category = scanner.nextLine();
            isValid = Categories.contains(category);
            if (isValid) {
                mealManager.addMeal(category);
            } else {
                System.out.println(WRONG_CATEGORY);
            }
        } while (!isValid);
    }

    private void handleShowAction() {
        System.out.println(SHOW_ACTION);
        boolean isValid;
        do {
            String category = scanner.nextLine();
            isValid = Categories.contains(category);
            if (isValid) {
                mealManager.showMeals(category);
            } else {
                System.out.println(WRONG_CATEGORY);
            }
        } while (!isValid);
    }

    private void handlePlanAction() {
        MealPlannerJDBC jdbc = new MealPlannerJDBC();

        String query = "SELECT plan.category, meal FROM plan JOIN meals ON plan.meal_id = meals.meal_id WHERE option = ?";

        for (DaysOfWeek day : DaysOfWeek.values()) {
            mealManager.planMeals(day);
        }

        for (DaysOfWeek day : DaysOfWeek.values()) {
            System.out.println(day);

            try (PreparedStatement statement = jdbc.getStatement(query)) {
                statement.setString(1, day.toString());
                ResultSet resultSet = statement.executeQuery(); {
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("category") + ": " + resultSet.getString("meal"));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to plan meals: " + e.getMessage());
            }
        }
    }
}