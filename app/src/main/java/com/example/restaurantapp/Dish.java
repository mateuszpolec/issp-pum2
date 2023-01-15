package com.example.restaurantapp;

import java.util.UUID;

public class Dish {

    private UUID mId;
    private String mName;
    private String mIngredients;
    private String mRecipe;
    private int mPrice;
    private int mWeight;
    private int mTimeToPrepare;
    private String mAllergens;

    // TODO: Alergeny.

    public Dish()
    {
        this.mId = UUID.randomUUID();
        this.mName = "Some dish";
    }

    public Dish(UUID id, String name, String ingredients, String recipe, int price, int weight, int timeToPrepare, String allergens)
    {
        this.mId = id;
        this.mName = name;
        this.mIngredients = ingredients;
        this.mRecipe = recipe;
        this.mPrice = price;
        this.mWeight = weight;
        this.mTimeToPrepare = timeToPrepare;
        this.mAllergens = allergens;
    }

    public void setName(String name) { this.mName = name; }
    public String getName() { return this.mName; }

    public void setIngredients(String ingredients) { this.mIngredients = ingredients; }
    public String getIngredients() { return this.mIngredients; }

    public void setRecipe(String recipe) { this.mRecipe = recipe; }
    public String getRecipe() { return this.mRecipe; }

    public void setPrice(int price) { this.mPrice = price; }
    public int getPrice() { return this.mPrice; }

    public void setWeight(int weight) { this.mWeight = weight; }
    public int getWeight() { return this.mWeight; }

    public void setTimeToPrepare(int timeToPrepare) { this.mTimeToPrepare = timeToPrepare; }
    public int getTimeToPrepare() { return this.mTimeToPrepare; }

    public void setAllergens(String allergens) { this.mAllergens = allergens; }
    public String getAllergens() { return this.mAllergens; }

    public UUID getId() { return this.mId; }

}
