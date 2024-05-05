package rpg;

/**
 * An enumeration introducing the different states an ingredient can have.
 *
 * @invar	Each state can only be liquid or powder.
 * 			| (this == LIQUID) || (this == POWDER)
 *
 * @author 	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author	Flor De Meulemeester
 *
 * @version 1.0
 */
public enum State {

	/**
	 * The possible states for an ingredient.
	 */
	LIQUID, POWDER;

}
