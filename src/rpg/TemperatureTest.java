package rpg;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TemperatureTest {

	@Test
	public void constructor_illegalHotAndCold() {
		Temperature t = new Temperature(1, 1);
		assertEquals(20, t.getHotness());
		assertEquals(0, t.getColdness());
		long[] result = t.getTemperature();
		assertEquals(0, result[0]);
		assertEquals(20, result[1]);
	}

	@Test
	public void constructor_illegalTooHot() {
		Temperature t = new Temperature(100000, 0);
		assertEquals(20, t.getHotness());
		assertEquals(0, t.getColdness());
		long[] result = t.getTemperature();
		assertEquals(0, result[0]);
		assertEquals(20, result[1]);
	}

	@Test
	public void constructor_illegalTooCold() {
		Temperature t = new Temperature(0, 100000);
		assertEquals(20, t.getHotness());
		assertEquals(0, t.getColdness());
		long[] result = t.getTemperature();
		assertEquals(0, result[0]);
		assertEquals(20, result[1]);
	}

	@Test
	public void constructor_legalTemperature1() {
		Temperature t = new Temperature(0, 0);
		assertEquals(0, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void constructor_legalTemperature2() {
		Temperature t = new Temperature(55, 0);
		assertEquals(55, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void constructor_legalTemperature3() {
		Temperature t = new Temperature(0,55);
		assertEquals(0, t.getHotness());
		assertEquals(55, t.getColdness());
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
	public void legalHeatUpperBound1() {
		Temperature t = new Temperature(500, 0);
		t.heat(10000000);
		assertEquals(10000, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void legalHeatUpperBound2() {
		Temperature t = new Temperature(0, 5000);
		t.heat(12000);
		assertEquals(7000, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void illegalHeat1() {
		Temperature t = new Temperature(0, 1000);
		t.heat(-20000000);
		assertEquals(0, t.getHotness());
		assertEquals(1000, t.getColdness());
	}

	@Test
	public void illegalHeat2() {
		Temperature t = new Temperature(0, 0);
		t.heat(-10);
		assertEquals(0, t.getHotness());
		assertEquals(0, t.getColdness());
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
	public void legalCoolUpperBound1() {
		Temperature t = new Temperature(0, 500);
		t.cool(10000000);
		assertEquals(0, t.getHotness());
		assertEquals(10000, t.getColdness());
	}

	@Test
	public void legalCoolUpperBound2() {
		Temperature t = new Temperature(5000, 0);
		t.cool(11000);
		assertEquals(0, t.getHotness());
		assertEquals(6000, t.getColdness());
	}

	@Test
	public void illegalCool() {
		Temperature t1 = new Temperature(0, 0);
		Temperature t2 = new Temperature(20, 0);
		t1.cool(-10);
		t2.cool(-10);
		assertEquals(0, t1.getHotness());
		assertEquals(0, t1.getColdness());
		assertEquals(20, t2.getHotness());
		assertEquals(0, t2.getColdness());
	}

}
