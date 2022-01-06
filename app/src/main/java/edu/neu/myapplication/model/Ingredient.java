package edu.neu.myapplication.model;

class Ingredient {
    private String IngredientName;
    private String amount;

    public Ingredient() {
    }

    public Ingredient(String ingredientName, String amount) {
        IngredientName = ingredientName;
        this.amount = amount;
    }

    public String getIngredientName() {
        return IngredientName;
    }

    public void setIngredientName(String ingredientName) {
        IngredientName = ingredientName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
