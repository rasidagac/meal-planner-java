package mealplanner;

import java.util.Scanner;

public class ActionHandler {
    private final MealManager mealManager;
    private final Scanner scanner;

    public ActionHandler(MealManager mealManager, Scanner scanner) {
        this.mealManager = mealManager;
        this.scanner = scanner;
    }

    public void handleAction(String action) {
        if (Validator.validateAction(action)) {
            switch (action) {
                case "add":
                    System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
                    handleAddAction();
                    break;
                case "show":
                    handleShowAction();
                    break;
                case "exit":
                    System.out.println("Bye!");
                    break;
            }
        }
    }

    private void handleAddAction() {
        String category = scanner.nextLine();
        if (Validator.validateCategory(category)) {
            mealManager.addMeal(category);
        } else {
            System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            handleAddAction();
        }
    }

    private void handleShowAction() {
        if (mealManager.hasMeals()) {
            mealManager.showMeals();
        } else {
            System.out.println("No meals saved. Add a meal first.");
        }
    }
}