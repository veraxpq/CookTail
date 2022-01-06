package edu.neu.myapplication.model;

public class LearningRecipes {
    private String RecipeName;
    private String RecipeIngredients;
    private int RecipeLevel,learnedNum;
    private String imgUrl;
    private String step1,step2,step3,step4;

    LearningRecipes(){

    }

    public LearningRecipes(String recipeName, String recipeIngredients, int recipeLevel, int learnedNum, String imgUrl, String step1, String step2, String step3, String step4) {
        RecipeName = recipeName;
        RecipeIngredients = recipeIngredients;
        RecipeLevel = recipeLevel;
        this.learnedNum = learnedNum;
        this.imgUrl = imgUrl;
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
        this.step4 = step4;
    }

    public LearningRecipes(String RecipeName, String RecipeIngredients, String imgUrl, String step1, String step2, String step3, String step4, int RecipeLevel){
        this.RecipeName = RecipeName;
        this.RecipeIngredients = RecipeIngredients;
        this.imgUrl = imgUrl;
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
        this.step4 = step4;
        this.RecipeLevel = RecipeLevel;
    }

    public LearningRecipes(String RecipeName, String RecipeIngredients, String imgUrl,String step1,String step2,String step3,String step4){
        this.RecipeName = RecipeName;
        this.RecipeIngredients = RecipeIngredients;
        this.imgUrl = imgUrl;
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
        this.step4 = step4;
    }

    public LearningRecipes(String RecipeName, String imgUrl,int RecipeLevel,int learnedNum) {
        this.RecipeName = RecipeName;
        this.imgUrl = imgUrl;
        this.RecipeLevel = RecipeLevel;
        this.learnedNum = learnedNum;
    }

    public LearningRecipes(String RecipeName, String imgUrl,int RecipeLevel) {
        this.RecipeName = RecipeName;
        this.imgUrl = imgUrl;
        this.RecipeLevel = RecipeLevel;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public String getRecipeIngredients() {
        return RecipeIngredients;
    }

    public int getRecipeLevel() {
        return RecipeLevel;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        RecipeIngredients = recipeIngredients;
    }

    public void setRecipeLevel(int recipeLevel) {
        RecipeLevel = recipeLevel;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setStep1(String step1) {
        this.step1 = step1;
    }

    public void setStep2(String step2) {
        this.step2 = step2;
    }

    public void setStep3(String step3) {
        this.step3 = step3;
    }

    public void setStep4(String step4) {
        this.step4 = step4;
    }

    public String getStep1() {
        return step1;
    }

    public String getStep2() {
        return step2;
    }

    public String getStep3() {
        return step3;
    }

    public String getStep4() {
        return step4;
    }


    public int getLearnedNum() {
        return learnedNum;
    }

    public void setLearnedNum(int learnedNum) {
        this.learnedNum = learnedNum;
    }




}
