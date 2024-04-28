package rpg;

import org.junit.jupiter.api.*;

public class UnitTest {

	@Test
	public void testConversionAllowed() {
		// from powder to liquid
		Assertions.assertFalse(Unit.PINCH.conversionAllowed(Unit.DROP));
		Assertions.assertTrue(Unit.PINCH.conversionAllowed(Unit.SPOON));
		// from liquid to powder
		Assertions.assertFalse(Unit.SPOON.conversionAllowed(Unit.PINCH)); // van spoon kan je toch naar alles, moet dit niet assertTrue zijn
		Assertions.assertTrue(Unit.BARREL.conversionAllowed(Unit.SACK)); // moet deze niet false zijn van enkel liquid naar enkel powder
	}

	@Test
	public void testConversion_NotAllowed(){
		Assertions.assertFalse(Unit.DROP.conversionAllowed(Unit.BOX));
		Assertions.assertFalse(Unit.JUG.conversionAllowed(Unit.SACHET));
		Assertions.assertFalse(Unit.CHEST.conversionAllowed(Unit.DROP));
	}

}
