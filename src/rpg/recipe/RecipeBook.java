package rpg.recipe;

import be.kuleuven.cs.som.annotate.*;

import java.util.ArrayList;

/**
 * A class representing a recipeBook
 *
 * @invar   The recipes of the recipe book are valid.
 *          | for each recipe in getRecipes():
 *          |   canHaveAsRecipe(recipe)
 *
 * @note    We doen aan TOTAL programming
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author 	Flor De Meulemeester
 * @version 1.0
 */
public class RecipeBook {

    /**
     * A variable for keeping track of all the recipes in the recipe book.
     *
     * @invar   recipes references an effective list
     *          | recipes != null
     * @invar   Each element in the list references an effective item.
     *          | for each recipe in recipes:
     *          |   recipe != null
     * @invar   Each element in the list references a non-terminated item.
     *          | for each recipe in recipes:
     *          | !recipe.isTerminated()
     */
    private ArrayList<Recipe> recipes = new ArrayList<>();

    /**********************************************************
     * Constructors
     **********************************************************/

    /**
     * A constructor for the RecipeBook class
     *
     * @param   recipes
     *          The new recipe list to be set
     *
     * @effect  Every recipe within an effective given recipes list
     *          is added as recipe
     *          | for each recipe in recipes:
     *          |   addRecipe(recipe)
     */
    public RecipeBook(ArrayList<Recipe> recipes) {
        if (recipes != null) {
            for (Recipe recipe : recipes) {
                addRecipe(recipe);
            }
        }
    }

    /**
     * A constructor for the RecipeBook class
     *
     * @effect  A recipe book is created with a null-referenced parameter
     *          | this(null);
     */
    public RecipeBook() {
        this(null);
    }

    /**
     * A method for adding a recipe to a recipe book
     *
     * @param   recipe
     *          The recipe to be added
     *
     * @post    If the recipe is valid,
     *          the number of recipes of this recipe book is
     *          incremented with 1.
     *          | if canHaveAsRecipe(recipe)
     *          | then new.getNbOfRecipes() == getNbOfRecipes() + 1
     * @post    If the recipe is valid,
     *          the given recipe is inserted at the given index.
     *          | if canHaveAsRecipe(recipe)
     *          | then new.getRecipeAt(getNbOfRecipes()-1) == recipe
     */
    public void addRecipe(Recipe recipe) {
        if (canHaveAsRecipe(recipe)) {
            recipes.add(recipe);
        }
    }

    /**
     * A method for removing a recipe out of a recipe book
     *
     * @param   recipe
     *          The recipe to be removed
     *
     * @return  True if and only if the recipe is actually removed
     *          | result ==
     *          |   new.getNbOfRecipes() + 1 == getNbOfRecipes()
     * @post    If the recipe is present in the recipe book,
     *          the number of recipes in the recipe book is
     *          decremented with 1.
     *          | if getRecipes().contains(recipe)
     *          | then new.getNbOfRecipes() + 1 == getNbOfRecipes()
     * @post    If the recipe is present in the recipe book,
     *          all elements to the right of the removed recipe
     *        	are shifted left by 1 position.
     *          | for each index in getIndexOfRecipe(recipe)+1..getNbOfRecipes():
     *       	|   new.getRecipeAt(index+1) == getRecipeAt(index)
     *
     */
    public boolean removeRecipe(Recipe recipe) {
        if (recipes.contains(recipe)) {
            recipes.remove(recipe);
            return true;
        }
        return false;
    }

    /**
     * Return the number of recipes in a recipe book
     */
    @Basic
    public int getNbOfRecipes() {
        return recipes.size();
    }

    /**
     * A method for getting the recipe at a given index
     *
     * @param   index
     *          The index to check for
     * @return  If the given index is within the boundaries
     *          of the recipes list,
     *          the copy of the recipe at that index is returned
     *          | if index >= 0 && index < getNbOfRecipes()
     *          | then result == getRecipes().get(index).clone()
     * @return  If the given index is not within the boundaries
     *          of the recipes list,
     *          a null reference is returned
     *          | if (index < 0) || (index >= getNbOfRecipes())
     *          | then result == null
     */
    @Basic
    public Recipe getRecipeAt(int index) {
        if (index >= 0 && index < getNbOfRecipes()) {
            // we make a copy so that the recipe can not be terminated while it is in the book
            return recipes.get(index).clone();
        }
        return null;
    }

    /**
     * A method that gives back the index
     * of a recipe in a recipe book
     *
     * @param   recipe
     *          The recipe to get the index of
     * @return  If the recipe is present in the recipe book
     *          the index of the recipe is returned
     *          | if getRecipes().contains(recipe)
     *          | then getRecipeAt(result) == recipe
     * @return  If the recipe is not present,
     *          negative one is returned
     *          | if ! getRecipes().contains(recipe)
     *          | then result == -1
     */
    @Basic
    public int getIndexOfRecipe(Recipe recipe) {
        if (recipes.contains(recipe)) {
            return recipes.indexOf(recipe);
        }
        return -1;
    }

    /**
     * A method for checking if a recipe book
     * can contain a given recipe
     *
     * @param   recipe
     *          The recipe to check
     *
     * @return  True if the recipe is not a null-reference,
     *          the recipe is not terminated
     *          and if the recipe is not already in the recipe book
     *          | result ==
     *          |   recipe != null && !recipe.isTerminated()
     *          |   && !getRecipes().contains(recipe)
     */
    public boolean canHaveAsRecipe(Recipe recipe) {
        return recipe != null && !recipe.isTerminated() && !getRecipes().contains(recipe);
    }

    /**
     * Return the recipe list
     */
    @Model
    private ArrayList<Recipe> getRecipes() {
        return recipes;
    }


}
