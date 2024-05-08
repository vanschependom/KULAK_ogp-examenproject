package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.Unit;

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
     * @post    The capacity of this new container is equal to the given capacity.
     *          | new.getCapacity() == capacity
     * @post    The content of this new container is equal to the given content (an ingredient).
     *          | new.getContent() == content
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
            this.capacity = capacity;           // final variable
        }
        if (!canHaveAsContent(content)){
            throw new IllegalArgumentException("The given content is not legal!");
        } else {
            this.content = content;           // final variable
        }
    }

    /**
     * A method for creating a new container with a given content and the minimum unit
     * for the container based on this content.
     *
     * @param   content
     *          The content of the new container (an ingredient).
     * @effect  A new container is created with the minimum unit for the container based on the content.
     *          | this(Unit.getMinUnitForContainerWith(content), content)
     */
    public IngredientContainer(AlchemicIngredient content) throws IllegalArgumentException {
        this(Unit.getMinUnitForContainerWith(content), content);
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

    /**
     * A method to check if the content (ingredient) could be stored in the container.
     *
     * @param   content
     *          The content that you want to check
     * @return  If the content is a null pointer, return true.
     *          | if (content == null)
     *          | then result == true
     * @return  If the amount of the content is less or equal than the capacity and
     *          if the capacity and the content share the same state and if the ingredient
     *          isn't terminated then return true, otherwise return false.
     *          | result == (content.getSpoonAmount() <= getCapacity().getSpoonEquivalent())
     *                      && ( getCapacity().hasAsAllowedState(content.getState()) )
     *                      && ( !content.isTerminated())
     */
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
        if (!isTerminated()){
            isTerminated = true;
            content = null;
        }
    }

}
