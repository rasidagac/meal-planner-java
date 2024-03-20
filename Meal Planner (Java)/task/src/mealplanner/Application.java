package mealplanner;

import java.util.Scanner;

public class Application {
    private final Scanner scanner = new Scanner(System.in);
    private final MealManager mealManager = new MealManager();
    private final ActionHandler actionHandler = new ActionHandler(mealManager, scanner);

    public void run() {
        String action;
        do {
            System.out.println("What would you like to do (add, show, exit)?");
            action = scanner.nextLine();
            actionHandler.handleAction(action);
        } while (!action.equals("exit"));
    }
}