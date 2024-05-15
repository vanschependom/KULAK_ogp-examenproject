package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import java.util.Random;

import static java.lang.Math.abs;

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

	/**
	 * A constructor for an oven given a temperature and a given laboratory.
	 *
	 * @param 	laboratory
	 * 			The laboratory in which the oven is placed.
	 * @param 	temperature
	 *			The temperature for this oven to heat to.
	 *
	 * @effect 	A temperature device with given temperature and laboratory is created.
	 * 	 		| super(laboratory, temperature)
	 */
	@Raw
	public Oven(Laboratory laboratory, Temperature temperature) throws IllegalArgumentException {
		super(laboratory, temperature);
	}



	/**********************************************************
	 * OPERATION EXECUTION
	 **********************************************************/

	/**
	 * A method that executes the operation of the oven device.
	 *
	 * @effect  Executes operation from temperature device.
	 *          | super.executeOperation()
	 *
	 * @post	If the temperature of the oven is not colder than the temperature of the ingredient,
	 * 			the ingredient is heated to the temperature of the oven (with a deviation of 5).
	 * 			| if !getTemperature().isColderThan(getIngredientAt(0).getTemperature())
	 * 			|	then (( getIngredientAt(0).getHotness() - getTemperature().getHotness() ) <= 5 )
	 * 			|		&& (( getIngredientAt(0).getColdness() - getTemperature().getColdness() ) <= 5 )
	 */
	@Override
	public void executeOperation() throws IllegalStateException {
		super.executeOperation();
		// if the temperature of the oven is higher than the temperature of the ingredient, do nothing
		if (!getTemperature().isColderThan(getIngredientAt(0).getTemperature())) {
			Random random = new Random();
			// if the temperature of the ingredient is lower than the temperature of the oven, heat the ingredient
			long difference = getTemperature().difference(getIngredientAt(0).getTemperature())
					+ random.nextInt(-5,5);
			// negative differences do nothing
			getIngredientAt(0).heat(difference);
		}
	}


}
