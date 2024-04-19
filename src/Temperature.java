public class Temperature {

	private static long UPPERBOUND = 10000;

	private long hotness;
	private long coldness;

	public Temperature(long hotness, long coldness) {
		if (hotness < 0 || coldness < 0) {
			throw new IllegalArgumentException("Temperature cannot be negative");
		}
		if (hotness > UPPERBOUND || coldness > UPPERBOUND) {
			throw new IllegalArgumentException("Temperature cannot be higher than " + UPPERBOUND);
		}
		if (hotness != 0 && coldness != 0) {
			throw new IllegalArgumentException("Temperature cannot be both hot and cold");
		}
		this.hotness = hotness;
		this.coldness = coldness;
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
		} else {
			throw new IllegalArgumentException("Temperature cannot be negative");
		}
	}

	protected void cool(int amount) {
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
		} else {
			throw new IllegalArgumentException("Temperature cannot be negative");
		}
	}

	public long getHotness() {
		return hotness;
	}

	public long getColdness() {
		return coldness;
	}

	public long[] getTemperature() {
		return new long[] {hotness, coldness};
	}

	public long difference(Temperature other) {
		long currentTemp = -coldness + hotness;
		long otherTemp = -other.getColdness() + other.getHotness();
		return currentTemp - otherTemp;
	}

}
