package rpg.alchemy;
import be.kuleuven.cs.som.annotate.*;

import static java.lang.Math.abs;
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

	/**********************************************************
	 * CONSTANTS
	 **********************************************************/

	/**
	 * A variable referencing the upperbound for both the hotness
	 * and the coldness; this will not change during the program.
	 */
	private static final long UPPERBOUND = 10000;

	/**
	 * Return the upper bound of temperature.
	 */
	@Immutable @Basic @Model
	protected static long getUpperbound() {
		return UPPERBOUND;
	}

	/**
	 * A variable referencing the standard hotness.
	 */
	private static final long STANDARD_HOTNESS = 20;

	/**
	 * A variable referencing the standard coldness.
	 */
	private static final long STANDARD_COLDNESS = 0;



	/**********************************************************
	 * CONSTRUCTORS
	 **********************************************************/

	/**
	 * A constructor for creating a new Temperature object with
	 * given hotness and coldness.
	 *
	 * @param 	hotness
	 * 			The hotness to be set.
	 * @param 	coldness
	 * 			The coldness to be set.
	 *
	 * @effect	If the given temperature is valid, the hotness and coldness
	 * 			are set to the given hotness and coldness.
	 * 			| if (isValidTemperature(coldness, hotness))
	 * 			| 	then setHotness(hotness) && setColdness(coldness)
	 * @effect	If the given temperature is not valid, the hotness and coldness
	 * 			are set to the standard values.
	 * 			| if (!isValidTemperature(coldness, hotness))
	 * 			| 	then setHotness(STANDARD_HOTNESS) && setColdness(STANDARD_COLDNESS)
	 */
	@Raw
	public Temperature(long coldness, long hotness) {
		super();
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
	 * A basic constructor for creating a new Temperature object with
	 * the standard hotness and coldness.
	 *
	 * @effect	A new temperature is created with the standard hotness and coldness.
	 * 			| this(STANDARD_COLDNESS, STANDARD_HOTNESS)
	 */
	@Raw
	public Temperature(){
		this(STANDARD_COLDNESS, STANDARD_HOTNESS);
	}

	/**
	 * A constructor for creating a new Temperature object with
	 * the given list of coldness and hotness.
	 *
	 * @param 	temperature
	 * 			A long array contains two values that represent the temperature.
	 *
	 * @effect	A new temperature is created with the given hotness and coldness.
	 * 			| this(temperature[0], temperature[1])
	 */
	public Temperature(long[] temperature) {
		this(temperature[0], temperature[1]);
	}



	/**********************************************************
	 * HOTNESS AND COLDNESS - total programming
	 **********************************************************/

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
	 * A method for getting the standard hotness.
	 */
	@Basic @Immutable
	public static long getStandardHotness() {
		return STANDARD_HOTNESS;
	}

	/**
	 * A setter for the hotness of the temperature.
	 *
	 * @param 	hotness
	 * 			The new hotness to be set.
	 *
	 * @post	If the hotness is bigger then the upperbound then the hotness is set to the upperbound.
	 * 			| if (hotness > UPPERBOUND)
	 * 			| 	then new.getHotness() == UPPERBOUND
	 * @post	If the hotness is positive and lower than the upperbound, the hotness is set to the given hotness.
	 * 			| if (hotness >= 0 && hotness <= UPPERBOUND)
	 * 			| 	then new.getHotness() == hotness
	 */
	@Model
	private void setHotness(long hotness) {
		if (hotness >= UPPERBOUND) {
			this.hotness = UPPERBOUND;
		} else if (hotness >= 0) {
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
	 * A method for getting the standard coldness.
	 */
	@Basic @Immutable
	public static long getStandardColdness() {
		return STANDARD_COLDNESS;
	}

	/**
	 * A setter for the coldness of the temperature.
	 *
	 * @param 	coldness
	 * 			The new coldness to be set.
	 *
	 * @post	If the coldness is bigger the upperbound then the coldness is set to the upperbound.
	 * 			| if (coldness >= UPPERBOUND)
	 * 			| 	then new.getColdness() == UPPERBOUND
	 *
	 * @post	If the coldness is positive and lower than the upperbound, the coldness is set to the given coldness.
	 * 			| if (coldness >= 0 && coldness < UPPERBOUND)
	 * 			| 	then new.getColdness() == coldness
	 */
	@Model @Raw
	private void setColdness(long coldness) {
		if (coldness >= UPPERBOUND) {
			this.coldness = UPPERBOUND;
		} else if (coldness >= 0) {
			this.coldness = coldness;
		}
	}

	/**
	 * A method for heating the temperature.
	 *
	 * @param 	amount
	 * 			The amount of heat to be added.
	 *
	 * @post	If the amount is positive and the current hotness, increased with the given amount,
	 * 			doesn't succeed the maximum value and the coldness is equal to zero,
	 * 			the temperature is increased with the given amount.
	 * 			| if (0 < amount && getHotness() + amount <= UPPERBOUND && getColdness() == 0)
	 * 			| 	then new.getHotness() == getHotness() + amount
	 * @post	If the amount is positive and the current hotness, increased with the given amount,
	 * 			does succeed the maximum value and the coldness is equal to zero,
	 * 			the hotness is set to the maximum value.
	 * 			| if (0 < amount && getHotness() + amount > UPPERBOUND && getColdness == 0)
	 * 			| 	then new.getHotness() == UPPERBOUND
	 * @post	If the amount is positive, and the current coldness is not zero and the current coldness,
	 * 			decreased with the given amount isn't negative, the coldness is decreased with the given amount.
	 * 			| if (0 < amount && getColdness() != 0 && getColdness() - amount >= 0)
	 * 			| 	then new.getColdness() == getColdness() - amount
	 * @post	If the amount is positive and the current coldness is not zero and the current coldness,
	 * 			decreased with the given amount is negative, the coldness is set to zero and the hotness
	 * 			is set to the difference.
	 * 			| if (0 < amount && getColdness() != 0 && getColdness() - amount < 0)
	 * 			| 	then new.getColdness() == 0 && new.getHotness() == -getColdness() + amount
	 * @post	If the amount is positive and the current coldness is not zero and the current coldness,
	 * 			decreased with the given amount is negative and the difference is bigger than the upperbound
	 * 			then the coldness is set to zero and the hotness to the upperbound.
	 * 			| if (0 < amount && getColdness() != 0 && getColdness() - amount < 0 && getColdness() - amount > UPPERBOUND)
	 * 	 		| 	then new.getColdness() == 0 && new.getHotness() == UPPERBOUND
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
					setHotness(min(-difference, UPPERBOUND));
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
	 *
	 * @post	If the amount is positive and the current coldness, increased with the given amount,
	 * 			doesn't succeed the maximum value and the hotness is zero,
	 * 			the coldness is increased with the given amount.
	 * 			| if (0 < amount && getColdness() + amount <= UPPERBOUND && getHotness() == 0)
	 * 			| 	then new.getColdness() == getColdness() + amount
	 * @post	If the amount is positive and the current coldness, increased with the given amount
	 * 			does succeed the maximum value and the hotness the coldness is set to the maximum value.
	 * 			| if (0 < amount && getColdness() + amount > UPPERBOUND && getHotness() == 0)
	 * 			| 	then new.getColdness() == UPPERBOUND
	 * @post	If the amount is positive and the current hotness is not zero
	 * 			and the current hotness, decreased with the given amount
	 * 			isn't negative, the hotness is decreased with the given amount.
	 * 			| if (0 < amount && getHotness() != 0 && getHotness() - amount >= 0)
	 * 			| 	then new.getHotness() == getHotness() - amount
	 * @post	If the amount is positive and the current hotness is not zero
	 * 			and the current hotness, decreased with the given amount
	 * 			is negative, the hotness is set to zero and the coldness is set to the difference.
	 * 			| if (0 < amount && getHotness() != 0 && getHotness() - amount < 0)
	 * 			| 	then new.getHotness() == 0 && new.getColdness() == -getHotness() + amount
	 * @post	If the amount is positive and the current hotness is not zero and the current hotness,
	 * 			decreased with the given amount is negative and the difference is bigger then the upperbound
	 * 			then the hotness is set to zero and the coldness is set to the upperbound.
	 * 			| if (0 < amount && getHotness() != 0 && getHotness() - amount < 0 && getHotness() - amount > 10000)
	 * 	 		| 	then new.getHotness() == 0 && new.getColdness() == UPPERBOUND
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
	 * A method for checking if one temperature is hotter than the other.
	 *
	 * @param 	other
	 * 			The other temperature (an array) to compare to.
	 *
	 * @return	True if and only if the other is a null pointer, or
	 * 			the other hotness is smaller than the own hotness, or
	 * 			the other coldness is bigger than the own coldness.
	 * 			| result ==
	 * 			|	( other == null	|| getHotness() > other[1]
	 * 			|					|| getColdness() < other[0] )
	 */
	public boolean isHotterThan(long[] other) {
		return (other == null || getHotness() > other[1] || getColdness() < other[0]);
	}

	/**
	 * A method for checking if one temperature is colder than the other.
	 *
	 * @param 	other
	 * 			The other temperature (an array) to compare to.
	 *
	 * @return	True if and only if the other is a null pointer, or
	 * 			the other coldness is smaller than the own coldness, or
	 * 			the other hotness is bigger than the own hotness.
	 * 			| result ==
	 * 			|	( other == null	|| getColdness() > other[0]
	 * 			|					|| getHotness() < other[1] )
	 */
	public boolean isColderThan(long[] other) {
		return (other == null || getColdness() > other[0] || getHotness() < other[1]);
	}

	/**
	 * Return the absolute difference between temperatures.
	 *
	 * @param 	other
	 * 			The other temperature to calculate the difference of.
	 *
	 * @return	The sum of the absolute value of the difference of the coldness
	 * 			and the absolute value of the difference of the hotness.
	 * 			| result == abs(getColdness() - other[0]) + abs(getHotness() - other[1])
	 */
	public long difference(long[] other) {
		return abs(getColdness() - other[0]) + abs(getHotness() - other[1]);
	}

	/**
	 * A method for getting both the hotness and the coldness of the temperature.
	 *
	 * @return	An array containing the coldness and the hotness of the temperature.
	 * 			| result[0] == getColdness() && result[1] == getHotness()
	 */
	public long[] getTemperature() {
		return new long[] {getColdness(), getHotness()};
	}

	/**
	 * A method for checking whether a given temperature is valid.
	 *
	 * @param 	hotness
	 * 			The hotness of the temperature.
	 * @param 	coldness
	 * 			The coldness of the temperature.
	 *
	 * @return	False if the hotness or the coldness is negative, if they are both not zero,
	 * 			or if the upperbound is exceeded; true otherwise.
	 * 			| result == (hotness >= 0 && hotness <= UPPERBOUND) &&
	 * 			|			(coldness >= 0 && coldness <= UPPERBOUND) &&
	 * 			|			!(hotness != 0 && coldness != 0)
	 */
	@Raw
	public static boolean isValidTemperature(long coldness, long hotness) {
		return (hotness >= 0 && hotness <= UPPERBOUND) &&
				(coldness >= 0 && coldness <= UPPERBOUND) &&
				!(hotness != 0 && coldness != 0);
	}



	/**********************************************************
	 * EQUALS
	 *********************************************************/

	/**
	 * A method for checking whether two temperatures are equal.
	 *
	 * @param 	other
	 * 			The other temperature to compare to.
	 *
	 * @return	True if and only if the other temperature is effective
	 * 			and the hotness and coldness are equal.
	 * 			| result == (other != null && getHotness() == other.getHotness()
	 * 			| 						   && getColdness() == other.getColdness())
	 */
	public boolean equals(Temperature other) {
		return other != null && getHotness() == other.getHotness() && getColdness() == other.getColdness();
	}



	/**********************************************************
	 * HELP METHODS
	 *********************************************************/

	/**
	 * A method to add two temperatures objects together.
	 *
	 * @param 	t1
	 * 			The first temperature that gets added together.
	 * @param	t2
	 * 			The other temperature that gets added together.
	 *
	 * @return	If the difference between the sum of the hotness and the sum of the coldness
	 * 			is positive then return a new temperature with coldness zero and hotness the minimum of the difference and the upperbound.
	 * 			| if (t1.getHotness() + t2.getHotness() > t1.getColdness() + t2.getColdness())
	 * 			| 	then result.equals( new Temperature(0, min(t1.getHotness() + t2.getHotness() - t1.getColdness() + t2.getColdness(), UPPERBOUND)) )
	 * @return	If the difference between the sum of the hotness and the sum of the coldness
	 * 			is negative then return a new temperature with coldness the minimum of the absolute value of the difference and the upperbound
	 * 			and with hotness zero.
	 * 			| if (t1.getHotness() + t2.getHotness() < t1.getColdness() + t2.getColdness())
	 * 			| 	then result.equals( new Temperature(min(abs(t1.getHotness() + t2.getHotness() - t1.getColdness() + t2.getColdness()), UPPERBOUND), 0) )
	 * @return	If the difference between the sum of the hotness and the sum of the coldness is equal to zero
	 * 			then return a new temperature with both hotness and coldness equal to zero.
	 * 			| if (t1.getHotness() + t2.getHotness() == t1.getColdness() + t2.getColdness())
	 * 			| 	then result.equals( new Temperature(0, 0) )
	 */
	@Model
	protected static Temperature add(Temperature t1, Temperature t2) {
		long cold = t1.getColdness() + t2.getColdness();
		long hot = t1.getHotness() + t2.getHotness();
		long difference = hot - cold;
		if (difference > 0) {
			return new Temperature(0, Math.min(difference,UPPERBOUND));
		} else if (difference < 0) {
			return  new Temperature(Math.min(Math.abs(difference),UPPERBOUND),0);
		} else {
			return new Temperature(0, 0);
		}
	}

	/**
	 * A method to multiply a temperature object with a given factor delta.
	 *
	 * @effect	The coldness of the new temperature is set to the current coldness multiplied with a factor delta,
	 * 			if delta is greater than 0.
	 * 			| if (delta > 0)
	 * 			| 	new.getColdness = setColdness(delta * getColdness())
	 * @effect	The hotness of the new temperature is set to the current hotness multiplied with a factor delta,
	 * 			if delta is greater than 0.
	 * 			| if (delta > 0)
	 * 			| 	new.getHotness = setHotness(delta * getHotness())
	 */
	@Model
	protected void mul(double delta) {
		if (delta > 0) {
			setColdness((long) (delta * getColdness()));
			setHotness((long) (delta * getHotness()));
		}
	}



	/**********************************************************
	 * DESTRUCTOR
	 **********************************************************/

	/**
	 * A variable for keeping track of whether the temperature is terminated.
	 */
	private boolean isTerminated = false;

	/**
	 * A method to check whether the temperature is terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return isTerminated;
	}

	/**
	 * A method to terminate the temperature.
	 *
	 * @post    The temperature is terminated.
	 *          | new.isTerminated()
	 */
	protected void terminate() {
		isTerminated = true;
	}

}
