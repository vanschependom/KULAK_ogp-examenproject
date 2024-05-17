package rpg.exceptions;

/**
 * A class for signaling that an ingredient is not present.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class IngredientNotPresentException extends RuntimeException {

	/**
	 * A constructor for an exception.
	 *
	 * @effect 	The constructor of the super class is called.
	 * 			| super()
	 */
	public IngredientNotPresentException() {
		super();
	}

}
