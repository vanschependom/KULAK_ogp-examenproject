package rpg.alchemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TemperatureDeviceTest {

    private Laboratory laboratory;
    private CoolingBox coolingBox;
    private Oven oven;

    @BeforeEach
    public void setUp() {
        laboratory = new Laboratory(30);
        coolingBox = new CoolingBox(laboratory, new Temperature(90, 0));
        oven = new Oven(laboratory, new Temperature(0, 120));
    }

    @Test
    public void constructorValid() {
        CoolingBox coolingBox1 = new CoolingBox(laboratory, new Temperature(90, 0));
        assertEquals(laboratory, coolingBox1.getLaboratory());
        assertTrue(new Temperature(90, 0).equals(coolingBox1.getTemperature()));
    }

    @Test
    public void constructorValid2() {
        Oven oven1 = new Oven(laboratory, new Temperature(0, 500));
        assertEquals(laboratory, oven1.getLaboratory());
        assertTrue(new Temperature(0, 500).equals(oven1.getTemperature()));
    }

    @Test
    public void constructorInvalid() {
        assertThrows(NullPointerException.class, () -> {
            CoolingBox coolingBox1 = new CoolingBox(laboratory, null);
        });
    }

    @Test
    public void constructorInvalid2() {
        assertThrows(NullPointerException.class, () -> {
            CoolingBox coolingBox1 = new CoolingBox(null, null);
        });
    }

    @Test
    public void constructorInvalid3() {
        assertThrows(NullPointerException.class, () -> {
            CoolingBox coolingBox1 = new CoolingBox(null, new Temperature(10, 0));
        });
    }

    @Test
    public void constructorInvalid4() {
        assertThrows(NullPointerException.class, () -> {
            Oven oven1 = new Oven(null, new Temperature(10, 0));
        });
    }

    @Test
    public void constructorInvalid5() {
        assertThrows(NullPointerException.class, () -> {
            Oven oven1 = new Oven(null, null);
        });
    }

    @Test
    public void constructorInvalid6() {
        assertThrows(NullPointerException.class, () -> {
            Oven oven1 = new Oven(laboratory, null);
        });
    }

    @Test
    public void setTemperatureValid() {
        coolingBox.setTemperature(new Temperature(120, 0));
        assertEquals(120, coolingBox.getColdness());
        assertEquals(0, coolingBox.getHotness());
    }

    @Test
    public void setTemperatureValid2() {
        oven.setTemperature(new Temperature(10, 0));
        assertEquals(10, coolingBox.getColdness());
        assertEquals(0, coolingBox.getHotness());
    }

    @Test
    public void setTemperatureInvalid() {
        assertThrows(NullPointerException.class, () -> {
            oven.setTemperature(null);
        });
    }
}
