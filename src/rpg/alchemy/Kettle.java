package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.State;
import rpg.Unit;

import java.util.*;

/**
 * A class representing a Kettle device inside a laboratory.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class Kettle extends Device {

	/**********************************************************
	 * CONSTRUCTORS
	 **********************************************************/

	/**
	 * A constructor for a kettle device with a given laboratory and
	 * a maximum number of ingredients of Integer.MAX_VALUE.
	 *
	 * @param 	laboratory
	 * 			The laboratory in which the kettle is placed.
	 *
	 * @effect	A kettle device with a maximum number of ingredients of
	 * 			Integer.MAX_VALUE and with the given laboratory is created.
	 * 			| super(laboratory, Integer.MAX_VALUE)
	 */
	@Raw
	public Kettle(Laboratory laboratory) {
		super(laboratory, Integer.MAX_VALUE);
	}



	/**********************************************************
	 * METHODS
	 **********************************************************/

	/**
	 * Return the new name object for the new mixed ingredient, with a special name of null.
	 * The simple name parts being all the simple name parts of the ingredients.
	 *
	 * @return  A new name object with all the simple name parts of the ingredients
	 * 			and a null special name.
	 * 			| result == new Name(null, ( for each I in 0..getNbOfIngredients()-1:
	 * 			|				       			getIngredientAt(I).getType().GetName().getSimpleNameParts() ))
	 * 			| TODO dit navragen voor alle methoden
	 */
	@Model
	private Name getNewName() {
		Set<String> names = new HashSet<>();
		for (int i = 0; i < getNbOfIngredients(); i++) {
			// we get all names of the ingredients
			names.addAll(Arrays.asList(getIngredientAt(i).getType().getName().getSimpleNameParts()));
		}
		// we return a new name with all the names of the ingredients
		return new Name(null, names.toArray(new String[0]));
	}

	/**
	 * The new state of the result in this kettle.
	 *
	 * @return 	The state of the ingredient
	 * 			in the kettle with the standard temperature
	 * 			closest to [0, 20], liquid is chosen over powder.
	 * 			| result ==
	 *
	 */
	@Model
	private State getNewState() {
		int closestTemperatureIndex = 0;
		long smallestDifferenceTemperature = Long.MAX_VALUE;
		for (int i = 0; i < getNbOfIngredients(); i++) {
			// We get the ingredient with a standard temperature closest to [0, 20]
			long difference = getIngredientAt(i).getType().standardTemperatureDifference(new long[]{0, 20});
			if (difference < smallestDifferenceTemperature) {
				smallestDifferenceTemperature = difference;
				closestTemperatureIndex = i;
			} else if (difference == smallestDifferenceTemperature && getIngredientAt(i).getState() == State.LIQUID) {
				closestTemperatureIndex = i;
			}
		}
		return getIngredientAt(closestTemperatureIndex).getState();
	}

	/**
	 * A method that calculates the new spoon amount for the new ingredient.
	 *
	 * @return 	The sum of all the spoon amounts of the ingredients.
	 * 			| result == getIngredients().stream()
	 *          |   .mapToDouble(AlchemicIngredient::getSpoonAmount)
	 *          |   .sum()
	 */
	@Model
	private double getNewSpoonAmount() {
		double totalSpoonAmount = 0;
		for (int i = 0; i < getNbOfIngredients(); i++) {
			// We add the amount to	the total spoon amount
			totalSpoonAmount += getIngredientAt(i).getSpoonAmount();
		}
		return totalSpoonAmount;
	}

	/**
	 * Return The standard temperature which is closest to [0, 20],
	 * if the difference is the same, the hottest standard temperature is chosen
	 */
	@Model
	private Temperature getNewStandardTemperature() {
		Temperature smallestDifferenceTemperature = new Temperature(0, Temperature.getUpperbound());
		long smallestDifference = smallestDifferenceTemperature.difference(new long[]{0, 20});
		for (int i = 0; i < getNbOfIngredients(); i++) {
			long difference = getIngredientAt(i).getType().standardTemperatureDifference(new long[]{0, 20});
			// We get the ingredient with a standard temperature closest to [0, 20]
			if ( difference < smallestDifference || ( difference == smallestDifference &&
					getIngredientAt(i).getHotness() > smallestDifferenceTemperature.getHotness() ) ) {
				smallestDifferenceTemperature = new Temperature(getIngredientAt(i).getType().getStandardTemperature());
				smallestDifference = difference;
			}
		}
		return smallestDifferenceTemperature;
	}

	/**
	 * Return the weighted average temperature of all the ingredients inside the kettle.
	 *
	 * @note 	The coldness and the hotness are calculated separately because otherwise in
	 * 			the Temperature class there is a lot of rounding to integer values.
	 */
	@Model
	private Temperature getNewTemperature() {
		double totalColdness = 0;
		double totalHotness = 0;
		for (int i = 0; i < getNbOfIngredients(); i++) {
			double spoonAmount = getIngredientAt(i).getSpoonAmount();
			double currentColdness = getIngredientAt(i).getColdness() * spoonAmount;
			double currentHotness = getIngredientAt(i).getHotness() * spoonAmount;
			totalColdness += currentColdness;
			totalHotness += currentHotness;
		}
		double difference = (totalHotness - totalColdness) / getNewSpoonAmount();
		if (difference > Temperature.getUpperbound()) {
			return new Temperature(0, Temperature.getUpperbound());
		} else if (difference > 0) {
			return new Temperature(0, (long) difference);
		} else if (difference < -Temperature.getUpperbound()) {
			return new Temperature(Temperature.getUpperbound(), 0);
		} else if (difference < 0) {
			return new Temperature((long) Math.abs(difference), 0);
		} else {
			return new Temperature(0,0);
		}
	}



	/**********************************************************
	 * OPERATION EXECUTION
	 **********************************************************/

	/**
	 * A method for executing a kettle
	 *
	 * @effect  Executes the operation from device
	 *          | super.executeOperation()
	 *
	 * @post	All ingredients are deleted and a new ingredient is created with
	 * 			a new combined name, a weighted average temperature, a new state, a new standard temperature
	 * 			and a new amount
	 * 			| ( addAsIngredient(new AlchemicIngredient((int) newSpoonAmount, Unit.SPOON, newTemperature,
	 * 			|	new IngredientType(newName, newState, newStandardTemperature, true), newState)) ) &&
	 * 			| ( for each I in 0..getNbOfIngredients()-2:
	 * 			|	removeAsIngredient(getIngredientAt(0)) )
	 */
	@Override
	public void executeOperation() {
		super.executeOperation();

		// Get all the new values
		Name newName = getNewName();
		State newState = getNewState();
		double newSpoonAmount = getNewSpoonAmount();
		Temperature newTemperature = getNewTemperature();
		Temperature newStandardTemperature = getNewStandardTemperature();

		// delete all ingredients
		while (getNbOfIngredients() > 0) {
			removeAsIngredient(getIngredientAt(0));
		}

		// create the new ingredient(type) and add to the kettle
		IngredientType newType = new IngredientType(newName, newState, newStandardTemperature, newName.isMixed());
		addAsIngredient(new AlchemicIngredient((int) newSpoonAmount, Unit.SPOON, newTemperature, newType, newState));
	}

}
