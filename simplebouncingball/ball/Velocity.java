package simplebouncingball.ball;

public class Velocity {

	private final IntegerRange rangeChecker;
	private int x = 0, y = 0;

	public Velocity(int max) {
		rangeChecker = new IntegerRange(max, -max);
	}

	public void modifyX(int amount) {
		if (rangeChecker.isInRange(x, amount)) {
			x += amount;
		}
	}

	public void modifyY(int amount) {
		if (rangeChecker.isInRange(y, amount)) {
			y += amount;
		}
	}

	public void reverseX() {
		x = -x;
	}

	public void reverseY() {
		y = -y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
