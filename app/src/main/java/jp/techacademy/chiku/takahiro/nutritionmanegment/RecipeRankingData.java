package jp.techacademy.chiku.takahiro.nutritionmanegment;

/**
 * Created by takahiro chiku on 2017/09/02.
 */

public class RecipeRankingData {
    private String RecipeId;
    private String RecipeTitle;
    private String RecipeUrl;
    private String SmallImageUrl;
    private String RecipeIndication;
    private String RecipeCost;

    public String getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(String recipeId) {
        RecipeId = recipeId;
    }

    public String getRecipeTitle() {
        return RecipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        RecipeTitle = recipeTitle;
    }

    public String getRecipeUrl() {
        return RecipeUrl;
    }

    public void setRecipeUrl(String recipeUrl) {
        RecipeUrl = recipeUrl;
    }

    public String getSmallImageUrl() {
        return SmallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        SmallImageUrl = smallImageUrl;
    }

    public String getRecipeIndication() {
        return RecipeIndication;
    }

    public void setRecipeIndication(String recipeIndication) {
        RecipeIndication = recipeIndication;
    }

    public String getRecipeCost() {
        return RecipeCost;
    }

    public void setRecipeCost(String recipeCost) {
        RecipeCost = recipeCost;
    }

}