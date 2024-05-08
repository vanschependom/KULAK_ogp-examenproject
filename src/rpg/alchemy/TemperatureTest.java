package rpg.alchemy;

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
		Temperature t = new Temperature(0, 55);
		assertEquals(0, t.getColdness());
		assertEquals(55, t.getHotness());
	}

	@Test
	public void constructor_legalTemperature3() {
		Temperature t = new Temperature(55,0);
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
		Temperature t = new Temperature(10, 0);
		t.heat(30);
		assertEquals(20, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void legalHeatUpperBound1() {
		Temperature t = new Temperature(0,500);
		t.heat(10000000);
		assertEquals(10000, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void legalHeatUpperBound2() {
		Temperature t = new Temperature(5000, 0);
		t.heat(12000); // je moet wel iets meer dan 10000 kunnen opwarmen denk ik, zoals in dit scenario
		assertEquals(7000, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void legalHeatUpperBound3(){
		Temperature t = new Temperature(500,0);
		t.heat(10000000);
		assertEquals(10000, t.getHotness());
		assertEquals(0, t.getColdness());
	}

	@Test
	public void illegalHeat1() {
		Temperature t = new Temperature(1000, 0);
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
		Temperature t = new Temperature(0, 10);
		t.cool(30);
		assertEquals(0, t.getHotness());
		assertEquals(20, t.getColdness());
	}

	@Test
	public void legalCoolUpperBound1() {
		Temperature t = new Temperature(500, 0);
		t.cool(10000000);
		assertEquals(0, t.getHotness());
		assertEquals(10000, t.getColdness());
	}

	@Test
	public void legalCoolUpperBound2() {
		Temperature t = new Temperature(0, 5000);
		t.cool(11000);
		assertEquals(0, t.getHotness());
		assertEquals(6000, t.getColdness());
	}

	@Test
	public void legalCoolUpperBound3() {
		Temperature t = new Temperature(0, 5000);
		t.cool(1000000000);
		assertEquals(0, t.getHotness());
		assertEquals(10000, t.getColdness());
	}

	@Test
	public void heatEdgeCase1() {
		Temperature t = new Temperature(1,0);
		t.heat(10001);
		assertEquals(0,t.getColdness());
		assertEquals(10000,t.getHotness());
	}

	@Test
	public void heatEdgeCase2() {
		Temperature t = new Temperature(9999,0);
		t.heat(10000);
		assertEquals(0,t.getColdness());
		assertEquals(1,t.getHotness());
	}

	@Test
	public void heatEdgeCase3() {
		Temperature t = new Temperature(9999,0);
		t.heat(19999);
		assertEquals(0,t.getColdness());
		assertEquals(10000,t.getHotness());
	}

	@Test
	public void heatEdgeCase4() {
		Temperature t = new Temperature(9999,0);
		t.heat(1000000000);
		assertEquals(0,t.getColdness());
		assertEquals(10000,t.getHotness());
	}

	@Test
	public void coolEdgeCase1() {
		Temperature t = new Temperature(0,1);
		t.cool(10001);
		assertEquals(10000,t.getColdness());
		assertEquals(0,t.getHotness());
	}

	@Test
	public void coolEdgeCase2() {
		Temperature t = new Temperature(0,9999);
		t.cool(10000);
		assertEquals(1,t.getColdness());
		assertEquals(0,t.getHotness());
	}

	@Test
	public void coolEdgeCase3() {
		Temperature t = new Temperature(0,9999);
		t.cool(19999);
		assertEquals(10000,t.getColdness());
		assertEquals(0,t.getHotness());
	}

	@Test
	public void coolEdgeCase4() {
		Temperature t = new Temperature(0,9999);
		t.cool(500000000);
		assertEquals(10000,t.getColdness());
		assertEquals(0,t.getHotness());
	}

	@Test
	public void illegalCool() {
		Temperature t1 = new Temperature(0, 0);
		Temperature t2 = new Temperature(0, 20);
		t1.cool(-10);
		t2.cool(-10);
		assertEquals(0, t1.getHotness());
		assertEquals(0, t1.getColdness());
		assertEquals(20, t2.getHotness());
		assertEquals(0, t2.getColdness());
	}

	@Test
	public void add1() {
		Temperature t1 = new Temperature(20, 0);
		Temperature t2 = new Temperature(0, 20);
		Temperature t3 = Temperature.add(t1,t2);
		assertEquals(0,t3.getColdness());
		assertEquals(0,t3.getHotness());
	}

	@Test
	public void add2() {
		Temperature t1 = new Temperature(451, 0);
		Temperature t2 = new Temperature(123, 0);
		Temperature t3 = Temperature.add(t1,t2);
		assertEquals(574,t3.getColdness());
		assertEquals(0,t3.getHotness());
	}

	@Test
	public void mul1() {
		Temperature t = new Temperature(0, 45);
		t.mul(2);
		assertEquals(0,t.getColdness());
		assertEquals(90,t.getHotness());
	}

	@Test
	public void mul2() {
		Temperature t = new Temperature(0, 45);
		t.mul(0.5);
		assertEquals(0,t.getColdness());
		assertEquals(22,t.getHotness());
	}

}
