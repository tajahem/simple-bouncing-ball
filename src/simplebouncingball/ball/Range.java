package simplebouncingball.ball;

public class Range {

	private final int max, min;

	public Range(int max, int min) {
		this.max = max;
		this.min = min;
	}

	public boolean isInRange(int value, int modifier) {
		if (value + modifier >= max || value + modifier <= min) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "min:" + min + " max:" + max;
	}

}
