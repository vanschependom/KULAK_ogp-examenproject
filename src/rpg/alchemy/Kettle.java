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
	 * A method that returns the new name object for the new mixed ingredient, with a special name of null.
	 * The simple name parts being all the simple name parts of the ingredients.
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
	 * A method that returns the state of the ingredient
	 * in the kettle with the standard temperature
	 * closest to [0, 20], liquid is chosen over powder.
	 */
	@Model
	private State getNewState() {
		int closestTemperatureIndex = 0;
		long smallestDifferenceTemperature = Long.MAX_VALUE;
		for (int i = 0; i < getNbOfIngredients(); i++) {
			// We get the ingredient with a standard temperature closest to [0, 20]
			long difference = getIngredientAt(i).getType().getStandardTemperatureDifference(new long[]{0, 20});
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
	 */
	@Model
	private double getNewSpoonAmount() {
		// we keep track of the spoon amounts of both states in a hashmap
		HashMap<State, Double> newSpoonAmounts = new HashMap<>();
		newSpoonAmounts.put(State.POWDER, 0D);
		newSpoonAmounts.put(State.LIQUID, 0D);
		for (int i = 0; i < getNbOfIngredients(); i++) {
			// we get the state of the ingredient
			State state = getIngredientAt(i).getState();
			// we add it to the old value
			newSpoonAmounts.put(state, newSpoonAmounts.get(state)+getIngredientAt(i).getSpoonAmount());
		}
		// the values of the other state get floored, while the values of the new state don't
        return Math.floor(newSpoonAmounts.get(getNewState().getNext()))
				+ newSpoonAmounts.get(getNewState());
	}

	/**
	 * A method that returns the standard temperature which is closest to [0, 20],
	 * if the difference is the same, the hottest standard temperature is chosen.
	 */
	@Model
	private Temperature getNewStandardTemperature() {
		Temperature smallestDifferenceTemperature = new Temperature(0, Temperature.getUpperbound());
		long smallestDifference = smallestDifferenceTemperature.difference(new long[]{0, 20});
		for (int i = 0; i < getNbOfIngredients(); i++) {
			long difference = getIngredientAt(i).getType().getStandardTemperatureDifference(new long[]{0, 20});
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
	 * A method that returns a new temperature object with the weighted average temperature
	 * of all the ingredients inside the kettle.
	 *
	 * @note 	The coldness and the hotness are calculated separately because otherwise in
	 * 			the Temperature class there is a lot of rounding to integer values and then
	 * 			the result would be inaccurate.
	 */
	@Model
	private Temperature getNewTemperature() {
		double totalColdness = 0;
		double totalHotness = 0;
		double addedSpoons = 0;
		for (int i = 0; i < getNbOfIngredients(); i++) {
			double spoonAmount = getIngredientAt(i).getSpoonAmount();
			double currentColdness = getIngredientAt(i).getColdness() * spoonAmount;
			double currentHotness = getIngredientAt(i).getHotness() * spoonAmount;
			totalColdness += currentColdness;
			totalHotness += currentHotness;
			addedSpoons += spoonAmount;
		}
		double difference = (totalHotness - totalColdness) / addedSpoons;
		return makeNewTemperature(difference);
	}

	/**
	 * A method that creates and returns a new temperature object given an integer
	 * that represents the difference with [0, 0].
	 *
	 * @param 	difference
	 *			The difference of the new temperature with [0, 0].
	 */
	private Temperature makeNewTemperature(double difference) {
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
	 * @effect  Executes the operation from device.
	 *          | super.executeOperation()
	 *
	 * @post	All ingredients are deleted and a new ingredient is created with
	 * 			a new combined name, a weighted average temperature, a new state, a new standard temperature
	 * 			and a new amount.
	 * 			| ( addAsIngredient(new AlchemicIngredient(
	 * 			|	(int) (newSpoonAmount/Unit.getBestUnitForStateAndSpoons(newState, newSpoonAmount).getSpoonEquivalent()),
	 * 			|	Unit.getBestUnitForStateAndSpoons(newState, newSpoonAmount), newTemperature,
	 * 			|	newType, newState)) ) &&
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

		// Delete all ingredients
		while (getNbOfIngredients() > 0) {
			removeAsIngredient(getIngredientAt(0));
		}

		// Create the new ingredient(type) and add to the kettle
		IngredientType newType = new IngredientType(newName, newState, newStandardTemperature, newName.isMixed());
		addAsIngredient(new AlchemicIngredient(
				(int) (newSpoonAmount/Unit.getBestUnitForStateAndSpoons(newState, newSpoonAmount).getSpoonEquivalent()),
				Unit.getBestUnitForStateAndSpoons(newState, newSpoonAmount), newTemperature, newType, newState));
	}

}
