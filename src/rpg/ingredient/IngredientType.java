package rpg.ingredient;

import be.kuleuven.cs.som.annotate.*;
import rpg.exceptions.IllegalNameException;
import rpg.*;

/**
 * A class representing an alchemic ingredient type.
 *
 * @invar	The simple name of an ingredient type must always be valid.
 * 			| canHaveAsName(getSimpleName())
 * @invar	The special name of an ingredient type must always be valid.
 * 			| getSpecialName() == null || canHaveAsName(getSpecialName())
 * @invar 	The state of an ingredient type must always be valid.
 * 			| isValidState(getStandardState())
 * @invar	The standard temperature of an ingredient type must always be valid.
 * 			| isValidTemperature(getStandardTemperatureObject())
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
	public static final IngredientType DEFAULT = new IngredientType("Water", null, State.LIQUID, new Temperature(0, 20), false);

	/**********************************************************
	 * CONSTRUCTORS
	 **********************************************************/

	/**
	 * A constructor for creating a new ingredient type with given simple name,
	 * special name, state, temperature and mixed state.
	 *
	 * @param 	simpleName
	 * 			The simple name of the new ingredient type.
	 * @param 	specialName
	 * 			The special name of the new ingredient type.
	 * @param 	standardState
	 * 			The state of the new ingredient type.
	 * @param 	standardTemperature
	 * 			The temperature of the new ingredient type.
	 * @param 	isMixed
	 * 			A boolean indicating whether the new ingredient type is mixed or not.
	 *
	 * @post	If the given simple name is a valid simple name, the simple name
	 * 			is set to the provided simple name.
	 * 			| if (canHaveAsName(simpleName))
	 * 			| then new.getSimpleName() == simpleName
	 * @post	If the given state is valid, the state of the new ingredient type is set to the
	 * 			given state.
	 * 			| if (isValidState(state))
	 * 			| then new.getStandardState() == state
	 * @post    If the given temperature is not a valid standard temperature, the temperature is
	 * 			set to the standard temperature.
	 *          | if (!isValidStandardTemperature(temperature))
	 *          | then new.getStandardTemperature()[0] == 0
	 *          |      && new.getStandardTemperature()[1] == 20
	 * @post    If the given temperature is a valid standard temperature, the temperature
	 * 			standard temperature is set to the given temperature.
	 *          | if (isValidStandardTemperature(temperature))
	 *          | then new.getStandardTemperatureObject() == temperature
	 * @post	The mixed state of the new ingredient type is set to the given mixed state.
	 * 			| new.isMixed() == isMixed
	 * @effect	If the special name is effective, the special name of the new ingredient type
	 * 			is set to the given special name if the given special name is a valid name for
	 * 			an ingredient type.
	 * 			| if (specialName != null)
	 * 			| then setSpecialName(specialName)
	 *
	 * @throws	IllegalNameException
	 * 			The given simple name is not a valid name for an ingredient type.
	 * 			| !canHaveAsName(simpleName)
	 * @throws	IllegalNameException
	 * 			The given effective special name is not a valid name for an ingredient type.
	 * 			| specialName != null && !canHaveAsName(specialName)
	 * @throws	IllegalStateException
	 * 			The given state is not a valid state for an ingredient type.
	 * 			| !isValidState(state)
	 */
	@Raw
	public IngredientType(String simpleName, String specialName, State standardState, Temperature standardTemperature, boolean isMixed)
			throws IllegalNameException, IllegalStateException {
		if (!canHaveAsName(simpleName)) {
			throw new IllegalNameException(simpleName);
		}
		if (!isValidState(standardState)) {
			throw new IllegalStateException("Invalid state! State must be effective.");
		}
		if (!isValidStandardTemperature(standardTemperature)) {
			this.standardTemperature = new Temperature();
		} else {
			this.standardTemperature = standardTemperature;
		}
		if (specialName != null) {
			setSpecialName(specialName);
		}
		this.simpleName = simpleName;			// legal, exception is thrown if not
		this.standardState = standardState;		// legal, exception is thrown if not
		this.isMixed = isMixed;					// always valid (boolean)
	}

	/**
	 * A constructor for creating a new ingredient type with given simple name,
	 * state, temperature and mixed state.
	 *
	 * @param 	simpleName
	 * 			The simple name of the new ingredient type.
	 * @param 	standardState
	 * 			The standard state of the new ingredient type.
	 * @param 	standardTemperature
	 * 			The standard temperature of the new ingredient type.
	 * @param 	isMixed
	 * 			A boolean indicating whether the new ingredient type is mixed or not.
	 *
	 * @effect	An ingredient type with the given parameters
	 * 			and a special name of null is created
	 * 			| this(simpleName, null, standardState, temperature, isMixed)
	 */
	@Raw
	public IngredientType(String simpleName, State standardState, Temperature standardTemperature, boolean isMixed)
			throws IllegalNameException, IllegalStateException {
		this(simpleName, null, standardState, standardTemperature, isMixed);
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
	@Basic @Immutable
	public boolean isMixed() {
		return isMixed;
	}

	/**
	 * A variable containing all the allowed symbols in the name of any ingredient type, which
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
	@Basic @Immutable
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
	@Model @Raw
	private void setSpecialName(String specialName) throws IllegalNameException {
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
	 * @return	If the name is a null pointer or the length of the name is zero then return false
	 * 			| if (name == null || name.isEmpty())
	 * 			| then result == false
	 * @return	If there is an invalid word in the name then return false
	 * 			| for every word in name
	 * 			| 	if (!canHaveAsNameWord(word))
	 * 			| 		then result == false
	 * @return 	If there is only one word in the name and that word is shorter than 3 characters
	 * 			then return false
	 * 			| if (words.length == 1 && words[0].length() < 3)
	 * 			| then result == false
	 * @return 	If none of the cases above apply then return true
	 * 			| result == true
	 * 			TODO: navragen of dit mag van specificatie
	 */
	@Raw
	public boolean canHaveAsName(String name) {
		if (name == null || name.isEmpty()) {
			return false;
		}
		String[] words = name.split(" ");
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
	 * @return	If the word is 'Heated' or 'Cooled' or the word is shorter than
	 * 			2 characters then return false
	 * 			| if (word.equals("Heated") || word.equals("Cooled") || word.length() < 2)
	 * 			| then result == false
	 * @return	If the ingredient type is mixed and the word is 'mixed' or 'with'
	 * 			then return true
	 * 			| if (isMixed() && 	word.equals("mixed") || word.equals("with"))
	 * 			| then result == true
	 * @return	If the ingredient type isn't mixed and the word is 'mixed' or 'with' while
	 * 			ignoring case then return false
	 * 			| if (!isMixed() && word.equalsIgnoreCase("mixed") || word.equalsIgnoreCase("with"))
	 * 			| then result == false
	 * @return 	If the first letter of the word is illegal or is lowercase then return false
	 * 			| if (!Character.isUpperCase(word.charAt(0)) &&
	 * 			|	!isLegalSymbol(word.charAt(0)))
	 * 			| result == false
	 * @return	If there is a letter except for the first letter that is also uppercase	or
	 * 			an illegal symbol, then return false
	 * 			| for every letter at index i (starting at index 1) of word
	 * 			| 	if (!Character.isLowerCase(word.charAt(i)) &&
	 * 			|  		!isLegalSymbol(word.charAt(i)))
	 * 			| 		then result == false
	 * @return 	If none of the cases above apply then return true
	 * 			| result == true
	 * 			TODO navragen of dit mag van specificatie
	 */
	@Model @Raw
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
			if (!Character.isLowerCase(word.charAt(i)) &&
					!isLegalSymbol(word.charAt(i))) {
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
	 * 			| result == (ALLOWED_NAME_SYMBOLS.indexOf(symbol) != -1)
	 */
	@Model @Raw
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
	@Basic @Immutable
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
	@Raw
	public static boolean isValidState(State state) {
		return state != null;
	}



	/**********************************************************
	 * TEMPERATURE - TOTAL
	 **********************************************************/

	/**
	 * A variable referencing the standard temperature of the ingredient type.
	 */
	private final Temperature standardTemperature;

	/**
	 * A getter for the standard temperature of the ingredient type.
	 */
	@Basic @Immutable
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

	/**
	 * A method for checking whether the given temperature is a valid standard temperature.
	 *
	 * @return 	True if and only if the temperature is effective.
	 * 			| result == (temperature != null)
	 */
	@Raw
	public static boolean isValidStandardTemperature(Temperature temperature) {
		return temperature != null;
	}


}
