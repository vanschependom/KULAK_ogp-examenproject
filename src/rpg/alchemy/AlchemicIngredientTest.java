package rpg.alchemy;
import org.junit.jupiter.api.*;
import rpg.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A JUnit 5 test class for testing the non-private methods of the AlchemicIngredient class.
 *
 * @author  Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 *
 * @version 1.0
 */
public class AlchemicIngredientTest {

	private static Name water;
	private static Name mixed;

	private IngredientType mixedIngrTypePowder;
	private AlchemicIngredient ingredient;
	private AlchemicIngredient legalIngredient;

	private AlchemicIngredient mixedIngredient;

	private static Temperature standardTemperature;
	private static Temperature coldTemperature;

	@BeforeAll
	public static void setUp() {
		water = Name.WATER;
		mixed = new Name("Mazout", "Beer", "Coke");
		// set up the standard temperature
		standardTemperature = new Temperature(0, 20);
		// set up the cold temperature
		coldTemperature = new Temperature(30, 0);
	}

	@BeforeEach
	public void setUpForEach(){
		mixedIngrTypePowder = new IngredientType(mixed, State.POWDER, coldTemperature, true);
		legalIngredient = new AlchemicIngredient(20, Unit.BOX, standardTemperature, mixedIngrTypePowder, State.POWDER);
	}

	/**
	 * CONSTRUCTOR
	 */

	@Test
	public void testConstructor_mostExtended_illegalUnit() {

		// no exception is thrown because we implemented this nominally
		ingredient = new AlchemicIngredient(10, Unit.BOTTLE, standardTemperature, mixedIngrTypePowder, State.POWDER);
		assertFalse(ingredient.canHaveAsUnit(Unit.BOTTLE));
		assertEquals(10, ingredient.getAmount());
		assertEquals(Unit.BOTTLE, ingredient.getUnit());
		assertEquals(mixedIngrTypePowder, ingredient.getType());
		assertEquals(State.POWDER, ingredient.getState());

	}

	@Test
	public void testConstructor_mostExtended_legal() {
		AlchemicIngredient ingredient = new AlchemicIngredient(30, Unit.PINCH, standardTemperature, mixedIngrTypePowder, State.POWDER);
		assertEquals(30, ingredient.getAmount());
		assertEquals(Unit.PINCH, ingredient.getUnit());
		assertEquals(standardTemperature.getColdness(), ingredient.getTemperature()[0]);
		assertEquals(standardTemperature.getHotness(), ingredient.getTemperature()[1]);
		assertEquals(mixedIngrTypePowder, ingredient.getType());
		assertEquals(State.POWDER, ingredient.getState());
	}

	/**
	 * UNIT
	 */
	@Test
	public void testCanHaveAsUnit() {
		AlchemicIngredient ingredient = new AlchemicIngredient(30, Unit.PINCH, standardTemperature, mixedIngrTypePowder, State.POWDER);
		assertTrue(ingredient.canHaveAsUnit(Unit.SPOON));
		assertTrue(ingredient.canHaveAsUnit(Unit.SACHET));
		assertFalse(ingredient.canHaveAsUnit(Unit.JUG));
	}

	/**
	 * (FULL) NAME
	 */
	@Test
	public void testFullName1() {
		AlchemicIngredient ingredient = new AlchemicIngredient(30, Unit.PINCH, standardTemperature, mixedIngrTypePowder, State.POWDER);
		assertEquals("Beer mixed with Coke", ingredient.getSimpleName());
		assertEquals("Mazout", ingredient.getSpecialName());
		assertEquals("Mazout (Beer mixed with Coke)", ingredient.getFullName());
	}

	@Test
	public void testFullName2() {
		IngredientType thisIngredientType = new IngredientType(water, State.LIQUID, standardTemperature, false);
		AlchemicIngredient ingredient = new AlchemicIngredient(2, Unit.BOTTLE, coldTemperature, thisIngredientType, State.LIQUID);
		// temperatuur(0, 20) --> (30, 0)
		assertEquals("Water", ingredient.getSimpleName());
		assertNull(ingredient.getSpecialName());
		assertEquals("Cooled Water", ingredient.getFullName());
	}

}
