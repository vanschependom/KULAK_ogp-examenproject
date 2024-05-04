package rpg;

import rpg.exceptions.IllegalNameException;

import java.util.Arrays;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a name of an ingredient type.
 *
 * @invar 	The simple name parts of a name must be valid.
 * 			| isValidSimpleNameParts(getSimpleNameParts())
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class Name {

	/**********************************************************
	 * CLASS PROPERTIES
	 **********************************************************/

	/**
	 * A variable referencing the default name, water.
	 */
	private static final Name WATER = new Name(null, "Water");

	/**
	 * A variable referencing the allowed symbols for a name.
	 */
	private static final String ALLOWED_NAME_SYMBOLS = " '()";

	/**
	 * A method for getting the default name, water.
	 */
	public static Name getWater() {
		return WATER;
	}



	/**********************************************************
	 * CONSTRUCTOR
	 **********************************************************/

	/**
	 * A constructor for creating a new name with given special name and simple name parts.
	 *
	 * @param 	specialName
	 * 			The special name of the name.
	 * @param 	simpleNameParts
	 * 			The simple name parts of the name.
	 *
	 * @post	If the simple name parts are legal, the simple name parts are set to the given simple name parts.
	 * 			| if (isValidSimpleNameParts(simpleNameParts))
	 * 			| then new.getSimpleNameParts() == simpleNameParts
	 * @effect	The special name of the new name is set to the given special name.
	 * 			| setSpecialName(specialName)
	 *
	 * @throws 	IllegalArgumentException
	 * 			The simple name parts are not valid.
	 * 			| !isValidSimpleNameParts(simpleNameParts)
	 * @throws	IllegalStateException
	 * 			No special name is allowed if the simpleNameParts length is greater than 1.
	 * 			| simpleNameParts.length > 1 && specialName != null
	 */
	@Raw
	public Name(String specialName, String... simpleNameParts) throws IllegalArgumentException, IllegalStateException {
		if (!isValidSimpleNameParts(simpleNameParts)) {
			throw new IllegalArgumentException("Illegal simple name!");
		}
		if (simpleNameParts.length == 1 && specialName != null) {
			throw new IllegalStateException("No special name allowed!");
		}
		// order the simple name parts and store them
		Arrays.sort(simpleNameParts);
		this.simpleNameParts = simpleNameParts;
		setSpecialName(specialName);
	}



	/**********************************************************
	 * SIMPLE NAME
	 **********************************************************/

	/**
	 * A variable referencing the simple name parts of the name.
	 */
	private final String[] simpleNameParts;

	/**
	 * A getter for the simple name parts of the name.
	 */
	@Model
	private String[] getSimpleNameParts() {
		return simpleNameParts;
	}

	/**
	 * A method that returns the simple name.
	 *
	 * @return	The first name part if the name is not mixed
	 * 			| if (!isMixed())
	 * 			| then result == getSimpleNameParts()[0]
	 * @return	The first name part followed by "mixed with" and the second name part if the name is mixed
	 * 			and the length of the simple name parts is 2.
	 * 			| if (isMixed() && getSimpleNameParts().length == 2)
	 * 			| then result == (getSimpleNameParts()[0] + " mixed with " + getSimpleNameParts()[1])
	 * @return	The first name part followed by "mixed with" and the other name parts separated by commas
	 * 			and the last name part preceded by "and" if the length of the simple name parts
	 * 			is greater than 2.
	 * 			| if (isMixed() && getSimpleNameParts().length > 2)
	 * 			|	then result == ( getSimpleNameParts()[0] + " mixed with " +
	 * 			|	getSimpleNameParts()[1] + ", " + ... + " and " + getSimpleNameParts()[n] )
	 */
	@Basic @Immutable
	public String getSimpleName() {
		if (!isMixed()) {
			return simpleNameParts[0];
		} else {
			// first part
			String toReturn = simpleNameParts[0] + " mixed with " + simpleNameParts[1];
			// middle parts
			for (int i = 2; i < simpleNameParts.length; i++) {
				if (i != simpleNameParts.length - 1) {
					toReturn += ", ";
				} else {
					// last part
					toReturn += " and ";
				}
				toReturn += simpleNameParts[i];
			}
			return toReturn;
		}
	}

	/**
	 * A method for checking whether a simple name parts array.
	 *
	 * @return	True if all simple name parts are valid.
	 * 			| result == (for all simpleNamePart in simpleNameParts:
	 * 			|	isValidName(simpleNamePart) )
	 */
	@Raw
	public static boolean isValidSimpleNameParts(String[] simpleNameParts) {
		if (simpleNameParts == null || simpleNameParts.length == 0) {
			return false;
		}
		for (String simpleNamePart : simpleNameParts) {
			if (!isValidName(simpleNamePart)) {
				return false;
			}
		}
		return true;
	}



	/**********************************************************
	 * SPECIAL NAME
	 **********************************************************/

	/**
	 * A variable referencing the special name of the name.
	 */
	private String specialName;

	/**
	 * A getter for the special name of the name.
	 */
	@Basic
	public String getSpecialName() {
		return specialName;
	}

	/**
	 * A method for setting the special name of the name.
	 *
	 * @param 	specialName
	 * 			The special name to set.
	 *
	 * @post	If the name is mixed and the given special name is not null and valid,
	 * 			the special name is set to the given special name.
	 * 			| if (isMixed() && isValidName(specialName) && specialName != null)
	 * 			| then new.getSpecialName() == specialName
	 *
	 * @throws	IllegalStateException
	 * 			The name is not mixed and the given special name is not null.
	 * 			| !isMixed() && specialName != null
	 * @throws	IllegalNameException
	 * 			The given special name is not null and not a valid name.
	 * 			| specialName != null && !isValidName(specialName)
	 */
	@Raw
	protected void setSpecialName(String specialName) throws IllegalStateException, IllegalNameException {
		if (!isMixed() && specialName != null) {
			throw new IllegalStateException("No special name allowed!");
		}
		if (specialName != null && !isValidName(specialName)) {
			throw new IllegalNameException(specialName);
		}
		this.specialName = specialName;
	}



	/**********************************************************
	 * SUPPORTING METHODS
	 **********************************************************/

	/**
	 * A method for checking whether the name is mixed.
	 *
	 * @return	True if the name consists of multiple simple name parts; false otherwise.
	 * 			| result == getSimpleName().contains("mixed with")
	 *
	 * @note	We must specify this specification in terms of public inspectors, e.g. getSimpleName().
	 * 			We can't use simpleParts.length > 1, because simpleParts is private.
	 */
	public boolean isMixed() {
		return simpleNameParts.length > 1;
	}

	/**
	 * A method for checking wheter a given name is a valid name for an ingredient type.
	 *
	 * @param 	name
	 * 			The name to check.
	 *
	 * @return	False if the name is null or the name is empty.
	 * 			| if (name == null || name.isEmpty())
	 * 			| then result == false
	 * @return	False if the name contains the words "mixed with", "and", "heated" or "cooled",
	 * 			ignoring the case.
	 * 			| if (name.toLowerCase().contains("mixed with") || name.toLowerCase().contains("and")
	 * 			|	|| name.toLowerCase().contains("heated") || name.toLowerCase().contains("cooled"))
	 * 			| then result == false
	 * @return	False if the name contains illegal symbols.
	 * 			| if (containsIllegalSymbols(name))
	 * 			| then result == false
	 * @return 	False if the name consists of one part and the part is not correctly cased or the part
	 * 			is shorter than 3 characters.
	 * 			| if ( (name.split(" ").length == 1)
	 * 			| and ( !isCorrectlyCased(parts[0]) || !parts[0].length() >= 3) )
	 * 			|	then result == false
	 * @return 	False if the name consists of multiple parts and at least one part is not correctly cased
	 * 			or the part is shorter than 2 characters.
	 * 			| if ( (name.split(" ").length > 1)
	 * 			| and ( for some part in name.split(" "): !isCorrectlyCased(part) || part.length() < 2) )
	 * 			|	then result == false
	 * @return	True otherwise.
	 * 			| todo vraag na voor specificatie
	 */
	public static boolean isValidName(String name) {
		// empty or null
		if (name == null || name.isEmpty()) {
			return false;
		}
		// illegal words
		if (name.toLowerCase().contains("mixed with") || name.toLowerCase().contains("and")
				|| name.toLowerCase().contains("heated") || name.toLowerCase().contains("cooled")) {
			return false;
		}
		// illegal symbols
		if (containsIllegalSymbols(name)) {
			return false;
		}
		// split
		String[] parts = name.split(" ");
		// only one word in name
		if (parts.length == 1) {
			if (!isCorrectlyCased(parts[0]) || parts[0].length() < 3) {
				return false;
			}
		} else {
			// multiple words in name
			for (String part : parts) {
				if (!isCorrectlyCased(part) || part.length() < 2) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * A method for checking whether a given string contains illegal symbols.
	 *
	 * @param 	str
	 * 			The string to check.
	 * @return	True if the string contains a symbol that is not a letter or an allowed symbol.
	 * 			| result == for some i in 0..str.length()-1:
	 * 			|	!Character.isLetter(str.charAt(i)) && !isLegalSymbol(str.charAt(i))
	 */
	public static boolean containsIllegalSymbols(String str) {
		for (int i = 0; i < str.length(); i++) {
			char symbol = str.charAt(i);
			// not a letter and not an allowed symbol
			if (!Character.isLetter(symbol) && !isLegalSymbol(symbol)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A method for checking whether a given string is correctly cased.
	 *
	 * @param 	str
	 * 			The string to check.
	 * @return	False if the first letter is not uppercase or a legal symbol.
	 * 			| if (!Character.isUpperCase(str.charAt(0)) && !isLegalSymbol(str.charAt(0)))
	 * 			| then result == false
	 * @return	False if at least one letter is uppercase.
	 * 			| if (for some i in 1..str.length()-1: Character.isUpperCase(str.charAt(i)))
	 * 			| then result == false
	 */
	public static boolean isCorrectlyCased(String str) {
		// the first letter must be uppercase or a legal symbol
		if (!Character.isUpperCase(str.charAt(0)) && !isLegalSymbol(str.charAt(0))) {
			return false;
		}
		// the rest of the letters can't be uppercase
		for (int i = 1; i < str.length(); i++) {
			if (Character.isUpperCase(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * A method for checking whether a given symbol is a legal symbol for a name.
	 *
	 * @param 	symbol
	 * 			The symbol to check.
	 * @return	True if the symbol is contained in the allowed name symbols.
	 * 			| result == ALLOWED_NAME_SYMBOLS.indexOf(symbol) != -1
	 */
	private static boolean isLegalSymbol(char symbol) {
		return ALLOWED_NAME_SYMBOLS.indexOf(symbol) != -1;
	}

}
