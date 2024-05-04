package rpg.alchemy;

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
		mixedIngrTypePowder = new IngredientType(null, null, State.POWDER, coldTemperature, true);
		ingredient = new AlchemicIngredient(20, Unit.BOX, standardTemperature, mixedIngrTypePowder, State.POWDER);
	}

	@Test
	public void constructorIllegal1 (){

	}

	@Test
	public void constructorIllegal2 (){

	}

}
