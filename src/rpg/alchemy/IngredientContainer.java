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
 *
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
     * @post    The capacity of this new container is equal to the given capacity if it's a valid capacity.
     *          | if (isValidCapacity(capacity))
     *          |   then new.getCapacity() == capacity
     * @post    The content of this new container is equal to the given content (an ingredient) if the
     *          content is legal for the container.
     *          | if (isValidContent())
     *          |   then new.getContent() == content
     *
     * @effect  The content is set to containerized.
     *          | content.setContainerized(true)
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
            throw new IllegalArgumentException("The given content is not legal for a container with the given capacity!");
        } else {
            this.content = content;           // final variable
        }
        content.setContainerized(true);
    }

    /**
     * A constructor for creating a new container with a given content and the minimum unit
     * for the container based on this content.
     *
     * @param   content
     *          The content of the new container (an ingredient).
     *
     * @effect  A new container is created with the minimum unit for the container based on the content.
     *          | this(Unit.getMinUnitForContainerWithIngredient(content), content)
     */
    public IngredientContainer(AlchemicIngredient content) throws IllegalArgumentException {
        this(Unit.getMinUnitForContainerWithIngredient(content), content);
    }



    /**********************************************************
     * INGREDIENT
     **********************************************************/

    /**
     * A variable containing the content (ingredient) that is stored in the container.
     */
    private final AlchemicIngredient content;

    /**
     * A method for getting the content of the container.
     */
    @Model @Basic @Immutable
    protected AlchemicIngredient getContent() {
        return content;
    }

    /**
     * A method to obtain the content of a container while
     * terminating the container itself.
     *
     * @effect  The container is terminated
     *          | this.terminate()
     *
     * @return  The content of the container
     *          | result == getContent()
     */
    @Raw
    public AlchemicIngredient obtainContent() {
        this.terminate(); // this makes sure that the ingredient is set to not containerized
    	return getContent();
    }

    /**
     * A method to check if the content (ingredient) could be stored in the container.
     *
     * @param   content
     *          The content that is to be checked.
     *
     * @return  If the content is a null pointer, return true.
     *          | if (content == null)
     *          |   then result == true
     * @return  If the amount of the effective content is less or equal than the capacity and
     *          if the capacity and the content share the same state and if the ingredient
     *          isn't terminated then return true, otherwise return false.
     *          | if (content != null) then
     *          |   result == (content.getSpoonAmount() <= getCapacity().getSpoonEquivalent())
     *          |            && ( getCapacity().hasAsAllowedState(content.getState()) )
     *          |            && ( !content.isTerminated())
     */
    @Raw
    public boolean canHaveAsContent(AlchemicIngredient content) {
        if (content == null) {
            return true;
        } else {
            return (content.getSpoonAmount() <= getCapacity().getSpoonEquivalent())
                    && ( getCapacity().hasAsAllowedState(content.getState()) )
                    && ( !content.isTerminated())
                    && ( !content.isContainerized());
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
    @Model @Raw
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
     * Gets the value of isTerminated.
     */
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    /**
     * Terminates the container.
     *
     * @post    The container is terminated.
     *          | new.isTerminated()
     *
     * @effect  The content is set to not containerized.
     *          | content.setContainerized(false)
     *
     * @throws  IllegalStateException
     *          The container is already terminated.
     *          | isTerminated()
     */
    public void terminate() throws IllegalStateException {
        if (isTerminated()){
            throw new IllegalStateException("The container is already terminated!");
        }
        content.setContainerized(false);
        isTerminated = true;
    }


    /**********************************************************
     * EQUALS
     **********************************************************/

    /**
     * A method for checking if this container is equal to another container.
     *
     * @param   other
     *          The other container to compare with.
     *
     * @return  True if and only if the capacity and the content of the two containers are equal.
     *          | result == (getCapacity() == other.getCapacity())
     *          |              && (getContent().equals(other.getContent()))
     */
    public boolean equals(IngredientContainer other) {
        return getCapacity() == other.getCapacity()
                && getContent().equals(other.getContent());
    }

}
