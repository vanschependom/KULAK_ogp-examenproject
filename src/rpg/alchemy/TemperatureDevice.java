package rpg.alchemy;

import be.kuleuven.cs.som.annotate.Model;

import java.util.ArrayList;

/**
 * A class representing a temperature device inside a laboratory.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public abstract class TemperatureDevice extends Device {

    /**********************************************************
     * CONSTRUCTORS
     **********************************************************/

    /**
     * A constructor for a temperature device.
     *
     * @param   laboratory
     *          The laboratory in which this device will be located.
     * @param   temperature
     *          The temperature for this device to cool or heat to.
     *
     * @effect  A device with a given laboratory is created.
     *          | super(laboratory)
     * @effect  The temperature of the temperature device is set to temperature
     *          | setTemperature(temperature)
     *
     * @throws  IllegalArgumentException
     *          The device cannot be inside the given laboratory
     *          | !isValidLaboratory(laboratory)
     *          | TODO vragen aan Tommy, maar wordt overgenomen door @effect en super() denk ik
     * @throws  IllegalArgumentException
     *          The given temperature is a null pointer
     *          | temperature == null
     *          | TODO zelfde opmerking
     *
     */
    public TemperatureDevice(Laboratory laboratory, Temperature temperature) throws IllegalArgumentException {
        super(laboratory);
        setTemperature(temperature);
    }



    /**********************************************************
     * INGREDIENTS
     **********************************************************/

    /**
     * A method to check if a temperature device has proper ingredients.
     *
     * @return  True if and only if the device has proper ingredients and the number of ingredients is 1.
     *          | result == super.hasProperIngredients() && getNbOfIngredients() == 1
     *
     * @note    The specification is now closed.
     */
    @Override
    public boolean hasProperIngredients() {
        return super.hasProperIngredients() && getNbOfIngredients() == 1;
    }

    /**
     * A method to add an ingredient to a temperature device.
     *
     * @param   ingredient
     *          The ingredient to add to the device.
     *
     * @effect  The ingredient is added to the device.
     *          | new.hasAsIngredient(ingredient) &&
     *          | new.getNbOfIngredients() == getNbOfIngredients() + 1 &&
     *          | new.getIngredientAt(getNbOfIngredients() - 1).equals(ingredient)
     *
     * @throws  IllegalStateException
     *          The device already has an ingredient.
     *          | getNbOfIngredients() == 1
     */
    @Override
    protected void addAsIngredient(AlchemicIngredient ingredient) {
        if (getNbOfIngredients() == 1) {
            throw new IllegalStateException("Temperature devices can only have one ingredient!");
        }
        super.addAsIngredient(ingredient);
    }



    /**********************************************************
     * TEMPERATURE
     **********************************************************/

    /**
     * A variable that keeps track of the heating or cooling temperature of a temperature device
     */
    private Temperature temperature = null;

    /**
     * A getter for the temperature of a temperature device.
     */
    @Model
    private Temperature getTemperature() {
        return temperature;
    }

    /**
     * A method to set the temperature of a temperature device
     *
     * @param   temperature
     *          The temperature to set the device to.
     *
     * @post    The temperature of the device is set to temperature.
     *          | new.getTemperature().equals(temperature)
     *
     * @throws  NullPointerException
     *          The given temperature is a null pointer
     *          | temperature == null
     *
     * @note    A temperature object is assumed to comply with its own invariants,
     *          so temperature.isValidTemperature() is not necessary.
     */
    @Model
    public void setTemperature(Temperature temperature) throws NullPointerException, IllegalArgumentException {
        if (temperature == null) {
            throw new NullPointerException();
        }
        this.temperature = temperature;
    }



    /**********************************************************
     * OPERATION EXECUTION
     **********************************************************/

    @Override
    public void executeOperation () {
       //
    }

}
