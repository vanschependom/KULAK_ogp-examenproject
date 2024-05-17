package rpg.alchemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TemperatureDeviceTest {

    private Laboratory laboratory;
    private Laboratory otherLaboratory;
    private CoolingBox coolingBox;
    private Oven oven;

    @BeforeEach
    public void setUp() {
        laboratory = new Laboratory(30);
        otherLaboratory = new Laboratory(20);
        coolingBox = new CoolingBox(laboratory, new Temperature(90, 0));
        oven = new Oven(laboratory, new Temperature(0, 120));
    }

    @Test
    public void constructorValid1() {
        assertTrue(new Temperature(90, 0).equals(coolingBox.getTemperatureObject()));
    }

    @Test
    public void constructorValid2() {
        Oven oven1 = new Oven(otherLaboratory, new Temperature(0, 500));
        assertTrue(new Temperature(0, 500).equals(oven1.getTemperatureObject()));
    }

    @Test
    public void constructorInvalid1() {
        assertThrows(NullPointerException.class, () -> {
            CoolingBox coolingBox1 = new CoolingBox(otherLaboratory, null);
        });
    }

    @Test
    public void constructorInvalid2() {
        assertThrows(IllegalArgumentException.class, () -> {
            Oven oven1 = new Oven(laboratory, null);
        });
    }

    @Test
    public void setTemperatureValid1() {
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
}
