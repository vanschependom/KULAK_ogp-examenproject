package rpg.alchemy;

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
		assertFalse(Name.isValidName("Mixed Coke"));
	}

	@Test
	public void testIsValidNameLegal() {
		assertTrue(Name.isValidName("Lizard's Tale"));
		assertTrue(Name.isValidName("Cat's Eye"));
		assertTrue(Name.isValidName("Red Mushroom Gas"));
		assertTrue(Name.isValidName("Ab Cd's Ef"));
	}

	@Test
	public void testConstructor1() {
		Name name = new Name(null, "Red Mushroom Gas");
		assertFalse(name.isMixed());
		assertEquals("Red Mushroom Gas", name.getSimpleName());
		assertNull(name.getSpecialName());
	}

	@Test
	public void testConstructor2() {
		Name name = new Name(null, "Red Mushroom Gas", "Lizard's Tale");
		assertTrue(name.isMixed());
		assertEquals("Lizard's Tale mixed with Red Mushroom Gas", name.getSimpleName());
		assertNull(name.getSpecialName());
	}

	@Test
	public void testConstructorAlphabeticalOrder() {
		Name name = new Name(null, "Def", "Abc");
		assertEquals("Abc mixed with Def", name.getSimpleName());
		assertNull(name.getSpecialName());
	}

	@Test
	public void testConstructor3() {
		Name name = new Name("Mazout", "Beer", "Coke");
		assertTrue(name.isMixed());
		assertEquals("Beer mixed with Coke", name.getSimpleName());
		assertEquals("Mazout", name.getSpecialName());
	}

	@Test
	public void testConstructor4() {
		Name name = new Name(null, "Ccc", "Ddd", "Aaa", "Bbb");
		assertEquals("Aaa mixed with Bbb, Ccc and Ddd", name.getSimpleName());
		assertNull(name.getSpecialName());
	}

}
