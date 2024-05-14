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
	
	private static Name mixedName;

	private IngredientType mixedIngrTypePowder;
	private AlchemicIngredient ingredient;
	private AlchemicIngredient legalIngredient;

	private AlchemicIngredient mixedIngredient;

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
	public void setUpForEach() {
		mixedName = new Name("Mazout", "Beer", "Coke");
		mixedIngrTypePowder = new IngredientType(mixedName, State.POWDER, coldTemperature, true);
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
	public void testConstructor_illegalState() {
		assertThrows(IllegalStateException.class, () -> {
			ingredient = new AlchemicIngredient(10, Unit.BOTTLE, standardTemperature, mixedIngrTypePowder, null);
		});
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
		assertEquals("Mazout (Heated Beer mixed with Coke)", ingredient.getFullName());
	}

	@Test
	public void testFullName2() {
		AlchemicIngredient ingredient = new AlchemicIngredient(30, Unit.PINCH, coldTemperature, mixedIngrTypePowder, State.POWDER);
		assertEquals("Beer mixed with Coke", ingredient.getSimpleName());
		assertEquals("Mazout", ingredient.getSpecialName());
		assertEquals("Mazout (Beer mixed with Coke)", ingredient.getFullName());
	}

	@Test
	public void testFullName3() {
		IngredientType thisIngredientType = new IngredientType(Name.WATER, State.LIQUID, standardTemperature, false);
		AlchemicIngredient ingredient = new AlchemicIngredient(2, Unit.BOTTLE, coldTemperature, thisIngredientType, State.LIQUID);
		// temperatuur(0, 20) --> (30, 0) cooled
		assertEquals("Water", ingredient.getSimpleName());
		assertNull(ingredient.getSpecialName());
		assertEquals("Cooled Water", ingredient.getFullName());
	}

	@Test
	public void testFullName4() {
		IngredientType thisIngredientType = new IngredientType(Name.WATER, State.LIQUID, standardTemperature, false);
		AlchemicIngredient ingredient = new AlchemicIngredient(2, Unit.BOTTLE, new Temperature(0,10), thisIngredientType, State.LIQUID);
		// temperatuur(0, 20) --> (0, 10) cooled
		assertEquals("Water", ingredient.getSimpleName());
		assertNull(ingredient.getSpecialName());
		assertEquals("Cooled Water", ingredient.getFullName());
	}

	@Test
	public void testFullName5() {
		IngredientType thisIngredientType = new IngredientType(Name.WATER, State.LIQUID, standardTemperature, false);
		AlchemicIngredient ingredient = new AlchemicIngredient(2, Unit.BOTTLE, standardTemperature, thisIngredientType, State.LIQUID);
		// temperatuur(0, 20) --> (0, 20) niets
		assertEquals("Water", ingredient.getSimpleName());
		assertNull(ingredient.getSpecialName());
		assertEquals("Water", ingredient.getFullName());
	}

	@Test
	public void testFullName6() {
		IngredientType thisIngredientType = new IngredientType(Name.WATER, State.LIQUID, standardTemperature, false);
		AlchemicIngredient ingredient = new AlchemicIngredient(2, Unit.BOTTLE, new Temperature(0,30), thisIngredientType, State.LIQUID);
		// temperatuur(0, 20) --> (0, 30) Heated
		assertEquals("Water", ingredient.getSimpleName());
		assertNull(ingredient.getSpecialName());
		assertEquals("Heated Water", ingredient.getFullName());
	}

	/**
	 * EQUALS
	 */
	@Test
	public void testEquals1() {
		AlchemicIngredient ingredient1 = new AlchemicIngredient(30, Unit.PINCH, standardTemperature, mixedIngrTypePowder, State.POWDER);
		AlchemicIngredient ingredient2 = new AlchemicIngredient(30, Unit.PINCH, standardTemperature, mixedIngrTypePowder, State.POWDER);
		assertTrue(ingredient1.equals(ingredient2));
	}

	@Test
	public void testEquals2() {
		Temperature firstTemperature = new Temperature(420, 0);
		Temperature secondTemperature = new Temperature(420, 0);
		AlchemicIngredient ingredient1 = new AlchemicIngredient(30, Unit.PINCH, firstTemperature, mixedIngrTypePowder, State.POWDER);
		AlchemicIngredient ingredient2 = new AlchemicIngredient(30, Unit.PINCH, secondTemperature, mixedIngrTypePowder, State.POWDER);
		assertTrue(ingredient1.equals(ingredient2));
	}

	@Test
	public void testEquals3() {
		AlchemicIngredient ingredient1 = new AlchemicIngredient(30, Unit.PINCH, standardTemperature, mixedIngrTypePowder, State.POWDER);
		AlchemicIngredient ingredient2 = new AlchemicIngredient(30, Unit.PINCH, standardTemperature, mixedIngrTypePowder, State.LIQUID);
		assertFalse(ingredient1.equals(ingredient2));
	}

}
