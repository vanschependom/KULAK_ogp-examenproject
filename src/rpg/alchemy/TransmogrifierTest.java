package rpg.alchemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.State;
import rpg.Unit;

import static org.junit.jupiter.api.Assertions.*;

public class TransmogrifierTest {

    private Laboratory lab;
    private Transmogrifier transmogrifier;
    private IngredientType type1;
    private IngredientType type2;
    private AlchemicIngredient ingredient1;
    private AlchemicIngredient ingredient2;
    private IngredientContainer container1;
    private IngredientContainer container2;

    @BeforeEach
    public void setUp() {
        lab = new Laboratory(5);
        type1 = new IngredientType(new Name(null, "Milk"), State.LIQUID, new Temperature(0, 15), false);
        type2 = new IngredientType(new Name(null, "Chocolate"), State.POWDER, new Temperature(0, 18), false);
        ingredient1 = new AlchemicIngredient(10, Unit.SPOON, type1);
        ingredient2 = new AlchemicIngredient(15, Unit.PINCH, type2);
        container1 = new IngredientContainer(Unit.BARREL, ingredient1);
        container2 = new IngredientContainer(Unit.CHEST, ingredient2);
        transmogrifier = new Transmogrifier();
    }

    @Test
    public void executeOperationValid() {
        transmogrifier.addIngredients(container1);
        transmogrifier.executeOperation();
        IngredientContainer container = transmogrifier.getResult();
        AlchemicIngredient ingredient = container.getContent();
        assertEquals((int)ingredient.getSpoonAmount(), (int)ingredient1.getSpoonAmount());
        assertEquals(ingredient.getType(), ingredient1.getType());
        assertEquals(ingredient.getTemperatureObject(), ingredient1.getTemperatureObject());
        assertEquals(Unit.SPOON, ingredient.getUnit());
        assertEquals(State.POWDER, ingredient.getState());
    }

    @Test
    public void executeOperationValid2() {
        transmogrifier.addIngredients(container2);
        transmogrifier.executeOperation();
        IngredientContainer container = transmogrifier.getResult();
        AlchemicIngredient ingredient = container.getContent();
        assertEquals((int) ingredient.getSpoonAmount(), (int) ingredient2.getSpoonAmount());
        assertEquals(ingredient.getType(), ingredient2.getType());
        assertEquals(ingredient.getTemperatureObject(), ingredient2.getTemperatureObject());
        assertEquals(Unit.SPOON, ingredient.getUnit());
        assertEquals(State.LIQUID, ingredient.getState());
    }

    @Test
    public void executeOperationInvalid() {
        assertThrows(IllegalStateException.class, () -> {
            transmogrifier.executeOperation();
        });
    }

}
