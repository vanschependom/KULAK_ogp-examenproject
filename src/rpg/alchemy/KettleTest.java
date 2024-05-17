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
        kettle.addContainer(container1);
        kettle.addContainer(container2);
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
        oven.addContainer(container2);
        oven.executeOperation();
        container2 = oven.getResult();
        kettle.addContainer(container1);
        kettle.addContainer(container2);
        kettle.addContainer(container3);
        kettle.addContainer(container4);
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
        oven.addContainer(container1);
        oven.executeOperation();
        container1 = oven.getResult();
        AlchemicIngredient ingredient = new AlchemicIngredient(5, Unit.BOX, new Temperature(200, 0), type1, State.POWDER);
        IngredientContainer container = new IngredientContainer(ingredient);
        kettle.addContainer(container1);
        kettle.addContainer(container);
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
        oven.addContainer(container1);
        oven.executeOperation();
        container1 = oven.getResult();
        kettle.addContainer(container1);
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
    public void executeOperationValid5() {
        AlchemicIngredient ingredient = new AlchemicIngredient(5, Unit.BOTTLE, new Temperature(200, 0), type1, State.LIQUID);
        IngredientContainer container = new IngredientContainer(ingredient);
        kettle.addContainer(container3);
        kettle.addContainer(container);
        kettle.executeOperation();
        container = kettle.getResult();
        AlchemicIngredient result = container.getContent();
        // 5 bottles and 3 jugs, the 3 jugs being 21 bottles equal 26 bottles
        assertEquals(26*Unit.BOTTLE.getSpoonEquivalent(), (int) result.getSpoonAmount());
        // milk, beer and coca cola are added
        assertEquals("Beer mixed with Coca Cola and Milk", result.getSimpleName());
        assertEquals(State.LIQUID, result.getState());
        assertEquals(0,result.getType().getStandardTemperature()[0]);
        assertEquals(15, result.getType().getStandardTemperature()[1]);
        // total hotness = 5 * 105 * 3 = 1575
        // total coldness = 200 * 15 * 5 = 15000
        // coldness = (15000 - 1575) / (15 * 5 + 105 * 3) = 34.4
        assertEquals(34, result.getColdness());
        assertEquals(0, result.getHotness());
        assertTrue(result.getType().isMixed());
    }

    @Test
    public void executeOperation_onlyOneIngredient() {
        kettle.addContainer(container1);
        AlchemicIngredient ingOld = container1.getContent();
        assertNull(ingOld.getSpecialName());
        assertEquals("Milk", ingOld.getSimpleName());
        assertEquals(State.LIQUID, ingOld.getState());
        assertEquals(0, ingOld.getColdness());
        assertEquals(15, ingOld.getHotness());
        assertFalse(ingOld.getType().isMixed());
        assertEquals(10, ingOld.getSpoonAmount());
        kettle.executeOperation();
        AlchemicIngredient ingNew = kettle.getResult().getContent();
        // everything stays the same
        assertNull(ingNew.getSpecialName());
        assertEquals("Milk", ingNew.getSimpleName());
        assertEquals(State.LIQUID, ingNew.getState());
        assertEquals(0, ingNew.getColdness());
        assertEquals(15, ingNew.getHotness());
        assertFalse(ingNew.getType().isMixed());
        assertEquals(10, ingNew.getSpoonAmount());
    }

    @Test
    public void executeOperationInvalid() {
        assertThrows(IllegalStateException.class, () -> {
            kettle.executeOperation();
        });
    }

    @Test
    public void executeOperation_EdgeCase1() {
        AlchemicIngredient ingredient1 = new AlchemicIngredient(1, Unit.SPOON, type1);
        AlchemicIngredient ingredient2 = new AlchemicIngredient(2, Unit.PINCH, type2);
        container1 = new IngredientContainer(ingredient1);
        container2 = new IngredientContainer(ingredient2);
        kettle.addContainer(container1);
        kettle.addContainer(container2);
        kettle.executeOperation();
        AlchemicIngredient ingNew = kettle.getResult().getContent();
        assertNull(ingNew.getSpecialName());
        assertEquals("Chocolate mixed with Milk", ingNew.getSimpleName());
        assertEquals(0, ingNew.getType().getStandardTemperature()[0]);
        assertEquals(18, ingNew.getType().getStandardTemperature()[1]);
        assertEquals(State.POWDER, ingNew.getType().getStandardState());
        assertEquals(State.POWDER, ingNew.getState());
        assertTrue(ingNew.getType().isMixed());
        assertEquals(8, ingNew.getAmount()); // 8 pinches
        assertEquals(Unit.PINCH, ingNew.getUnit());
        assertEquals(0, ingNew.getTemperature()[0]);
        // (1 * 15 + 2/6 * 18) / (8/6) = 15.75
        assertEquals(15, ingNew.getTemperature()[1]);
    }

    @Test
    public void executeOperation_EdgeCase2() {
        AlchemicIngredient ingredient1 = new AlchemicIngredient(5, Unit.DROP, type3); // temp = [0,5]
        AlchemicIngredient ingredient2 = new AlchemicIngredient(3, Unit.DROP, type4); // temp = [10,0]
        container1 = new IngredientContainer(ingredient1);
        container2 = new IngredientContainer(ingredient2);
        kettle.addContainer(container1);
        kettle.addContainer(container2);
        kettle.executeOperation();
        AlchemicIngredient ingNew = kettle.getResult().getContent();
        assertNull(ingNew.getSpecialName());
        assertEquals("Beer mixed with Coca Cola and Stella Artois", ingNew.getSimpleName());
        assertEquals(0, ingNew.getType().getStandardTemperature()[0]);
        assertEquals(5, ingNew.getType().getStandardTemperature()[1]);
        assertEquals(State.LIQUID, ingNew.getType().getStandardState());
        assertEquals(State.LIQUID, ingNew.getState());
        assertTrue(ingNew.getType().isMixed());
        assertEquals(1, ingNew.getAmount()); // 5 drops + 3 drops = 1 spoon
        assertEquals(Unit.SPOON, ingNew.getUnit());
        // hotness = 5/8 * 5 = 25/8
        // coldness = 3/8 * 10 = 30/8
        // total = 25/8 - 30/8 = -5/8 --> -1 (afgerond naar beneden)
        assertEquals(1, ingNew.getTemperature()[0]);
        assertEquals(0, ingNew.getTemperature()[1]);
    }

    @Test
    public void executeOperation_EdgeCase3() {
        // same as previous but amounts switched
        AlchemicIngredient ingredient1 = new AlchemicIngredient(3, Unit.DROP, type3); // temp = [0,5]
        AlchemicIngredient ingredient2 = new AlchemicIngredient(5, Unit.DROP, type4); // temp = [10,0]
        container1 = new IngredientContainer(ingredient1);
        container2 = new IngredientContainer(ingredient2);
        kettle.addContainer(container1);
        kettle.addContainer(container2);
        kettle.executeOperation();
        AlchemicIngredient ingNew = kettle.getResult().getContent();
        assertNull(ingNew.getSpecialName());
        assertEquals("Beer mixed with Coca Cola and Stella Artois", ingNew.getSimpleName());
        assertEquals(0, ingNew.getType().getStandardTemperature()[0]);
        assertEquals(5, ingNew.getType().getStandardTemperature()[1]);
        assertEquals(State.LIQUID, ingNew.getType().getStandardState());
        assertEquals(State.LIQUID, ingNew.getState());
        assertTrue(ingNew.getType().isMixed());
        assertEquals(1, ingNew.getAmount()); // 5 drops + 3 drops = 1 spoon
        assertEquals(Unit.SPOON, ingNew.getUnit());
        // coldness = 5/8 * 10 = 50/8
        // hotness = 3/8 * 5 = 15/8
        // total = -50/8 + 15/8 = -35/8 --> -5 (afgerond naar beneden)
        assertEquals(5, ingNew.getTemperature()[0]);
        assertEquals(0, ingNew.getTemperature()[1]);
    }

    @Test
    public void executeOperation_EdgeCase4() {
        // similar to previous but second ingredient is warm
        IngredientType newType = new IngredientType(new Name(null, "Stella Artois"), State.LIQUID, new Temperature(0, 10), false);
        AlchemicIngredient ingredient1 = new AlchemicIngredient(3, Unit.DROP, type3); // temp = [0,5]
        AlchemicIngredient ingredient2 = new AlchemicIngredient(5, Unit.DROP, newType); // temp = [0,10]
        container1 = new IngredientContainer(ingredient1);
        container2 = new IngredientContainer(ingredient2);
        kettle.addContainer(container1);
        kettle.addContainer(container2);
        kettle.executeOperation();
        AlchemicIngredient ingNew = kettle.getResult().getContent();
        assertNull(ingNew.getSpecialName());
        assertEquals("Beer mixed with Coca Cola and Stella Artois", ingNew.getSimpleName());
        assertEquals(0, ingNew.getType().getStandardTemperature()[0]);
        assertEquals(10, ingNew.getType().getStandardTemperature()[1]);
        assertEquals(State.LIQUID, ingNew.getType().getStandardState());
        assertEquals(State.LIQUID, ingNew.getState());
        assertTrue(ingNew.getType().isMixed());
        assertEquals(1, ingNew.getAmount()); // 5 drops + 3 drops = 1 spoon
        assertEquals(Unit.SPOON, ingNew.getUnit());
        // hotness = 3/8 * 5 + 5/8 * 10 = 15/8 + 50/8 = 65/8 = 8  (afgrond naar beneden)
        assertEquals(0, ingNew.getTemperature()[0]);
        assertEquals(8, ingNew.getTemperature()[1]);
    }

}
