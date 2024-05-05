package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.Unit;

import java.util.ArrayList;

/**
 * A class representing a location for a storage
 *
 * @invar   The ingredients in a storage location must be valid.
 *          | hasProperIngredients()
 *
 * @note    This class is worked out using Defensive Programming
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author 	Flor De Meulemeester
 * @version 1.0
 */
public abstract class StorageLocation {

    /**********************************************************
     * CONSTRUCTORS
     **********************************************************/

    /**
     * A constructor for creating a new storage location.
     *
     * @post    The new storage location has no ingredients.
     *          | new.isEmpty()
     */
    public StorageLocation() {
        //
    }



    /**********************************************************
     * INGREDIENTS
     **********************************************************/

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
     *          | for each index in 0..getNbOfIngredients()-1:
     *          |   for each otherIndex in 0..getNbOfIngredients()-1:
     *          |       if index != otherIndex
     *          |           then !getIngredientAt(index).equals(getIngredientAt(otherIndex)
     */
    private final ArrayList<AlchemicIngredient> ingredients = new ArrayList<>();

    /**
     * A method for checking if a storage location is empty.
     *
     * @return  True if and only if the storage location has no ingredients.
     *          | result == (getNbOfIngredients() == 0)
     */
    public boolean isEmpty() {
        return getNbOfIngredients() == 0;
    }

    /**
     * A method for checking if the ingredients in a storage location are valid.
     *
     * @return  False if there is an ingredient that is not valid
     *          | if ( for some ingredient in ingredients:
     *          |       !canHaveAsIngredient(ingredient) )
     *          |   then result == false
     *
     * @note    We don't close the specification yet!
     */
    public boolean hasProperIngredients() {
        for (AlchemicIngredient ingredient : ingredients) {
            if (!canHaveAsIngredient(ingredient)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A method for checking if a storage location can contain a given ingredient
     *
     * @param   ingredient
     *          The ingredient to check
     *
     * @return  True if and only if the ingredient is effective,
     *          not terminated and not already in the storage location
     *          | result == (ingredient != null && !ingredient.isTerminated() && !containsTwice(ingredient))
     */
    public boolean canHaveAsIngredient(AlchemicIngredient ingredient) {
        return (ingredient != null
                && !ingredient.isTerminated()
                && !containsTwice(ingredient));
    }

    /**
     * A method for checking if a storage location contains two of the same ingredients
     *
     * @param   ingredient
     *          The ingredient to check
     * @return  True if and only if the ingredient is present twice or more in the storage location
     *          | result == ( for some I in 0..getNbOfIngredients()-1:
     *          |   for some J in 0..getNbOfIngredients()-1:
     *          |       (I != J) && getIngredientAt(I).equals(getIngredientAt(J)) )
     */
    public boolean containsTwice(AlchemicIngredient ingredient) {
        int count = 0;
        for (AlchemicIngredient ing : ingredients) {
            if (ing.equals(ingredient)) count++;
        }
        return count > 1;
    }

    /**
     * A method for getting the ingredient at a given index.
     *
     * @param   index
     *          The index of the ingredient to be returned
     * @throws  IndexOutOfBoundsException
     *          The index is negative or is bigger than the size of ingredients
     *          | (index < 0) || (index > getNbOfIngredients())
     */
    @Basic
    public AlchemicIngredient getIngredientAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= getNbOfIngredients()) {
            throw new IndexOutOfBoundsException();
        }
        return ingredients.get(index);
    }

    /**
     * Return the number of ingredients in container.
     */
    @Basic
    public int getNbOfIngredients() {
        return ingredients.size();
    }

    /**
     * A method for adding an ingredient to the storage location
     *
     * @param   ingredient
     *          The ingredient to add
     *
     * @post    The ingredient is added to the storage location, on the last index
     *          | new.getNbOfIngredients() == getNbOfIngredients() + 1 &&
     *          | new.getIngredientAt(new.getNbOfIngredients()-1) == ingredient &&
     *          | new.hasAsIngredient(ingredient)
     *
     * @throws  IllegalArgumentException
     *          The ingredient is not valid
     *          | !canHaveAsIngredient(ingredient)
     * @throws  IllegalArgumentException
     *          The ingredient is already present
     *          | hasAsIngredient(ingredient)
     */
    protected void addAsIngredient(AlchemicIngredient ingredient) throws IllegalArgumentException {
        if (!canHaveAsIngredient(ingredient)) {
            throw new IllegalArgumentException("Invalid ingredient!");
        }
        if (hasAsIngredient(ingredient)) {
            throw new IllegalArgumentException("Ingredient already present!");
        }
        ingredients.add(ingredient);
    }

    /**
     * A method for getting the index of a given ingredient.
     *
     * @param   ingredient
     *          The ingredient to get the index of.
     *
     * @return  The index of the ingredient in the storage location.
     *          | getItemAt(result).equals(ingredient)
     *
     * @throws  IllegalArgumentException
     *          The ingredient is not present in the storage location
     *          | !hasAsIngredient(ingredient)
     * @throws  NullPointerException
     *          The given ingredient is null
     *          | ingredient == null
     */
    public int getIndexOf(AlchemicIngredient ingredient) throws IllegalArgumentException {
        if (ingredient == null) {
            throw new NullPointerException();
        }
        if (!hasAsIngredient(ingredient)) {
            throw new IllegalArgumentException("This ingredient is not present!");
        }
        else {
            for (int i=0; i<getNbOfIngredients(); i++) {
                if (getIngredientAt(i).equals(ingredient)) {
                    return i;
                }
            }
            //this will never happen!
            assert false;
            return -1;
        }
    }

    /**
     * A method for checking if a storage location contains a given ingredient.
     *
     * @param   ingredient
     *          The ingredient to check
     *
     * @return  True if and only if the ingredient is present in the storage location
     *          | result == ( for some I in 0..getNbOfIngredients()-1:
     *          |   getIngredientAt(I).equals(ingredient) )
     */
    public boolean hasAsIngredient(AlchemicIngredient ingredient) {
        for (int i=0; i < getNbOfIngredients(); i++) {
            if (getIngredientAt(i).equals(ingredient)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method for adding a container to the storage location, unpacking
     * the contents of the container and adding them to the ingredients ArrayList.
     *
     * @param   container
     *          The container of which the contents should be added to the storage location.
     *
     * @post    If the AlchemicIngredient inside the container is already present in the storage location,
     *          just looking at the properties and not the actual object, the already present object is replaced
     *          with a new object with the spoon amount of the old object and the new object combined, and the
     *          other properties of the old object; the old object is terminated.
     *          | if hasAsIngredient(container.getContent())
     *          |   then new.getNbOfIngredients() == getNbOfIngredients() &&
     *          |        for some I in 0..getNbOfIngredients()-1:
     *          |           if getIngredientAt(I).equals(container.getContent())
     *          |               then new.getIngredientAt(I).getSpoonAmount() == getIngredientAt(I).getSpoonAmount() + container.getContent().getSpoonAmount() &&
     *          |                    new.getIngredientAt(I).getTemperature().equals(getIngredientAt(I).getTemperature()) &&
     *          |                    new.getIngredientAt(I).getType() == getIngredientAt(I).getType() &&
     *          |                    new.getIngredientAt(I).getState() == getIngredientAt(I).getState() &&
     *          |                    new.getIngredientAt(I).isTerminated()
     *
     * @effect  If the AlchemicIngredient inside the container is not already present in the storage location,
     *          the AlchemicIngredient is added to the storage location.
     *          | if !hasAsIngredient(container.getContent())
     *          |   then addAsIngredient(container.getContent())
     *
     * @throws  NullPointerException
     *          The provided container is not effective.
     *          | container == null
     * @throws  IllegalArgumentException
     *          The contents of the container are not valid.
     *          | !canHaveAsIngredient(container.getContent())
     */
    public void addIngredients(IngredientContainer container) throws NullPointerException, IllegalArgumentException {
        if (container == null) {
            throw new NullPointerException("Container is null!");
        }
        if (!canHaveAsIngredient(container.getContent())) {
            throw new IllegalArgumentException("Invalid contents in container!");
        }
        if (hasAsIngredient(container.getContent())) {
            int index = getIndexOf(container.getContent());
            AlchemicIngredient alreadyInLocation = getIngredientAt(index);
            AlchemicIngredient replacement = new AlchemicIngredient(
                    alreadyInLocation.getSpoonAmount() + container.getContent().getSpoonAmount(),
                    Unit.SPOON,
                    new Temperature(alreadyInLocation.getColdness(), alreadyInLocation.getHotness()),
                    alreadyInLocation.getType(),
                    alreadyInLocation.getState());
            // replace the old ingredient with the new one
            ingredients.set(index, replacement);
            // terminate the old ingredient (and thus the temperature)
            alreadyInLocation.terminate();
        }
        else {
            addAsIngredient(container.getContent());
        }
        // set containerized to false
        container.getContent().setContainerized(false);
        // terminate the container
        container.terminate();
    }

}
