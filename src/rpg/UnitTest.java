package rpg;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

	@Test
	public void testConversionAllowed() {
		// from powder to liquid
		assertFalse(Unit.PINCH.conversionAllowed(Unit.DROP));
		assertTrue(Unit.PINCH.conversionAllowed(Unit.SPOON));
		// from liquid to powder
		assertFalse(Unit.BARREL.conversionAllowed(Unit.SACK));
		// spoon kan naar alles
		assertTrue(Unit.SPOON.conversionAllowed(Unit.PINCH));
	}

	@Test
	public void testConversion_NotAllowed(){
		assertFalse(Unit.DROP.conversionAllowed(Unit.BOX));
		assertFalse(Unit.JUG.conversionAllowed(Unit.SACHET));
		assertFalse(Unit.CHEST.conversionAllowed(Unit.DROP));
	}

	@Test
	public void spoonToBox() {
		Unit quantity = Unit.SPOON;
		double conversion = quantity.getConversionFor(Unit.BOX);
		double expected = (double) 1/42;
		assertEquals(expected,conversion);
	}

	@Test
	public void storeroomToJug() {
		Unit quantity = Unit.STOREROOM;
		double conversion = quantity.getConversionFor(Unit.JUG);
		double expected = (double) 6300/105;
		assertEquals(expected,conversion);
	}

	@Test
	public void maxUnitForContainer() {
		assertEquals(Unit.BARREL, Unit.getMaxUnitForContainerWithState(State.LIQUID));
		assertEquals(Unit.CHEST, Unit.getMaxUnitForContainerWithState(State.POWDER));
	}

}
