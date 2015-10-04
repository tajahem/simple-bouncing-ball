package simplebouncingball.config;

public enum ConfigOption {

	WINDOW_SIZE("WINDOW_SIZE", "500x500", "# Window Size: format WIDTHxHEIGHT"),
	BALL_SIZE("BALL_SIZE", "50","# Window Size: format WIDTHxHEIGHT"),
	MAX_VELOCITY("MAX_VELOCITY", "10", "# Window Size: format WIDTHxHEIGHT"),
	BG_COLOR("BALL_COLOR", "50,150,150", "# Colors"),
	MENU_BG_COLOR("MENU_COLOR", "50,150,150", "# Colors"),
	TEXT_COLOR("TEXT_COLOR", "255,255,255", "# Colors"),
	HIGHLIGHT_COLOR("HIGHLIGHT", "255,255,255,10", "# Colors"),
	SHADOW_COLOR("SHADOW", "50,50,50,50", "# Colors"),
	FONT_STYLE("FONT_STYLE", "\"Sans Serif\"", "# Font"),
	FONT_SIZE("FONT_SIZE", "20", "# Font");

	public String key;
	
	// default value and section for each object in the configuration file
	public String defaultValue;
	public String section;

	ConfigOption(String k, String d, String s) {
		key = k;
		defaultValue = d;
		section = s;
	}

}
