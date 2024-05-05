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

	public CoolingBox(Laboratory laboratory, Temperature temperature) throws IllegalArgumentException {
		super(laboratory, temperature);
	}



	/**********************************************************
	 * OPERATION EXECUTION
	 **********************************************************/

	/**
	 * A method that executes the operation of the cooling box device.
	 *
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
