package org.launchcodeliftoff.recipetracker.controllers;

import org.launchcodeliftoff.recipetracker.data.CookbookRepository;
import org.launchcodeliftoff.recipetracker.data.RecipeRepository;
import org.launchcodeliftoff.recipetracker.data.UserRepository;
import org.launchcodeliftoff.recipetracker.models.Cookbook;
import org.launchcodeliftoff.recipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cookbook")
public class CookbookController {

    @Autowired
    private CookbookRepository cookbookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/add")
    public String displayAddCookbookForm(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");

        model.addAttribute("title", "Create A Cookbook");
        model.addAttribute(new Cookbook());
        List<Recipe> recipes = new ArrayList<>();
        List<Recipe> myRecipes = (List<Recipe>) recipeRepository.findByRecipeAuthorId(userId);
        List<Recipe> savedRecipes = userRepository.findById(userId).get().getSavedRecipes();
        recipes.addAll(myRecipes);
        recipes.addAll(savedRecipes);
        model.addAttribute("recipes", recipes);
        return "add-cookbook";
    }

    @PostMapping("/add")
    public String processAddCookbookForm(@ModelAttribute @Valid Cookbook newCookbook, Errors errors,
                                   HttpServletRequest request,
                                         Model model){
        if (errors.hasErrors()){
            return "add-recipe";
        }

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");
        newCookbook.setUser(userRepository.findById(userId).get());

        cookbookRepository.save(newCookbook);
        return "view-cookbook";
    }

    @GetMapping("/view/{cookbookId}")
    public String displayViewCookbook(@PathVariable Integer cookbookId, Model model){
        model.addAttribute("cookbook", cookbookRepository.findById(cookbookId).get());
        return "view-cookbook";
    }
}
