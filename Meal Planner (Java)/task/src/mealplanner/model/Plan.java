package mealplanner.model;

public class Plan {
    private String option;
    private String category;
    private int meal_id;
    private int plan_id;

    public Plan(String option, String category, int meal_id) {
        this.option = option;
        this.category = category;
        this.meal_id = meal_id;
    }

    public Plan(String option, String category, int meal_id, int plan_id) {
        this.option = option;
        this.category = category;
        this.meal_id = meal_id;
        this.plan_id = plan_id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }
}
