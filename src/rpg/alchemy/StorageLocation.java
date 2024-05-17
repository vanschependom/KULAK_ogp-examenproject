package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.Unit;
import rpg.exceptions.IngredientNotPresentException;

import java.util.ArrayList;

/**
 * A class representing a location for a storage, i.e. a location that contains
 * ingredients and where ingredients can be added with a container.
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
     *          | new.getNbOfIngredients() == 0
     * @post    The new storage location is not yet terminated.
     *          | !new.isTerminated()
     * @throws  NullPointerException [can]
     *          | ? true
     */
    @Raw
    public StorageLocation() throws NullPointerException {
        super();
    }



    /**********************************************************
     * INGREDIENTS
     **********************************************************/

    /**
     * A variable for keeping track of the ingredients in a storage location
     *
     * @invar   ingredients references an effective list.
     *          | ingredients != null
     * @invar   Each ingredient in the list references an effective ingredient.
     *          | for each ingredient in ingredients:
     *          |   ingredient != null
     * @invar   Each ingredient in the list references a non-terminated ingredient.
     *          | for each ingredient in ingredients:
     *          |   !ingredient.isTerminated()
     * @invar   Each element in the list is unique (looking at the properties of the ingredient).
     *          | for each index in 0..getNbOfIngredients()-1:
     *          |   for each otherIndex in 0..getNbOfIngredients()-1:
     *          |       if index != otherIndex
     *          |           then !getIngredientAt(index).equals(getIngredientAt(otherIndex))
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
     *          | if ( for I in 0..getNbOfIngredients()-1:
     *          |       !canHaveAsIngredient(getIngredientAt(I)) )
     *          |   then result == false
     *
     * @note    We don't close the specification yet!
     */
    public boolean hasProperIngredients() {
        for (int i=0; i<getNbOfIngredients(); i++) {
            if (!canHaveAsIngredient(getIngredientAt(i))) {
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
     * @return  True if and only if the ingredient is effective,
     *          not terminated and not already in the storage location
     *          | result == (ingredient != null
     *          |   && !ingredient.isTerminated()
     *          |   && !containsIngredientTwice(ingredient) )
     */
    public boolean canHaveAsIngredient(AlchemicIngredient ingredient) {
        return (ingredient != null
                && !ingredient.isTerminated()
                && !containsIngredientTwice(ingredient));
    }

    /**
     * A method for checking if a storage location contains two of the same ingredients.
     *
     * @param   ingredient
     *          The ingredient to check
     * @return  True if and only if the ingredient is present twice or more in the storage location
     *          | result == ( for some I in 0..getNbOfIngredients()-1:
     *          |   for some J in 0..getNbOfIngredients()-1:
     *          |       (I != J) && getIngredientAt(I).equals(getIngredientAt(J)) )
     */
    public boolean containsIngredientTwice(AlchemicIngredient ingredient) {
        int count = 0;
        for (int i=0; i<getNbOfIngredients(); i++) {
            if (getIngredientAt(i).equals(ingredient)) {
                count++;
            }
        }
        return count > 1;
    }

    /**
     * A method for getting the ingredient at a given index.
     *
     * @param   index
     *          The index of the ingredient to be returned.
     * @throws  IndexOutOfBoundsException
     *          The index is negative or is bigger than the size of ingredients.
     *          | (index < 0) || (index > getNbOfIngredients())
     */
    public AlchemicIngredient getIngredientAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= getNbOfIngredients()) {
            throw new IndexOutOfBoundsException();
        }
        return ingredients.get(index);
    }

    /**
     * A method for removing an ingredient at a given index.
     *
     * @param   index
     *          The index of the ingredient to be removed
     * @throws  IndexOutOfBoundsException
     *          The index is negative or is bigger than the size of ingredients
     *          | (index < 0) || (index >= getNbOfIngredients())
     */
    public void removeIngredientAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= getNbOfIngredients()) {
            throw new IndexOutOfBoundsException();
        }
        ingredients.remove(index);
    }

    /**
     * Return the number of ingredients in this storage location.
     */
    @Basic
    public int getNbOfIngredients() {
        return ingredients.size();
    }

    /**
     * Return the ingredients of this laboratory.
     */
    private ArrayList<AlchemicIngredient> getIngredients() {
        return ingredients;
    }

    /**
     * A method for adding an ingredient to the storage location.
     *
     * @param   ingredient
     *          The ingredient to add.
     *
     * @post    The number of ingredients registered in this storage location is
     *          incremented with 1.
     *          | new.getNbOfIngredients() == getNbOfIngredients() + 1
     * @post    The given ingredient is inserted at the last index.
     *          | new.getIngredientAt(getNbOfIngredients()-1) == ingredient
     *
     * @throws  IllegalArgumentException
     *          The ingredient is not valid.
     *          | !canHaveAsIngredient(ingredient)
     * @throws  IllegalArgumentException
     *          The ingredient is already present.
     *          | hasAsIngredient(ingredient)
     * @throws  IllegalStateException [can]
     *          | ? true
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
     * A method for removing an ingredient from this storage location.
     *
     * @param   ingredient
     *          The ingredient to remove.
     *
     * @effect  The ingredient is removed from the storage location.
     *          | removeIngredientAt(getIndexOfIngredient(ingredient))
     *
     * @throws  IngredientNotPresentException
     *          The ingredient is not present in the storage location.
     *          | !hasAsIngredient(ingredient)
     * @throws  NullPointerException
     *          The ingredient is null.
     *          | ingredient == null
     */
    protected void removeAsIngredient(AlchemicIngredient ingredient) throws IngredientNotPresentException, NullPointerException {
        try {
            removeIngredientAt(getIndexOfIngredient(ingredient));
        } catch (IngredientNotPresentException e) {
            throw new IngredientNotPresentException();
        } catch (NullPointerException e) {
            throw new NullPointerException("Ingredient is null!");
        }
    }

    /**
     * A method for getting the index of a given ingredient, just looking at the properties
     * and not the actual object itself.
     *
     * @param   ingredient
     *          The ingredient to get the index of.
     *
     * @return  The index of the ingredient in the storage location, if it is present.
     *          | getIngredientAt(result).equals(ingredient)
     *
     * @throws  NullPointerException
     *          The given ingredient is null.
     *          | ingredient == null
     * @throws  IngredientNotPresentException
     *          The given ingredient is not present in the storage location.
     *          | !hasAsIngredient(ingredient)
     */
    public int getIndexOfIngredient(AlchemicIngredient ingredient) throws NullPointerException, IngredientNotPresentException {
        if (ingredient == null) {
            throw new NullPointerException("Ingredient is null!");
        }
        for (int i=0; i<getNbOfIngredients(); i++) {
            if (getIngredientAt(i).equals(ingredient)) {
                return i;
            }
        }
        // not found
        throw new IngredientNotPresentException();
    }

    /**
     * A method for checking if a storage location contains a given ingredient,
     * not looking at the object reference, but only looking at the characteristics
     *
     * @param   ingredient
     *          The ingredient to check
     *
     * @return  True if and only if the ingredient is present in the storage location
     *          | result == ( for some I in 0..getNbOfIngredients()-1:
     *          |   getIngredientAt(I).equals(ingredient) )
     *
     * @note    We use .equals() here and not '==' !
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
     * A method for checking if a storage location contains an ingredient with a given simple name.
     *
     * @param   name
     *          The simple name of the ingredient to check
     *
     * @return  True if and only if the storage location contains an ingredient with the given simple name
     *          | result == ( for some I in 0..getNbOfIngredients()-1:
     *          |   getIngredientAt(I).getSimpleName().equals(name) )
     */
    public boolean hasIngredientWithSimpleName(String name) {
    	for (int i=0; i < getNbOfIngredients(); i++) {
            if (getIngredientAt(i).getSimpleName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method for checking if a storage location contains an ingredient with a given special name.
     *
     * @param   name
     *          The special name of the ingredient to check
     *
     * @return  True if and only if the storage location
     *          contains an ingredient with the given special name
     *          | result == ( for some I in 0..getNbOfIngredients()-1:
     *          |   name.equals(getIngredientAt(i).getSpecialName()) )
     */
    public boolean hasIngredientWithSpecialName(String name) {
        for (int i=0; i < getNbOfIngredients(); i++) {
            if (name.equals(getIngredientAt(i).getSpecialName())) {
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
     * @effect  If the AlchemicIngredient inside the container is already present in the storage location,
     *          just looking at the properties and not the actual object, the already present object is removed
     *          a new object with the spoon amount of the old object and the new object combined, and the
     *          other properties of the old object is added; the old object is terminated.
     *          | if hasAsIngredient(container.getContent())
     *          |   then new.getNbOfIngredients() == getNbOfIngredients() &&
     *          |        for some I in 0..getNbOfIngredients()-1:
     *          |           if getIngredientAt(I).equals(container.getContent())
     *          |               then removeAsIngredient( getIngredientAt(I)) &&
     *          |                    addAsIngredient( new AlchemicIngredient(
     *          |                       getIngredientAt(I).getSpoonAmount() + container.getContent().getSpoonAmount(),
     *          |                       Unit.SPOON,
     *          |                       new Temperature(getIngredientAt(I).getTemperature()),
     *          |                       getIngredientAt(I).getType(),
     *          |                       getIngredientAt(I).getState()
     *          |                   ) )
     *          |                   && getIngredientAt(I).terminate()
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
        try {
            // if the ingredient is already present, combine them
            AlchemicIngredient alreadyInLocation = getIngredientAt(getIndexOfIngredient(container.getContent()));
            AlchemicIngredient replacement = new AlchemicIngredient(
                    alreadyInLocation.getSpoonAmount() + container.getContent().getSpoonAmount(),
                    Unit.SPOON,
                    new Temperature(alreadyInLocation.getTemperature()),
                    alreadyInLocation.getType(),
                    alreadyInLocation.getState());
            // delete the old ingredient
            removeAsIngredient(alreadyInLocation);
            // add the replacement
            addAsIngredient(replacement);
            // terminate the old ingredient
            alreadyInLocation.terminate();
        } catch (IngredientNotPresentException e) {
            addAsIngredient(container.getContent());
        }
        // terminate the container
        container.terminate();
    }



    /**********************************************************
     * DESTRUCTION
     **********************************************************/

    /**
     * A variable for keeping track of whether the storage location is terminated.
     */
    private boolean isTerminated = false;

    /**
     * Return whether the storage location is terminated.
     */
    @Basic @Raw
    public boolean isTerminated() {
        return isTerminated;
    }

    /**
     * A method for terminating this storage location.
     *
     * @post    If the storage location is not already terminated,
     *          the storage location becomes terminated.
     *          | if (!isTerminated())
     *          |   then new.isTerminated() == true
     * @throws  IllegalStateException
     *          The storage location is already terminated.
     *          | isTerminated()
     */
    public void terminate() throws IllegalStateException {
        if (isTerminated()) {
            throw new IllegalStateException("Already terminated!");
        }
    	isTerminated = true;
    }

}
