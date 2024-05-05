package rpg.alchemy;

/**
 * A class representing a Cooling Box device inside a laboratory.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class CoolingBox extends TemperatureDevice {

	/**********************************************************
	 * CONSTRUCTORS
	 **********************************************************/

	/**
	 * A constructor for a cooling box.
	 *
	 * @param 	laboratory
	 * 			The laboratory in which the cooling box will be situated.
	 * @param 	temperature
	 * 			The cooling temperature for the cooling box.
	 *
	 * @effect	A temperature device with given laboratory and temperature is created
	 * 			| super(laboratory, temperature)
	 */
	public CoolingBox(Laboratory laboratory, Temperature temperature) throws IllegalArgumentException {
		super(laboratory, temperature);
	}



	/**********************************************************
	 * OPERATION EXECUTION
	 **********************************************************/

	/**
	 * A method that executes the operation of the cooling box device.
	 *
	 * @post	The ingredient in the device is cooled to the temperature
	 * 			of the device.
	 * 			If the ingredient is already cooler, nothing happens
	 * 			| if getTemperature().isColderThan(getIngredientAt(0).getTemperature())
	 * 			| then getIngredientAt(0).cool(getTemperature().difference(getIngredientAt(0).getTemperature()))
	 * @throws 	IllegalStateException
	 * 			There are no items in the device
	 * 			| isEmpty()
	 */
	@Override
	public void executeOperation() throws IllegalStateException {
		if (isEmpty()) {
			throw new IllegalStateException("There are no items in the device!");
		}
		// if the temperature of the cooling box is higher than the temperature of the ingredient, do nothing
		if (getTemperature().isHotterThan(getIngredientAt(0).getTemperature())) {
			return;
		} else {
			// if the temperature of the ingredient is higher than the temperature of the cooling box, cool the ingredient
			long difference = getTemperature().difference(getIngredientAt(0).getTemperature());
			getIngredientAt(0).cool(difference);
		}
	}


}
