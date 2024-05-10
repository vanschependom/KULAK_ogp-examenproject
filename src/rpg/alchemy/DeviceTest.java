package rpg.alchemy;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import rpg.*;

/**
 * A JUnit 5 test class for testing the non-private methods of the (abstract) Device class.
 *
 * @author  Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 *
 * @version 1.0
 */
public class DeviceTest {

	// we test the abstract class with concrete subclasses.

	Laboratory lab;
	Oven oven;
	CoolingBox coolingBox;
	Kettle kettle;
	Transmogrifier transmogrifier;

	IngredientType powderType;
	IngredientType powderTypeMixed;
	IngredientType liquidType;
	IngredientType liquidTypeMixed;

	AlchemicIngredient powder;
	AlchemicIngredient heatedPowder;
	AlchemicIngredient cooledPowder;
	AlchemicIngredient mixedPowder;
	AlchemicIngredient liquid;
	AlchemicIngredient heatedLiquid;
	AlchemicIngredient cooledLiquid;
	AlchemicIngredient mixedLiquid;

	@BeforeEach
	public void setUp() {
		// laboratory
		lab = new Laboratory(3);	// lab with the capacity of 3 storerooms
		oven = new Oven(lab, new Temperature(0, 300));
		coolingBox = new CoolingBox(lab, new Temperature(400, 0));
		kettle = new Kettle(lab);
		transmogrifier = new Transmogrifier(lab);
		// ingredient types
		powderType = new IngredientType(new Name(null, "Powder Sugar"), State.POWDER, new Temperature(), false);
		powderTypeMixed = new IngredientType(new Name("Breakfast", "Oatmeal", "Seeds"), State.POWDER, new Temperature(), true);
		liquidType = new IngredientType(new Name(null, "Sprite"), State.LIQUID, new Temperature(0, 100), false);
		liquidTypeMixed = new IngredientType(new Name("Watery Coke", "Water", "Coke"), State.LIQUID, new Temperature(), true);
		// ingredients
		powder = new AlchemicIngredient(100, Unit.PINCH, powderType);
		heatedPowder = new AlchemicIngredient(2, Unit.CHEST, new Temperature(0, 300), powderType);
		cooledPowder = new AlchemicIngredient(2, Unit.CHEST, new Temperature(300, 0), powderType);
		mixedPowder = new AlchemicIngredient(5, Unit.SACHET, powderTypeMixed);
		liquid = new AlchemicIngredient(2, Unit.JUG, liquidType);
		heatedLiquid = new AlchemicIngredient(8, Unit.BOTTLE, new Temperature(0, 300), liquidTypeMixed);
		cooledLiquid = new AlchemicIngredient(8, Unit.BOTTLE, new Temperature(300, 0), liquidTypeMixed);
		mixedLiquid = new AlchemicIngredient(10, Unit.VIAL, liquidTypeMixed);
	}

	@Test
	public void testConstructor_legal() {
		// a new storage location has no ingredients
		assertEquals(kettle.getNbOfIngredients(),0);
		// the max number of ingredients is set to maxNbOfIngredients
		assertEquals(kettle.getMaxNbOfIngredients(), Integer.MAX_VALUE);
		assertEquals(oven.getMaxNbOfIngredients(), 1);
		assertEquals(coolingBox.getMaxNbOfIngredients(), 1);
		assertEquals(transmogrifier.getMaxNbOfIngredients(), 1);
		// the device is added to the lab
		assertEquals(oven.getLaboratory(), lab);
		assertTrue(lab.hasAsDevice(oven));
	}

	@Test
	public void testConstructor_illegal_nullLab () {
		assertThrows(IllegalArgumentException.class, () -> {
			new Oven(null, new Temperature(0, 500));
		});
	}

	@Test
	public void testConstructor_illegal_alreadyPresentType () {
		assertThrows(IllegalArgumentException.class, () -> {
			new Oven(lab, new Temperature(0, 500));
		});
	}

	@Test
	public void testAddAsIngredient_legal() {
		oven.addAsIngredient(powder);
		//  The number of ingredients registered in this storage location is incremented with 1.
		assertEquals(oven.getNbOfIngredients(), 1);
		// the given ingredient is inserted at the last index
		assertEquals(oven.getIngredientAt(oven.getNbOfIngredients()-1), powder);
	}

	@Test
	public void testAddAsIngredient_illegal_nullIngredient() {
		assertThrows(IllegalArgumentException.class, () -> {
			kettle.addAsIngredient(null);
		});
	}

	@Test
	public void testAddAsIngredient_illegal_maxCapacity() {
		oven.addAsIngredient(powder);
		assertThrows(IllegalStateException.class, () -> {
			oven.addAsIngredient(powder);
		});
	}

	@Test
	public void testAddAsIngredient_illegal_alreadyPresent() {
		kettle.addAsIngredient(liquid);
		assertThrows(IllegalArgumentException.class, () -> {
			kettle.addAsIngredient(liquid);
		});
	}

	// TODO test alle methodes
	// kijk altijd lijn per lijn naar alle specificatie en test die ook op die manier

}




















