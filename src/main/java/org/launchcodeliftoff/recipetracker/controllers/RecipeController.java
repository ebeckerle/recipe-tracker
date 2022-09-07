package org.launchcodeliftoff.recipetracker.controllers;

import org.launchcodeliftoff.recipetracker.data.CommentRepository;
import org.launchcodeliftoff.recipetracker.data.CookbookRepository;
import org.launchcodeliftoff.recipetracker.data.RecipeRepository;
import org.launchcodeliftoff.recipetracker.data.UserRepository;
import org.launchcodeliftoff.recipetracker.models.Comment;
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

@Controller
//TODO: requestmapping will probably need to change before / after merged with main
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private CookbookRepository cookbookRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public String displayAddRecipe(Model model){
        model.addAttribute("title", "Add A Recipe");
        model.addAttribute(new Recipe());
        return "add-recipe";
    }

    @PostMapping("/add")
    public String processAddRecipe(@ModelAttribute @Valid Recipe newRecipe, Errors errors,
                                   HttpServletRequest request,
                                   Model model){
        if (errors.hasErrors()){
            return "add-recipe";
        }

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");
        newRecipe.setRecipeAuthor(userRepository.findById(userId).get());

        recipeRepository.save(newRecipe);
        model.addAttribute("recipe", recipeRepository.findById(newRecipe.getId()).get());
        model.addAttribute("title", recipeRepository.findById(newRecipe.getId()).get().getName());
        model.addAttribute("recipeAuthor", newRecipe.getRecipeAuthor().getUsername());
        model.addAttribute(new Comment());

        ArrayList<Integer> ratings = new ArrayList<>();
        ratings.add(1);
        ratings.add(2);
        ratings.add(3);
        ratings.add(4);
        ratings.add(5);
        model.addAttribute("ratings", ratings);
        return "view-recipe";
    }

    @GetMapping("/view/{recipeId}")
//    @RequestMapping(value="/{recipeId}", method = RequestMethod.GET)
    public String displayViewRecipe(Model model, @PathVariable Integer recipeId, HttpServletRequest request){
        Recipe recipe = recipeRepository.findById(recipeId).get();
        model.addAttribute("title", recipeRepository.findById(recipeId).get().getName());
        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeAuthor", recipe.getRecipeAuthor().getUsername());
        model.addAttribute("comments", commentRepository.findByRecipeId(recipeId));

        //for the Save Recipe form on the page - which we only want visible when a user is logged in and
        // the user is not the recipe's author
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");
        model.addAttribute("isUserLoggedIn", userRepository.findById(userId).isPresent());
        if(userRepository.findById(userId).isPresent()){
            model.addAttribute("userLoggedIn", userRepository.findById(userId).get().getUsername());
        }else{
            model.addAttribute("userLoggedIn", null);
        }


        //to populate the Add Comment Form on the page
        ArrayList<Integer> ratings = new ArrayList<>();
        ratings.add(1);
        ratings.add(2);
        ratings.add(3);
        ratings.add(4);
        ratings.add(5);
        model.addAttribute("ratings", ratings);
        model.addAttribute(new Comment());

        return "view-recipe";
    }


//    @GetMapping("/view/{recipeId}")
//    public String displayViewRecipe(Model model, @PathVariable Integer recipeId){
//        Recipe recipe = recipeRepository.findById(recipeId).get();
//        model.addAttribute("title", recipeRepository.findById(recipeId).get().getName());
//        model.addAttribute("recipe", recipe);
//        model.addAttribute("recipeAuthor", recipe.getRecipeAuthor().getUsername());
//        model.addAttribute("comments", commentRepository.findByRecipeId(recipeId));
//
//        return "view-recipe";
//    }

    @PostMapping("/view/{recipeId}")
//    @RequestMapping(value="/{recipeId}", method = RequestMethod.POST, params = "action=postComment")
    public String processAddCommentForm(Comment newComment,
                                      HttpServletRequest request,
                                        Model model, @PathVariable Integer recipeId
                                        ){
        // finding logged in user by getting session
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");

        //attaching the user who made the comment to the comment (as the comment author)
        newComment.setUser(userRepository.findById(userId).get());
        //attaching the recipe to the comment
        newComment.setRecipe(recipeRepository.findById(recipeId).get());
        commentRepository.save(newComment);


        //update the recipe's average rating since it has a new comment
        Recipe recipe = recipeRepository.findById(recipeId).get();
        recipe.setComment(newComment);
        recipe.calculateAverageRating();
        recipeRepository.save(recipe);

        model.addAttribute("title", recipeRepository.findById(recipeId).get().getName());
        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeAuthor", recipe.getRecipeAuthor().getUsername());
        model.addAttribute("comments", commentRepository.findByRecipeId(recipeId));
        model.addAttribute(new Comment());
        ArrayList<Integer> ratings = new ArrayList<>();
        ratings.add(1);
        ratings.add(2);
        ratings.add(3);
        ratings.add(4);
        ratings.add(5);
        model.addAttribute("ratings", ratings);

        return "view-recipe";
    }

////    @RequestMapping(value="/saveRecipe", method = RequestMethod.POST, params = "action=saveRecipe")
//    @PostMapping(value="/{recipeId}", params="saveRecipe")
//    public String processSaveRecipe(@RequestParam Integer recipesId, HttpServletRequest request,
//                                        Model model, @PathVariable Integer recipeId){
//
//        HttpSession session = request.getSession();
//        Integer userId = (Integer) session.getAttribute("user");
//        User user = userRepository.findById(userId).get();
//        Recipe recipe = recipeRepository.findById(recipesId).get();
//        user.addToSavedRecipes(recipe);
//
//        userRepository.save(user);
//
//        return "home";
//    }



}
