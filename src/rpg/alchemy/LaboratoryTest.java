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
public class LaboratoryTest {

	private static Laboratory lab;
	private static Temperature standardTemperature;
	private static Temperature coldTemperature;

	// devices
	private static CoolingBox coolingBox;
	private static Oven oven;
	private static Transmogrifier transmogrifier;
	private static Kettle kettle;
	private static CoolingBox coolingBoxNotAdded;
	private static Oven ovenNotAdded;
	private static Transmogrifier transmogrifierNotAdded;
	private static Kettle kettleNotAdded;

	@BeforeEach
	public void setupFixture() {
		// set up the standard temperature
		standardTemperature = new Temperature();
		// set up the cold temperature
		coldTemperature = new Temperature(30, 0);
		lab = new Laboratory(2); // lab with capacity of 2 storerooms
//		// not yet added devices
//		coolingBoxNotAdded = new CoolingBox(lab, new Temperature(20, 0));
//		ovenNotAdded = new Oven(lab, new Temperature(0, 1000));
//		transmogrifierNotAdded = new Transmogrifier(lab);
//		kettleNotAdded = new Kettle(lab);
//		// added devices
//		coolingBox = new CoolingBox(lab, new Temperature(20, 0));
//		oven = new Oven(lab, new Temperature(0, 1000));
//		transmogrifier = new Transmogrifier(lab);
//		kettle = new Kettle(lab);
	}

	@Test
	public void testConstructor_illegal() {
		assertThrows(IllegalArgumentException.class, () -> new Laboratory(0));
		assertThrows(IllegalArgumentException.class, () -> new Laboratory(-1));
	}

	@Test
	public void testAddDevices_legal() {
		coolingBox = new CoolingBox(lab, new Temperature(20, 0));
		oven = new Oven(lab, new Temperature(0, 1000));
		transmogrifier = new Transmogrifier(lab);
		kettle = new Kettle(lab);
	}

	@Test
	public void testAddDevices_typeAlreadyAdded() {
		coolingBox = new CoolingBox(lab, new Temperature(20, 0));
		// a cooling box is already added
		assertThrows(IllegalArgumentException.class, () -> {
			new CoolingBox(lab, new Temperature(20, 0));
		});
	}

	@Test
	public void testAddDevices_typeAlreadyAdded2() {
		oven = new Oven(lab, new Temperature(0, 1000));
		// an oven is already added
		assertThrows(IllegalArgumentException.class, () -> {
			new Oven(lab, new Temperature(0, 1000));
		});
	}

	@Test
	public void testAddDevices_typeAlreadyAdded3() {
		transmogrifier = new Transmogrifier(lab);
		assertThrows(IllegalArgumentException.class, () -> {
			new Transmogrifier(lab);
		});
	}

	@Test
	public void testAddDevices_typeAlreadyAdded4() {
		kettle = new Kettle(lab);
		assertThrows(IllegalArgumentException.class, () -> {
			new Kettle(lab);
		});
	}

	@Test
	public void testAddDevices_IllegalCase() {
		assertThrows(IllegalArgumentException.class, () -> {
			lab.addAsDevice(null);
		});
	}

	@Test
	public void testAddDevices_IllegalCase2() {
		Laboratory otherlab = new Laboratory(2);
		coolingBox = new CoolingBox(otherlab, new Temperature(20, 0));
		assertThrows(IllegalStateException.class, () -> {
			lab.addAsDevice(coolingBox);
		});
	}

	@Test
	public void testRemoveAsDevice_IllegalCase() {
		Laboratory otherlab = new Laboratory(2);
		coolingBox = new CoolingBox(otherlab, new Temperature(20, 0));
		assertThrows(IllegalArgumentException.class, () -> {
			lab.removeAsDevice(coolingBox);
		});
	}

