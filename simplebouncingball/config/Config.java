package simplebouncingball.config;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Fetches and stores configuration information from the config.ini file. Throws
 * a runtime exception if all of the keys are not present, and throws other
 * exceptions if any of the data fails to parse correctly.
 * 
 * @author tajahem
 *
 */
public class Config {

	public static final String configLocation = "config.ini";

	private final String[] keys = { "Window_Size", "Ball_Size", "Max_Velocity",
			"BG_Color", "Ball_Color", "Menu_BG_Color", "Text_Color", "Font",
			"Text_Size" };

	public int width, height;
	public int ballSize;
	public int maxVelocity;
	public int textSize;
	public Color background;
	public Color initialBallColor;
	public Color menuColor;
	public Color textColor;
	public String font;

	public Config() {
		Map<String, String> settings = readConfigFile();
		validate(settings);
		setValues(settings);
	}

	private Map<String, String> readConfigFile() {
		Map<String, String> settings = new HashMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					configLocation)));

			String line = reader.readLine();
			while (line != null) {
				// ignore anything without an assignment operator
				if (line.contains("=")) {
					int splitPoint = line.indexOf('=');
					try {
						settings.put(line.substring(0, splitPoint),
								line.substring(splitPoint + 1));
					} catch (StringIndexOutOfBoundsException s) {
					}// prevent crashing in case someone writes a line of '='
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("ERROR: config.ini corrupted or missing");
			System.exit(0);
		}
		return settings;
	}

	// wondering if this couldn't be condensed and rolled into validation
	private void setValues(Map<String, String> settings) {
		parseSize(settings.get(keys[0]));
		ballSize = Integer.parseInt(settings.get(keys[1]));
		maxVelocity = Integer.parseInt(settings.get(keys[2]));
		background = parseColor(settings.get(keys[3]));
		initialBallColor = parseColor(settings.get(keys[4]));
		menuColor = parseColor(settings.get(keys[5]));
		textColor = parseColor(settings.get(keys[6]));
		font = settings.get(keys[7]);
		textSize = Integer.parseInt(settings.get(keys[8]));
	}

	private void parseSize(String s) {
		String[] sizes = s.split("x");
		width = Integer.parseInt(sizes[0].trim());
		height = Integer.parseInt(sizes[1].trim());
	}

	private Color parseColor(String s) {
		String[] values = s.split(",");
		int r = Integer.parseInt(values[0].trim());
		int g = Integer.parseInt(values[1].trim());
		int b = Integer.parseInt(values[2].trim());
		return new Color(r, g, b);
	}

	// Make sure all of the keys exist
	private void validate(Map<String, String> settings) {
		boolean valid = true;
		InvalidConfigException ice = new InvalidConfigException();
		for (String s : keys) {
			if (!settings.containsKey(s) || settings.get(s).isEmpty()) {
				valid = false;
				ice.addDetail("\n  " + s
						+ " value missing or invalid in configuration");
			}
		}
		if (!valid) {
			throw ice;
		}
	}
}
