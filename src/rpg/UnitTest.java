package rpg;

import org.junit.jupiter.api.*;

public class UnitTest {

	@Test
	public void testConversionAllowed() {
		//from powder to liquid
		Assertions.assertFalse(Unit.PINCH.conversionAllowed(Unit.DROP));
		Assertions.assertTrue(Unit.PINCH.conversionAllowed(Unit.SPOON));
		// from liquid to powder
		Assertions.assertFalse(Unit.SPOON.conversionAllowed(Unit.PINCH));
		Assertions.assertTrue(Unit.BARREL.conversionAllowed(Unit.SACK));
	}

}
