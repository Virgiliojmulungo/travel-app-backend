package ja.esta.trips.backend.exceptions;

public class IncorrectUrlFormatException extends Exception {
     
	private static final long serialVersionUID = 1L;

	public IncorrectUrlFormatException(String message) {
		super(message);
	}
}
