package rpg.alchemy;
import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;

/**
 * A class representing a temperature device inside a laboratory.
 *
 * @invar   The temperature device must always have a valid temperature.
 *          | isValidTemperature(getTemperature())
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
     * @effect  A device with a given laboratory
     *          and a maximum number of ingredients equal to 1 is created.
     *          | super(laboratory, 1)
     * @effect  The temperature of the temperature device is set to temperature
     *          | setTemperature(temperature, 1)
     */
    @Raw
    public TemperatureDevice(Laboratory laboratory, Temperature temperature) throws IllegalArgumentException {
        super(laboratory, 1);
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
    protected Temperature getTemperature() {
        return temperature;
    }

    /**
     * A method to check if a temperature is a valid temperature.
     *
     * @param   temperature
     *          The temperature to check.
     *
     * @return  False if the temperature is a null pointer.
     *          | temperature == null
     *
     * @note    We don't close the specification yet!
     */
    public boolean isValidTemperature(Temperature temperature) {
        return temperature != null;
    }

    /**
     * A method to get the hotness of a temperature device.
     */
    public long getHotness() {
        return getTemperature().getHotness();
    }

    /**
     * A method to get the coldness of a temperature device.
     */
    public long getColdness() {
        return getTemperature().getColdness();
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
    private void setTemperature(Temperature temperature) throws NullPointerException, IllegalArgumentException {
        if (temperature == null) {
            throw new NullPointerException();
        }
        this.temperature = temperature;
    }

    /**
     * A method to change the temperature of a temperature device.
     *
     * @param   temperature
     *          The new temperature for the device.
     * @effect  The current temperature of the device is terminated and the new temperature is set.
     *          | getTemperature().terminate()
     *          | && setTemperature(temperature)
     */
    public void changeTemperatureTo(Temperature temperature) throws NullPointerException {
        setTemperature(temperature);
        getTemperature().terminate();
    }



    /**********************************************************
     * OPERATION EXECUTION
     **********************************************************/

    /**
     * A method for executing the device instruction.
     *
     * @effect  Executes the operation from device
     *          | super.executeOperation()
     */
    @Override
    public void executeOperation () {
       super.executeOperation();
    }

}
