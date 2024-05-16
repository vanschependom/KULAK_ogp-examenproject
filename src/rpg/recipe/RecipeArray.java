package rpg.recipe;

import be.kuleuven.cs.som.annotate.*;
import rpg.alchemy.AlchemicIngredient;
import rpg.alchemy.Device;

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
public class RecipeArray {

	/**
	 * A variable for keeping track of all ingredients of the recipe.
	 */
	private final AlchemicIngredient[] ingredients;

	/**
	 * A variable for keeping track of all instructions of the recipe.ations:
	 *          |   operation != null
	 */
	private final Operation[] operations;



	/**********************************************************
	 * Constructors
	 **********************************************************/

	/**
	 * A constructor for creating a new recipe with a given set of ingredients and operations.
	 *
	 * @param   ingredients
	 *          The ingredient array for the recipe
	 * @param   operations
	 *          The operation array for the recipe
	 *
	 * @effect  The ingredient list of the recipe is set to ingredients if the instruction set is valid.
	 *          | if isValidInstructionSet(ingredients, operations)
	 *          |   then TODO
	 * @effect  The operation list of the recipe is set to operations if the instruction set is valid.
	 *          | if isValidInstructionSet(ingredients, operations)
	 *          |   then TODO
	 *
	 * @throws  IllegalArgumentException
	 *          The instruction set is not valid.
	 *          | !isValidInstructionSet(ingredients, operations)
	 */
	@Raw
	public RecipeArray(AlchemicIngredient[] ingredients, Operation[] operations) throws IllegalArgumentException {
		if (!isValidInstructionSet(ingredients, operations)) {
			throw new IllegalArgumentException("Invalid instruction set!");
		}
		this.ingredients = ingredients;
		this.operations = operations;
	}



	/**********************************************************
	 * Ingredients/Operations: Instructions
	 **********************************************************/

	/**
	 * A method for checking if an instruction set consisting of
	 * ingredients and operations is valid.
	 *
	 * TODO NAKIJKEN!
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
	public boolean isValidInstructionSet(AlchemicIngredient[] ingredients, Operation[] operations) {
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
		return ingredients.length == nbOfAdds;
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
	 * A method for getting the ingredients.
	 */
	@Model
	private AlchemicIngredient[] getIngredients() {
		return ingredients;
	}

	/**
	 * A method for getting the operations.
	 */
	@Model
	private Operation[] getOperations() {
		return operations;
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
			return ingredients[index];
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
			return operations[index];
		}
		return null;
	}

	/**
	 * A method for getting the length of ingredients of a recipe.
	 */
	@Basic
	public int getNbOfIngredients() {
		return ingredients.length;
	}

	/**
	 * A method for getting the length of operations of a recipe.
	 */
	@Basic
	public int getNbOfOperations() {
		return operations.length;
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
	 * COPY
	 **********************************************************/

	/**
	 * A method for cloning a recipe.
	 *
	 * @return  A copy of the original recipe.
	 *          | result ==
	 *          |   new Recipe(getIngredients(), getOperations())
	 */
	@Model @Override
	protected Recipe clone() {
		return new Recipe(getIngredients(), getOperations());
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
	 *          |   then new.isTerminated() == true
	 */
	public void terminate() {
		if (canBeTerminated()) {
			isTerminated = true;
		}
	}

}
