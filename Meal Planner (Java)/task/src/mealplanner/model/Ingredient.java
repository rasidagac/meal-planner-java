package mealplanner.model;

public class Ingredient {

    private int ingredient_id;
    private int meal_id;
    private String ingredient;

    public Ingredient(int meal_id, String ingredient) {
        this.meal_id = meal_id;
        this.ingredient = ingredient;
    }

    public Ingredient(int ingredient_id, int meal_id, String ingredient) {
        this.ingredient_id = ingredient_id;
        this.meal_id = meal_id;
        this.ingredient = ingredient;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }
}
