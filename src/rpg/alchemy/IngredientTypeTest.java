package rpg.alchemy;
import org.junit.jupiter.api.*;
import rpg.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A JUnit 5 test class for testing the non-private methods of the IngredientType class.
 *
 * @author  Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 *
 * @version 1.0
 */
public class IngredientTypeTest {

	private IngredientType ingrTypeEverything;
	private IngredientType ingrTypeNullSpecialName;
	private IngredientType ingrTypeDiffTemp;
	private IngredientType ingrTypeMixed;
	private IngredientType ingrTypeDiffState;

	private static Temperature standardTemperature;
	private static Temperature coldTemperature;

	private static Name mixed;

	@BeforeAll
	public static void setUp() {
		mixed = new Name(null, "Beer", "Coke");
		// set up the standard temperature
		standardTemperature = new Temperature(0, 20);
		// set up the cold temperature
		coldTemperature = new Temperature(30, 0);
	}

	@BeforeEach
	public void setUpForEach(){
		ingrTypeEverything = new IngredientType(Name.WATER, State.LIQUID, standardTemperature, false);
		ingrTypeMixed = new IngredientType(mixed, State.LIQUID, standardTemperature, true);
	}

	/**
	 * CONSTRUCTOR
	 */

	@Test
	public void testConstructor_mostExtended_legal() {

		ingrTypeNullSpecialName = new IngredientType(Name.WATER, State.LIQUID, standardTemperature, false);
		ingrTypeDiffTemp = new IngredientType(Name.WATER, State.LIQUID, coldTemperature, false);
		ingrTypeMixed = new IngredientType(mixed, State.LIQUID, standardTemperature, true);
		ingrTypeDiffState = new IngredientType(Name.WATER, State.POWDER, standardTemperature, false);

		// ingrTypeEverything
		assertEquals(State.LIQUID, ingrTypeEverything.getStandardState());
		assertEquals(0, ingrTypeEverything.getStandardTemperature()[0]);
		assertEquals(20, ingrTypeEverything.getStandardTemperature()[1]);
		assertFalse(ingrTypeEverything.isMixed());

		// different objects
		assertEquals(30, ingrTypeDiffTemp.getStandardTemperature()[0]);
		assertEquals(0, ingrTypeDiffTemp.getStandardTemperature()[1]);
		assertTrue(ingrTypeMixed.isMixed());
		assertEquals(State.POWDER, ingrTypeDiffState.getStandardState());

	}
}
