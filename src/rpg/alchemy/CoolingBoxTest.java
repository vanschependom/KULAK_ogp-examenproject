package rpg.alchemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.State;
import rpg.Unit;

import static org.junit.jupiter.api.Assertions.*;

public class CoolingBoxTest {

    private Laboratory lab;
    private CoolingBox coolingBox;
    private IngredientType type1;
    private IngredientType type2;
    private AlchemicIngredient ingredient1;
    private AlchemicIngredient ingredient2;
    private IngredientContainer container1;
    private IngredientContainer container2;

    @BeforeEach
    public void SetUp() {
        lab = new Laboratory(2);
        type1 = new IngredientType(new Name(null, "Milk"), State.LIQUID, new Temperature(0, 15), false);
        type2 = new IngredientType(new Name(null, "Chocolate"), State.POWDER, new Temperature(0, 18), false);
        ingredient1 = new AlchemicIngredient(10, Unit.SPOON, type1);
        ingredient2 = new AlchemicIngredient(15, Unit.PINCH, type2);
        container1 = new IngredientContainer(Unit.BARREL, ingredient1);
        container2 = new IngredientContainer(Unit.CHEST, ingredient2);
        coolingBox = new CoolingBox(lab, new Temperature(20, 0));
    }

    @Test
    public void executeOperationValid() {
        coolingBox.addContainer(container1);
        coolingBox.executeOperation();
        IngredientContainer container = coolingBox.getResult();
        AlchemicIngredient ingredient = container.getContent();
        assertEquals(ingredient.getAmount(), ingredient1.getAmount());
        assertEquals(ingredient.getType(), ingredient1.getType());
        assertTrue(coolingBox.getTemperatureObject().difference(ingredient.getTemperature()) <= 5);
        assertEquals(ingredient.getUnit(), ingredient1.getUnit());
        assertEquals(ingredient.getState(), ingredient1.getState());
    }

    @Test
    public void executeOperationValid2() {
        coolingBox.addContainer(container2);
        coolingBox.changeTemperatureTo(new Temperature(60, 0));
        coolingBox.executeOperation();
        IngredientContainer container = coolingBox.getResult();
        AlchemicIngredient ingredient = container.getContent();
        assertEquals(ingredient.getAmount(), ingredient2.getAmount());
        assertEquals(ingredient.getType(), ingredient2.getType());
        assertTrue(coolingBox.getTemperatureObject().difference(ingredient.getTemperature()) <= 5);
        assertEquals(ingredient.getUnit(), ingredient2.getUnit());
        assertEquals(ingredient.getState(), ingredient2.getState());
    }

    @Test
    public void executeOperation_alreadyUnderneathTemperature() {
        IngredientType type = new IngredientType(new Name(null, "Milk"), State.LIQUID, new Temperature(50, 0), false);
        AlchemicIngredient ingredient = new AlchemicIngredient(10, Unit.SPOON, type);
        IngredientContainer container = new IngredientContainer(Unit.BARREL, ingredient);
        coolingBox.addContainer(container);
        assertEquals(50, ingredient.getColdness());
        coolingBox.executeOperation();
        assertEquals(50,ingredient.getColdness());
    }

    @Test
    public void executeOperationInvalid() {
        assertThrows(IllegalStateException.class, () -> {
            coolingBox.executeOperation();
        });
    }
}
