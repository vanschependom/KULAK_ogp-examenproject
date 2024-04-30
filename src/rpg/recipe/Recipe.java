package rpg.recipe;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.ingredient.AlchemicIngredient;

import java.util.ArrayList;

/**
 * A class representing a recipe in a recipeBook
 *
 * @invar   The instruction set of the recipe is valid.
 *          | isValidInstructionSet(getIngredients(), getOperations())
 *
 * @note    We doen aan TOTAL programming
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
     * @invar   Each element in the list references an effective item.
     *          | for each ingredient in ingredients:
     *          |   ingredient != null
     * @invar   Each element in the list references a non-terminated item.
     *          | for each ingredient in ingredients:
     *          | !ingredient.isTerminated()
     */
    private ArrayList<AlchemicIngredient> ingredients = new ArrayList<>();

    /**
     * A variable for keeping track of all instructions of the recipe.
     *
     * @invar   operations references an effective list
     *          | operations != null
     * @invar   Each element in the list references an effective item.
     *          | for each operation in operations:
     *          |   operation != null
     * @invar   Each element in the list references a non-terminated item.
     *          | for each operation in operations:
     *          | !operation.isTerminated()
     */
    private ArrayList<Operation> operations = new ArrayList<>();

    /**********************************************************
     * Constructors
     **********************************************************/

    /**
     * A constructor for the Recipe class
     *
     * @param   ingredients
     *          The ingredient list for the recipe
     * @param   operations
     *          The operation list for the recipe
     *
     * @effect  The ingredient list of the recipe is set to ingredients
     *          | setIngredients(ingredients);
     * @effect  The operation list of the recipe is set to operations
     *          | setOperations(operations);
     */
    @Raw
    public Recipe(ArrayList<AlchemicIngredient> ingredients, ArrayList<Operation> operations) {
        if (isValidInstructionSet(ingredients, operations)) {
            setIngredients(ingredients);
            setOperations(operations);
        }
    }

    /**********************************************************
     * Termination
     **********************************************************/

    /**
     * A variable for keeping track of whether or not a recipe is terminated.
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
     *          | then new.isTerminated()
     */
    public void terminate() {
        if (canBeTerminated()) {isTerminated = true;}
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
     *          | if ingredients == null || operations == null
     *          | then result == false
     * @return  False if one of the instructions is not valid.
     *          | TODO
     * @return  True if the amount of operations in operations which are equal to the add operation
     *          is equal to the size of ingredients
     *          | for each operation in 1..ingredients.size():
     *          |    if operation == Operation.ADD
     *          |    amountOfAdds++;
     *          | result == (ingredients.size() == amountOfAdds)
     */
    public boolean isValidInstructionSet(ArrayList<AlchemicIngredient> ingredients, ArrayList<Operation> operations) {
        if (ingredients == null || operations == null) {return false;}
        int nbOfAdds = 0;
        for (Operation operation : operations) {
            if (!isValidInstruction(ingredients.get(nbOfAdds), operation)) return false;
            if (operation == Operation.ADD) {
                nbOfAdds++;
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
    @Basic @Model
    private ArrayList<AlchemicIngredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    /**
     * A method for getting the operations
     */
    @Basic @Model
    private ArrayList<Operation> getOperations() {
        return new ArrayList<>(operations);
    }

    /**
     * A method for setting the ingredients of a recipe
     *
     * @param   ingredients
     *          The ingredients for the recipe
     *
     * @post    The ingredients of the recipe are set to ingredients
     *          | new.getIngredients() == ingredients
     */
    @Model @Raw
    private void setIngredients(ArrayList<AlchemicIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * A method for setting the operations of a recipe
     *
     * @param   operations
     *          The operations for the recipe
     *
     * @post    The operations of the recipe are set to operations
     *          | new.getOperations() == operations
     */
    @Model @Raw
    private void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
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
     * @post    The given ingredient is inserted at the given index.
     *          | new.getIngredientAt(index) == ingredient
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
     * @post    The given operation is inserted at the given index.
     *          | new.getOperationAt(index) == operation
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
     *          If the operation does not require an ingredient, null should be provided
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
    public int getNbOfIngredients() {
        return ingredients.size();
    }

    /**
     * A method for getting the length of operations of a recipe
     */
    public int getNbOfOperations() {
        return operations.size();
    }


}