	@Test
	public void testRemoveAsDevice_IllegalCase2() {
		coolingBox = new CoolingBox(lab, new Temperature(20, 0));
		assertThrows(IllegalStateException.class, () -> {
			lab.removeAsDevice(coolingBox);
		});
	}

	@Test
	public void testGetAllOfIngredientAt() {
		IngredientType type = new IngredientType(new Name(null, "Name"), State.POWDER, new Temperature(0, 20), false);
		IngredientType type2 = new IngredientType(new Name(null, "Namezef"), State.POWDER, new Temperature(0, 20), false);
		AlchemicIngredient ingredient1 = new AlchemicIngredient(2, Unit.SPOON, type);
		AlchemicIngredient ingredient2 = new AlchemicIngredient(15, Unit.SPOON, type2);
		IngredientContainer container1 = new IngredientContainer(ingredient1);
		IngredientContainer container2 = new IngredientContainer(ingredient2);
		lab.addIngredients(container1);
		lab.addIngredients(container2);
		assertTrue(ingredient1.equals(lab.getAllOfIngredientAt(0).getContent()));
		assertEquals(1, lab.getNbOfIngredients());
		assertTrue(ingredient2.equals(lab.getIngredientAt(0)));
	}

	@Test
	public void testGetAllOfIngredientAt_IllegalCase() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			lab.getAllOfIngredientAt(-1);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			lab.getAllOfIngredientAt(2);
		});
	}

	@Test
	public void testGetAmountOfIngredientAt() {
		IngredientType type = new IngredientType(new Name(null, "Name"), State.POWDER, new Temperature(0, 20), false);
		IngredientType type2 = new IngredientType(new Name(null, "Nameze"), State.POWDER, new Temperature(0, 20), false);
		AlchemicIngredient ingredient1 = new AlchemicIngredient(2, Unit.SPOON, type);
		AlchemicIngredient ingredient2 = new AlchemicIngredient(15, Unit.SPOON, type2);
		IngredientContainer container1 = new IngredientContainer(ingredient1);
		IngredientContainer container2 = new IngredientContainer(ingredient2);
		lab.addIngredients(container1);
		lab.addIngredients(container2);
		AlchemicIngredient ingredient = lab.getAmountOfIngredientAt(1, 12, Unit.SPOON).getContent();
		assertTrue(new AlchemicIngredient(1, Unit.SPOON, type).equals(lab.getAmountOfIngredientAt(0, 1, Unit.SPOON).getContent()));
		assertTrue(new AlchemicIngredient(12, Unit.SPOON, type2).equals(ingredient));
		assertTrue(new AlchemicIngredient(1, Unit.SPOON, type).equals(lab.getIngredientAt(1)));
		assertTrue(new AlchemicIngredient(3, Unit.SPOON, type2).equals(lab.getIngredientAt(0)));
		assertEquals(2, lab.getNbOfIngredients());
	}

	@Test
	public void testGetAmountOfIngredientAt_IllegalCase() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			lab.getAmountOfIngredientAt(4, 6, Unit.SACK);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			lab.getAmountOfIngredientAt(-3, 3, Unit.BARREL);
		});
	}

	@Test
	public void testGetIndexOfDevice() {
		coolingBox = new CoolingBox(lab, new Temperature(20, 0));
		oven = new Oven(lab, new Temperature(0, 1000));
		transmogrifier = new Transmogrifier(lab);
		kettle = new Kettle(lab);
		assertEquals(0, lab.getIndexOfDevice(coolingBox));
		assertEquals(1, lab.getIndexOfDevice(oven));
		assertEquals(2, lab.getIndexOfDevice(transmogrifier));
		assertEquals(3, lab.getIndexOfDevice(kettle));
	}

	@Test
	public void testGetIndexOfDevice_Illegal() {
		Laboratory otherlab = new Laboratory(2);
		coolingBox = new CoolingBox(otherlab, new Temperature(20, 0));
		assertThrows(IllegalArgumentException.class, () -> {
			lab.getIndexOfDevice(coolingBox);
		});

	}

	@Test
	public void testGetIndexOfDevice_Illegal2() {
		assertThrows(IllegalArgumentException.class, () -> {
			lab.getIndexOfDevice(null);
		});

	}

	@Test
	public void testGetDeviceOfType_Illegal() {
		assertThrows(IllegalArgumentException.class, () -> {
			lab.getIndexOfDevice(null);
		});
	}

	@Test
	public void testGetDeviceOfType_Illegal2() {
		Laboratory otherlab = new Laboratory(2);
		coolingBox = new CoolingBox(otherlab, new Temperature(20, 0));
		assertThrows(IllegalArgumentException.class, () -> {
			lab.getIndexOfDevice(coolingBox);
		});
	}

	@Test
	public void testHasDeviceTypeOf() {
		Laboratory otherlab = new Laboratory(2);
		coolingBox = new CoolingBox(lab, new Temperature(20, 0));
		oven = new Oven(lab, new Temperature(0, 1000));
		transmogrifier = new Transmogrifier(otherlab);
		kettle = new Kettle(lab);
		assertTrue(lab.hasDeviceOfType(CoolingBox.class));
		assertTrue(lab.hasDeviceOfType(oven.getClass()));
		assertFalse(lab.hasDeviceOfType(transmogrifier.getClass()));
		assertTrue(lab.hasDeviceOfType(kettle.getClass()));
	}

	@Test
	public void testGetIndexOfSimpleName() {
		IngredientType type = new IngredientType(new Name(null, "Name"), State.POWDER, new Temperature(0, 20), false);
		IngredientType type2 = new IngredientType(new Name(null, "Name Hemlk"), State.POWDER, new Temperature(0, 20), false);
		AlchemicIngredient ingredient1 = new AlchemicIngredient(2, Unit.SPOON, type);
		AlchemicIngredient ingredient2 = new AlchemicIngredient(15, Unit.SPOON, type2);
		IngredientContainer container1 = new IngredientContainer(ingredient1);
		IngredientContainer container2 = new IngredientContainer(ingredient2);
		lab.addIngredients(container1);
		lab.addIngredients(container2);
		assertEquals(0, lab.getIndexOfSimpleName("Name"));
		assertEquals(1, lab.getIndexOfSimpleName("Name Hemlk"));
	}

	@Test
	public void testGetIndexOfSimpleName_IllegalCase() {
		assertThrows(IllegalArgumentException.class, () -> {
			assertEquals(0, lab.getIndexOfSimpleName("Name Hello"));
		});
	}

	@Test
	public void testGetIndexOfSpecialName() {
		IngredientType type = new IngredientType(new Name(null, "Name"), State.POWDER, new Temperature(0, 20), false);
		IngredientType type2 = new IngredientType(new Name(null, "Name Namez"), State.POWDER, new Temperature(0, 20), false);
		IngredientType type3 = new IngredientType(new Name("Mazout", "Beer", "Cola"), State.LIQUID, new Temperature(10, 0), true);
		AlchemicIngredient ingredient1 = new AlchemicIngredient(2, Unit.SPOON, type);
		AlchemicIngredient ingredient2 = new AlchemicIngredient(15, Unit.SPOON, type2);
		AlchemicIngredient ingredient3 = new AlchemicIngredient(8, Unit.JUG, type3);
		IngredientContainer container1 = new IngredientContainer(ingredient1);
		IngredientContainer container2 = new IngredientContainer(ingredient2);
		IngredientContainer container3 = new IngredientContainer(ingredient3);
		lab.addIngredients(container1);
		lab.addIngredients(container2);
		lab.addIngredients(container3);
		assertEquals(2, lab.getIndexOfSpecialName("Mazout"));
	}

	@Test
	public void testGetIndexOfSpecialName_IllegalCase() {
		assertThrows(IllegalArgumentException.class, () -> {
			assertEquals(0, lab.getIndexOfSpecialName("Name Name"));
		});
	}

	@Test
	public void testExceedsCapacity() {
		Laboratory otherLab = new Laboratory(1);
		new Kettle(otherLab);
		IngredientType type = new IngredientType(new Name(null, "Name"), State.POWDER, new Temperature(0, 20), false);
		AlchemicIngredient ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		IngredientContainer container1 = new IngredientContainer(ingredient1);
		assertFalse(otherLab.exceedsCapacity(container1));
		otherLab.addIngredients(container1);
		type = new IngredientType(new Name(null, "Nameezg"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		assertFalse(otherLab.exceedsCapacity(container1));
		otherLab.addIngredients(container1);
		type = new IngredientType(new Name(null, "Nameazrvnoiaerv"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		assertFalse(otherLab.exceedsCapacity(container1));
		otherLab.addIngredients(container1);
		type = new IngredientType(new Name(null, "Nameajernva"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		assertFalse(otherLab.exceedsCapacity(container1));
		otherLab.addIngredients(container1);
		type = new IngredientType(new Name(null, "Nameaeerjnjaerv"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		assertFalse(otherLab.exceedsCapacity(container1));
		otherLab.addIngredients(container1);
		type = new IngredientType(new Name(null, "Nameaerlrjkfn"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		assertTrue(otherLab.exceedsCapacity(container1));
	}

	@Test
	public void testExceedsCapacity_IllegalCase() {
		assertThrows(NullPointerException.class, () -> {
			lab.exceedsCapacity(null);
		});
	}

	@Test
	public void testAddIngredients_IllegalCase() {
		Laboratory otherLab = new Laboratory(1);
		new Kettle(otherLab);
		IngredientType type = new IngredientType(new Name(null, "Name"), State.POWDER, new Temperature(0, 20), false);
		AlchemicIngredient ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		IngredientContainer container1 = new IngredientContainer(ingredient1);
		otherLab.addIngredients(container1);
		type = new IngredientType(new Name(null, "Nameezg"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		otherLab.addIngredients(container1);
		type = new IngredientType(new Name(null, "Nameazrvnoiaerv"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		otherLab.addIngredients(container1);
		type = new IngredientType(new Name(null, "Nameajernva"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		otherLab.addIngredients(container1);
		type = new IngredientType(new Name(null, "Nameaeerjnjaerv"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		otherLab.addIngredients(container1);
		type = new IngredientType(new Name(null, "Nameaerlrjkfn"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		IngredientContainer finalContainer = container1;
		assertThrows(IllegalArgumentException.class, () -> {
			otherLab.addIngredients(finalContainer);
		});
	}

	@Test
	public void testAddIngredients_IllegalCase2() {
		Laboratory laboratory = new Laboratory(2);
		IngredientType type = new IngredientType(new Name(null, "Name"), State.POWDER, new Temperature(0, 20), false);
		AlchemicIngredient ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		IngredientContainer container1 = new IngredientContainer(ingredient1);
		laboratory.addIngredients(container1);
		 type = new IngredientType(new Name(null, "Name"), State.POWDER, new Temperature(0, 20), false);
		ingredient1 = new AlchemicIngredient(1, Unit.CHEST, type);
		container1 = new IngredientContainer(ingredient1);
		IngredientContainer finalContainer = container1;
		assertThrows(IllegalStateException.class, () -> {
			laboratory.addIngredients(finalContainer);
		});
	}



	@Test
	public void testHasProperDevices() {
		Laboratory otherlab = new Laboratory(2);
		coolingBox = new CoolingBox(lab, new Temperature(20, 0));
		oven = new Oven(lab, new Temperature(0, 1000));
		transmogrifier = new Transmogrifier(otherlab);
		kettle = new Kettle(lab);
		assertTrue(lab.hasProperDevices());
		assertTrue(otherlab.hasProperDevices());
	}

}
