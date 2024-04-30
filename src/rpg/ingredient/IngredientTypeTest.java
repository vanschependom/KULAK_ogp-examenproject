package rpg.ingredient;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import rpg.*;

/**
 * A JUnit (5) test class for testing the non-private methods of the IngredientType class.
 *
 * @author  Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 *
 * @version 1.0
 */
public class IngredientTypeTest {

	public void testConstructor_mostExtended_legal() {

		IngredientType ingrTypeEverything = new IngredientType("simpleName", "specialName", State.LIQUID, new Temperature(0, 20), false);
		IngredientType ingrTypeNullSpecialName = new IngredientType("simpleName", null, State.LIQUID, new Temperature(0, 20), false);

	}

}
