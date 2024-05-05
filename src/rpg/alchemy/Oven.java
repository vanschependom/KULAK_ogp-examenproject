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
	 * 			| if getTemperature().isHotterThan(getIngredientAt(0).getTemperatureObject())
	 * 			| then getIngredientAt(0).heat(getIngredientAt(0).getTemperatureObject().difference(getTemperature()))
	 * @throws 	IllegalStateException
	 * 			There are no items in the device
	 * 			| getNbOfIngredients() == 0
	 */
	@Override
	public void executeOperation() throws IllegalStateException {
		if (getNbOfIngredients() == 0) throw new IllegalStateException("No ingredients in Cooling Box");
		AlchemicIngredient ing = getIngredientAt(0);
		if (getTemperature().isHotterThan(ing.getTemperatureObject())) {
			ing.heat(ing.getTemperatureObject().difference(getTemperature()));
		}
	}

}
