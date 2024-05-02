package rpg.ingredient;

import be.kuleuven.cs.som.annotate.*;
import rpg.State;
import rpg.Unit;

import java.util.ArrayList;

/**
 * A class representing an ingredient containers.
 *
 * @invar   A container contains proper ingredients.
 *          | hasProperIngredients()
 * @invar   A container has a valid capacity.
 *          | isValidCapacity(getCapacity())
 * @invar   A container has a valid state.
 *          | isValidState(getState())
 * @invar   A container has valid ingredient type.
 *          | isValidIngredientType(getIngredientType())
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class IngredientContainer {

    /*****************************
     * Constructor
     ****************************/

    /**
     * A constructor for a new container.
     *
     * @param   capacity
     *          The capacity of the new container.
     * @param   ingredientType
     *          The ingredient type that will be used for the new container.
     * @param   state
     *          The state of the container
     * @throws  IllegalArgumentException
     *          If one of the invariants is broken.
     */
    @Raw
    public IngredientContainer(Unit capacity, IngredientType ingredientType, State state) throws IllegalArgumentException {
        // check invariants
        if (!isValidCapacity(capacity)){
            throw new IllegalArgumentException("Invalid capacity or unit");
        }
        if (!isValidIngredientType(ingredientType)){
            throw new IllegalArgumentException("Invalid ingredient type");
        }
        if (!isValidState()){
            throw new IllegalArgumentException("Invalid state");
        }
        // set values if valid
        this.capacity = capacity;
        this.ingredientType = ingredientType;
        this.state = state;
        setTerminated(false);
    }

    /*****************************
     * Destructor
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

    @Basic
    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }

    public void terminate() {
        // TODO
        // elk ingredient uit de lijst verwijderen
    }

    /*****************************
     * (Alchemic) Ingredients
     ****************************/

    /**
     * A variable for keeping track of the ingredients in a capacity.
     *
     * @invar   ingredients references an effective list
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
     * Gets the list of ingredients.
     */
    @Basic
    private ArrayList<AlchemicIngredient> getIngredients() {
        return ingredients;
    }

    /**
     * Return the number of ingredients in container.
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
     *          |
     * @throws  IllegalArgumentException
     *          If the ingredient is not of the same type.
     *          If the ingredient is  already in the container.
     */
    public void addIngredient(AlchemicIngredient ingredient) throws IllegalArgumentException {
        if (!canHaveAsIngredient(ingredient)) {
            throw new IllegalArgumentException("Ingredient is not from the correct type.");
        }
        if (hasAsIngredient(ingredient)) {
            throw new IllegalArgumentException("Ingredient is already in container");
        }
        ingredients.add(ingredient);
    }

    public boolean hasAsIngredient(AlchemicIngredient ingredient) {
    }

    private boolean canHaveAsIngredient(AlchemicIngredient ingredient) {
        // TODO check of de ingredient van juiste type is
    }

    private boolean hasProperIngredients() {
    }

    /*****************************
     * Capacity (Unit)
     ****************************/

    /**
     * A variable with the unit of the container.
     */
    private final Unit capacity;

    /**
     * Get the capacity of the container
     */
    @Basic @Immutable
    public Unit getCapacity() {
        return capacity;
    }

    /**
     * A method to check if the container has a valid unit
     * @return  False if the capacity is a drop, a pinch or a storeroom, else true
     *          | result == !capacity.equals(Unit.DROP) && !capacity.equals(Unit.PINCH)
     *                      && !capacity.equals(Unit.STOREROOM)
     */
    @Model
    private boolean isValidCapacity(Unit capacity) {
        return !capacity.equals(Unit.DROP) && !capacity.equals(Unit.PINCH)
                && !capacity.equals(Unit.STOREROOM);
    }

    /*****************************
     * Ingredient Type
     ****************************/

    /**
     * A variable that holds the ingredient type that can be in the container.
     */
    private IngredientType ingredientType;

    /**
     * Get the ingredient type that can be in the container.
     */
    @Basic
    public IngredientType getIngredientType() {
        return ingredientType;  // TODO maak kopie
    }

    @Basic
    private void setIngredientType(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
    }

    private boolean isValidIngredientType(IngredientType ingredientType) {
        // TODO
    }

    /*****************************
     * State ??
     ****************************/

    private State state;

    /**
     * Get the state of a container.
     */
    @Basic
    public State getState() {
        return state;
    }

    @Basic
    private void setState(State state) {
        this.state = state;
    }

    private boolean isValidState(){
        return state != null;
    }

}
