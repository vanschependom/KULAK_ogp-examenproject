package rpg.ingredient;
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

	@BeforeAll
	public static void setUp() {
		// set up the standard temperature
		standardTemperature = new Temperature(0, 20);
		// set up the cold temperature
		coldTemperature = new Temperature(30, 0);
	}

	@BeforeEach
	public void setUpForEach(){
		ingrTypeEverything = new IngredientType("Simple Name", "Special Name", State.LIQUID, standardTemperature, false);
	}

	@Test
	public void testConstructor_mostExtended_legal() {

		ingrTypeEverything = new IngredientType("Simple Name", "Special Name", State.LIQUID, standardTemperature, false);
		ingrTypeNullSpecialName = new IngredientType("Simple Name", null, State.LIQUID, standardTemperature, false);
		ingrTypeDiffTemp = new IngredientType("Simple Name", "Special Name", State.LIQUID, coldTemperature, false);
		ingrTypeMixed = new IngredientType("Simple Name", "Special Name", State.LIQUID, standardTemperature, true);
		ingrTypeDiffState = new IngredientType("Simple Name", "Special Name", State.POWDER, standardTemperature, false);

		// ingrTypeEverything
		assertEquals("Simple Name", ingrTypeEverything.getSimpleName());
		assertEquals("Special Name", ingrTypeEverything.getSpecialName());
		assertEquals(State.LIQUID, ingrTypeEverything.getStandardState());
		assertEquals(0, ingrTypeEverything.getStandardTemperature()[0]);
		assertEquals(20, ingrTypeEverything.getStandardTemperature()[1]);
		assertFalse(ingrTypeEverything.isMixed());

		// different objects
		assertNull(ingrTypeNullSpecialName.getSpecialName());
		assertEquals(30, ingrTypeDiffTemp.getStandardTemperature()[0]);
		assertEquals(0, ingrTypeDiffTemp.getStandardTemperature()[1]);
		assertTrue(ingrTypeMixed.isMixed());
		assertEquals(State.POWDER, ingrTypeDiffState.getStandardState());

	}

	@Test
	public void name_LegalCase1(){
		String name = "Cola";
		assertTrue(ingrTypeEverything.canHaveAsName(name));
	}

	@Test
	public void name_LegalCase2(){
		String name = "Red Mushroom Gas";
		assertTrue(ingrTypeEverything.canHaveAsName(name));
	}

	@Test
	public void name_LegalCase3(){
		String name = "Rat's Eye Fluid";
		assertTrue(ingrTypeEverything.canHaveAsName(name));
	}

	@Test
	public void name_LegalCase4(){
		String name = "Sap";
		assertTrue(ingrTypeEverything.canHaveAsName(name));
	}

	@Test
	public void name_LegalCase5(){
		String name = "Water Qi";
		assertTrue(ingrTypeEverything.canHaveAsName(name));
	}

	@Test
	public void name_IllegalCase1(){
		String name = "Heated Water";
		assertFalse(ingrTypeEverything.canHaveAsName(name));
	}

	@Test
	public void name_IllegalCase2(){
		String name = "Cooled Water mixed with Cola";
		assertFalse(ingrTypeEverything.canHaveAsName(name));
	}

	@Test
	public void name_IllegalCase3(){
		String name = "Water mixed with Cola";
		assertFalse(ingrTypeEverything.canHaveAsName(name));
	}

	@Test
	public void name_IllegalCase4(){
		String name = "Qi";
		assertFalse(ingrTypeEverything.canHaveAsName(name));
	}

	@Test
	public void name_IllegalCase5(){
		String name = "";
		assertFalse(ingrTypeEverything.canHaveAsName(name));
	}

}
