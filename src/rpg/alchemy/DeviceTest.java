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



	/**********************************************************
	 * CONSTRUCTORS
	 **********************************************************/

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



	/**********************************************************
	 * INGREDIENTS
	 **********************************************************/

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
			oven.addAsIngredient(heatedPowder);
		});
	}

	@Test
	public void testAddAsIngredient_illegal_alreadyPresent() {
		kettle.addAsIngredient(liquid);
		assertThrows(IllegalArgumentException.class, () -> {
			kettle.addAsIngredient(liquid);
		});
	}



	/**********************************************************
	 * LABORATORY
	 **********************************************************/

	@Test
	public void testMove_legal() {
		Laboratory newLab = new Laboratory(2);
		oven.move(newLab);
		// The new lab is set to lab of the device
		assertEquals(oven.getLaboratory(), newLab);
		// The device is removed from old lab
		assertFalse(lab.hasAsDevice(oven));
		assertFalse(lab.hasDeviceOfType(Oven.class));
		// The device is added to the new lab
		assertTrue(newLab.hasAsDevice(oven));
		assertTrue(newLab.hasDeviceOfType(Oven.class));
	}

	@Test
	public void testMove_illegal_terminated() {
		Laboratory newLab = new Laboratory(2);
		oven.terminate();
		assertThrows(IllegalStateException.class, () -> {
			oven.move(newLab);
		});
	}

	@Test
	public void testMove_illegal_null() {
		assertThrows(IllegalArgumentException.class, () -> {
			oven.move(null);
		});
	}

	@Test
	public void testMove_illegal_sameLab() {
		assertThrows(IllegalArgumentException.class, () -> {
			oven.move(lab);
		});
	}

	@Test
	public void testMove_illegal_alreadyOtherDeviceInLab() {
		Laboratory newLab = new Laboratory(2);
		Oven newOven = new Oven(newLab, new Temperature());
		assertThrows(IllegalArgumentException.class, () -> {
			newOven.move(lab); // an oven is already present in the lab
		});
	}

	/**
	 * @note	The move(.) method implicitly tests the setLaboratory(.) method.
	 * 			--> setLaboratory(.) is a protected method, so we can't test it directly.
 	 */

	@Test
	public void testCanHaveAsLaboratory_legal() {
		Laboratory newLab = new Laboratory(2);
		oven.move(newLab);
		assertTrue(oven.canHaveAsLaboratory(lab));
	}

	@Test
	public void testCanHaveAsLaboratory_illegal_null() {
		assertFalse(oven.canHaveAsLaboratory(null));
	}

	@Test
	public void testCanHaveAsLaboratory_illegal_terminated() {
		Laboratory newLab = new Laboratory(2);
		oven.move(newLab);
		oven.terminate();
		assertFalse(oven.canHaveAsLaboratory(lab));
	}

	@Test
	public void testCanHaveAsLaboratory_illegal_terminatedAndNull() {
		Laboratory newLab = new Laboratory(2);
		oven.move(newLab);
		oven.terminate();
		assertTrue(oven.canHaveAsLaboratory(null));
	}

	@Test
	public void testHasProperLaboratory_legal() {
		assertTrue(oven.hasProperLaboratory());
	}

	/**
	 * @note We can't test illegal cases for hasProperLaboratory(.).
	 */



	/**********************************************************
	 * OPERATION EXECUTION (we can test the general exceptions here)
	 **********************************************************/

	@Test
	public void testExecuteOperation_illegal_terminated() {
		oven.addAsIngredient(powder); // not empty
		oven.terminate();
		// oven is terminated
		assertThrows(IllegalStateException.class, () -> {
			oven.executeOperation();
		});
	}

	@Test
	public void testExecuteOperation_illegal_empty() {
		// oven is empty
		assertThrows(IllegalStateException.class, () -> {
			oven.executeOperation();
		});
	}



	/**********************************************************
	 * DESTRUCTION
	 **********************************************************/

	@Test
	public void testTerminate_legal() {
		oven.terminate();
		// device will be terminated
		assertTrue(oven.isTerminated());
		// lab is set to null
		assertNull(oven.getLaboratory());
		// lab will not have device anymore
		assertFalse(lab.hasAsDevice(oven));
	}

	@Test
	public void testTerminate_illegal_alreadyTerminated() {
		oven.terminate();
		// already terminated
		assertThrows(IllegalStateException.class, () -> {
			oven.terminate();
		});
	}

}




















