package rpg;
import be.kuleuven.cs.som.annotate.*;
import static java.lang.Math.min;

/**
 * A class representing the temperature.
 *
 * @invar 	The temperature must always be valid.
 * 			| isValidTemperature(getColdness(), getHotness())
 *
 * @note	We use TOTAL PROGRAMMING for this class.
 *
 * @author	Vincent Van Schependom
 * @author 	Arne Claerhout
 * @author 	Flor De Meulemeester
 * @version 1.0
 *
 */
public class Temperature {

	/**
	 * A variable referencing the upperbound for both the hotness
	 * and the coldness; this will not change during the program.
	 */
	private static final long UPPERBOUND = 10000;

	/**
	 * A variable referencing the standard hotness.
	 */
	private static final long STANDARD_HOTNESS = 20;

	/**
	 * A variable referencing the standard coldness.
	 */
	private static final long STANDARD_COLDNESS = 0;

	/**********************************************************
	 * Constructors
	 **********************************************************/

	/**
	 * A constructor for creating a new Temperature object with
	 * given hotness and coldness.
	 *
	 * @param 	hotness
	 * 			The hotness to be set.
	 * @param 	coldness
	 * 			The coldness to be set.
	 * @effect	If the given temperature is valid, the hotness and coldness
	 * 			are set to the given hotness and coldness, otherwise, the hotness
	 * 			and coldness are set to the standard values.
	 * 			| if (isValidTemperature(hotness, coldness))
	 * 			| then setHotness(hotness) && setColdness(coldness)
	 *			| else setHotness(STANDARD_HOTNESS) && setColdness(STANDARD_COLDNESS)
	 */
	@Raw
	public Temperature(long coldness, long hotness) {
		if (isValidTemperature(coldness, hotness)) {
			// set to the provided temperature
			setHotness(hotness);
			setColdness(coldness);
		} else {
			// set to the default temperature
			setHotness(STANDARD_HOTNESS);
			setColdness(STANDARD_COLDNESS);
		}
	}

	/**
	 * A variable referencing the hotness of the temperature.
	 */
	private long hotness;

	/**
	 * A getter for the hotness of the temperature.
	 */
	@Basic
	public long getHotness() {
		return hotness;
	}

	/**
	 * A setter for the hotness of the temperature.
	 * @param 	hotness
	 * 			The new hotness to be set
	 * @post	If the hotness is positive, the hotness is set to the given hotness.
	 * 			| if (hotness >= 0)
	 * 			| then new.getHotness() == hotness
	 */
	@Model
	private void setHotness(long hotness) {
		if (hotness >= 0) {
			this.hotness = hotness;
		}
	}

	/**
	 * A variable referencing the coldness of the temperature.
	 */
	private long coldness;

	/**
	 * A getter for the coldness of the temperature.
	 */
	@Basic
	public long getColdness() {
		return coldness;
	}

	/**
	 * A setter for the coldness of the temperature.
	 * @param 	coldness
	 * 			The new coldness to be set
	 * @post	If the coldness is positive, the coldness is set to the given coldness
	 * 			| if (coldness >= 0)
	 * 			| then new.getColdness() == coldness
	 */
	@Model
	private void setColdness(long coldness) {
		if (coldness >= 0) {
			this.coldness = coldness;
		}
	}

	/**
	 * A method for heating the temperature.
	 *
	 * @param 	amount
	 * 			The amount of heat to be added.
	 * @post	If the amount is positive and the current hotness, increased with the given amount,
	 * 			doesn't succeed the maximum value and the coldness is equal to zero,
	 * 			the temperature is increased with the given amount.
	 * 			| if (0 < amount && getHotness() + amount <= UPPERBOUND && getColdness() == 0)
	 * 			| then new.getHotness() == getHotness() + amount
	 * @post	If the amount is positive and the current hotness, increased with the given amount,
	 * 			does succeed the maximum value and the coldness is equal to zero,
	 * 			the hotness is set to the maximum value.
	 * 			| if (0 < amount && getHotness() + amount > UPPERBOUND && getColdness == 0)
	 * 			| then new.getHotness() == UPPERBOUND
	 * @post	If the amount is positive, and the current coldness is not zero and the current coldness,
	 * 			decreased with the given amount isn't negative, the coldness is decreased with the given amount.
	 * 			| if (0 < amount && getColdness() != 0 && getColdness() - amount >= 0)
	 * 			| then new.getColdness() == getColdness() - amount
	 * @post	If the amount is positive and the current coldness is not zero and the current coldness,
	 * 			decreased with the given amount is negative, the coldness is set to zero and the hotness
	 * 			is set to the difference.
	 * 			| if (0 < amount && getColdness() != 0 && getColdness() - amount < 0)
	 * 			| then new.getColdness() == 0 && new.getHotness() == -getColdness() + amount
	 * @post	If the amount is positive and the current coldness is not zero and the current coldness,
	 * 			decreased with the given amount is negative and the difference is bigger than the upperbound
	 * 			then the coldness is set to zero and the hotness to the upperbound.
	 * 			| if (0 < amount && getColdness() != 0 && getColdness() - amount < 0 && getColdness() - amount > UPPERBOUND)
	 * 	 		| then new.getColdness() == 0 && new.getHotness() == UPPERBOUND
	 *
	 * @note 	Because of the class invariants at least one, coldness or hotness, is always equal to zero.
	 */
	protected void heat(long amount) {
		if (0 < amount) {
			if (getHotness() == 0) {
				long difference = getColdness() - amount;
				if (difference > 0) {
					setColdness(difference);
				} else {
					setColdness(0);
					setHotness(min(-difference,UPPERBOUND));
				}
			} else {
				if (amount < UPPERBOUND - getHotness()) {
					setHotness(getHotness() + amount);
				} else {
					setHotness(UPPERBOUND);
				}
			}
		}
		// negative amount, do nothing
	}

