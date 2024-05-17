package rpg.recipe;

import be.kuleuven.cs.som.annotate.*;

import java.util.ArrayList;

/**
 * A class representing a recipeBook.
 *
 * @invar   The recipes of the recipe book are valid.
 *          | hasProperRecipes()
 *
 * @note    We use TOTAL programming for this class.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author 	Flor De Meulemeester
 * @version 1.0
 */
public class RecipeBook {

    /**********************************************************
     * Constructors
     **********************************************************/

    /**
     * A constructor for the RecipeBook class.
     *
     * @post    The new recipe book has no recipes.
     *          | new.getNbOfRecipes() == 0
     */
    public RecipeBook() {
        super();
    }



    /**********************************************************
     * Recipes
     **********************************************************/

    /**
     * A variable for keeping track of all the recipes in the recipe book.
     *
     * @invar   recipes references an effective list.
     *          | recipes != null
     * @invar   Each element in the list must be a valid recipe.
     *          | for each recipe in recipes:
     *          |   canHaveAsRecipe(recipe)
     * @invar   Elements are deleted by setting them to null.
     *          | for some recipe in recipes:
     *          |   recipe == null
     * @invar   No two recipes are the same.
     *          | for each I in 0..getNbOfRecipes()-1:
     *          |   !containsRecipeTwice(getRecipeAt(I))
     */
    private ArrayList<Recipe> recipes = new ArrayList<>();

    /**
     * A method for checking whether the recipebook has proper recipes inside of it.
     *
     * @return  True if and only if this recipebook can have all its recipes
     * 			at their respective indices.
     *          | result ==
     *          |   for each I in 0..getNbOfRecipes()-1 :
     *          |     canHaveAsRecipe(getRecipeAt(I))
     */
    @Raw
    public boolean hasProperRecipes() {
        for (Recipe recipe : recipes) {
            if (!canHaveAsRecipe(recipe)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A method for adding a recipe to a recipe book.
     *
     * @param   recipe
     *          The recipe to be added.
     *
     * @post    If the recipe is valid, it is not already in the recipeBook
     *          and the recipe is not null,
     *          the number of recipes of this recipe book is
     *          incremented with 1.
     *          | if (canHaveAsRecipe(recipe) && !hasAsRecipe(recipe) && recipe != null)
     *          |   then new.getNbOfRecipes() == getNbOfRecipes() + 1
     * @post    If the recipe is valid, the recipe is not already present, and the recipe is not null
     *          the given recipe is inserted at the last index.
     *          | if (canHaveAsRecipe(recipe) && !hasAsRecipe(recipe) && recipe != null)
     *          |   then new.getRecipeAt(getNbOfRecipes()-1) == recipe
     */
    public void addAsRecipe(Recipe recipe) {
        if (canHaveAsRecipe(recipe) && !hasAsRecipe(recipe) && recipe != null) {
            recipes.add(recipe);
        }
    }

    /**
     * A method for removing a recipe out of a recipe book.
     *
     * @param   recipe
     *          The recipe to be removed.
     *
     * @post    If the recipe is present in the recipe book,
     *          the recipe at the index of the given recipe is set to null.
     *          "The page is torn out".
     *          | if hasAsRecipe()
     *          |   then new.getRecipeAt(getIndexOfRecipe(recipe)) == null
     *
     */
    public void removeAsRecipe(Recipe recipe) {
        if (hasAsRecipe(recipe) ) {
            // set the recipe to null, "the page is torn out"
            recipes.set(getIndexOfRecipe(recipe), null);
        }
    }

    /**
     * Return the number of recipes in a recipe book.
     */
    @Basic
    public int getNbOfRecipes() {
        return recipes.size();
    }

    /**
     * Check whether the given recipe is present in this recipebook.
     *
     * @param 	recipe
     *        	The recipe to check.
     * @return 	False if the given recipe is not effective.
     * 	   		| if (recipe == null)
     * 	   		| 	then result == false
     * @return 	True if a recipe equal to the given recipe is registered at some
     *         	position in this laboratory; false otherwise.
     *         	| result ==
     *         	|    for some I in 0..getNbOfRecipes()-1 :
     *         	| 	      (getRecipeAt(I) == recipe)
     */
    public boolean hasAsRecipe(Recipe recipe) {
        if (recipe == null) return false;
        for (int i=0; i<getNbOfRecipes(); i++) {
            if (getRecipeAt(i) == recipe) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method for getting the recipe at a given index.
     *
     * @param   index
     *          The index at which the recipe is to be returned.
     */
    @Basic
    public Recipe getRecipeAt(int index) {
        if (index >= 0 && index < getNbOfRecipes()) {
            return recipes.get(index);
        }
        return null;
    }

    /**
     * A method that gives back the index of a recipe in a recipe book.
     *
     * @param   recipe
     *          The recipe to get the index of.
     *
     * @return  If the recipe is present in the recipe book
     *          the index of the recipe is returned.
     *          | if hasAsRecipe(recipe)
     *          |   then getRecipeAt(result) == recipe
     * @return  Negative one is returned if the recipe is not present.
     *          | if !hasAsRecipe(recipe)
     *          |   then result == -1
     */
    @Basic
    public int getIndexOfRecipe(Recipe recipe) {
        for (int i = 0; i < getNbOfRecipes(); i++) {
            if (getRecipeAt(i) == recipe) return i;
        }
        // this should never happen
        assert false;
        return -1;
    }

    /**
     * A method for checking if a recipe book
     * can contain a given recipe.
     *
     * @param   recipe
     *          The recipe to check
     *
     * @return  If the given recipe is null,
     *          true is returned.
     *          | if (recipe == null)
     *          |   then result == true
     * @return  If the given recipe is not null,
     *          the result is true if and only if the recipe is not terminated
     *          and the recipe is not present twice in the recipe book.
     *          | if (recipe != null)
     *          |   then result == !recipe.isTerminated() && !containsRecipeTwice(recipe)
     */
    @Raw
    public boolean canHaveAsRecipe(Recipe recipe) {
        if (recipe == null) {
            return true;
        } else {
            return !recipe.isTerminated()
                    && !containsRecipeTwice(recipe);
        }
    }

    /**
     * A method for checking if a recipebook contains two of the same recipes.
     *
     * @return	True if and only if some recipe is present twice in this recipebook.
     *          | result == ( for some I in 0..getNbOfRecipes()-1:
     *          |   for some J in 0..getNbOfRecipes()-1:
     *          |       (I != J) && getRecipeAt(I) == getRecipeAt(J) )
     */
    @Raw @Model
    private boolean containsRecipeTwice(Recipe recipe) {
        int count = 0;
        for (int i=0; i<getNbOfRecipes(); i++) {
            if (getRecipeAt(i) == recipe) {
                count++;
            }
        }
        return count > 1;
    }


}
