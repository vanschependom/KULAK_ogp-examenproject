package rpg.alchemy;

/**
 * A class representing an Oven device inside a laboratory.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class Oven extends TemperatureDevice {

	/**********************************************************
	 * CONSTRUCTORS
	 **********************************************************/

	public Oven(Laboratory laboratory, Temperature temperature) throws IllegalArgumentException {
		super(laboratory, temperature);
	}



	/**********************************************************
	 * OPERATION EXECUTION
	 **********************************************************/

	/**
	 * A method that executes the operation of the cooling box device.
	 *
	 * @effect  Executes operation from temperature device
	 *          | super.executeOperation()
	 *
	 * @post	If the temperature of the oven is not colder than the temperature of the ingredient,
	 * 			the ingredient is heated to the temperature of the oven (with a deviation of 5).
	 * 			| if !getTemperature().isColderThan(getIngredientAt(0).getTemperature())
	 * 			|	then getIngredientAt(0).getHotness() == getTemperature().getHotness() &&
	 * 			|		 getIngredientAt(0).getColdness() == getTemperature().Coldness()
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
		if (!getTemperature().isColderThan(getIngredientAt(0).getTemperature())) {
			// if the temperature of the ingredient is higher than the temperature of the cooling box, cool the ingredient
			long difference = getTemperature().difference(getIngredientAt(0).getTemperature());
			getIngredientAt(0).heat(difference);
		}
	}


}
