package rpg.recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.alchemy.Name;
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
        type = new IngredientType(new Name(null, "Beer"), State.LIQUID, new Temperature(0, 20), false);
        type2 = new IngredientType(new Name(null, "Water"), State.LIQUID, new Temperature(0, 20), false);
        ingredientsSetUp.add(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID));
        ingredientsSetUp.add(new AlchemicIngredient(3, Unit.SACHET,
                new Temperature(20, 0), type2, State.POWDER));
        ingredientsSetUp.add(new AlchemicIngredient(3, Unit.CHEST,
                new Temperature(0, 40), type2, State.POWDER));
        preMadeRecipe = new Recipe();
        preMadeRecipe.addAsInstruction(ingredientsSetUp.get(0), Operation.ADD);
        preMadeRecipe.addAsInstruction(Operation.COOL);
        preMadeRecipe.addAsInstruction(Operation.COOL);
        preMadeRecipe.addAsInstruction(Operation.COOL);
        preMadeRecipe.addAsInstruction(Operation.HEAT);
        preMadeRecipe.addAsInstruction(ingredientsSetUp.get(1), Operation.ADD);
        preMadeRecipe.addAsInstruction(Operation.MIX);
        preMadeRecipe.addAsInstruction(ingredientsSetUp.get(2), Operation.ADD);
        preMadeRecipe.addAsInstruction(Operation.HEAT);
        preMadeRecipe.addAsInstruction(Operation.MIX);
    }

    @Test
    public void constructorFullValidSet2() {
        assertEquals(ingredientsSetUp.size(), preMadeRecipe.getNbOfIngredients());
        assertEquals(10, preMadeRecipe.getNbOfOperations());
        for (int i = 0; i < preMadeRecipe.getNbOfIngredients(); i++) {
            assertEquals(ingredientsSetUp.get(i), preMadeRecipe.getIngredientAt(i));
        }
        assertEquals(Operation.COOL, preMadeRecipe.getOperationAt(1));
    }

    @Test
    public void testaddAsInstructionValidCase() {
        AlchemicIngredient ingredient = new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID);
        preMadeRecipe.addAsInstruction(ingredient, Operation.ADD);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size()+1);
        assertEquals(preMadeRecipe.getNbOfOperations(), 11);
        assertEquals(preMadeRecipe.getIngredientAt(preMadeRecipe.getNbOfIngredients()-1), ingredient);
        assertEquals(preMadeRecipe.getOperationAt(preMadeRecipe.getNbOfOperations()-1), Operation.ADD);
    }

    @Test
    public void testaddAsInstructionValidCase2() {
        preMadeRecipe.addAsInstruction(null, Operation.HEAT);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size());
        assertEquals(preMadeRecipe.getNbOfOperations(), 11);
        assertEquals(preMadeRecipe.getOperationAt(preMadeRecipe.getNbOfOperations()-1), Operation.HEAT);
    }

    @Test
    public void testaddAsInstructionValidCase3() {
        preMadeRecipe.addAsInstruction(Operation.HEAT);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size());
        assertEquals(preMadeRecipe.getNbOfOperations(), 11);
        assertEquals(preMadeRecipe.getOperationAt(preMadeRecipe.getNbOfOperations()-1), Operation.HEAT);
    }

    @Test
    public void testaddAsInstructionInvalidCase() {
        AlchemicIngredient ingredient = new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID);
        preMadeRecipe.addAsInstruction(ingredient, Operation.HEAT);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size());
        assertEquals(preMadeRecipe.getNbOfOperations(), 10);
    }

    @Test
    public void testaddAsInstructionInvalidCase2() {
        preMadeRecipe.addAsInstruction(null, Operation.ADD);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size());
        assertEquals(preMadeRecipe.getNbOfOperations(), 10);
    }

    @Test
    public void testaddAsInstructionInvalidCase3() {
        preMadeRecipe.addAsInstruction(null, null);
        assertEquals(preMadeRecipe.getNbOfIngredients(), ingredientsSetUp.size());
        assertEquals(preMadeRecipe.getNbOfOperations(), 10);
    }

}