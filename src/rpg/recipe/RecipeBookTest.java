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

    private Recipe recipe1 = new Recipe();
    private Recipe recipe2 = new Recipe();
    private IngredientType type;
    private IngredientType type2;

    @BeforeEach
    public void setUp() {
        type = new IngredientType(new Name(null, "Beer"), State.LIQUID, new Temperature(0, 20), false);
        type2 = new IngredientType(new Name(null, "Water"), State.LIQUID, new Temperature(0, 20), false);
        recipe1.addInstruction(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID), Operation.ADD);
        recipe1.addInstruction(Operation.COOL);
        recipe1.addInstruction(Operation.COOL);
        recipe1.addInstruction(new AlchemicIngredient(3, Unit.SACHET,
                new Temperature(20, 0), type2, State.POWDER), Operation.ADD);
        recipe1.addInstruction(Operation.HEAT);
        recipe1.addInstruction(Operation.MIX);

        recipe2.addInstruction(new AlchemicIngredient(2, Unit.BOTTLE,
                new Temperature(0, 20), type, State.LIQUID), Operation.ADD);
        recipe2.addInstruction(Operation.COOL);
        recipe2.addInstruction(Operation.COOL);
        recipe2.addInstruction(new AlchemicIngredient(3, Unit.SACHET,
                new Temperature(20, 0), type2, State.POWDER), Operation.ADD);
        recipe2.addInstruction(Operation.HEAT);
        recipe2.addInstruction(Operation.MIX);
        recipe2.addInstruction(new AlchemicIngredient(3, Unit.CHEST,
                new Temperature(0, 40), type2, State.POWDER), Operation.ADD);
        recipe2.addInstruction(Operation.COOL);
        recipe2.addInstruction(Operation.COOL);
        recipe2.addInstruction(Operation.MIX);
    }

    @Test
    public void constructorFullValid() {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe1);
        recipeList.add(recipe2);
        RecipeBook rb = new RecipeBook(recipeList);
        assertEquals(2, rb.getNbOfRecipes());
        assertTrue(recipe1.equals(rb.getRecipeAt(0)));
        assertTrue(recipe2.equals(rb.getRecipeAt(1)));
    }

    @Test
    public void constructorFullInvalid() {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe1);
        recipeList.add(recipe2);
        recipeList.add(null);
        RecipeBook rb = new RecipeBook(recipeList);
        assertEquals(2, rb.getNbOfRecipes());
        assertTrue(recipe1.equals(rb.getRecipeAt(0)));
        assertTrue(recipe2.equals(rb.getRecipeAt(1)));
    }

    @Test
    public void constructorFullInvalid2() {
        RecipeBook rb = new RecipeBook(null);
        assertEquals(0, rb.getNbOfRecipes());
    }

    @Test
    public void constructor() {
        RecipeBook rb = new RecipeBook();
        assertEquals(0, rb.getNbOfRecipes());
    }

    @Test
    public void addRecipeValid() {
        RecipeBook rb = new RecipeBook();
        rb.addRecipe(recipe1);
        assertEquals(1, rb.getNbOfRecipes());
        assertTrue(recipe1.equals(rb.getRecipeAt(0)));
    }

    @Test
    public void addRecipeInvalid() {
        RecipeBook rb = new RecipeBook();
        rb.addRecipe(null);
        assertEquals(0, rb.getNbOfRecipes());
    }

    @Test
    public void addRecipeInvalid2() {
        RecipeBook rb = new RecipeBook();
        rb.addRecipe(recipe1);
        rb.addRecipe(recipe1);
        assertEquals(1, rb.getNbOfRecipes());
        assertTrue(recipe1.equals(rb.getRecipeAt(0)));
    }

    @Test
    public void removeRecipeValid() {
        RecipeBook rb = new RecipeBook();
        rb.addRecipe(recipe1);
        rb.removeRecipe(recipe1);
        assertEquals(0, rb.getNbOfRecipes());
    }

    @Test
    public void removeRecipeValid2() {
        RecipeBook rb = new RecipeBook();
        rb.addRecipe(recipe1);
        rb.addRecipe(recipe2);
        rb.removeRecipe(recipe1);
        assertEquals(1, rb.getNbOfRecipes());
    }

    @Test
    public void removeRecipeInvalid() {
        RecipeBook rb = new RecipeBook();
        rb.addRecipe(recipe1);
        rb.removeRecipe(recipe2);
        assertEquals(1, rb.getNbOfRecipes());
    }

    @Test
    public void removeRecipeInvalid2() {
        RecipeBook rb = new RecipeBook();
        rb.removeRecipe(recipe2);
        assertEquals(0, rb.getNbOfRecipes());
    }

    @Test
    public void removeRecipeInvalid3() {
        RecipeBook rb = new RecipeBook();
        rb.addRecipe(recipe1);
        rb.removeRecipe(null);
        assertEquals(1, rb.getNbOfRecipes());
        assertTrue(recipe1.equals(rb.getRecipeAt(0)));
    }


}
