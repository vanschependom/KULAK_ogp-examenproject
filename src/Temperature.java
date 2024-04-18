public class Temperature {

	private static int UPPERBOUND = 10000;

	private int hotness;
	private int coldness;

	public Temperature(int hotness, int coldness) {
		if (hotness < 0 || coldness < 0) {
			throw new IllegalArgumentException("Temperature cannot be negative");
		}
		if (hotness > UPPERBOUND || coldness > UPPERBOUND) {
			throw new IllegalArgumentException("Temperature cannot be higher than " + UPPERBOUND);
		}
		if (hotness > 0 && coldness != 0) {
			throw new IllegalArgumentException("Temperature cannot be both hot and cold");
		}
		if (coldness > 0 && hotness != 0) {
			throw new IllegalArgumentException("Temperature cannot be both hot and cold");
		}
		this.hotness = hotness;
		this.coldness = coldness;
	}


	public void heat(int amount) {
		if (amount > 0) {
			if (hotness == 0) {
				coldness -= amount;
				if (coldness < 0) {
					hotness = -coldness;
					coldness = 0;
				}
			} else {
				if (amount + hotness > UPPERBOUND) {
					hotness = UPPERBOUND;
				} else {
					hotness += amount;
				}
			}
		} else {
			throw new IllegalArgumentException("Temperature cannot be negative");
		}
	}

	public void cool(int amount) {
		if (amount > 0) {
			if (coldness == 0) {
				hotness -= amount;
				if (hotness < 0) {
					coldness = -hotness;
					hotness = 0;
				}
			} else {
				if (amount + coldness > UPPERBOUND) {
					coldness = UPPERBOUND;
				} else {
					coldness += amount;
				}
			}
		} else {
			throw new IllegalArgumentException("Temperature cannot be negative");
		}
	}

	public int getHotness() {
		return hotness;
	}

	public int getColdness() {
		return coldness;
	}

	public int[] getTemperature() {
		return new int[] {hotness, coldness};
	}

	public int difference(Temperature other) {
		int currentTemp = -coldness + hotness;
		int otherTemp = -other.getColdness() + other.getHotness();
		return currentTemp - otherTemp;
	}

}
