package rpg.ingredient;

import be.kuleuven.cs.som.annotate.*;
import rpg.exceptions.IllegalNameException;
import rpg.*;

/**
 * A class representing an alchemic ingredient type.
 *
 * @invar	The simple name of an ingredient type must always be valid.
 * 			| isValidName(getSimpleName())
 * @invar	The special name of an ingredient type must always be valid.
 * 			| getSpecialName() == null || isValidName(getSpecialName())
 * @invar 	The state of an ingredient type must always be valid.
 * 			| isValidState(getStandardState())
 * @invar	The standard temperature of an ingredient type must always be valid.
 * 			| Temperature.isValidStandardTemperature(getStandardTemperatureObject.getColness(), getStandardTemperatureObject.getHotness())
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

	public IngredientType(String simpleName, String specialName, State state, Temperature temperature, boolean isMixed) {
		// todo condities checken en exception gooien
		this.simpleName = simpleName;
		setSpecialName(specialName);
		this.standardState = state;
		this.standardTemperature = temperature;
		this.isMixed = isMixed;
	}


	/**********************************************************
	 * NAME - DEFENSIVE
	 **********************************************************/

	/**
	 * A variable to keep track of whether the ingredient type is mixed or not.
	 */
	private final boolean isMixed;

	/**
	 * A getter for the mixed state of the ingredient type.
	 */
	@Basic
	public boolean isMixed() {
		return isMixed;
	}

	/**
	 * A list of all the allowed symbols in the name of any ingredient type, which
	 * will not change during the execution of the program.
	 */
	private final static String ALLOWED_NAME_SYMBOLS = "'()";

	/**
	 * A variable referencing the simple name of the ingredient type.
	 */
	private final String simpleName;

	/**
	 * A getter for the simple name of the ingredient type.
	 */
	@Basic
	public String getSimpleName() {
		return simpleName;
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
	 * 			| !canHaveAsName(specialName)
	 */
	private void setSpecialName(String specialName) {
		if (!canHaveAsName(specialName)) {
			throw new IllegalNameException(specialName);
		}
		this.specialName = specialName;
	}

	/**
	 * Check whether the given name is a valid name for an ingredient type.
	 *
	 * @param 	name
	 * 			The name to check.
	 * @return	...
	 */
	public boolean canHaveAsName(String name) {
		if (name == null || name.isEmpty()) {
			return false;
		}
		String words[] = name.split(" ");
		for (String word : words) {
			if (!canHaveAsNameWord(word)) {
				return false;
			}
		}
		if (words.length == 1 && words[0].length() < 3) {
			return false;
		}
		return true;
	}

	/**
	 * Check whether the given word is a valid part for the name for an ingredient type.
	 *
	 * @param 	word
	 * 			The word in the name to check.
	 * @return	...
	 */
	private boolean canHaveAsNameWord(String word) {
		if (word.equals("Heated") || word.equals("Cooled")) {
			return false;
		}
		if (isMixed()) {
			if (word.equals("mixed") || word.equals("with")) {
				return true;
			}
		} else {
			if (word.equalsIgnoreCase("mixed") ||
					word.equalsIgnoreCase("with")) {
				return false;
			}
		}
		if (word.length() < 2) {
			return false;
		}
		if (!Character.isUpperCase(word.charAt(0)) &&
				!isLegalSymbol(word.charAt(0))) {
			return false;
		}
		// check other characters
		for (int i = 1; i < word.length(); i++) {
			if (!Character.isLowerCase(word.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * A method for checking whether a given symbol is a legal symbol for a name.
	 * @param 	symbol
	 * 			The symbol to check
	 * @return	True if the symbol is a legal symbol for a name; false otherwise.
	 * 			| result == ALLOWED_NAME_SYMBOLS.indexOf(symbol) != -1)
	 */
	private static boolean isLegalSymbol(char symbol) {
		return ALLOWED_NAME_SYMBOLS.indexOf(symbol) != -1;
	}



	/**********************************************************
	 * STATE
	 **********************************************************/

	/**
	 * A variable referencing the standard state of the ingredient type.
	 */
	private final State standardState;

	/**
	 * A getter for the standard state of the ingredient type.
	 */
	@Basic
	public State getStandardState() {
		return standardState;
	}

	/**
	 * Check whether the given state is a valid state for an ingredient type.
	 *
	 * @param 	state
	 * 			The state to check.
	 * @return	True if and only if the state is effective.
	 * 			| result == (state != null)
	 */
	public static boolean isValidState(State state) {
		return state != null;
	}



	/**********************************************************
	 * TEMPERATURE
	 **********************************************************/

	/**
	 * A variable referencing the standard temperature of the ingredient type.
	 */

	private final Temperature standardTemperature;

	/**
	 * A getter for the standard temperature of the ingredient type.
	 */
	@Basic
	public long[] getStandardTemperature() {
		return getStandardTemperatureObject().getTemperature();
	}

	/**
	 * A getter for the standard temperature object of the ingredient type.
	 */
	@Model
	private Temperature getStandardTemperatureObject() {
		return standardTemperature;
	}

}
