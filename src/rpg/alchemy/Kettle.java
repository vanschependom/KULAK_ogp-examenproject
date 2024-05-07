package rpg.alchemy;

import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kettle extends Device{

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
	 * @post
	 */
	@Override
	public void executeOperation() {
		super.executeOperation();
		ArrayList<String> names = new ArrayList<>();
		int closestTemperatureIndex = 0;
		long smallestDifferenceTemperature = Long.MAX_VALUE;
		for (int i = 0; i < getNbOfIngredients(); i++) {
			// We get all the names of the ingredients
			names.addAll(Arrays.asList(getIngredientAt(i).getType().getName().getSimpleNameParts()));
			// We get the ingredient with a temperature closest to [0, 20]
			if (getIngredientAt(i).getType().getStandardTemperatureObject().difference(new long[] {0, 20}) < smallestDifferenceTemperature) {
				smallestDifferenceTemperature = getIngredientAt(i).getType().getStandardTemperatureObject().difference(new long[] {0, 20});
				closestTemperatureIndex = i;
			}
		}
		Name name = new Name(null, String.valueOf(List.of(names)));
		getIngredientAt(closestTemperatureIndex).getState();

	}

}
