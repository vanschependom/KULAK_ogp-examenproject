package rpg.recipe;

import be.kuleuven.cs.som.annotate.*;
import rpg.alchemy.AlchemicIngredient;

import java.util.ArrayList;

/**
 * A class representing a recipe in a recipeBook
 *
 * @invar   The instruction set of the recipe is valid.
 *          | isValidInstructionSet(getIngredients(), getOperations())
 *
 * @note    We implement this class using Total Programming.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author 	Flor De Meulemeester
 * @version 1.0
 */
public class Recipe {

    /**
     * A variable for keeping track of all ingredients of the recipe.
     *
     * @invar   ingredients references an effective list.
     *          | ingredients != null
     * @invar   Each ingredient in the list references an effective ingredient.
     *          | for each ingredient in ingredients:
     *          |   ingredient != null
     * @invar   Each ingredient in the list references a non-terminated ingredient.
     *          | for each ingredient in ingredients:
     *          |   !ingredient.isTerminated()
     */
    private ArrayList<AlchemicIngredient> ingredients = new ArrayList<AlchemicIngredient>();

    /**
     * A variable for keeping track of all instructions of the recipe.
     *
     * @invar   operations references an effective list.
     *          | operations != null
     * @invar   Each operation in the list references an effective operation.
     *          | for each operation in operations:
     *          |   operation != null
     */
    private ArrayList<Operation> operations = new ArrayList<Operation>();



    /**********************************************************
     * Constructors
     **********************************************************/

    /**
     * A constructor for creating a new recipe with a given set of ingredients and operations.
     *
     * @post    There are no instructions in the recipe.
     *          | new.getNbOfInstructions() == 0
     * @post    There are no ingredients in the recipe.
     *          | new.getNbOfIngredients() == 0
     */
    @Raw
    public Recipe() {
        super();
    }



    /**********************************************************
     * Ingredients/Operations: Instructions
     **********************************************************/

    /**
     * A method for checking if an instruction set consisting of
     * ingredients and operations is valid.
     *
     * @param   ingredients
     *          The ingredients list to check.
     * @param   operations
     *          The operations list to check.
     *
     * @return  False if either ingredients or operations is a null reference.
     *          | if (ingredients == null || operations == null)
     *          | then result == false
     * @return  False if one of the instructions is not valid.
     *          | result == ( for some I in 0..getNbOfOperations()-1:
     *          |   if (getOperationAt(I) == Operation.ADD)
     *          |       then !isValidInstruction( getIngredientAt(getOperations.subList(0, I).stream().filter(operation -> operation == Operation.ADD).count()), getOperationAt(I) )
     *          |   else
     *          |   then !isValidInstruction(null, getOperationAt(I))
     *          |   (I != J) && getDeviceAt(I).getClass() == getIngredientAt(J).getClass() )
     * @return  Otherwise true if the amount of operations in operations
     *          which are equal to the add operation,
     *          is equal to the size of ingredients.
     *          | result == ( ingredients.size() ==
     *          |   getOperations.stream().filter(operation -> operation == Operation.ADD).count() )
     *
     */
    public boolean isValidInstructionSet(ArrayList<AlchemicIngredient> ingredients, ArrayList<Operation> operations) {
        if (ingredients == null || operations == null) {
            return false;
        }
        int nbOfAdds = 0;
        for (Operation operation : operations) {
            if (operation == Operation.ADD) {
                if (!isValidInstruction(getIngredientAt(nbOfAdds), operation)) {
                    return false;
                }
                nbOfAdds++;
            } else {
                if (!isValidInstruction(null, operation)) {
                    return false;
                }
            }
        }
        return ingredients.size() == nbOfAdds;

    }

    /**
     * A method for checking if a certain instruction is valid.
     *
     * @param   ingredient
     *          The ingredient to check.
     * @param   operation
     *          The operation to check.
     *
     * @return  True if and only if either the operation is an add operation
     *          and the ingredient is not null and not terminated.
     *          Or if the operation is not null, not an add operation and the ingredient is null.
     *          | result ==
     *          |   ( operation == Operation.ADD && ingredient != null && !ingredient.isTerminated()) ||
     *          |   ( operation != null && operation != Operation.ADD && ingredient == null )
     */
    public boolean isValidInstruction(AlchemicIngredient ingredient, Operation operation) {
        return ((operation == Operation.ADD && ingredient != null && !ingredient.isTerminated()) ||
                (operation != null && operation != Operation.ADD && ingredient == null));
    }

    /**
     * A method for adding an ingredient to the ingredients of the recipe.
     *
     * @param   ingredient
     *          The ingredient to be added.
     *
     * @post    The number of ingredients of this recipe is
     *          incremented with 1.
     *          | new.getNbOfIngredients() == getNbOfIngredients() + 1
     * @post    The given ingredient is inserted at the end of the list.
     *          | new.getIngredientAt(getNbOfIngredients()-1) == ingredient
     */
    @Model @Raw
    private void addAsIngredient(AlchemicIngredient ingredient) {
        if (isValidIngredient(ingredient)) {
            ingredients.add(ingredient);
        }
    }

    public boolean isValidIngredient(AlchemicIngredient ingredient) {
        return ingredient != null && !ingredient.isTerminated();
    }

    /**
     * A method for adding an operation to the operations of the recipe.
     *
     * @param   operation
     *          The operation to be added.
     *
     * @post    The number of operations of this recipe is
     *          incremented with 1.
     *          | new.getNbOfOperations() == getNbOfOperations() + 1
     * @post    The given operation is inserted at the end of the list.
     *          | new.getOperationAt(getNbOfOperations()-1) == operation
     */
    @Model @Raw
    private void addAsOperation(Operation operation) {
        if (isValidOperation(operation)) {
            operations.add(operation);
        }
    }

