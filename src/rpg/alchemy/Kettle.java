package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.State;
import rpg.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class representing a Kettle device inside a laboratory.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class Kettle extends Device {

	/**
	 * A constructor for a kettle device.
	 *
	 * @param 	laboratory
	 * 			The laboratory in which this device will be positioned
	 * @effect	A device with the given laboratory
	 * 			and a maximum number of ingredients equal to
	 * 			the maximum integer value is created.
	 * 			| super(laboratory, Integer.MAX_VALUE)
	 */
	@Raw
	public Kettle(Laboratory laboratory) {
		super(laboratory, Integer.MAX_VALUE);
	}

	/**
	 * A method for executing a kettle
	 *
	 * @effect  Executes the operation from device
	 *          | super.executeOperation()
	 * @post	All ingredients are deleted and a new ingredient is created with
	 * 			a new combined name, a weighted average temperature,
	 * 			the new state is the state of the ingredient with the standard temperature
	 * 			closest to [0,20], the new temperature is the temperature of the ingredient
	 * 			with the standard temperature closest to [0,20] and the new amount is the total
	 * 			amount of all ingredients.
	 * 			TODO
	 */
	@Override
	public void executeOperation() {
		super.executeOperation();

		if (getNbOfIngredients() == 1) {	// if only 1 ingredient, don't mix

			ArrayList<String> names = new ArrayList<>();
			int closestTemperatureIndex = 0;
			long smallestDifferenceTemperature = Long.MAX_VALUE;
			double totalSpoonAmount = 0;
			Temperature totalTemperature = new Temperature(0, 0);

			for (int i = 0; i < getNbOfIngredients(); i++) {
				// We get all the names of the ingredients
				names.addAll(Arrays.asList(getIngredientAt(i).getType().getName().getSimpleNameParts()));
				// We get the ingredient with a standard temperature closest to [0, 20]
				if (getIngredientAt(i).getType().getStandardTemperatureObject().difference(new long[]{0, 20}) < smallestDifferenceTemperature) {
					smallestDifferenceTemperature = getIngredientAt(i).getType().getStandardTemperatureObject().difference(new long[]{0, 20});
					closestTemperatureIndex = i;
				}
				// We add the amount to	the total spoon amount
				totalSpoonAmount += getIngredientAt(i).getSpoonAmount();
				// We calculate the total temperature
				Temperature.add(getIngredientAt(i).getTemperatureObject(), totalTemperature).mul(getIngredientAt(i).getSpoonAmount());
			}

			// Set the new name
			Name newName = new Name(null, String.valueOf(List.of(names)));

			// Set the new state
			State newState = getIngredientAt(closestTemperatureIndex).getState();

			// Set the newStandardTemperature
			Temperature newStandardTemperature = getIngredientAt(closestTemperatureIndex).getType().getStandardTemperatureObject();

			// Now divide with the total spoon amount to get the average temperature
			totalTemperature.mul(1 / totalSpoonAmount);
			Temperature averageTemperature = totalTemperature;

			// delete all ingredients
			while (getNbOfIngredients() > 0) {
				removeAsIngredient(getIngredientAt(0));
			}

			// create the new ingredient(type) and add to the kettle
			IngredientType newType = new IngredientType(newName, newState, newStandardTemperature, true);
			addAsIngredient(new AlchemicIngredient((int) totalSpoonAmount, Unit.SPOON, averageTemperature, newType, newState));

		}
	}

}