	/**
	 * A method for cooling the temperature.
	 *
	 * @param 	amount
	 * 			The amount of coolness to be added.
	 * @post	If the amount is positive and the current coldness, increased with the given amount,
	 * 			doesn't succeed the maximum value and the hotness is zero,
	 * 			the coldness is increased with the given amount.
	 * 			| if (0 < amount && getColdness() + amount <= UPPERBOUND && getHotness() == 0)
	 * 			| then new.getColdness() == getColdness() + amount
	 * @post	If the amount is positive and the current coldness, increased with the given amount
	 * 			does succeed the maximum value and the hotness the coldness is set to the maximum value.
	 * 			| if (0 < amount && getColdness() + amount > UPPERBOUND && getHotness() == 0)
	 * 			| then new.getColdness() == UPPERBOUND
	 * @post	If the amount is positive and the current hotness is not zero
	 * 			and the current hotness, decreased with the given amount
	 * 			isn't negative, the hotness is decreased with the given amount.
	 * 			| if (0 < amount && getHotness() != 0 && getHotness() - amount >= 0)
	 * 			| then new.getHotness() == getHotness() - amount
	 * @post	If the amount is positive and the current hotness is not zero
	 * 			and the current hotness, decreased with the given amount
	 * 			is negative, the hotness is set to zero and the coldness is set to the difference.
	 * 			| if (0 < amount && getHotness() != 0 && getHotness() - amount < 0)
	 * 			| then new.getHotness() == 0 && new.getColdness() == -getHotness() + amount
	 * @post	If the amount is positive and the current hotness is not zero and the current hotness,
	 * 			decreased with the given amount is negative and the difference is bigger then the upperbound
	 * 			then the hotness is set to zero and the coldness is set to the upperbound.
	 * 			| if (0 < amount && getHotness() != 0 && getHotness() - amount < 0 && getHotness() - amount > 10000)
	 * 	 		| then new.getHotness() == 0 && new.getColdness() == UPPERBOUND
	 *
	 * @note 	Because of the class invariants at least one, coldness or hotness, is always equal to zero.
	 */
	protected void cool(long amount) {
		if (0 < amount) {
			if (getColdness() == 0) {
				long difference = getHotness() - amount;
				if (difference > 0) {
					setHotness(difference);
				} else {
					setHotness(0);
					setColdness(min(-difference,UPPERBOUND));
				}
			} else {
				if (amount < UPPERBOUND - getColdness()) {
					setColdness(getColdness() + amount);
				} else {
					setColdness(UPPERBOUND);
				}
			}
		}
		// negative amount, do nothing
	}

	/**
	 * A method for getting both the hotness and the coldness of the temperature.
	 * @return	An array containing the coldness and the hotness of the temperature.
	 * 			| result == new long[]{getColdness(), getHotness()}
	 */
	public long[] getTemperature() {
		return new long[] {coldness, hotness};
	}

	/**
	 * A method for checking whether a given temperature is valid.
	 * @param 	hotness
	 * 			The hotness of the temperature
	 * @param 	coldness
	 * 			The coldness of the temperature
	 * @return	False if the hotness or the coldness is negative, if they are both not zero,
	 * 			or if the upperbound is exceeded; true otherwise.
	 * 			| result == (hotness >= 0 && hotness <= UPPERBOUND) &&
	 * 			|			(coldness >= 0 && coldness <= UPPERBOUND) &&
	 * 			|			!(hotness != 0 && coldness != 0)
	 */
	public static boolean isValidTemperature(long coldness, long hotness) {
		return (hotness >= 0 && hotness <= UPPERBOUND) &&
				(coldness >= 0 && coldness <= UPPERBOUND) &&
				!(hotness != 0 && coldness != 0);
	}

}
