package rpg.alchemy;

public class Transmogrifier extends Device{

	/**********************************************************
	 * CONSTRUCTOR
	 **********************************************************/

	/**
	 * A constructor for a transmogrifier device.
	 *
	 * @param 	laboratory
	 * 			The laboratory in which this device will be positioned
	 * @effect	A device with given laboratory
	 * 			and a maximum number of ingredients equal to 1 is created
	 * 			| super(laboratory, 1)
	 */
	@Raw
	public Transmogrifier(Laboratory laboratory) {
		super(laboratory, 1);
	}



	/**********************************************************
	 * OPERATION EXECUTION
	 **********************************************************/

	@Override
	public void executeOperation() {
		//
	}

}
