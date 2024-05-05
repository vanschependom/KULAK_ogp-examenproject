package rpg.alchemy;

public class Oven extends TemperatureDevice{

	/**
	 * A constructor for an oven.
	 *
	 * @param 	laboratory
	 * 			The laboratory in which the oven will be situated.
	 * @param 	temperature
	 * 			The cooling temperature for the oven.
	 *
	 * @effect	A temperature device with given laboratory and temperature is created
	 * 			| super(laboratory, temperature)
	 */
	public Oven(Laboratory laboratory, Temperature temperature) throws IllegalArgumentException {
		super(laboratory, temperature);
	}

	/**
	 * A method that executes the oven device
	 *
	 * @post	The ingredient in the device is heated to the temperature
	 * 			of the device.
	 * 			If the ingredient is already warmer, nothing happens
	 * 			| if getTemperature().isHotterThan(getIngredientAt(0).getTemperature())
	 * 			| then getIngredientAt(0).heat(getTemperature().difference(getIngredientAt(0).getTemperature()))
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
		if (getTemperature().isColderThan(getIngredientAt(0).getTemperature())) {
			return;
		} else {
			// if the temperature of the ingredient is higher than the temperature of the cooling box, cool the ingredient
			long difference = getTemperature().difference(getIngredientAt(0).getTemperature());
			getIngredientAt(0).heat(difference);
		}
	}

}
