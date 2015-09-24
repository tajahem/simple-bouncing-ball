package simplebouncingball.ball;

import java.awt.Color;

public class ColorManager {

	private final Range rangeChecker = new Range(255, 0);
	private int[] change = new int[3];
	public int[] color = new int[3];

	public ColorManager() {
		change[0] = -1;
		change[1] = 1;
		change[2] = 1;
		color[0] = 255;
		color[1] = 150;
		color[2] = 0;
	}

	private void update() {
		for (int n = 0; n < 3; n++) {
			if (!rangeChecker.isInRange(color[n], change[n])) {
				change[n] = -change[n];
			}
			color[n] += change[n];
		}
	}

	public Color getColor() {
		update();
		return new Color(color[0], color[1], color[2]);
	}

}
