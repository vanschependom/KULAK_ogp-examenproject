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
     * A method for checking whether or not a list of ingredients
     * is possible for a temperature device
     *
     * @param   ingredients
     *          The ingredients list to check
     * @return  True if the size of ingredients is 0 or 1,
     *          ingredients is not a null reference
     *          and if every ingredient is valid.
     *          False otherwise.
     *          | result ==
     *          |   super.canHaveAsIngredients(ingredients) && ingredients.size() <= 1
     */
    @Override
    public boolean canHaveAsIngredients(ArrayList<AlchemicIngredient> ingredients) {
        return super.canHaveAsIngredients(ingredients) && ingredients.size() <= 1;
    }

    /**
     * A method for checking if a temperature device can have a given ingredient
     *
     * @param   ingredient
     *          The ingredient to check
     * @return  True if the ingredient is valid and
     *          the number of ingredients of the device is either 0
     *          or the first and only ingredient is equal to ingredient
     *          | result ==
     *          |   super.canHaveAsIngredient(ingredient) &&
     *          |       ( getNbOfIngredients() == 0 || getIngredientAt(0).equals(ingredient) )
     */
    @Override
    public boolean canHaveAsIngredient(AlchemicIngredient ingredient) {
        return super.canHaveAsIngredient(ingredient) &&
                ( getNbOfIngredients() == 0 || getIngredientAt(0).equals(ingredient));
    }
}
