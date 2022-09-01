package org.launchcodeliftoff.recipetracker.models;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class RecipeData {


    private static final String RECIPE_DATA_FILE = "recipe.csv";
    private static boolean isDataLoaded = false;

    private static ArrayList<Recipe> allRecipes;

    private static ArrayList<String> allNames;
    private static ArrayList<String> allDescriptions;
    private static ArrayList<String> allIngredientLists;
    private static ArrayList<String> allRecipeInstructions;
    private static ArrayList<User> allRecipeAuthors;


    public static ArrayList<Recipe> findAll() {

        // load data, if not already loaded
        loadData();

        // Bonus mission; normal version returns allJobs
        return new ArrayList<>(allRecipes);
    }

    private static Object findExistingObject(ArrayList list, String value){
        for (Object item : list){
            if (item.toString().toLowerCase().equals(value.toLowerCase())){
                return item;
            }
        }
        return null;
    }

    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Resource resource = new ClassPathResource(RECIPE_DATA_FILE);
            InputStream is = resource.getInputStream();
            Reader reader = new InputStreamReader(is);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allRecipes = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {

                String anId = record.get(0);
                String aDescription = record.get(1);
                String anIngredientList = record.get(2);
                String aName = record.get(3);
                String aRecipeInstruction = record.get(4);
                String aRecipeAuthorId = record.get(5);
                String anAverageRating = record.get(6);

//                Integer newRecipeAuthorId = (Integer) findExistingObject(allRecipeAuthors, aRecipeAuthorId);

                String newDescription = (String) findExistingObject(allDescriptions, aDescription);
                PositionType newPosition = (PositionType) findExistingObject(allPositionTypes, aPosition);
                CoreCompetency newSkill = (CoreCompetency) findExistingObject(allCoreCompetency, aSkill);

//                if (newRecipeAuthorId == null){
//                    newRecipeAuthorId = new Integer(aRecipeAuthorId);
//                    allRecipeAuthors.add(newRecipeAuthorId);
//                }

//                if (newLocation == null){
//                    newLocation = new Location(aLocation);
//                    allLocations.add(newLocation);
//                }
//
//                if (newSkill == null){
//                    newSkill = new CoreCompetency(aSkill);
//                    allCoreCompetency.add(newSkill);
//                }
//
//                if (newPosition == null){
//                    newPosition = new PositionType(aPosition);
//                    allPositionTypes.add(newPosition);
//                }

                Recipe newRecipe = new Recipe(aName, aDescription, anIngredientList, aRecipeInstruction);

                allRecipes.add(newRecipe);
            }
            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
