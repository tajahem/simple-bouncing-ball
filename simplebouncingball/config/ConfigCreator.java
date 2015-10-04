package simplebouncingball.config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Creates a default configuration file
 * 
 * @author tajahem
 *
 */
public class ConfigCreator {

	private final String intro = "# Simple Bouncing Ball Configuration \n\n";

	public ConfigCreator() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(Config.configLocation));
			writer.write(intro);
			String section = "";
			for (ConfigOption c : ConfigOption.values()) {
				if (c.section != section) {
					writer.write(c.section + "\n");
					section = c.section;
				}
				writer.write(c.key + "=" + c.defaultValue + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Unable to create configuration");
		}
	}

}
