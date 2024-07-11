package rpg.alchemy;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestKlasse {

	@Test
	public void testTest() {
		assertThrows(IllegalArgumentException.class, ()->{
			Name name = new Name(null, null);
		});
	}


}
