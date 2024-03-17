package mealplanner;

import java.util.Scanner;

public class Main {
  static String category, name;
  static String[] ingredients;
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
    category = scanner.nextLine();
    System.out.println("Input the meal's name:");
    name = scanner.nextLine();
    System.out.println("Input the ingredients:");
    ingredients = scanner.nextLine().split(",");
    printRecipe();
    System.out.println("The meal has been added!");
  }

  static void printRecipe() {
    System.out.println("Category: " + category);
    System.out.println("Name: " + name);
    System.out.println("Ingredients:");
    for (String ingredient : ingredients) {
      System.out.println(ingredient);
    }
  }
}