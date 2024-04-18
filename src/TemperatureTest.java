import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TemperatureTest {

	@Test
	public void illegalHotAndCold() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Temperature(1, 1);
		});
	}

	@Test
	public void illegalTooHot() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Temperature(100000, 0);
		});
	}

	@Test
	public void illegalTooCold() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Temperature(0, 100000);
		});
	}

	@Test
	public void legalTemperature() {
		Temperature t = new Temperature(0, 0);
		assertEquals(0, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void legalHeat1() {
		Temperature t = new Temperature(0, 0);
		t.heat(10);
		assertEquals(10, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void legalHeat2() {
		Temperature t = new Temperature(0, 0);
		t.heat(10);
		t.heat(20);
		assertEquals(30, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void legalHeat3() {
		Temperature t = new Temperature(0, 10);
		t.heat(30);
		assertEquals(20, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void legalHeatUpperBound() {
		Temperature t = new Temperature(500, 0);
		t.heat(10000000);
		assertEquals(10000, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void illegalHeat() {
		assertThrows(IllegalArgumentException.class, () -> {
			Temperature t = new Temperature(0, 0);
			t.heat(-10);
		});
	}

	@Test
	public void legalCool1() {
		Temperature t = new Temperature(0, 0);
		t.cool(10);
		assertEquals(0, t.getHotness());
		assertEquals(10, t.getColdness());
	}

	@Test
	public void legalCool2() {
		Temperature t = new Temperature(0, 0);
		t.cool(10);
		t.cool(20);
		assertEquals(0, t.getHotness());
		assertEquals(30, t.getColdness());
	}

	@Test
	public void legalCool3() {
		Temperature t = new Temperature(10, 0);
		t.cool(30);
		assertEquals(0, t.getHotness());
		assertEquals(20, t.getColdness());
	}

	@Test
	public void legalCoolUpperBound() {
		Temperature t = new Temperature(0, 500);
		t.cool(10000000);
		assertEquals(0, t.getHotness());
		assertEquals(10000, t.getColdness());
	}

	@Test
	public void illegalCool() {
		assertThrows(IllegalArgumentException.class, () -> {
			Temperature t = new Temperature(0, 0);
			t.cool(-10);
		});
	}

}
