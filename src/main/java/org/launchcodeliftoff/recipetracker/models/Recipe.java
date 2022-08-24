package org.launchcodeliftoff.recipetracker.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private String ingredientList;

    @NotNull
    private String recipeInstruction;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    private User recipeAuthor;

    @ManyToMany(mappedBy = "recipes")
    private List<Cookbook> cookbooks = new ArrayList<>();

    public Recipe(){}

    public Recipe(String name, String description, String ingredientList, String recipeInstruction, User recipeAuthor) {
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

