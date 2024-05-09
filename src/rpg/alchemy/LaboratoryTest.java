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
		// not yet added devices
		coolingBoxNotAdded = new CoolingBox(new Temperature(20, 0));
		ovenNotAdded = new Oven(new Temperature(0, 1000));
		transmogrifierNotAdded = new Transmogrifier();
		kettleNotAdded = new Kettle();
		// added devices
		coolingBox = new CoolingBox(new Temperature(20, 0));
		oven = new Oven(new Temperature(0, 1000));
		transmogrifier = new Transmogrifier();
		kettle = new Kettle();
	}

	@Test
	public void testConstructor_illegal() {
		assertThrows(IllegalArgumentException.class, () -> new Laboratory(0));
		assertThrows(IllegalArgumentException.class, () -> new Laboratory(-1));
	}

	@Test
	public void testAddDeviceLegal() {

		lab.addAsDevice(coolingBox);
		assertTrue(lab.hasDeviceOfType(coolingBox.getClass()));

		lab.addAsDevice(oven);
		assertTrue(lab.hasDeviceOfType(oven.getClass()));

		lab.addAsDevice(transmogrifier);
		assertTrue(lab.hasDeviceOfType(transmogrifier.getClass()));

		lab.addAsDevice(kettle);
		assertTrue(lab.hasDeviceOfType(kettle.getClass()));

	}

	@Test
	public void testAddDeviceAlreadyPresent1() {
		lab.addAsDevice(coolingBox);
		assertThrows(IllegalArgumentException.class, () -> {
			lab.addAsDevice(new CoolingBox(new Temperature(350, 0)));
		});
	}

	@Test
	public void testAddDeviceAlreadyPresent2() {
		lab.addAsDevice(coolingBox);
		assertThrows(IllegalArgumentException.class, () -> {
			lab.addAsDevice(coolingBox);
		});
	}

	@Test
	public void testAddDeviceAlreadyPresent3() {
		lab.addAsDevice(transmogrifier);
		assertThrows(IllegalArgumentException.class, () -> {
			lab.addAsDevice(new Transmogrifier());
		});
	}

	@Test
	public void testAddDeviceNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			lab.addAsDevice(null);
		});
	}

}
