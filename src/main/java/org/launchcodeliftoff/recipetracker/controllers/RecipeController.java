package org.launchcodeliftoff.recipetracker.controllers;

import org.launchcodeliftoff.recipetracker.data.CookbookRepository;
import org.launchcodeliftoff.recipetracker.data.RecipeRepository;
import org.launchcodeliftoff.recipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
//TODO: requestmapping will probably need to change before / after merged with main
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private CookbookRepository cookbookRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/add")
    public String displayAddRecipe(Model model){
        model.addAttribute(new Recipe());
        return "add-recipe";
    }

    @PostMapping("/add")
    public String processAddRecipe(@ModelAttribute @Valid Recipe newRecipe, Errors errors,  Model model){
        if (errors.hasErrors()){
            return "add-recipe";
        }
        recipeRepository.save(newRecipe);
        model.addAttribute("recipe", recipeRepository.findById(newRecipe.getId()).get());
        return "view-recipe";
    }

    @GetMapping("/{recipeId}")
    public String displayViewRecipe(Model model, @PathVariable Integer recipeId){
        model.addAttribute("recipe", recipeRepository.findById(recipeId).get());
        return "view-recipe";
    }

}
