package rpg.alchemy;

import be.kuleuven.cs.som.annotate.*;
import rpg.Unit;

public class Transmogrifier extends Device{

	/**********************************************************
	 * CONSTRUCTOR
	 **********************************************************/

	/**
	 * A constructor for a transmogrifier device with a given laboratory
	 * and a maximum number of ingredients equal to 1.
	 *
	 * @param 	laboratory
	 * 			The laboratory in which the transmogrifier is placed.
	 *
	 * @effect	A device with a maximum number of ingredients equal to 1 is created.
	 * 			| super(laboratory, 1)
	 */
	@Raw
	public Transmogrifier(Laboratory laboratory) throws IllegalArgumentException {
		super(laboratory, 1);
	}



	/**********************************************************
	 * OPERATION EXECUTION
	 **********************************************************/

	/**
	 * A method for executing a transmogrifier.
	 *
	 * @effect  Executes the operation from device.
	 *          | super.executeOperation()
	 * @effect	The only ingredient is removed and added again with the next state,
	 * 			if conversion does not result in a spoon amount of 0.
	 * 			| removeAsIngredient(getIngredientAt(0))
	 * 			| && if ((int) getIngredientAt(0).getSpoonAmount() != 0)
	 * 			|	then addAsIngredient (new AlchemicIngredient( (int) getIngredientAt(0).getSpoonAmount(),
	 * 			|		 Unit.SPOON, new Temperature(getIngredientAt(0).getTemperature()),
	 * 			|		 getIngredientAt(0).getType(), getIngredientAt(0).getState().getNext() ) )
	 */
	@Override
	public void executeOperation() {
		super.executeOperation();
		AlchemicIngredient ing = getIngredientAt(0);
		removeAsIngredient(ing);
		if ((int) ing.getSpoonAmount() != 0) {
			addAsIngredient(new AlchemicIngredient((int) ing.getSpoonAmount(), Unit.SPOON, new Temperature(ing.getTemperature()), ing.getType(), ing.getState().getNext()));
		}
	}


}
