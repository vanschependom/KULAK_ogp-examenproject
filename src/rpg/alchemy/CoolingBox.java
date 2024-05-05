package rpg.alchemy;

/**
 * A class representing a Cooling Box device inside a laboratory.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class CoolingBox extends TemperatureDevice{

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

	/**
	 * A method that executes the cooling box device
	 *
	 * @post	The ingredient in the device is cooled to the temperature
	 * 			of the device.
	 * 			If the ingredient is already cooler, nothing happens
	 * 			| if getIngredientAt(0).getTemperatureObject().isHotterThan(getTemperature())
	 * 			| then getIngredientAt(0).cool(getIngredientAt(0).getTemperatureObject().difference(getTemperature()))
	 * @throws 	IllegalStateException
	 * 			There are no items in the device
	 * 			| getNbOfIngredients() == 0
	 */
	@Override
	public void executeOperation() throws IllegalStateException {
		if (getNbOfIngredients() == 0) throw new IllegalStateException("No ingredients in Cooling Box");
		AlchemicIngredient ing = getIngredientAt(0);
		if (ing.getTemperatureObject().isHotterThan(getTemperature())) {
			ing.cool(ing.getTemperatureObject().difference(getTemperature()));
		}
	}


}
