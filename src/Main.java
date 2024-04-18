public class Main {

	public static void main(String[] args) {
		Quantity hoeveelheid = new Quantity(1, LiquidUnit.BOTTLE);
		System.out.println(hoeveelheid);
		hoeveelheid.convertTo(LiquidUnit.DROP);
		System.out.println(hoeveelheid);

		Quantity hoeveelheid2 = new Quantity(1, PowderUnit.BOX);
		System.out.println(hoeveelheid2);
		hoeveelheid2.convertTo(PowderUnit.PINCH);
		System.out.println(hoeveelheid2);
	}

}
