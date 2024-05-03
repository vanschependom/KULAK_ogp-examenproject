package rpg.ingredient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.State;
import rpg.Unit;

import static org.junit.jupiter.api.Assertions.*;

public class ContainerTest {

	private AlchemicIngredient ingredient;
	private IngredientType mixedIngrTypePowder;
	private IngredientContainer container;

	private static Temperature standardTemperature;
	private static Temperature coldTemperature;

	@BeforeAll
	public static void setUp() {
		// set up the standard temperature
		standardTemperature = new Temperature(0, 20);
		// set up the cold temperature
		coldTemperature = new Temperature(30, 0);
	}

	@BeforeEach
	public void setUpForEach(){
		mixedIngrTypePowder = new IngredientType("Red Mushroom Gas mixed with Coke", "Special Name", State.POWDER, coldTemperature, true);
		ingredient = new AlchemicIngredient(20, Unit.BOX, standardTemperature, mixedIngrTypePowder, State.POWDER);
	}

	@Test
	public void constructorIllegal1 (){
		assertThrows(IllegalArgumentException.class, () -> {
			// null
			container = new IngredientContainer(null, mixedIngrTypePowder, State.POWDER);
		});
	}

	@Test
	public void constructorIllegal2 (){
		assertThrows(IllegalArgumentException.class, () -> {
			// storeroom is not legal
			container = new IngredientContainer(Unit.STOREROOM, mixedIngrTypePowder, State.POWDER);
		});
	}

}
