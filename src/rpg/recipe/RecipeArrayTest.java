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


public class RecipeArrayTest {

	private AlchemicIngredient[] ingredients;
	private AlchemicIngredient[] ingredientsSetUp;
	private Operation[] operations;
	private Operation[] operationsSetUp;
	private IngredientType type;
	private IngredientType type2;
	private Recipe preMadeRecipe;


	@BeforeEach
	public void setUp() {
		type = new IngredientType(new Name(null, "Beer"), State.LIQUID, new Temperature(0, 20), false);
		type2 = new IngredientType(new Name(null, "Water"), State.LIQUID, new Temperature(0, 20), false);
		ingredientsSetUp = new AlchemicIngredient[] {
				new AlchemicIngredient(2, Unit.BOTTLE,
						new Temperature(0, 20), type, State.LIQUID),
				new AlchemicIngredient(3, Unit.SACHET,
						new Temperature(20, 0), type2, State.POWDER),
				new AlchemicIngredient(3, Unit.CHEST,
						new Temperature(0, 40), type2, State.POWDER)};
		operationsSetUp = new Operation[] {
				Operation.ADD, Operation.COOL, Operation.COOL, Operation.COOL,
				Operation.HEAT, Operation.ADD, Operation.MIX, Operation.ADD,
				Operation.HEAT, Operation.MIX};
		preMadeRecipe = new Recipe(ingredientsSetUp, operationsSetUp);
	}

	@Test
	public void constructorFullValidSet() {
		ingredients = new AlchemicIngredient[] {
				new AlchemicIngredient(2, Unit.BOTTLE,
						new Temperature(0, 20), type, State.LIQUID),
				new AlchemicIngredient(3, Unit.SACHET,
						new Temperature(20, 0), type2, State.POWDER)};
		operations = new Operation[] {Operation.ADD, Operation.COOL, Operation.ADD, Operation.MIX};
		Recipe recipe = new Recipe(ingredients, operations);
		assertEquals(ingredients.length, recipe.getNbOfIngredients());
		assertEquals(operations.length, recipe.getNbOfOperations());
		for (int i = 0; i < recipe.getNbOfIngredients(); i++) {
			assertEquals(ingredients[i], recipe.getIngredientAt(i));
		}
		for (int i = 0; i < recipe.getNbOfOperations(); i++) {
			assertEquals(operations[i], recipe.getOperationAt(i));
		}
	}

	@Test
	public void constructorFullValidSet2() {
		assertEquals(ingredientsSetUp.length, preMadeRecipe.getNbOfIngredients());
		assertEquals(operationsSetUp.length, preMadeRecipe.getNbOfOperations());
		for (int i = 0; i < preMadeRecipe.getNbOfIngredients(); i++) {
			assertEquals(ingredientsSetUp[i], preMadeRecipe.getIngredientAt(i));
		}
		for (int i = 0; i < preMadeRecipe.getNbOfOperations(); i++) {
			assertEquals(operationsSetUp[i], preMadeRecipe.getOperationAt(i));
		}
	}

	@Test
	public void constructorFullInvalidSet() {
		AlchemicIngredient[] illegalIngredients = new AlchemicIngredient[] {
				new AlchemicIngredient(2, Unit.BOTTLE,
						new Temperature(0, 20), type, State.LIQUID),
				new AlchemicIngredient(3, Unit.SACHET,
						new Temperature(20, 0), type2, State.POWDER),
				new AlchemicIngredient(3, Unit.CHEST,
						new Temperature(0, 40), type2, State.POWDER),
				new AlchemicIngredient(7, Unit.SPOON,
						new Temperature(0, 40), type2, State.LIQUID)
		};

		Recipe recipe = new Recipe(illegalIngredients, operationsSetUp);
		assertEquals(0, recipe.getNbOfIngredients());
		assertEquals(0, recipe.getNbOfOperations());
	}

	@Test
	public void constructorFullInvalidSet2() {
		ingredients = new AlchemicIngredient[] {
				new AlchemicIngredient(2, Unit.BOTTLE,
						new Temperature(0, 20), type, State.LIQUID)
		};

		operations = new Operation[] {null, Operation.ADD};

		Recipe recipe = new Recipe(ingredients, operations);
		assertEquals(0, recipe.getNbOfIngredients());
		assertEquals(0, recipe.getNbOfOperations());
	}

	@Test
	public void constructorFullInvalidSet3() {

		ingredients = new AlchemicIngredient[] {
				new AlchemicIngredient(2, Unit.BOTTLE,
						new Temperature(0, 20), type, State.LIQUID),
				null
		};

		operations = new Operation[] {Operation.ADD, Operation.MIX};

		Recipe recipe = new Recipe(ingredients, operations);
		assertEquals(0, recipe.getNbOfIngredients());
		assertEquals(0, recipe.getNbOfOperations());
	}

	@Test
	public void constructorFullInvalidSet4() {
		ingredients = new AlchemicIngredient[] {
				new AlchemicIngredient(2, Unit.BOTTLE,
						new Temperature(0, 20), type, State.LIQUID)
		};

		operations = new Operation[] {Operation.ADD, null, Operation.MIX};

		Recipe recipe = new Recipe(ingredients, operations);
		assertEquals(0, recipe.getNbOfIngredients());
		assertEquals(0, recipe.getNbOfOperations());
	}

	@Test
	public void constructorFullInvalidSet5() {
		operations = new Operation[] {Operation.ADD, Operation.COOL, Operation.MIX};

		Recipe recipe = new Recipe(null, operations);
		assertEquals(0, recipe.getNbOfIngredients());
		assertEquals(0, recipe.getNbOfOperations());
	}

	@Test
	public void constructorFullInvalidSet6() {
		ingredients = new AlchemicIngredient[] {
				new AlchemicIngredient(2, Unit.BOTTLE,
						new Temperature(0, 20), type, State.LIQUID),
				new AlchemicIngredient(7, Unit.SACK,
						new Temperature(0, 25), type, State.POWDER)
		};

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
		operations = new Operation[] {Operation.ADD, null, Operation.MIX};

		Recipe recipe = new Recipe(null, operations);
		assertEquals(0, recipe.getNbOfIngredients());
		assertEquals(0, recipe.getNbOfOperations());
	}

	@Test
	public void constructorFullInvalidSet9() {
		ingredients = new AlchemicIngredient[] {
				new AlchemicIngredient(2, Unit.BOTTLE,
						new Temperature(0, 20), type, State.LIQUID),
				null
		};

		Recipe recipe = new Recipe(ingredients, operations);
		assertEquals(0, recipe.getNbOfIngredients());
		assertEquals(0, recipe.getNbOfOperations());
	}

}
