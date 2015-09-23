package simplebouncingball.config;

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
