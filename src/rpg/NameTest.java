package rpg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NameTest {

	@Test
	public void testIsValidNameIllegal() {
		assertFalse(Name.isValidName("a"));
		assertFalse(Name.isValidName("ab"));
		assertFalse(Name.isValidName("abc"));
		assertFalse(Name.isValidName("!!"));
		assertFalse(Name.isValidName("ab b"));
		assertFalse(Name.isValidName("ab!"));
		assertFalse(Name.isValidName("Beer mixed with Coke"));
		assertFalse(Name.isValidName("Heated Cola"));
		assertFalse(Name.isValidName("cooled Water"));
		assertFalse(Name.isValidName("Something and Something Else"));
	}

	@Test
	public void testIsValidNameLegal() {
		assertTrue(Name.isValidName("Lizard's Tale"));
		assertTrue(Name.isValidName("Cat's Eye"));
		assertTrue(Name.isValidName("Red Mushroom Gas"));
		assertTrue(Name.isValidName("Ab Cd's Ef"));
	}

}
