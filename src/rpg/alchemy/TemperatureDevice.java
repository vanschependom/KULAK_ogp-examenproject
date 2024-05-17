package rpg.alchemy;
import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;

/**
 * A class representing a temperature device inside a laboratory.
 *
 * @invar   The temperature device must always have a valid temperature.
 *          | isValidTemperature(getTemperatureObject())
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
     * A constructor for a temperature device with a given temperature and laboratory
     * and a maximum number of ingredients equal to 1.
     *
     * @param   laboratory
     *          The laboratory in which the temperature device is placed.
     * @param   temperature
     *          The temperature for this device to cool or heat to.
     *
     * @effect  A device with a maximum number of ingredients equal to 1 and
     *          the given laboratory as its laboratory is created.
     *          | super(laboratory, 1)
     * @effect  The temperature of the temperature device is set to temperature.
     *          | setTemperature(temperature)
     */
    @Raw
    public TemperatureDevice(Laboratory laboratory, Temperature temperature) throws IllegalArgumentException, NullPointerException {
        super(laboratory,1);
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
     * A variable that keeps track of the heating or cooling temperature of a temperature device.
     */
    private Temperature temperature = null;

    /**
     * A getter for the temperature of a temperature device.
     *
     * @return  The temperature of the temperature device.
     *          | result.getHotness() == getHotness() && result.getColdness() == getColdness()
     */
    @Model @Raw
    protected Temperature getTemperatureObject() {
        return temperature;
    }

    

    /**
     * A method to check if a temperature is a valid temperature.
     *
     * @param   temperature
     *          The temperature to check.
     *
     * @return  False if the temperature is a null pointer, true otherwise.
     *          | result == ( temperature != null )
     */
    public boolean isValidTemperature(Temperature temperature) {
        return temperature != null;
    }

    /**
     * A method to get the hotness of a temperature device.
     */
    @Basic
    public long getHotness() {
        return getTemperatureObject().getHotness();
    }

    /**
     * A method to get the coldness of a temperature device.
     */
    @Basic
    public long getColdness() {
        return getTemperatureObject().getColdness();
    }

    /**
     * A method to set the temperature of a temperature device.
     *
     * @param   temperature
     *          The temperature to set the device to.
     *
     * @post    The temperature of the device is set to temperature.
     *          | new.getHotness() == temperature.getHotness() && new.getColdness() == temperature.getColdness()
     *
     * @throws  NullPointerException
     *          The given temperature is a null pointer.
     *          | temperature == null
     *
     * @note    A temperature object is assumed to comply with its own invariants,
     *          so temperature.isValidTemperature() is not necessary.
     */
    @Model @Raw
    private void setTemperature(Temperature temperature) throws NullPointerException {
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
     *
     * @effect  The new temperature is set, if the given temperature is valid.
     *          | if (isValidTemperature(new Temperature(temperature)) && (temperature.length == 2)
     *          |   then setTemperature(new Temperature(temperature))
     */
    public void changeTemperatureTo(long[] temperature) {
        if (isValidTemperature(new Temperature(temperature)) && temperature.length == 2) {
            setTemperature(new Temperature(temperature));
        }
    }

    /**
     * A method to change the temperature of a temperature device.
     *
     * @param   temperature
     *          The new temperature for the device.
     *
     * @effect  The new temperature is set, if the given temperature is valid.
     *          | if (isValidTemperature(temperature))
     *          |   then setTemperature(temperature)
     */
    public void changeTemperatureTo(Temperature temperature) {
        if (isValidTemperature(temperature)) {
            setTemperature(temperature);
        }
    }



    /**********************************************************
     * OPERATION EXECUTION
     **********************************************************/

    /**
     * A method for executing the device instruction.
     *
     * @effect  Executes the operation from device.
     *          | super.executeOperation()
     */
    @Override
    public void executeOperation() {
       super.executeOperation();
    }

}
