package mealplanner;

import java.util.Arrays;
import java.util.Scanner;

public class Meal {
    private String name;
    private String[] ingredients;
    private final Scanner scanner = new Scanner(System.in);

    public Meal() {
        System.out.println("Input the meal's name:");
        setName();
        System.out.println("Input the ingredients:");
        setIngredients();
    }

    public String getName() {
        return name;
    }

    public void setName() {
        String name = scanner.nextLine();
        if (Validator.validateName(name)) {
            this.name = name.trim();
        } else {
            System.out.println("Wrong format. Use letters only!");
            setName();
        }
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients() {
        String[] ingredients = scanner.nextLine().split(",");
        if (Validator.validateIngredients(ingredients)) {
            this.ingredients = Arrays.stream(ingredients).map(String::trim).toArray(String[]::new);
        } else {
            System.out.println("Wrong format. Use letters only!");
            setIngredients();
        }
    }
}