    public boolean isValidOperation(Operation operation) {
        return operation != null;
    }

    /**
     * A method for adding an instruction to a recipe.
     *
     * @param   ingredient
     *          The ingredient to be added as part of the instruction.
     *          If the operation does not require an ingredient, null can be provided.
     * @param   operation
     *          The operation to be added as part of the instruction.
     *
     * @effect  TODO
     *          | if isValidInstruction(ingredient, operation) && ingredient != null
     *          |   addAsIngredient(ingredient)
     * @effect  TODO
     *          | if isValidInstruction(ingredient, operation)
     *          |   addAsOperation(operation)
     */
    @Raw
    public void addAsInstruction(AlchemicIngredient ingredient, Operation operation) {
        if (isValidInstruction(ingredient, operation)) {
            if (ingredient != null) {
                addAsIngredient(ingredient);
            }
            addAsOperation(operation);
        }
    }

    /**
     * A method for adding an instruction to a recipe.
     *
     * @param   operation
     *          The operation to be added as part of the instruction.
     * @effect  The operation with a null reference ingredient is added.
     *          | addInstruction(null, operation)
     * @note    This method is a convenience overloaded method for adding an operation.
     */
    @Raw
    public void addAsInstruction(Operation operation) {
        addAsInstruction(null, operation);
    }

    /**
     * A method for getting the ingredient at a certain index.
     *
     * @param   index
     *          The index of the ingredient.
     */
    @Basic
    public AlchemicIngredient getIngredientAt(int index) {
        if (index >= 0 && index < getNbOfIngredients()) {
            return ingredients.get(index);
        }
        return null;
    }

    /**
     * A method for getting the operation at a certain index.
     *
     * @param   index
     *          The index of the operation.
     */
    @Basic
    public Operation getOperationAt(int index) {
        if (index >= 0 && index < getNbOfOperations()) {
            return operations.get(index);
        }
        return null;
    }

    /**
     * A method for getting the length of ingredients of a recipe.
     */
    @Basic
    public int getNbOfIngredients() {
        return ingredients.size();
    }

    /**
     * A method for getting the length of operations of a recipe.
     */
    @Basic
    public int getNbOfOperations() {
        return operations.size();
    }

    /**
     * Check whether the given operation is present in this recipe.
     *
     * @param 	operation
     *        	The operation to check.
     * @return 	False if the given operation is not effective.
     * 	   		| if (operation == null)
     * 	   		| 	then result == false
     * @return 	True if the operation is registered at some
     *          position in this recipe; false otherwise.
     *         	| result ==
     *         	|    for some I in 0..getNbOfOperations()-1 :
     *         	| 	      (getOperationAt(I) == operation)
     */
    public boolean hasAsOperation(Operation operation) {
        if (operation == null) return false;
        for (int i=0; i<getNbOfOperations(); i++) {
            if (getOperationAt(i) == operation) {
                return true;
            }
        }
        return false;
    }



    /**********************************************************
     * EQUALS
     **********************************************************/

    /**
     * A method to check if two recipes are equal (based on their ingredients and operations).
     *
     * @param   other
     *          The recipe to compare with.
     * @return  If the recipes don't have the same amount of operations or
     *          the same amount of ingredients, return false.
     *          | if ( getNbOfIngredients() != other.getNbOfIngredients()
     *          |       || getNbOfOperations() != other.getNbOfOperations() )
     *          | then result == false
     * @return  If not all the ingredients and all the operations are equal, return false.
     *          | if ( ( for some I in 0..getNbOfOperations()-1:
     *          |       ! getIngredientAt(I).equals(other.getIngredientAt(I)) ||
     *          |       ! getOperationAt(I).equals(other.getOperationAt(I)))) )
     *          |   then result == false
     * @return  True otherwise.
     *          | if ( (getNbOfIngredients() != other.getNbOfIngredients()
     *          |       || getNbOfOperations() != other.getNbOfOperations()) &&
     *          |       (for each I in 0..getNbOfOperations()-1:
     *          |           getIngredientAt(I).equals(other.getIngredientAt(I)) &&
     *          |           getOperationAt(I).equals(other.getOperationAt(I))) )
     *          |   then result == true
     */
    public boolean equals(Recipe other) {
        // if there is a difference in the amount of ingredients or operations, return false
        if (getNbOfIngredients() != other.getNbOfIngredients()
                || getNbOfOperations() != other.getNbOfOperations()) {
            return false;
        }
        // iterate over the ingredients
        for (int i=0; i < getNbOfIngredients(); i++) {
            if (!getIngredientAt(i).equals(other.getIngredientAt(i))) {
                return false;
            }
        }
        // iterate over the operations
        for (int i=0; i < getNbOfOperations(); i++) {
            if (!getOperationAt(i).equals(other.getOperationAt(i))) {
                return false;
            }
        }
        return true;
    }



    /**********************************************************
     * Termination
     **********************************************************/

    /**
     * A variable for keeping track of whether a recipe is terminated or not.
     */
    private boolean isTerminated = false;

    /**
     * A method for checking if a recipe is terminated.
     */
    @Basic
    public boolean isTerminated() {
        return isTerminated;
    }

    /**
     * A method for checking if a recipe can be terminated.
     *
     * @return  True if and only if the recipe is not terminated.
     *          | ! isTerminated()
     */
    public boolean canBeTerminated() {
        return !isTerminated();
    }

    /**
     * A method for terminating a recipe.
     *
     * @post    The recipe is terminated if it can be terminated.
     *          | if canBeTerminated()
     *          | then new.isTerminated() == true
     */
    public void terminate() {
        if (canBeTerminated()) {isTerminated = true;}
    }

}