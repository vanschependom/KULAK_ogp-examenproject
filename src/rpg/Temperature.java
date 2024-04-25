package rpg;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing the temperature.
 *
 * @invar 	The temperature must always be valid.
 * 			| isValidTemperature(getHotness(), getColdness())
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
	private static long UPPERBOUND = 10000;

	/**********************************************************
	 * Constructors
	 **********************************************************/

	public Temperature(long hotness, long coldness) {
		if (isValidTemperature(hotness, coldness)) {
			// set to the provided temperature
			setHotness(hotness);
			setColdness(coldness);
		} else {
			// set to the default temperature
			setHotness(20);
			setColdness(0);
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
	 * @post	If the hotness is positive, the hotness is set to the given hotness;
	 * 			if not, the hotness is set to zero.
	 */
	@Model
	private void setHotness(long hotness) {
		if (hotness > 0) {
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

	@Model
	private void setColdness(long coldness) {
		if (coldness > 0) {
			this.coldness = coldness;
		}
	}

	protected void heat(long amount) {
		if (amount > 0) {
			if (hotness == 0) {
				coldness -= amount;
				if (coldness < 0) {
					hotness = -coldness;
					coldness = 0;
				}
			} else {
				if (amount > UPPERBOUND - hotness) {
					hotness = UPPERBOUND;
				} else {
					hotness += amount;
				}
			}
		}
	}

	protected void cool(long amount) {
		if (amount > 0) {
			if (coldness == 0) {
				hotness -= amount;
				if (hotness < 0) {
					coldness = -hotness;
					hotness = 0;
				}
			} else {
				if (amount > UPPERBOUND - coldness) {
					coldness = UPPERBOUND;
				} else {
					coldness += amount;
				}
			}
		}
	}

	/**
	 * A method for getting both the hotness and the coldness of the temperature.
	 * @return	An array containing the hotness and the coldness of the temperature.
	 * 			| result == new long[]{getHotness(), getColdness()}
	 */
	public long[] getTemperature() {
		return new long[] {hotness, coldness};
	}

	public long difference(Temperature other) {
		long currentTemp = -coldness + hotness;
		long otherTemp = -other.getColdness() + other.getHotness();
		return currentTemp - otherTemp;
	}

}
