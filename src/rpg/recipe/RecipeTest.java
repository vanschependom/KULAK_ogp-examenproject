package rpg.recipe;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.State;
import rpg.Unit;
import rpg.ingredient.AlchemicIngredient;
import rpg.ingredient.IngredientType;
import rpg.ingredient.Temperature;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class RecipeTest {

    private ArrayList<AlchemicIngredient> ingredients;
    private ArrayList<Operation> operations;
    private IngredientType type;
    private IngredientType type2;


    @BeforeEach
    public void setUp() {
        ingredients = new ArrayList<>();
        operations = new ArrayList<>();
        type = new IngredientType("Beer", State.LIQUID, new Temperature(0, 20), false);
        type2 = new IngredientType("Cola", State.LIQUID, new Temperature(0, 20), false);
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
        assertEquals(2, recipe.getNbOfIngredients());
        assertEquals(4, recipe.getNbOfOperations());
        for (int i = 0; i < recipe.getNbOfIngredients(); i++) {
            assertEquals(ingredients.get(i), recipe.getIngredientAt(i));
        }
        for (int i = 0; i < recipe.getNbOfOperations(); i++) {
            assertEquals(operations.get(i), recipe.getOperationAt(i));
        }
    }
}
