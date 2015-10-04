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

	public int width, height;
	public int ballSize;
	public int maxVelocity;
	public int textSize;
	public Color background;
	public Color menuColor;
	public Color textColor;
	public Color highlight;
	public Color shadow;
	public String font;

	public Config() {
		Map<String, String> settings = null;
		try {
			settings = readConfigFile();
		} catch (IOException e) {
			// attempt to create a new configuration file once
			new ConfigCreator();
			try {
				settings = readConfigFile();
			} catch (IOException f) {
				// things really did not go well
				System.err.println("Configuration error");
				f.printStackTrace();
				System.exit(0);
			}
		}
		validate(settings);
		setValues(settings);
	}

	private Map<String, String> readConfigFile() throws IOException {
		Map<String, String> settings = new HashMap<>();
		BufferedReader reader = new BufferedReader(new FileReader(new File(configLocation)));

		String line = reader.readLine();
		while (line != null) {
			// ignore anything without an assignment operator
			if (line.contains("=")) {
				int splitPoint = line.indexOf('=');
				try {
					settings.put(line.substring(0, splitPoint), line.substring(splitPoint + 1));
				} catch (StringIndexOutOfBoundsException s) {
				} // prevent crashing in case someone writes a line of '='
			}
			line = reader.readLine();
		}
		reader.close();

		return settings;
	}

	private void setValues(Map<String, String> settings) {
		parseSize(settings.get(ConfigOption.WINDOW_SIZE.key));
		ballSize = Integer.parseInt(settings.get(ConfigOption.BALL_SIZE.key));
		maxVelocity = Integer.parseInt(settings.get(ConfigOption.MAX_VELOCITY.key));
		background = parseColor(settings.get(ConfigOption.BG_COLOR.key));
		menuColor = parseColor(settings.get(ConfigOption.MENU_BG_COLOR.key));
		textColor = parseColor(settings.get(ConfigOption.TEXT_COLOR.key));
		highlight = parseColor(settings.get(ConfigOption.HIGHLIGHT_COLOR.key));
		shadow = parseColor(settings.get(ConfigOption.SHADOW_COLOR.key));
		font = settings.get(ConfigOption.FONT_STYLE.key);
		textSize = Integer.parseInt(settings.get(ConfigOption.FONT_SIZE.key));
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
		int a = 255;
		if (values.length > 3) {
			a = Integer.parseInt(values[3].trim());
		}
		return new Color(r, g, b, a);
	}

	// Make sure all of the keys exist
	private void validate(Map<String, String> settings) {
		boolean valid = true;
		InvalidConfigException ice = new InvalidConfigException();
		for (ConfigOption s : ConfigOption.values()) {
			if (!settings.containsKey(s.key) || settings.get(s.key).isEmpty()) {
				valid = false;
				ice.addDetail("\n  " + s + " value missing or invalid in configuration");
			}
		}
		if (!valid) {
			throw ice;
		}
	}

}
