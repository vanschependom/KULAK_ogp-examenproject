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
     * @invar   ingredients references an effective list
     *          | ingredients != null
     * @invar   Each ingredient in the list references an effective ingredient.
     *          | for each ingredient in ingredients:
     *          |   ingredient != null
     * @invar   Each ingredient in the list references a non-terminated ingredient.
     *          | for each ingredient in ingredients:
     *          | !ingredient.isTerminated()
     */
    private ArrayList<AlchemicIngredient> ingredients = new ArrayList<AlchemicIngredient>();

    /**
     * A variable for keeping track of all instructions of the recipe.
     *
     * @invar   operations references an effective list
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
     * @param   ingredients
     *          The ingredient list for the recipe
     * @param   operations
     *          The operation list for the recipe
     *
     * @effect  The ingredient list of the recipe is set to ingredients if the instruction set is valid
     *          | if isValidInstructionSet(ingredients, operations)
     *          |   then setIngredients(ingredients)
     * @effect  The operation list of the recipe is set to operations if the instruction set is valid
     *          | if isValidInstructionSet(ingredients, operations)
     *          |   then setOperations(operations)
     */
    @Raw
    public Recipe(ArrayList<AlchemicIngredient> ingredients, ArrayList<Operation> operations) {
        if (isValidInstructionSet(ingredients, operations)) {
            setIngredients(ingredients);
            setOperations(operations);
        }
    }

    /**
     * A constructor for creating a new recipe with an empty set of ingredients and operations.
     *
     * @effect  A recipe with no ingredients and no operations is created
     *          | this(new ArrayList<AlchemicIngredient>(), new ArrayList<Operation>())
     */
    @Raw
    public Recipe() {
        this(new ArrayList<AlchemicIngredient>(), new ArrayList<Operation>());
    }



    /**********************************************************
     * Ingredients/Operations: Instructions
     **********************************************************/

    /**
     * A method for checking if an instruction set consisting of
     * ingredients and operations is valid.
     *
     * @param   ingredients
     *          The ingredients list to check
     * @param   operations
     *          The operations list to check
     *
     * @return  False if either ingredients or operations is a null reference
     *          | if (ingredients == null || operations == null)
     *          | then result == false
     * @return  False if one of the instructions is not valid.
     *          | TODO
     * @return  True if the amount of operations in operations
     *          which are equal to the add operation,
     *          is equal to the size of ingredients
     *          | for each operation in getOperations():
     *          |    if operation == Operation.ADD
     *          |    then amountOfAdds++;
     *          | result == (ingredients.size() == amountOfAdds) TODO dit klopt niet denk ik
     */
    public boolean isValidInstructionSet(ArrayList<AlchemicIngredient> ingredients, ArrayList<Operation> operations) {
        if (ingredients == null || operations == null) {
            return false;
        }
        int nbOfAdds = 0;
        for (Operation operation : operations) {
            if (operation == Operation.ADD) {
                if (!isValidInstruction(ingredients.get(nbOfAdds), operation)) return false;
                nbOfAdds++;
            } else {
                if (!isValidInstruction(null, operation)) return false;
            }
        }
        return ingredients.size() == nbOfAdds;

    }

    /**
     * A method for checking if a certain instruction is valid.
     *
     * @param   ingredient
     *          The ingredient to check
     * @param   operation
     *          The operation to check
     *
     * @return  True if and only if either the operation is an add operation
     *          and the ingredient is not null and not terminated
     *          or the operation is not null and not add and the ingredient is null
     *          | result ==
     *          |   ( operation == Operation.ADD && ingredient != null && !ingredient.isTerminated()) ||
     *          |   (operation != null && operation != Operation.ADD && ingredient == null) )
     */
    public boolean isValidInstruction(AlchemicIngredient ingredient, Operation operation) {
        return ((operation == Operation.ADD && ingredient != null && !ingredient.isTerminated()) ||
                (operation != null && operation != Operation.ADD && ingredient == null));
    }

    /**
     * A method for getting the ingredients
     */
    @Model
    private ArrayList<AlchemicIngredient> getIngredients() {
        return new ArrayList<AlchemicIngredient>(ingredients);
    }

    /**
     * A method for getting the operations
     */
    @Model
    private ArrayList<Operation> getOperations() {
        return new ArrayList<Operation>(operations);
    }

    /**
     * A method for setting the ingredients of a recipe
     *
     * @param   ingredients
     *          The ingredients for the recipe
     *
     * @post    The ingredients of the recipe are set to a copy of ingredients
     *          | new.getIngredients().equals(ingredients)
     */
    @Model @Raw
    private void setIngredients(ArrayList<AlchemicIngredient> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
    }

    /**
     * A method for setting the operations of a recipe
     *
     * @param   operations
     *          The operations for the recipe
     *
     * @post    The operations of the recipe are set to a copy of operations
     *          | new.getOperations().equals(operations)
     */
    @Model @Raw
    private void setOperations(ArrayList<Operation> operations) {
        this.operations = new ArrayList<>(operations);
    }

    /**
     * A method for adding an ingredient to the ingredients of the recipe
     *
     * @param   ingredient
     *          The ingredient to be added
     *
     * @post    The number of ingredients of this recipe is
     *          incremented with 1.
     *          | new.getNbOfIngredients() == getNbOfIngredients() + 1
     * @post    The given ingredient is inserted at the end of the list.
     *          | new.getIngredientAt(getNbOfIngredients()-1) == ingredient
     */
    @Model @Raw
    private void addIngredient(AlchemicIngredient ingredient) {
        ingredients.add(ingredient);
    }

    /**
     * A method for adding an operation to the operations of the recipe
     *
     * @param   operation
     *          The operation to be added
     *
     * @post    The number of operations of this recipe is
     *          incremented with 1.
     *          | new.getNbOfOperations() == getNbOfOperations() + 1
     * @post    The given operation is inserted at the end of the list.
     *          | new.getOperationAt(getNbOfOperations()-1) == operation
     */
    @Model @Raw
    private void addOperation(Operation operation) {
        operations.add(operation);
    }

    /**
     * A method for adding an instruction to a recipe
     *
     * @param   ingredient
     *          The ingredient to be added as part of the instruction
     *          If the operation does not require an ingredient, null can be provided
     * @param   operation
     *          The operation to be added as part of the instruction
     *
     * @effect  If the instruction is valid as a whole,
     *          the operation is added to the recipe
     *          | if isValidInstruction(ingredient, operation)
     *          | then addOperation(ingredient)
     * @effect  If the instruction is valid as a whole
     *          and the ingredient is not a null-reference,
     *          the ingredient is added to the recipe
     *          | if ( isValidInstruction(ingredient, operation)
     *          |           && ingredient != null )
     *          | then addIngredient(operation)
     */
    @Raw
    public void addInstruction(AlchemicIngredient ingredient, Operation operation) {
        if (isValidInstruction(ingredient, operation)) {
            if (ingredient != null) addIngredient(ingredient);
            addOperation(operation);
        }
    }

    /**
     * A method for adding an instruction to a recipe
     *
     * @param   operation
     *          The operation to be added as part of the instruction
     *
     * @effect  The operation with a null reference ingredient is added
     *          | addInstruction(null, operation)
     */
    @Raw
    public void addInstruction(Operation operation) {
        addInstruction(null, operation);
    }

    /**
     * A method for getting the ingredient at a certain index
     *
     * @param   index
     *          The index of the ingredient
     *
     * @return  The ingredient at the given index if the index is positive
     *          and the index is smaller than the length of ingredients
     *          | if (index >= 0) && (index < getNbOfIngredients())
     *          | then result == getIngredients().get(index)
     * @return  null if the index is not valid
     *          according to the limitations above
     *          | if (index < 0) || (index >= getNbOfIngredients())
     *          | then result == null
     */
    @Basic
    public AlchemicIngredient getIngredientAt(int index) {
        if (index >= 0 && index < getNbOfIngredients()) return getIngredients().get(index); //TODO kopie maken
        return null;
    }

    /**
     * A method for getting the operation at a certain index
     *
     * @param   index
     *          The index of the operation
     *
     * @return  The operation at the given index if the index is positive
     *          and the index is smaller than the length of operations
     *          | if (index >= 0) && (index < getNbOfOperations())
     *          | then result == getOperations().get(index)
     * @return  null if the index is not valid
     *          according to the limitations above
     *          | if (index < 0) || (index >= getNbOfOperations())
     *          | then result == null
     */
    @Basic
    public Operation getOperationAt(int index) {
        if (index >= 0 && index < getNbOfOperations()) return getOperations().get(index); // TODO kopie maken
        return null;
    }

    /**
     * A method for getting the length of ingredients of a recipe
     */
    @Basic
    public int getNbOfIngredients() {
        return ingredients.size();
    }

    /**
     * A method for getting the length of operations of a recipe
     */
    @Basic
    public int getNbOfOperations() {
        return operations.size();
    }



    /**********************************************************
     * COPY
     **********************************************************/

    /**
     * A method for cloning a recipe
     *
     * @return  A copy of the original recipe
     *          | result ==
     *          |   new Recipe(getIngredients(), getOperations())
     */
    @Model
    protected Recipe clone() {
        return new Recipe(getIngredients(), getOperations());
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
     * A method for terminating a recipe
     *
     * @post    The recipe is terminated if it can be terminated.
     *          | if canBeTerminated()
     *          | then new.isTerminated() == true
     */
    public void terminate() {
        if (canBeTerminated()) {isTerminated = true;}
    }

}
