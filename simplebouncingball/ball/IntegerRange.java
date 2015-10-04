package simplebouncingball.ball;

/**
 * 
 * IntegerRange is used to ensure that an integer value is between certain values
 * 
 * @author tajahem
 *
 */
public class IntegerRange {

	private final int max, min;

	public IntegerRange(int max, int min) {
		this.max = max;
		this.min = min;
	}

	/**
	 * Checks if a value plus a modifier is within the range
	 * 
	 * @param value
	 * @param modifier
	 * @return
	 */
	public boolean isInRange(int value, int modifier) {
		if (value + modifier >= max || value + modifier <= min) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if a value is within the range
	 * 
	 * @param value
	 * @return
	 */
	public boolean isInRange(int value) {
		if (value >= max || value <= min) {
			return false;
		}
		return true;
	}

}
