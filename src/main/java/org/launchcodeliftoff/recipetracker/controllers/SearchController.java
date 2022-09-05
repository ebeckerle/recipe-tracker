package org.launchcodeliftoff.recipetracker.controllers;

import org.launchcodeliftoff.recipetracker.data.RecipeRepository;
import org.launchcodeliftoff.recipetracker.data.UserRepository;
import org.launchcodeliftoff.recipetracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping("/list-recipes/{recipeAuthorId}")
    public String displayListOfRecipesByRecipeAuthor(Model model, @PathVariable Integer recipeAuthorId){
        User recipeAuthor = userRepository.findById(recipeAuthorId).get();
        model.addAttribute("recipeAuthor", recipeAuthor);
        model.addAttribute("recipes", recipeRepository.findByRecipeAuthorId(recipeAuthorId));
        return "list-recipes";
    }
}
