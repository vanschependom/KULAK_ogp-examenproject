package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.Unit;

import java.util.List;

/**
 * A class representing an ingredient containers.
 *
 * @invar   A container must always have a valid content.
 *          | canHaveAsContent(getContent())
 * @invar   A container must always have a valid capacity.
 *          | isValidCapacity(getCapacity())
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
     * @param   content
     *          The contents of the new container (an ingredient).
     *
     * @throws  IllegalArgumentException
     *          The given capacity is not valid.
     *          | !isValidCapacity(capacity)
     * @throws  IllegalArgumentException
     *          The given content is not valid.
     *          | !canHaveAsContent(content)
     */
    @Raw
    public IngredientContainer(Unit capacity, AlchemicIngredient content) throws IllegalArgumentException {
        // check invariants
        if (!isValidCapacity(capacity)) {
            throw new IllegalArgumentException("The given unit is not a valid capacity!");
        } else {
            this.capacity = capacity;           // static variable
        }
        if (!canHaveAsContent(content)){
            throw new IllegalArgumentException("The given content is not legal!");
        } else {
            this.content = content;           // static variable
        }
    }



    /**********************************************************
     * INGREDIENT
     **********************************************************/

    /**
     * A variable containing the content (ingredient) that is stored in the container.
     */
    private AlchemicIngredient content;

    /**
     * A method for getting the content of the container.
     */
    @Model
    protected AlchemicIngredient getContent() {
        return content;
    }

    public boolean canHaveAsContent(AlchemicIngredient content) {
        if (content == null) {
            return true;
        } else {
            return (content.getSpoonAmount() <= getCapacity().getSpoonEquivalent())
                    && ( getCapacity().hasAsAllowedState(content.getState()) )
                    && ( !content.isTerminated());
        }
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

    /**
     * Terminates the container.
     */
    public void terminate() {
        isTerminated = true;
        content = null;
    }



}
