package rpg;

import java.util.Arrays;

public class Name {

	// TODO SPECIFICATION

	private final String[] simpleNameParts;
	private String specialName;
	private final static Name WATER = new Name(null, "Water");

	public static Name getWater() {
		return WATER;
	}

	private static final String ALLOWED_NAME_SYMBOLS = " '()";

	public Name(String specialName, String... simpleNameParts) {
		if (simpleNameParts == null || simpleNameParts.length == 0) {
			throw new IllegalArgumentException("Illegal simple name!");
		}
		if (simpleNameParts.length == 1 && specialName != null) {
			throw new IllegalArgumentException("No special name allowed!");
		}
		for (String simpleNamePart : simpleNameParts) {
			if (!isValidName(simpleNamePart)) {
				throw new IllegalArgumentException("Illegal Name");
			}
		}
		// order the simple name parts and store them
		Arrays.sort(simpleNameParts);
		this.simpleNameParts = simpleNameParts;
		setSpecialName(specialName);
	}

	public static boolean isValidName(String[] names) {
		for (String name : names) {
			if (!isValidName(name)) {
				return false;
			}
		}
		return true;
	}

	protected void setSpecialName(String specialName) {
		if (simpleNameParts.length == 1 && specialName != null) {
			throw new IllegalStateException("No special name allowed!");
		}
		if (specialName != null && !isValidName(specialName)) {
			throw new IllegalArgumentException("Illegal Name");
		}
		this.specialName = specialName;
	}

	public boolean isMixed() {
		return simpleNameParts.length > 1;
	}

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
			if (!correctlyCased(parts[0]) || parts[0].length() < 3) {
				return false;
			}
		} else {
			// multiple words in name
			for (String part : parts) {
				if (!correctlyCased(part) || part.length() < 2) {
					return false;
				}
			}
		}
		return true;
	}

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

	public static boolean correctlyCased(String str) {
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

	private static boolean isLegalSymbol(char symbol) {
		return ALLOWED_NAME_SYMBOLS.indexOf(symbol) != -1;
	}

	public String getSpecialName() {
		if (!isMixed()) {
			throw new IllegalStateException("No special name available since this is not a mixture!");
		}
		return specialName;
	}

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

}
