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

}
