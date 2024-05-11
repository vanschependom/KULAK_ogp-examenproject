package rpg.alchemy;

import org.junit.jupiter.api.*;
import rpg.State;
import rpg.Unit;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientContainerTest {

	private AlchemicIngredient ingredient;
	private IngredientType mixedIngrTypePowder;
	private IngredientContainer container;

	private static Name water;
	private static Name mixed;

	private static Temperature standardTemperature;
	private static Temperature coldTemperature;

	@BeforeAll
	public static void setUp() {
		// set up the standard temperature
		standardTemperature = new Temperature(0, 20);
		// set up the cold temperature
		coldTemperature = new Temperature(30, 0);
		// set up a name
		water = Name.WATER;
		mixed = new Name("Mazout", "Beer", "Coke");
	}

	@BeforeEach
	public void setUpForEach(){
		mixedIngrTypePowder = new IngredientType(mixed, State.POWDER, coldTemperature, true);
		ingredient = new AlchemicIngredient(20, Unit.BOX, standardTemperature, mixedIngrTypePowder, State.POWDER);
	}

	@Test
	public void constructorLegal() {
		IngredientContainer c = new IngredientContainer(Unit.CHEST, ingredient);
		assertEquals(Unit.CHEST, c.getCapacity());
		assertEquals(ingredient, c.getContent());
	}

	@Test
	public void constructorIllegal1() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			IngredientContainer c = new IngredientContainer(Unit.BOTTLE, ingredient);
		});
	}

	@Test
	public void constructorIllegal2() {
		ingredient.terminate();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			IngredientContainer c = new IngredientContainer(Unit.CHEST, ingredient);
		});
	}

}
