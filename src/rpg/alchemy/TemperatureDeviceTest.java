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
        coolingBox = new CoolingBox(new Temperature(90, 0));
        oven = new Oven(new Temperature(0, 120));
    }

    @Test
    public void constructorValid() {
        CoolingBox coolingBox1 = new CoolingBox(new Temperature(90, 0));
        assertTrue(new Temperature(90, 0).equals(coolingBox1.getTemperature()));
    }

    @Test
    public void constructorValid2() {
        Oven oven1 = new Oven(new Temperature(0, 500));
        assertTrue(new Temperature(0, 500).equals(oven1.getTemperature()));
    }

    @Test
    public void constructorInvalid() {
        assertThrows(NullPointerException.class, () -> {
            CoolingBox coolingBox1 = new CoolingBox(null);
        });
    }

    @Test
    public void constructorInvalid5() {
        assertThrows(IllegalArgumentException.class, () -> {
            Oven oven1 = new Oven( null);
        });
    }

    @Test
    public void setTemperatureValid() {
        coolingBox.changeTemperatureTo(new Temperature(120, 0));
        assertEquals(120, coolingBox.getColdness());
        assertEquals(0, coolingBox.getHotness());
    }

    @Test
    public void setTemperatureValid2() {
        oven.changeTemperatureTo(new Temperature(10, 0));
        assertEquals(10, oven.getColdness());
        assertEquals(0, oven.getHotness());
    }

    @Test
    public void setTemperatureInvalid() {
        assertThrows(NullPointerException.class, () -> {
            oven.changeTemperatureTo(null);
        });
    }
}
