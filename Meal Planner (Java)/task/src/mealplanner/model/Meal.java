package mealplanner.model;

public class Meal {
    private int meal_id;
    private String meal;
    private String category;

    public Meal(int meal_id, String meal, String category) {
        this.meal_id = meal_id;
        this.meal = meal;
        this.category = category;
    }

    public Meal(String meal, String category) {
        this.meal = meal;
        this.category = category;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
