package rpg.ingredient;

import be.kuleuven.cs.som.annotate.*;
import rpg.State;
import rpg.Unit;

import java.util.ArrayList;

/**
 * A class representing an ingredient containers.
 *
 * @invar   A container must always contain proper ingredients.
 *          | hasProperIngredients()
 * @invar   A container must always have a valid capacity.
 *          | isValidCapacity(getCapacity())
 * @invar   A container must always have a valid state.
 *          | isValidState(getState())
 * @invar   A container must always have a valid ingredient type.
 *          | isValidIngredientType(getIngredientType())
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class IngredientContainer {

    /**********************************************************
     * CONSTRUCTORS
     **********************************************************/

    /**
     * A constructor for creating a new container with a given capacity, ingredient type and state.
     *
     * @param   capacity
     *          The capacity of the new container.
     * @param   ingredientType
     *          The ingredient type that will be used for the new container.
     * @param   state
     *          The state of the container
     *
     * @throws  IllegalArgumentException
     *          The given capacity is not valid.
     *          | !isValidCapacity(capacity)
     * @throws  IllegalStateException
     *          The given state is not valid.
     *          | !isValidState(state)
     */
    @Raw
    public IngredientContainer(Unit capacity, IngredientType ingredientType, State state) throws IllegalArgumentException, IllegalStateException {
        // check invariants
        if (!isValidCapacity(capacity)) {
            throw new IllegalArgumentException("The given unit is not a valid capacity!");
        }
        if (!isValidState(state)) {
            throw new IllegalStateException("Invalid state!");
        }
        if (!isValidIngredientType(ingredientType)){
            this.ingredientType = IngredientType.DEFAULT;   // static variable
        } else {
            this.ingredientType = ingredientType;           // static variable
        }
        // set values if valid
        this.capacity = capacity;               // exceptional cases already checked & static
        this.state = state;                     // exceptional cases already checked & static
    }



    /**********************************************************
     * INGREDIENTS
     **********************************************************/

    /**
     * A variable for keeping track of the ingredients in a capacity.
     *
     * @invar   This ArrayList of ingredients is an effective list
     *          | ingredients != null
     * @invar   Each ingredient in the list references an effective ingredient.
     *          | for each ingredient in ingredients:
     *          |   ingredient != null
     * @invar   Each ingredient in the list references a non-terminated ingredient.
     *          | for each ingredient in ingredients:
     *          |   !ingredient.isTerminated()
     *
     * @note    Deze relatie is unidirectioneel
     */
    private final ArrayList<AlchemicIngredient> ingredients = new ArrayList<>();

    /**
     * A private method for getting the internal List of ingredients in the container.
     */
    private ArrayList<AlchemicIngredient> getIngredients() {
        return ingredients;
    }

    /**
     * Return the number of ingredients in container.
     *
     * @return  The number of ingredients in the container.
     *          | result == ingredients.size()
     */
    @Basic
    public int getNbOfIngredients() {
        return ingredients.size();
    }

    /**
     * Get the ingredient at the given index.
     *
     * @param   index
     *          The index of the ingredient
     * @return  The ingredient at the given index.
     *          | result == TODO (kijk naar directory)
     */
    public AlchemicIngredient getIngredientAtIndex(int index) {
        return ingredients.get(index);
    }

    /**
     * A method for adding an ingredient to the container.
     *
     * @param   ingredient
     *          The ingredient to be added.
     * @post    If the ingredient is valid then the number of ingredients in the
     *          container increases.
     *          | TODO
     * @throws  IllegalArgumentException
     *          If the ingredient is not of the same type.
     *          If the ingredient is  already in the container.
     */
    public void addIngredient(AlchemicIngredient ingredient) throws IllegalArgumentException {
        if (!canHaveAsIngredient(ingredient)) {
            throw new IllegalArgumentException("Ingredient is not from the correct type.");
        }
        if (hasAsIngredient(ingredient)) {
            throw new IllegalArgumentException("Ingredient is already in container!");
        }
        ingredients.add(ingredient);
    }

    public boolean hasAsIngredient(AlchemicIngredient ingredient) {
        return false; //TODO
    }

    private boolean canHaveAsIngredient(AlchemicIngredient ingredient) {
        return false;// TODO check of de ingredient van juiste type is
    }

    private boolean hasProperIngredients() {
        return false; //TODO
    }



    /**********************************************************
     * CAPACITY
     **********************************************************/

    /**
     * A variable containing the unit that expresses the capacity of the container.
     */
    private final Unit capacity;

    /**
     * A method for getting the capacity of the container.
     */
    @Basic @Immutable
    public Unit getCapacity() {
        return capacity;
    }

    /**
     * A method to check if the container has a valid capacity.
     *
     * @return  True if and only if the capacity is not null and is allowed for a container.
     *          | result == (capacity != null && capacity.isAllowedForContainer())
     */
    @Model
    private static boolean isValidCapacity(Unit capacity) {
        return capacity != null && capacity.isAllowedForContainer();
    }



    /**********************************************************
     * INGREDIENT TYPE
     **********************************************************/

    /**
     * A variable that holds the ingredient type that can be in the container.
     */
    private final IngredientType ingredientType;

    /**
     * Get the ingredient type that can be in the container.
     */
    @Basic @Immutable
    public IngredientType getIngredientType() {
        return ingredientType;  // TODO maak kopie
    }

    private boolean isValidIngredientType(IngredientType ingredientType) {
        return false; // TODO
    }



    /*****************************
     * STATE
     ****************************/

    /**
     * A variable that holds the state of the container.
     */
    private final State state;

    /**
     * Get the state of a container.
     */
    @Basic @Immutable
    public State getState() {
        return state;
    }

    /**
     * A method to check if the state of the container is valid.
     *
     * @param   state
     *          The state to check.
     * @return  True if and only if the state is not null.
     *          | result == (state != null)
     */
    public static boolean isValidState(State state){
        return state != null;
    }



    /*****************************
     * DESTRUCTORS
     ****************************/

    /**
     * A variable for keeping track of whether the container is terminated.
     */
    private boolean isTerminated = false;

    /**
     * Gets the value of isTerminated
     */
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    public void terminate() {
        // TODO (setTerminated() niet nodig!)
        // elk ingredient uit de lijst verwijderen
    }



}
