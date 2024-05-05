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
public class TemperatureDevice extends Device{

    /**
     * A variable that keeps track of the heating or cooling temperature of a temperature device
     */
    private Temperature temperature = new Temperature(0, 20);

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
     * @throws  IllegalArgumentException
     *          The given temperature is a null pointer
     *          | temperature == null
     *
     */
    public TemperatureDevice(Laboratory laboratory, Temperature temperature) throws IllegalArgumentException{
        super(laboratory);
        setTemperature(temperature);
    }

    /**
     * A method to set the temperature of a temperature device
     *
     * @param   temperature
     *          The temperature to set the device to.
     *
     * @post    The temperature of the device is set to temperature.
     *          | this.temperature = temperature
     *
     * @throws  IllegalArgumentException
     *          The given temperature is a null pointer
     *          | temperature == null
     */
    @Model
    public void setTemperature(Temperature temperature) throws IllegalArgumentException {
        if (temperature == null) throw new IllegalArgumentException();
        this.temperature = temperature;
    }

    /**
     * Return whether or not a given list of ingredients
     * is possible for this device to have
     *
     * @param   ingredients
     *          The ingredients list to check
     */
    @Override
    public boolean canHaveAsIngredients(ArrayList<AlchemicIngredient> ingredients) {
        if (ingredients.size() > 1) return false;
        if (ingredients.isEmpty()) return true;
        return ingredients.getFirst() != null && !ingredients.getFirst().isTerminated();
    }

    /**
     * Return whether or not a given ingredient is possible for this device to have
     *
     * @param   ingredient
     *          The ingredient to check
     */
    @Override
    public boolean canHaveAsIngredient(AlchemicIngredient ingredient) {
        if (!super.canHaveAsIngredient(ingredient)) return false;
        if (getNbOfIngredients() == 0) return true;
        return getIngredientAt(0).equals(ingredient);
    }
}
