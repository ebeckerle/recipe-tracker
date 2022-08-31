package org.launchcodeliftoff.recipetracker.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = "Please name your Recipe!")
    private String name;

    private String description;

    ///TODO: further research on a photo/image field : private Img photo;

    @NotNull(message = "Please include Ingredients")
    @Size(max=500, message = "The ingredient list is too long, the limit is 500 characters")
    private String ingredientList;

    @NotNull
    @Size(max=2000, message = "The recipe instruction is too long, the limit is 2000 characters")
    private String recipeInstruction;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    private User recipeAuthor;

    @ManyToMany(mappedBy = "recipes")
    private List<Cookbook> cookbooks = new ArrayList<>();

    public Recipe(){}

    public Recipe(String name, String description, @Size(max=500) String ingredientList, @Size(max=2000) String recipeInstruction, User recipeAuthor) {
        this.name = name;
        this.description = description;
        this.ingredientList = ingredientList;
        this.recipeInstruction = recipeInstruction;
        this.recipeAuthor = recipeAuthor;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(String ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getRecipeInstruction() {
        return recipeInstruction;
    }

    public void setRecipeInstruction(String recipeInstruction) {
        this.recipeInstruction = recipeInstruction;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getRecipeAuthor() {
        return recipeAuthor;
    }

    public void setRecipeAuthor(User recipeAuthor) {
        this.recipeAuthor = recipeAuthor;
    }

    public List<Cookbook> getCookbooks() {
        return cookbooks;
    }

    public void setCookbooks(List<Cookbook> cookbooks) {
        this.cookbooks = cookbooks;
    }
}

