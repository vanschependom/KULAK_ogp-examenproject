package rpg.recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.State;
import rpg.Unit;
import rpg.alchemy.AlchemicIngredient;
import rpg.alchemy.IngredientType;
import rpg.alchemy.Temperature;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class RecipeTest {

    private ArrayList<AlchemicIngredient> ingredients;
    private ArrayList<AlchemicIngredient> ingredientsSetUp;
    private ArrayList<Operation> operations;
    private ArrayList<Operation> operationsSetUp;
    private IngredientType type;
    private IngredientType type2;
    private Recipe preMadeRecipe;


    @BeforeEach
    public void setUp() {
        ingredients = new ArrayList<>();
        ingredientsSetUp = new ArrayList<>();
        operations = new ArrayList<>();
        operationsSetUp = new ArrayList<>();
        type = new IngredientType(null, State.LIQUID, new Temperature(0, 20), false);
        type2 = new IngredientType(null, State.LIQUID, new Temperature(0, 20), false);
        ingredientsSetUp.add(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID));
        ingredientsSetUp.add(new AlchemicIngredient(3, Unit.SACHET,
                new Temperature(20, 0), type2, State.POWDER));
        ingredientsSetUp.add(new AlchemicIngredient(3, Unit.CHEST,
                new Temperature(0, 40), type2, State.POWDER));
        operationsSetUp.add(Operation.ADD);
        operationsSetUp.add(Operation.COOL);
        operationsSetUp.add(Operation.COOL);
        operationsSetUp.add(Operation.COOL);
        operationsSetUp.add(Operation.HEAT);
        operationsSetUp.add(Operation.ADD);
        operationsSetUp.add(Operation.MIX);
        operationsSetUp.add(Operation.ADD);
        operationsSetUp.add(Operation.HEAT);
        operationsSetUp.add(Operation.MIX);
        preMadeRecipe = new Recipe(ingredientsSetUp, operationsSetUp);
    }

    @Test
    public void constructorFullValidSet() {
        ingredients.add(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID));
        ingredients.add(new AlchemicIngredient(3, Unit.SACHET,
                new Temperature(20, 0), type2, State.POWDER));
        operations.add(Operation.ADD);
        operations.add(Operation.COOL);
        operations.add(Operation.ADD);
        operations.add(Operation.MIX);

        Recipe recipe = new Recipe(ingredients, operations);
        assertEquals(ingredients.size(), recipe.getNbOfIngredients());
        assertEquals(operations.size(), recipe.getNbOfOperations());
        for (int i = 0; i < recipe.getNbOfIngredients(); i++) {
            assertEquals(ingredients.get(i), recipe.getIngredientAt(i));
        }
        for (int i = 0; i < recipe.getNbOfOperations(); i++) {
            assertEquals(operations.get(i), recipe.getOperationAt(i));
        }
    }

    @Test
    public void constructorFullValidSet2() {
        assertEquals(ingredients.size(), preMadeRecipe.getNbOfIngredients());
        assertEquals(operations.size(), preMadeRecipe.getNbOfOperations());
        for (int i = 0; i < preMadeRecipe.getNbOfIngredients(); i++) {
            assertEquals(ingredients.get(i), preMadeRecipe.getIngredientAt(i));
        }
        for (int i = 0; i < preMadeRecipe.getNbOfOperations(); i++) {
            assertEquals(operations.get(i), preMadeRecipe.getOperationAt(i));
        }
    }

    @Test
    public void constructorFullInvalidSet() {
        ingredientsSetUp.add(new AlchemicIngredient(7, Unit.SPOON,
                new Temperature(0, 40), type2, State.LIQUID));

        Recipe recipe = new Recipe(ingredientsSetUp, operationsSetUp);
        assertEquals(0, recipe.getNbOfIngredients());
        assertEquals(0, recipe.getNbOfOperations());
    }

    @Test
    public void constructorFullInvalidSet2() {
        ingredients.add(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID));

        operations.add(null);
        operations.add(Operation.ADD);

        Recipe recipe = new Recipe(ingredients, operations);
        assertEquals(0, recipe.getNbOfIngredients());
        assertEquals(0, recipe.getNbOfOperations());
    }

    @Test
    public void constructorFullInvalidSet3() {
        ingredients.add(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID));
        ingredients.add(null);

        operations.add(Operation.ADD);
        operations.add(Operation.MIX);

        Recipe recipe = new Recipe(ingredients, operations);
        assertEquals(0, recipe.getNbOfIngredients());
        assertEquals(0, recipe.getNbOfOperations());
    }

    @Test
    public void constructorFullInvalidSet4() {
        ingredients.add(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID));
        ingredients.add(null);

        operations.add(Operation.ADD);
        operations.add(null);
        operations.add(Operation.MIX);

        Recipe recipe = new Recipe(ingredients, operations);
        assertEquals(0, recipe.getNbOfIngredients());
        assertEquals(0, recipe.getNbOfOperations());
    }

    @Test
    public void constructorFullInvalidSet5() {
        operations.add(Operation.ADD);
        operations.add(Operation.MIX);
        operations.add(Operation.COOL);

        Recipe recipe = new Recipe(null, operations);
        assertEquals(0, recipe.getNbOfIngredients());
        assertEquals(0, recipe.getNbOfOperations());
    }

    @Test
    public void constructorFullInvalidSet6() {
        ingredients.add(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID));
        ingredients.add(new AlchemicIngredient(7, Unit.SACK,
                new Temperature(0, 25), type, State.POWDER));

        Recipe recipe = new Recipe(ingredients, null);
        assertEquals(0, recipe.getNbOfIngredients());
        assertEquals(0, recipe.getNbOfOperations());
    }

    @Test
    public void constructorFullInvalidSet7() {
        Recipe recipe = new Recipe(null, null);
        assertEquals(0, recipe.getNbOfIngredients());
        assertEquals(0, recipe.getNbOfOperations());
    }

    @Test
    public void constructorFullInvalidSet8() {
        operations.add(Operation.ADD);
        operations.add(null);
        operations.add(Operation.MIX);

        Recipe recipe = new Recipe(null, operations);
        assertEquals(0, recipe.getNbOfIngredients());
        assertEquals(0, recipe.getNbOfOperations());
    }

    @Test
    public void constructorFullInvalidSet9() {
        ingredients.add(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID));
        ingredients.add(null);

        Recipe recipe = new Recipe(ingredients, operations);
        assertEquals(0, recipe.getNbOfIngredients());
        assertEquals(0, recipe.getNbOfOperations());
    }

    @Test
    public void testAddInstructionValidCase() {
        AlchemicIngredient ingredient = new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID);
        preMadeRecipe.addInstruction(ingredient, Operation.ADD);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size()+1);
        assertEquals(preMadeRecipe.getNbOfOperations(), operationsSetUp.size()+1);
        assertEquals(preMadeRecipe.getIngredientAt(preMadeRecipe.getNbOfIngredients()-1), ingredient);
        assertEquals(preMadeRecipe.getOperationAt(preMadeRecipe.getNbOfOperations()-1), Operation.ADD);
    }

    @Test
    public void testAddInstructionValidCase2() {
        preMadeRecipe.addInstruction(null, Operation.HEAT);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size());
        assertEquals(preMadeRecipe.getNbOfOperations(), operationsSetUp.size()+1);
        assertEquals(preMadeRecipe.getOperationAt(preMadeRecipe.getNbOfOperations()-1), Operation.HEAT);
    }

    @Test
    public void testAddInstructionInvalidCase() {
        AlchemicIngredient ingredient = new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID);
        preMadeRecipe.addInstruction(ingredient, Operation.HEAT);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size());
        assertEquals(preMadeRecipe.getNbOfOperations(), operationsSetUp.size());
    }

    @Test
    public void testAddInstructionInvalidCase2() {
        preMadeRecipe.addInstruction(null, Operation.ADD);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size());
        assertEquals(preMadeRecipe.getNbOfOperations(), operationsSetUp.size());
    }

    @Test
    public void testAddInstructionInvalidCase3() {
        AlchemicIngredient ingredient = new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID);
        preMadeRecipe.addInstruction(null, null);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size());
        assertEquals(preMadeRecipe.getNbOfOperations(), operationsSetUp.size());
    }

}
