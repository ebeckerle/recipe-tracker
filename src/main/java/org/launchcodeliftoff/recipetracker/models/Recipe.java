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

    @Size(max=600, message = "The ingredient list is too long, the limit is 600 characters")
    private String description;

    ///TODO: further research on a photo/image field : private Img photo;

    @NotNull(message = "Please include Ingredients")
    @Size(max=1000, message = "The ingredient list is too long, the limit is 1000 characters")
    private String ingredientList;

    @NotNull
    @Size(max=3000, message = "The recipe instruction is too long, the limit is 3000 characters")
    private String recipeInstruction;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    private User recipeAuthor;

    @ManyToMany(mappedBy = "recipes")
    private List<Cookbook> cookbooks = new ArrayList<>();

    private Integer averageRating = 0;

    public Recipe(){}

    public Recipe(String name, @Size(max=600) String description, @Size(max=1000) String ingredientList, @Size(max=3000) String recipeInstruction, User recipeAuthor) {
        this.name = name;
        this.description = description;
        this.ingredientList = ingredientList;
        this.recipeInstruction = recipeInstruction;
        this.recipeAuthor = recipeAuthor;
    }

    public Recipe(String aName, String aDescription, String anIngredientList, String aRecipeInstruction) {
        this.name = aName;
        this.description = aDescription;
        this.ingredientList = anIngredientList;
        this.recipeInstruction = aRecipeInstruction;
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

    public void setComment(Comment comment) {

        this.comments.add(comment);
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

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public void calculateAverageRating(){
        if(this.comments.size()>=1){
            Integer averageRating;
            Integer totalOfRatings = 0;
            Integer divisor = this.comments.size();
            for (Comment comment:
                    this.comments) {
                totalOfRatings += comment.getRating();
            }
            averageRating = totalOfRatings / divisor;
            this.averageRating = averageRating;
        }else{
            this.averageRating = 0;
        }
    }
}

