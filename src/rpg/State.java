package rpg;

import java.util.Arrays;

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

	/**
	 * A method to get the next state in the list of states.
	 *
	 * @return	The State which is next in the values of State.
	 * 			| State.values()[(Arrays.asList(State.values()).indexOf(this)+1)%State.values().length]
	 */
	public State getNext() {
		return State.values()[(Arrays.asList(State.values()).indexOf(this)+1)%State.values().length];
	}

}
