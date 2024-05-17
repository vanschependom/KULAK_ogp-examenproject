package rpg.exceptions;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for signaling illegal names of ingredient types.
 *
 * @invar	The illegal name must be effective.
 * 			| isValidName(getName())
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 * @version	1.0
 */
public class IllegalNameException extends RuntimeException {

	/**
	 * A variable referencing the illegal name.
	 */
	private String name;

	/**
	 * Return the illegal name involved in this illegal name exception.
	 */
	@Basic
	public String getName() {
		return name;
	}

	/**
	 * Initialize this new illegal name exception involving the
	 * given illegal name.
	 *
	 * @param	name
	 * 			The illegal name for the new illegal name exception.
	 * @post	The illegal name involved in the new illegal name exception
	 * 			is set to the given illegal name.
	 * 			| new.getName() == name
	 */
	public IllegalNameException(String name) {
		this.name = name;
	}

	/**
	 * Check whether the given name is a valid name for this Exception.
	 * @param 	name
	 * 			The name to check.
	 * @return	True if and only if the name is effective.
	 * 			| result == (name != null)
	 */
	public static boolean isValidName(String name) {
		return name != null;
	}

}
