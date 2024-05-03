package rpg;

import rpg.ingredient.IngredientType;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		String name = "Beer mixed with Flour, Coke and Water";

		System.out.println(canHaveAsName(name));
	}

	public static boolean canHaveAsName (String name) {
		if (name == null || name.isEmpty()) {
			return false;
		}
		String[] words = name.split(" ");
		if (isMixed()) {

			if (!name.contains("mixed with")) {
				return false;
			}
			String[] parts = name.split("mixed with");

			String mainIngredient = parts[0].strip();

			System.out.println(mainIngredient);

			if (!firstLetterUppercase(mainIngredient)) {
				return false;
			}
			if (!otherLettersLegal(mainIngredient)) {
				return false;
			}

			String[] anded = parts[1].split("and");

			if (anded.length == 2) {
				String[] commad = anded[0].split(",");
				if (commad.length > 1) {
					// e.g. Beer mixed with [ (Coke, Salt) and (Water) ]
					for (String ingredient : commad) {
						String stripped = ingredient.strip();
						System.out.println(stripped);
					}
				} else {
					// e.g. Beer mixed with [ (Coke) and (Salt) ]
					// ...
				}
				System.out.println(anded[1].strip());
			} else {
				// e.g. Beer mixed with [ (Coke) ]
				// ...
			}



		}
		return true;
	}

	public static boolean isMixed() { return true; }

	public static boolean firstLetterUppercase(String word) {
		return Character.isUpperCase(word.charAt(0));
	}

	public static boolean otherLettersLegal(String word) {
		final String ALLOWED_NAME_SYMBOLS = "'()";
		for (int i = 1; i < word.length(); i++) {
			char symbol = word.charAt(i);
			if (!Character.isLowerCase(symbol) || (ALLOWED_NAME_SYMBOLS.indexOf(symbol) != -1)) {
				return false;
			}
		}
		return true;
	}

}
