package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.Unit;

import java.util.ArrayList;

/**
 * A class representing a location for a storage
 *
 * @invar   The ingredients inside the storage location are valid
 *          | canHaveAsIngredients(getIngredients())
 *
 * @note    We doen aan DEFENSIVE programming
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author 	Flor De Meulemeester
 * @version 1.0
 */
abstract public class StorageLocation {

    /**
     * A variable for keeping track of the ingredients in a storage location
     *
     * @invar   ingredients references an effective list
     *          | ingredients != null
     * @invar   Each ingredient in the list references an effective ingredient.
     *          | for each ingredient in ingredients:
     *          |   ingredient != null
     * @invar   Each ingredient in the list references a non-terminated ingredient.
     *          | for each ingredient in ingredients:
     *          |   !ingredient.isTerminated()
     * @invar   Each element in the list is unique
     *          | for each index in 1..getNbOfIngredients():
     *          |   for each otherIndex in 1..getNbOfIngredients():
     *          |       if index != otherIndex
     *          |       then !getIngredientAt(index).equalsIngredient(getIngredientAt(otherIndex)
     */
    private ArrayList<AlchemicIngredient> ingredients = new ArrayList<>();

    /*****************************
     * Constructors
     ****************************/

    /**
     * A constructor for a storage location
     *
     * @param   ingredients
     *          The ingredients to be put into the storage location
     *
     * @post    A new storage location is created.
     *          The ingredients of the storage location are set to ingredients.
     *          | new.getIngredients() == ingredients
     *
     * @throws  IllegalArgumentException
     *          the given ingredients are not valid
     *          | !canHaveAsIngredients(ingredients)
     */
    protected StorageLocation(ArrayList<AlchemicIngredient> ingredients) throws IllegalArgumentException{
        if (!canHaveAsIngredients(ingredients)) throw new IllegalArgumentException();
        this.ingredients = ingredients;
    }

    /**
     * A constructor for a storage location
     *
     * @effect  A new storage location with an empty ingredient list is created
     *          | this(new ArrayList<AlchemicIngredient>())
     */
    protected StorageLocation() {
        this(new ArrayList<AlchemicIngredient>());
    }

    /**
     * Returns the ingredient list
     */
    @Model
    protected ArrayList<AlchemicIngredient> getIngredients() {
        return ingredients;
    }

    /**
     * A method for checking if a list is capable
     * of being an ingredients list for a storage
     *
     * @param   ingredients
     *          The ingredients list to check
     *
     * @return  False if an ingredient is not unique, an ingredient is not valid
     *          or ingredients is a null reference
     *          | TODO
     */
    public boolean canHaveAsIngredients(ArrayList<AlchemicIngredient> ingredients) {
        if (ingredients == null) return false;
        for (int i = 0; i < getNbOfIngredients(); i++) {
            if (!canHaveAsIngredient(getIngredientAt(i))) return false;
            for (int j = i + 1; j < getNbOfIngredients(); j++) {
                if (getIngredientAt(i).equals(getIngredientAt(j))) { //TODO equals uitwerken
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * A method for checking if a storage can contain a given ingredient
     *
     * @param   ingredient
     *          The ingredient to check
     *
     * @return  False if ingredient is a null reference
     *          or if the ingredient is terminated
     *          | if ingredient == null || ingredient.isTerminated()
     *          | then result == false
     */
    public boolean canHaveAsIngredient(AlchemicIngredient ingredient) {
        return (ingredient != null && !ingredient.isTerminated());
    }

    /**
     * Returns ingredient at a given index
     *
     * @param   index
     *          The index of the ingredient to be returned
     * @throws  IllegalArgumentException
     *          The index is negative or is bigger than the size of ingredients
     *          | (index < 0) || (index > getNbOfIngredients())
     */
    @Basic
    public AlchemicIngredient getIngredientAt(int index) throws IllegalArgumentException {
        if (index < 0 || index >= ingredients.size()) throw new IllegalArgumentException();
        return getIngredients().get(index); // TODO een kopie
    }

    /**
     * Return the number of ingredients in container.
     */
    @Basic
    public int getNbOfIngredients() {
        return ingredients.size();
    }

    /**
     * Return the index of an exact ingredient
     *
     * @param   ingredient
     *          The ingredient to get the index of
     * @throws  NullPointerException
     *          The ingredient to get the index of is a null pointer
     *          | ingredient == null
     * @throws  IllegalArgumentException
     *          The exact ingredient does not exist in the ingredients list
     *          | !getIngredients().contains(ingredient)
     */
    @Basic
    public int getIndexOfIngredient(AlchemicIngredient ingredient)
            throws NullPointerException, IllegalArgumentException {
        if (ingredient == null) throw new NullPointerException();
        if (!getIngredients().contains(ingredient)) throw new IllegalArgumentException();
        return getIngredients().indexOf(ingredient);
    }

    /**
     * Return the index of an ingredient with the same
     * IngredientType, State and Temperature
     *
     * @param   ingredient
     *          The ingredient to get the index of
     *
     * @throws  NullPointerException
     *          The ingredient is a null pointer
     *          | ingredient == null
     * @throws  IllegalArgumentException
     *          An ingredient with the same type, state and temperature
     *          does not exist in the ingredients list
     *          | for each otherIngredient in getIngredients():
     *          |   !otherIngredient.equals(ingredient) //TODO kan nog andere naam van methode zijn
     */
    @Basic
    public int getIndexOfIngredientNonSensitive(AlchemicIngredient ingredient)
            throws NullPointerException, IllegalArgumentException {
        // Method that does not check the quantity or pointer of ingredient
        if (ingredient == null) throw new NullPointerException();
        for (int i = 0; i < getNbOfIngredients(); i++) {
            if (getIngredientAt(i).equals(ingredient)) return i;
        }
        throw new IllegalArgumentException();
    }

    /**
     * A method for adding an ingredient that is inside a container
     * to a storage location
     *
     * @param   container
     *          The container containing the ingredient to add
     * @post    The number of ingredients is incremented by one
     *          if there is no ingredient that is equal to the ingredient
     *          | if for each ingredient in getIngredients():
     *          |   !ingredient.equals(container.getContent())
     *          | then new.getNbOfIngredients() == getNbOfIngredients()+1
     *          TODO
     * @throws  IllegalArgumentException
     *          The container is a null reference
     *          or the ingredient inside the container is not valid for this storage
     *          | container == null || !canHaveAsIngredient(container.getContent())
     */
    public void addIngredient(IngredientContainer container) throws IllegalArgumentException{
        if (container == null || !canHaveAsIngredient(container.getContent())) throw new IllegalArgumentException();
        for (int i = 0; i < getNbOfIngredients(); i++) {
            if (getIngredientAt(i).equals(container.getContent())) {
                AlchemicIngredient ing = getIngredientAt(i);
                AlchemicIngredient addedIngredient = new AlchemicIngredient(ing.getSpoonAmount() + container.getContent().getSpoonAmount(),
                        Unit.SPOON, new Temperature(ing.getColdness(), ing.getHotness()), ing.getType(), ing.getState());
                getIngredients().set(i, addedIngredient);
                container.getContent().setContainerized(false);
                container.terminate();
                return;
            }
        }
        getIngredients().add(container.getContent());
        container.getContent().setContainerized(false);
        container.terminate();
    }

}
