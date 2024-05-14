package rpg.alchemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.State;
import rpg.Unit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A JUnit 5 test class for testing the public methods of the Kettle class.
 *
 * @author  Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 *
 * @version 1.0
 */
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

    @Test
    public void executeOperationValid3() {
        Oven oven = new Oven(lab, new Temperature(0, 200));
        oven.addIngredients(container1);
        oven.executeOperation();
        container1 = oven.getResult();
        AlchemicIngredient ingredient = new AlchemicIngredient(5, Unit.BOX, new Temperature(200, 0), type1, State.POWDER);
        IngredientContainer container = new IngredientContainer(ingredient);
        kettle.addIngredients(container1);
        kettle.addIngredients(container);
        kettle.executeOperation();
        container = kettle.getResult();
        AlchemicIngredient result = container.getContent();
        assertEquals((int) (ingredient1.getSpoonAmount() + ingredient.getSpoonAmount()), (int) result.getSpoonAmount());
        assertEquals("Milk", result.getSimpleName());
        assertEquals(State.LIQUID, result.getState());
        assertEquals(0,result.getType().getStandardTemperatureObject().getColdness());
        assertEquals(15, result.getType().getStandardTemperatureObject().getHotness());
        // total hotness = (tussen 195 en 205) * 10 = tussen 1950 en 2050
        // total coldness = 200 * 42 * 5 = 42000
        // lower bound = (42000 - 2050) / (42 * 5 + 10) = 181.59
        // average temp = (42000 - 2000) / (42 * 5 + 10) = 181.81
        // upperbound = (42000 - 1950) / (42 * 5 + 10) = 182.04
        assertTrue(result.getColdness() == 181 || result.getColdness() == 182);
        assertEquals(0, result.getHotness());
        assertFalse(result.getType().isMixed());
    }

    @Test
    public void executeOperationValid4() {
        Oven oven = new Oven(lab, new Temperature(0, 150));
        oven.addIngredients(container1);
        oven.executeOperation();
        container1 = oven.getResult();
        kettle.addIngredients(container1);
        kettle.executeOperation();
        IngredientContainer container = kettle.getResult();
        AlchemicIngredient result = container.getContent();
        assertEquals((int) (ingredient1.getSpoonAmount()), (int) result.getSpoonAmount());
        assertEquals("Milk", result.getSimpleName());
        assertEquals(State.LIQUID, result.getState());
        assertEquals(0,result.getType().getStandardTemperatureObject().getColdness());
        assertEquals(15, result.getType().getStandardTemperatureObject().getHotness());
        assertEquals(0, result.getColdness());
        assertTrue(result.getHotness() >= 145 && result.getHotness() <= 155);
        assertFalse(result.getType().isMixed());
    }

    @Test
    public void executeOperation_onlyOneIngredient() {
        kettle.addIngredients(container1);
        assertEquals(1,kettle.getNbOfIngredients());
        kettle.executeOperation();
        // nothing happens
        assertEquals(1,kettle.getNbOfIngredients());
    }

    @Test
    public void executeOperationInvalid() {
        assertThrows(IllegalStateException.class, () -> {
            kettle.executeOperation();
        });
    }



}
