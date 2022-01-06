package edu.neu.myapplication.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class RecipeModel {

    private String bakingTime;
    private String description;
    private String difficulty;
    private String ingredients;
    private String prepareTime;
    private String recipeName;
    private String restingTime;
    private String postImage;

    public String getBakingTime() {
        return bakingTime;
    }

    public void setBakingTime(String bakingTime) {
        this.bakingTime = bakingTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(String prepareTime) {
        this.prepareTime = prepareTime;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRestingTime() {
        return restingTime;
    }

    public void setRestingTime(String restingTime) {
        this.restingTime = restingTime;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public RecipeModel(String bakingTime, String description, String difficulty, String ingredients, String prepareTime, String recipeName, String restingTime, String postImage) {
        this.bakingTime = bakingTime;
        this.description = description;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
        this.prepareTime = prepareTime;
        this.recipeName = recipeName;
        this.restingTime = restingTime;
        this.postImage = postImage;
    }
}
