package rpg.recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.alchemy.Name;
import rpg.State;
import rpg.Unit;
import rpg.alchemy.AlchemicIngredient;
import rpg.alchemy.IngredientType;
import rpg.alchemy.Temperature;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeBookTest {

    private Recipe recipe1;
    private Recipe recipe2;
    private IngredientType type;
    private IngredientType type2;

    @BeforeEach
    public void setUp() {
        type = new IngredientType(new Name(null, "Beer"), State.LIQUID, new Temperature(0, 20), false);
        type2 = new IngredientType(new Name(null, "Water"), State.LIQUID, new Temperature(0, 20), false);
        Operation[] recipe1Operations = new Operation[] {
                Operation.ADD, Operation.COOL, Operation.COOL, Operation.ADD,
                Operation.ADD, Operation.COOL, Operation.COOL, Operation.MIX
        };


        recipe1.addAsInstruction(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID), Operation.ADD);
        recipe1.addAsInstruction(Operation.COOL);
        recipe1.addAsInstruction(Operation.COOL);
        recipe1.addAsInstruction(new AlchemicIngredient(3, Unit.SACHET,
                new Temperature(20, 0), type2, State.POWDER), Operation.ADD);
        recipe1.addAsInstruction(Operation.HEAT);
        recipe1.addAsInstruction(Operation.MIX);

        recipe2.addAsInstruction(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID), Operation.ADD);
        recipe2.addAsInstruction(Operation.COOL);
        recipe2.addAsInstruction(Operation.COOL);
        recipe2.addAsInstruction(new AlchemicIngredient(3, Unit.SACHET,
                new Temperature(20, 0), type2, State.POWDER), Operation.ADD);
        recipe2.addAsInstruction(Operation.HEAT);
        recipe2.addAsInstruction(Operation.MIX);
        recipe2.addAsInstruction(new AlchemicIngredient(3, Unit.CHEST,
                new Temperature(0, 40), type2, State.POWDER), Operation.ADD);
        recipe2.addAsInstruction(Operation.COOL);
        recipe2.addAsInstruction(Operation.COOL);
        recipe2.addAsInstruction(Operation.MIX);
    }

    @Test
    public void constructor() {
        RecipeBook rb = new RecipeBook();
        assertEquals(0, rb.getNbOfRecipes());
    }

    @Test
    public void addAsRecipeValid() {
        RecipeBook rb = new RecipeBook();
        rb.addAsRecipe(recipe1);
        assertEquals(1, rb.getNbOfRecipes());
        assertTrue(recipe1.equals(rb.getRecipeAt(0)));
    }

    @Test
    public void addAsRecipeInvalid() {
        RecipeBook rb = new RecipeBook();
        rb.addAsRecipe(null);
        assertEquals(0, rb.getNbOfRecipes());
    }

    @Test
    public void addAsRecipeInvalid2() {
        RecipeBook rb = new RecipeBook();
        rb.addAsRecipe(recipe1);
        rb.addAsRecipe(recipe1);
        assertEquals(1, rb.getNbOfRecipes());
        assertTrue(recipe1.equals(rb.getRecipeAt(0)));
    }

    @Test
    public void removeAsRecipeValid() {
        RecipeBook rb = new RecipeBook();
        rb.addAsRecipe(recipe1);
        rb.removeAsRecipe(recipe1);
        assertEquals(0, rb.getNbOfRecipes());
    }

    @Test
    public void removeAsRecipeValid2() {
        RecipeBook rb = new RecipeBook();
        rb.addAsRecipe(recipe1);
        rb.addAsRecipe(recipe2);
        rb.removeAsRecipe(recipe1);
        assertEquals(1, rb.getNbOfRecipes());
    }

    @Test
    public void removeAsRecipeInvalid() {
        RecipeBook rb = new RecipeBook();
        rb.addAsRecipe(recipe1);
        rb.removeAsRecipe(recipe2);
        assertEquals(1, rb.getNbOfRecipes());
    }

    @Test
    public void removeAsRecipeInvalid2() {
        RecipeBook rb = new RecipeBook();
        rb.removeAsRecipe(recipe2);
        assertEquals(0, rb.getNbOfRecipes());
    }

    @Test
    public void removeAsRecipeInvalid3() {
        RecipeBook rb = new RecipeBook();
        rb.addAsRecipe(recipe1);
        rb.removeAsRecipe(null);
        assertEquals(1, rb.getNbOfRecipes());
        assertTrue(recipe1.equals(rb.getRecipeAt(0)));
    }


}
