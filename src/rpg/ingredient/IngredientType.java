package rpg.ingredient;

import be.kuleuven.cs.som.annotate.*;
import rpg.exceptions.IllegalNameException;
import rpg.exceptions.*;

/**
 * A class representing an alchemic ingredient type.
 *
 * @invar	The simple name of an ingredient type must always be valid.
 * 			| isValidSimpleName(getSimpleName())
 * @invar	The special name of an ingredient type must always be valid.
 * 			| isValidSpecialName(getSpecialName())
 * @invar 	The state of an ingredient type must always be valid.
 * 			| isValidState(getState())
 * @invar	The standard temperature of an ingredient type must always be valid.
 * 			| isValidStandardTemperature(getStandardTemperature())
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class IngredientType {

	/**
	 * A variable referencing the default ingredient type, water.
	 */
	public static final IngredientType DEFAULT = new IngredientType("water");

	/**********************************************************
	 * CONSTRUCTORS
	 **********************************************************/

	// ...


	/**********************************************************
	 * NAME - DEFENSIVE
	 **********************************************************/

	/**
	 * A variable to keep track of whether the ingredient type is mixed or not.
	 */
	private boolean isMixed = false;

	/**
	 * A list of all the allowed symbols in the name of any ingredient type, which
	 * will not change during the execution of the program.
	 */
	private final static char[] ALLOWED_NAME_SYMBOLS = {'\'', '(', ')'};

	/**
	 * A variable referencing the simple name of the ingredient type.
	 */
	private String simpleName = null;

	/**
	 * A getter for the simple name of the ingredient type.
	 */
	@Basic
	public String getSimpleName() {
		return simpleName;
	}

	/**
	 * A setter for the simple name of the ingredient type.
	 *
	 * @param 	simpleName
	 * 			The simple name to be set.
	 * @post	The simple name of the ingredient type is set to the given simple name.
	 * 			| new.getSimpleName() == simpleName
	 * @throws	IllegalNameException
	 * 			The given simple name is not a valid name for an ingredient type.
	 * 			| !isValidSimpleName(simpleName)
	 */
	private void setSimpleName(String simpleName) throws IllegalNameException {
		if (!isValidSimpleName(simpleName)) {
			throw new IllegalNameException(simpleName);
		}
		this.simpleName = simpleName;
	}

	/**
	 * A variable referencing the special name of the ingredient type.
	 */
	private String specialName = null;

	/**
	 * A getter for the special name of the ingredient type.
	 */
	@Basic
	public String getSpecialName() {
		return specialName;
	}

	/**
	 * A setter for the special name of the ingredient type.
	 *
	 * @param 	specialName
	 * 			The special name to be set.
	 * @post	The special name of the ingredient type is set to the given special name.
	 * 			| new.getSpecialName() == specialName
	 * @throws	IllegalNameException
	 * 			The given special name is not a valid name for an ingredient type.
	 * 			| !isValidSpecialName(specialName)
	 */
	private void setSpecialName(String specialName) {
		if (!isValidSpecialName(specialName)) {
			throw new IllegalNameException(specialName);
		}
		this.specialName = specialName;
	}

	@Override
	public String toString() {
		return "rpg.ingredient.IngredientType{" +
				"simpleName='" + this.simpleName + "'" +
				'}';
	}

}
