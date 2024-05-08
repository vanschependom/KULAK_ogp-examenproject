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
		// we return a new name with all the names of the ingredients
		return new Name(null, String.valueOf(List.of(names)));
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
				smallestDifference = smallestDifferenceTemperature.difference(new long[]{0, 20});
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
			Temperature.add(getIngredientAt(i).getTemperatureObject(), totalTemperature).mul(getIngredientAt(i).getSpoonAmount());
		}

		totalTemperature.mul(1 / getNewSpoonAmount());
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
		Temperature newStandardTemperature = getNewStandardTemperature();
		Temperature newTemperature = getNewTemperature();

		// delete all ingredients
		while (getNbOfIngredients() > 0) {
			removeAsIngredient(getIngredientAt(0));
		}

		// create the new ingredient(type) and add to the kettle
		IngredientType newType = new IngredientType(newName, newState, newStandardTemperature, true);
		addAsIngredient(new AlchemicIngredient((int) newSpoonAmount, Unit.SPOON, newTemperature, newType, newState));
	}

}
