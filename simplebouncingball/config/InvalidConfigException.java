package simplebouncingball.config;

/**
 * A runtime exception thrown when configuration data is missing
 * 
 * @author tajahem
 *
 */
@SuppressWarnings("serial")
public class InvalidConfigException extends RuntimeException {

	String details = "";

	public void addDetail(String s) {
		details += s;
	}

	@Override
	public String getMessage() {
		return details;
	}

}
