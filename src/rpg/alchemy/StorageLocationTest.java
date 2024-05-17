package rpg.alchemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.State;
import rpg.Unit;
import rpg.exceptions.IngredientNotPresentException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * A JUnit 5 test class for testing the non-private methods of the StorageLocation class.
 *
 * @author  Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 *
 * @version 1.0
 */
public class StorageLocationTest {
    // we test the abstract class with concrete subclasses

    Laboratory lab;
    Oven oven;
    CoolingBox coolingBox;
    Kettle kettle;
    Transmogrifier transmogrifier;

    IngredientType powderType;
    IngredientType powderTypeMixed;
    IngredientType liquidType;
    IngredientType liquidTypeMixed;

    AlchemicIngredient powder;
    AlchemicIngredient mixedPowder;
    AlchemicIngredient liquid;
    AlchemicIngredient mixedLiquid;

    IngredientContainer container1;
    IngredientContainer container2;

    @BeforeEach
    public void setUp() {
        // laboratory
        lab = new Laboratory(3);	// lab with the capacity of 3 storerooms
        oven = new Oven(lab, new Temperature(0, 300));
        coolingBox = new CoolingBox(lab, new Temperature(400, 0));
        kettle = new Kettle(lab);
        transmogrifier = new Transmogrifier(lab);
        // ingredient types
        powderType = new IngredientType(new Name(null, "Powder Sugar"), State.POWDER, new Temperature(), false);
        powderTypeMixed = new IngredientType(new Name("Breakfast", "Oatmeal", "Seeds"), State.POWDER, new Temperature(), true);
        liquidType = new IngredientType(new Name(null, "Sprite"), State.LIQUID, new Temperature(0, 100), false);
        liquidTypeMixed = new IngredientType(new Name("Watery Coke", "Water", "Coke"), State.LIQUID, new Temperature(), true);
        // ingredients
        powder = new AlchemicIngredient(10, Unit.PINCH, powderType);
        mixedPowder = new AlchemicIngredient(5, Unit.SACHET, powderTypeMixed);
        liquid = new AlchemicIngredient(2, Unit.JUG, liquidType);
        mixedLiquid = new AlchemicIngredient(10, Unit.VIAL, liquidTypeMixed);
        // container
        container1 = new IngredientContainer(Unit.BARREL, liquid);
        container2 = new IngredientContainer(Unit.CHEST, powder);
    }

    @Test
    public void testConstructor() {
        // number of ingredients is 0 after creation
        assertEquals(0, lab.getNbOfIngredients());
        assertTrue(lab.isEmpty());
        // is not yet terminated
        assertFalse(lab.isTerminated());
    }

    @Test
    public void testHasProperIngredients_Legal() {
        lab.addContainer(container1);
        lab.addContainer(container2);
        assertTrue(lab.hasProperIngredients());
    }

    @Test
    public void testCanHaveAsIngredient_Legal() {
        assertTrue(lab.canHaveAsIngredient(powder));
    }

    @Test
    public void testCanHaveAsIngredient_Illegal_null() {
        AlchemicIngredient i = null;
        assertFalse(lab.canHaveAsIngredient(i));
    }

    @Test
    public void testCanHaveAsIngredient_Illegal_terminated() {
        powder.terminate();
        assertFalse(lab.canHaveAsIngredient(powder));
    }

    @Test
    public void testContainsIngredientTwice_Legal() {
        lab.addContainer(container1); // with ingredient liquid
        IngredientContainer container3 = new IngredientContainer(Unit.BARREL, liquid);
        lab.addContainer(container3); // dit doet niet wat het moet
        // this is false because the newly added ingredient is added to the old
        assertFalse(lab.containsIngredientTwice(liquid));
    }

    @Test
    public void testGetIngredientAt_Legal() {
        lab.addContainer(container1);
        assertEquals(liquid, lab.getIngredientAt(0));
    }

    @Test
    public void testGetIngredientAt_Illegal() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            lab.getIngredientAt(12);
        });
    }

    @Test
    public void removeIngredientAt_Legal() {
        lab.addContainer(container1);
        assertEquals(1, lab.getNbOfIngredients());
        lab.removeIngredientAt(0);
        assertEquals(0,lab.getNbOfIngredients());
    }

    @Test
    public void removeIngredientAt_Illegal() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            lab.removeIngredientAt(0);
        });
    }

    @Test
    public void testGetNbOfIngredients_Legal() {
        lab.addContainer(container1);
        lab.addContainer(container2);
        assertEquals(2, lab.getNbOfIngredients());
    }

    @Test
    public void testGetIndexOfIngredient_Legal() {
        lab.addContainer(container1); // contains liquid
        lab.addContainer(container2); // contains powder
        assertEquals(0, lab.getIndexOfIngredient(liquid));
        assertEquals(1, lab.getIndexOfIngredient(powder));
    }

    @Test
    public void testGetIndexOfIngredient_Illegal_Null() {
        lab.addContainer(container1); // contains liquid
        assertThrows(NullPointerException.class, () -> {
            lab.getIndexOfIngredient(null);
        });
    }

    @Test
    public void  testGetIndexOfIngredient_Illegal_NotPresent() {
        lab.addContainer(container1); // contains liquid
        assertThrows(IngredientNotPresentException.class, () -> {
            lab.getIndexOfIngredient(powder);
        });
    }

    @Test
    public void testHasAsIngredient_Legal() {
        lab.addContainer(container1); // contains liquid
        lab.addContainer(container2); // contains powder
        assertTrue(lab.hasAsIngredient(liquid));
        assertTrue(lab.hasAsIngredient(powder));
    }

    @Test
    public void testHasAsIngredient_Illegal() {
        lab.addContainer(container1); // contains liquid
        assertTrue(lab.hasAsIngredient(liquid));
        assertFalse(lab.hasAsIngredient(powder));
    }

    @Test
    public void testHasIngredientWithSimpleName() {
        lab.addContainer(container1); // contains liquid
        assertTrue(lab.hasIngredientWithSimpleName("Sprite"));
        assertFalse(lab.hasIngredientWithSimpleName("Powder Sugar"));
    }

    @Test
    public void testHasIngredientWithSpecialName() {
        IngredientContainer container3 = new IngredientContainer(Unit.CHEST, mixedPowder);
        lab.addContainer(container3);
        IngredientContainer container4 = new IngredientContainer(Unit.BARREL, mixedLiquid);
        lab.addContainer(container4);
        assertTrue(lab.hasIngredientWithSpecialName("Breakfast"));
        assertTrue(lab.hasIngredientWithSpecialName("Watery Coke"));
        assertFalse(lab.hasIngredientWithSpecialName("Iets Anders"));
    }

    @Test
    public void testaddContainer_Legal() {
        lab.addContainer(container1); // contains liquid
        assertEquals(1, lab.getNbOfIngredients());
        assertTrue(lab.hasAsIngredient(liquid));
        assertTrue(lab.hasIngredientWithSimpleName("Sprite"));
    }

    @Test
    public void testaddContainer_Illegal_null() {
        assertThrows(NullPointerException.class, () -> {
            lab.addContainer(null);
        });
    }

    @Test
    public void testaddContainer_Illegal_IllegalIngredient() {
        liquid.terminate();
        assertThrows(IllegalArgumentException.class, () -> {
            lab.addContainer(container1);
        });
    }

    @Test
    public void testTerminate() {
        lab.terminate();
        assertTrue(lab.isTerminated());
    }

}
