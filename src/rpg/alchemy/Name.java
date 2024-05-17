package rpg.alchemy;

import rpg.exceptions.IllegalNameException;

import java.util.Arrays;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a name of an ingredient type.
 *
 * @invar 	The simple name parts of a name must be valid.
 * 			| isValidSimpleNameParts(getSimpleNameParts())
 *
 * @note 	We use DEFENSIVE PROGRAMMING for the name.
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
	 *
	 * @note 	We make this variable public because it is a constant and
	 * 			clients can use it freely, i.e. they can't do illegal things with it.
	 */
	public static final Name WATER = new Name(null, "Water");

	/**
	 * A variable referencing the allowed symbols for a name.
	 */
	private static final String ALLOWED_NAME_SYMBOLS = " '()";



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
	 * @post	The simple name parts are set to the given simple name parts.
	 * 			| Arrays.equals(new.getSimpleNameParts(), simpleNameParts)
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
			throw new IllegalArgumentException("Invalid simple name parts!");
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
	 * A method for getting the simple name parts of this name.
	 */
	@Basic @Immutable
	public String[] getSimpleNameParts() {
		return simpleNameParts;
	}

	/**
	 * A method that returns the simple name.
	 *
	 * @return	The first name part if the name is not mixed
	 * 			| if (!isMixed())
	 * 			| 	then result == getSimpleNameParts()[0]
	 * @return	The first name part followed by "mixed with" and the second name part if the name is mixed
	 * 			and the length of the simple name parts is 2.
	 * 			| if (isMixed() && getSimpleNameParts().length == 2)
	 * 			| 	then result == (getSimpleNameParts()[0] + " mixed with " + getSimpleNameParts()[1])
	 * @return	The first name part followed by "mixed with" and the other name parts separated by commas
	 * 			and the last name part preceded by "and" if the length of the simple name parts
	 * 			is greater than 2.
	 * 			| if (isMixed() && getSimpleNameParts().length > 2)
	 * 			|	then result == ( getSimpleNameParts()[0] + " mixed with " +
	 * 			|		getSimpleNameParts()[1] + ", " + ... + " and " + getSimpleNameParts()[getSimpleNameParts().length-1] )
	 */
	@Immutable
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
	 * A method for checking whether a simple name parts array is valid.
	 *
	 * @return	False if the simple name parts array is a null pointer or
	 * 			if the length of the array is zero.
	 * 			| if (simpleNameParts == null || simpleNameParts.length == 0)
	 * 			| 	then result == false
	 * @return	If the simple name parts array isn't a null pointer and
	 *			if the length of the array isn't zero then return true if
	 *			all simple name parts are valid.
	 *			| if (simpleNameParts != null && simpleNameParts.length != 0)
	 * 			| 	result == (for all simpleNamePart in simpleNameParts:
	 * 			|		isValidName(simpleNamePart) )
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
	 * 			| 	then new.getSpecialName().equals(specialName)
	 *
	 * @throws	IllegalStateException
	 * 			The name is not mixed and the given special name is not null.
	 * 			| !isMixed() && specialName != null
	 * @throws	IllegalNameException
	 * 			The given special name is not null and not a valid name.
	 * 			| specialName != null && !isValidName(specialName)
	 */
	@Raw @Model
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
	 * 			| result == getSimpleNameParts().length > 1
	 */
	public boolean isMixed() {
		return getSimpleNameParts().length > 1;
	}

	/**
	 * A method for checking whether a given name is a valid name for an ingredient type.
	 *
	 * @param 	name
	 * 			The name to check.
	 *
	 * @return	True if and only if the name is not null, the name is not empty, the name doesn't
	 * 			contain any illegal words, the name doesn't contain any illegal symbols
	 * 			and the name doesn't contain any illegal parts.
	 * 			| result ==
	 * 			| 	( name != null
	 * 			|	&& !name.isEmpty()
	 * 			|	&& !containsIllegalWords(name)
	 * 			|	&& !containsIllegalSymbols(name)
	 * 			|	&& !containsIllegalPart(name.split(" ") )
	 */
	public static boolean isValidName(String name) {
		return (
				name != null
				&& !name.isEmpty()
				&& !containsIllegalWords(name)
				&& !containsIllegalSymbols(name)
				&& !containsIllegalPart(name.split(" "))
		);
	}

	/**
	 * A method for checking if a given string contains illegal words for a name.
	 *
	 * @param 	str
	 * 			The string to check.
	 *
	 * @return	True if and only if the string contains (ignoring case) mixed, with, and, heated or cooled.
	 * 			| result ==
	 * 			|	( str.toLowerCase().contains("mixed")
	 * 			|		|| str.toLowerCase().contains("with")
	 * 			|		|| str.toLowerCase().contains("and")
	 * 			|		|| str.toLowerCase().contains("heated")
	 * 			|		|| str.toLowerCase().contains("cooled") )
	 */
	public static boolean containsIllegalWords(String str) {
		return (str.toLowerCase().contains("mixed")
				|| str.toLowerCase().contains("with")
				|| str.toLowerCase().contains("and")
				|| str.toLowerCase().contains("heated")
				|| str.toLowerCase().contains("cooled"));
	}

	/**
	 * A method for checking if a list of name parts contains an illegal part,
	 * i.e. a part that is too short or isn't correctly cased.
	 *
	 * @param 	parts
	 * 			The list of strings to check.
	 *
	 * @return	If there is only one part, true if and only if the part is not correctly cased
	 * 			or the length of this part is smaller than 3.
	 * 			| if (parts.length == 1) then
	 * 			| 	result == ( !isCorrectlyCased(parts[0])	|| parts[0].length < 3 )
	 * @return	If there is more than one part, true if and only if there exists a part
	 * 			which is not correctly cased or which has a length shorter than 2.
	 * 			| if (parts.length > 1) then
	 * 			| 	result == ( for some part in parts:
	 * 			|		!isCorrectlyCased(part) || part.length() < 2 )
	 */
	public static boolean containsIllegalPart(String[] parts) {
		// only one part
		if (parts.length == 1) {
			return (!isCorrectlyCased(parts[0]) || parts[0].length() < 3);
		} else {
			// multiple parts
			for (String part : parts) {
				if (!isCorrectlyCased(part) || part.length() < 2) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * A method for checking whether a given string contains illegal symbols.
	 *
	 * @param 	str
	 * 			The string to check.
	 *
	 * @return	True if the string contains a symbol that is not a letter or an allowed symbol.
	 * 			| result == ( for some i in 0..str.length()-1:
	 * 			|	!Character.isLetter(str.charAt(i)) && !isLegalSymbol(str.charAt(i)) )
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
	 *
	 * @return	False if the first letter is not uppercase or a legal symbol.
	 * 			| if (!Character.isUpperCase(str.charAt(0)) && !isLegalSymbol(str.charAt(0)))
	 * 			| 	then result == false
	 * @return	False if at least one letter is uppercase.
	 * 			| if (for some i in 1..str.length()-1: Character.isUpperCase(str.charAt(i)))
	 * 			| 	then result == false
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
	 *
	 * @return	True if the symbol is contained in the allowed name symbols.
	 * 			| result == (ALLOWED_NAME_SYMBOLS.indexOf(symbol) != -1)
	 */
	@Model
	private static boolean isLegalSymbol(char symbol) {
		return ALLOWED_NAME_SYMBOLS.indexOf(symbol) != -1;
	}

}
