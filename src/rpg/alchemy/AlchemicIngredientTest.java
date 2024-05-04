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
	public void setUpForEach(){
		mixedIngrTypePowder = new IngredientType(null, State.POWDER, coldTemperature, true);
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

}
