package rpg.alchemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.State;
import rpg.Unit;

import static org.junit.jupiter.api.Assertions.*;

public class OvenTest {

    private Laboratory lab;
    private Oven oven;
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
        oven = new Oven(new Temperature(0, 150));
    }

    @Test
    public void executeOperationValid() {
        oven.addIngredients(container1);
        oven.executeOperation();
        IngredientContainer container = oven.getResult();
        AlchemicIngredient ingredient = container.getContent();
        assertEquals(ingredient.getAmount(), ingredient1.getAmount());
        assertEquals(ingredient.getType(), ingredient1.getType());
        assertTrue(oven.getTemperature().difference(ingredient.getTemperature()) <= 5);
        assertEquals(ingredient.getUnit(), ingredient1.getUnit());
        assertEquals(ingredient.getState(), ingredient1.getState());
    }

    @Test
    public void executeOperationValid2() {
        oven.addIngredients(container2);
        oven.changeTemperatureTo(new Temperature(0, 600));
        oven.executeOperation();
        IngredientContainer container = oven.getResult();
        AlchemicIngredient ingredient = container.getContent();
        assertEquals(ingredient.getAmount(), ingredient2.getAmount());
        assertEquals(ingredient.getType(), ingredient2.getType());
        assertTrue(oven.getTemperature().difference(ingredient.getTemperature()) <= 5);
        assertEquals(ingredient.getUnit(), ingredient2.getUnit());
        assertEquals(ingredient.getState(), ingredient2.getState());
    }

    @Test
    public void executeOperationInvalid() {
        assertThrows(IllegalStateException.class, () -> {
            oven.executeOperation();
        });
    }
}
