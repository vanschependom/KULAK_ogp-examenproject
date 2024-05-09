package rpg.alchemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.State;
import rpg.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KettleTest {

    private Laboratory lab;
    private Kettle kettle;
    private IngredientType type1;
    private IngredientType type2;
    private IngredientType type3;
    private IngredientType type4;
    private AlchemicIngredient ingredient1;
    private AlchemicIngredient ingredient2;
    private AlchemicIngredient ingredient3;
    private AlchemicIngredient ingredient4;
    private IngredientContainer container1;
    private IngredientContainer container2;
    private IngredientContainer container3;
    private IngredientContainer container4;


    @BeforeEach
    public void setUp() {
        lab = new Laboratory(5);
        type1 = new IngredientType(new Name(null, "Milk"), State.LIQUID, new Temperature(0, 15), false);
        type2 = new IngredientType(new Name(null, "Chocolate"), State.POWDER, new Temperature(0, 18), false);
        type3 = new IngredientType(new Name("Booze", "Beer", "Coca Cola"), State.LIQUID, new Temperature(0, 5), true);
        type4 = new IngredientType(new Name(null, "Stella Artois"), State.LIQUID, new Temperature(10, 0), false);
        ingredient1 = new AlchemicIngredient(10, Unit.SPOON, type1);
        ingredient2 = new AlchemicIngredient(15, Unit.PINCH, type2);
        ingredient3 = new AlchemicIngredient(3, Unit.JUG, type3);
        ingredient4 = new AlchemicIngredient(6, Unit.BOTTLE, type4);
        container1 = new IngredientContainer(Unit.BARREL, ingredient1);
        container2 = new IngredientContainer(Unit.CHEST, ingredient2);
        container3 = new IngredientContainer(Unit.BARREL, ingredient3);
        container4 = new IngredientContainer(Unit.BARREL, ingredient4);
        kettle = new Kettle(lab);
    }

    @Test
    public void executeOperationValid() {
        kettle.addIngredients(container1);
        kettle.addIngredients(container2);
        kettle.executeOperation();
        IngredientContainer container = kettle.getResult();
        AlchemicIngredient result = container.getContent();
        assertEquals((int) (ingredient1.getSpoonAmount() + ingredient2.getSpoonAmount()), (int) result.getSpoonAmount());
        assertEquals("Chocolate mixed with Milk", result.getSimpleName());
        assertEquals(State.POWDER, result.getState());
        assertEquals(0,result.getType().getStandardTemperatureObject().getColdness());
        assertEquals(18, result.getType().getStandardTemperatureObject().getHotness());
        assertEquals(0, result.getColdness());
        assertEquals(15, result.getHotness());
    }

    @Test
    public void executeOperationValid2() {
        Oven oven = new Oven(lab, new Temperature(0, 100));
        oven.addIngredients(container2);
        oven.executeOperation();
        container2 = oven.getResult();
        kettle.addIngredients(container1);
        kettle.addIngredients(container2);
        kettle.addIngredients(container3);
        kettle.addIngredients(container4);
        kettle.executeOperation();
        IngredientContainer container = kettle.getResult();
        AlchemicIngredient result = container.getContent();
        assertEquals((int) (ingredient1.getSpoonAmount() + ingredient2.getSpoonAmount()
                + ingredient3.getSpoonAmount() + ingredient4.getSpoonAmount()), (int) result.getSpoonAmount());
        assertEquals("Beer mixed with Chocolate, Coca Cola, Milk and Stella Artois", result.getSimpleName());
        assertEquals(State.POWDER, result.getState());
        assertEquals(0,result.getType().getStandardTemperatureObject().getColdness());
        assertEquals(18, result.getType().getStandardTemperatureObject().getHotness());
        // weighted added hotness is in between 1962.5 && 1987.5
        // hotness is 4
        // coldness is 2
        assertEquals(0, result.getColdness());
        assertEquals(2, result.getHotness());
    }

}
