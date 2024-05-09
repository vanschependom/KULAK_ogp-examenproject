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

	/**
	 * A constructor for a kettle device.
	 *
	 * @effect	A kettle device with a maximum number of ingredients of
	 * 			Integer.MAX_VALUE is created.
	 * 			| super(Integer.MAX_VALUE)
	 */
	@Raw
	public Kettle() {
		super(Integer.MAX_VALUE);
	}

	/**
	 * Return the new name object for the new mixed ingredient, with a special name of null.
	 * The simple name parts being all the simple name parts of the ingredients.
	 */
	@Model @Basic
	private Name getNewName() {
		ArrayList<String> names = new ArrayList<>();

		for (int i = 0; i < getNbOfIngredients(); i++) {
			// we get all names of the ingredients
			names.addAll(Arrays.asList(getIngredientAt(i).getType().getName().getSimpleNameParts()));
		}
		// we remove all duplicates, so that the same ingredientType but different Alchemic ingredient does not get repeated
		Set<String> set = new HashSet<>(names);
		names.clear();
		names.addAll(set);
		// we return a new name with all the names of the ingredients
		return new Name(null, names.toArray(new String[0]));
	}

	/**
	 * Return the state of the ingredient with a standard temperature closest to [0, 20].
	 * Liquid is chosen over powder in an ex aequo.
	 */ // TODO kan  dit hier basic genomen worden of moet hier heel wat specificatie bijkomen met meer uitleg
	// TODO geldt eigenlijk voor elke new method
	@Model @Basic
	private State getNewState() {
		int closestTemperatureIndex = 0;
		long smallestDifferenceTemperature = Long.MAX_VALUE;

		for (int i = 0; i < getNbOfIngredients(); i++) {
			// We get the ingredient with a standard temperature closest to [0, 20]
			long difference = getIngredientAt(i).getType().getStandardTemperatureObject().difference(new long[]{0, 20});
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
	 * Return the sum of all the spoon amounts of the ingredients
	 */
	@Model @Basic
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
			long difference = getIngredientAt(i).getType().getStandardTemperatureObject().difference(new long[]{0, 20});
			// We get the ingredient with a standard temperature closest to [0, 20]
			if ( difference < smallestDifference || ( difference == smallestDifference &&
					getIngredientAt(i).getHotness() > smallestDifferenceTemperature.getHotness() ) ) {
				smallestDifferenceTemperature = getIngredientAt(i).getType().getStandardTemperatureObject();
				smallestDifference = difference;
			}
		}
		return smallestDifferenceTemperature;
	}

	/**
	 * Return the weighted average temperature of all the ingredients inside the kettle.
	 */
	@Model @Basic
	private Temperature getNewTemperature() {
		Temperature totalTemperature = new Temperature(0, 0);
		for (int i = 0; i < getNbOfIngredients(); i++) {
			Temperature tempToAdd = new Temperature(getIngredientAt(i).getColdness(), getIngredientAt(i).getHotness());
			tempToAdd.mul(getIngredientAt(i).getSpoonAmount());
			totalTemperature = Temperature.add(tempToAdd, totalTemperature);
		}
		totalTemperature.mul(1/getNewSpoonAmount());
		return totalTemperature;
	}

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
