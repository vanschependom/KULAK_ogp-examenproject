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
	public Transmogrifier(Laboratory laboratory) {
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
	 * @effect	The only ingredient is removed and added again with the next state.
	 * 			| removeAsIngredient(getIngredientAt(0))
	 * 			| && addAsIngredient (new AlchemicIngredient( getIngredientAt(0).getFlooredSpoonAmount(),
	 * 			|	Unit.SPOON, getIngredientAt(0).getTemperatureObject(),
	 * 			|	getIngredientAt(0).getType(), getIngredientAt(0).getState().getNext() ) )
	 */
	@Override
	public void executeOperation() {
		super.executeOperation();
		AlchemicIngredient ing = getIngredientAt(0);
		removeAsIngredient(ing);
		addAsIngredient( new AlchemicIngredient( ing.getFlooredSpoonAmount(), Unit.SPOON, ing.getTemperatureObject(), ing.getType(), ing.getState().getNext() ) );
	}


}
