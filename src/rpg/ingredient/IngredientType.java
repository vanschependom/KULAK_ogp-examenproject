package rpg.ingredient;

import be.kuleuven.cs.som.annotate.*;
import rpg.exceptions.IllegalNameException;
import rpg.exceptions.*;

/**
 * A class representing an alchemic ingredient type.
 *
 * @invar	The simple name of an ingredient type must always be valid.
 * 			| isValidName(getSimpleName())
 * @invar	The special name of an ingredient type must always be valid.
 * 			| getSpecialName() == null || isValidName(getSpecialName())
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
	private final boolean isMixed;

	/**
	 * A getter for the mixed state of the ingredient type.
	 */
	@Basic
	public boolean isMixed() {
		return isMixed;
	}

	/**
	 * A setter for the mixed state of the ingredient type.
	 *
	 * @param 	isMixed
	 * 			The mixed state to be set.
	 * @post	The mixed state of the ingredient type is set to the given mixed state.
	 * 			| new.isMixed() == isMixed
	 */
	private void setMixed(boolean isMixed) {
		this.isMixed = isMixed;
	}

	/**
	 * A list of all the allowed symbols in the name of any ingredient type, which
	 * will not change during the execution of the program.
	 */
	private final static String ALLOWED_NAME_SYMBOLS = "'()";

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
	 * 			| !canHaveAsName(simpleName)
	 */
	private void setSimpleName(String simpleName) throws IllegalNameException {
		if (!canHaveAsName(simpleName)) {
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

	@Override
	public String toString() {
		return "rpg.ingredient.IngredientType{" +
				"simpleName='" + this.simpleName + "'" +
				'}';
	}

}
